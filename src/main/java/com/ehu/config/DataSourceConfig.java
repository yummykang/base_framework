package com.ehu.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;


/**
 * 数据源配置，支持多数据源配置.
 *
 * @author demon
 * @since 2017-03-02 15:53.
 */
@Configuration
@EnableCaching
public class DataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @Qualifier("mysql")
    @Primary
    @ConfigurationProperties("app.datasource.mysql")
    public DataSource mysqlDataSource() {
        logger.info("------------------建立数据源bean:mysql---------------------");
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier("mysql2")
    @ConfigurationProperties("app.datasource.mysql2")
    public DataSource mysql2DataSource() {
        logger.info("------------------建立数据源bean:mysql2---------------------");
        return DataSourceBuilder.create().build();
    }


    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<String, String>(), null);
    }

    @Bean(name = "mysqlEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean mssqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(mysqlDataSource())
                .packages("com.ehu.entity.primary")
                .persistenceUnit("mysql")
                .build();
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        em.setJpaProperties(properties);
        return em;
    }

    @Bean(name = "mysql2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(mysql2DataSource())
                .packages("com.ehu.entity.another")
                .persistenceUnit("mysql2")
                .build();
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        em.setJpaProperties(properties);
        return em;
    }

    @Bean(name = "mysqlTransactionManager")
    @Primary
    PlatformTransactionManager mssqlTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(mssqlEntityManagerFactory(builder).getObject());
    }

    @Bean(name = "mysql2TransactionManager")
    @Primary
    PlatformTransactionManager mysqlTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(mysqlEntityManagerFactory(builder).getObject());
    }

    /***********************************redis相关设置--start*******************************************/


    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.redis")
    public RedisConnectionFactory jedisConnectionFactory() {
        logger.info("------------------建立数据源bean:redis---------------------");
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "redisKeyGen")
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean(name = "redisCache")
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // 设置默认过期时间为2分钟
        cacheManager.setDefaultExpiration(12000);
        return cacheManager;
    }

    /*********************************redis相关设置--end*********************************************/
}
