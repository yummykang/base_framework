package com.ehu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-03 10:29.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="mysqlEntityManagerFactory",
        transactionManagerRef="mysqlTransactionManager",
        basePackages= { "com.ehu.repository.primary" }) //设置Repository所在位置
public class PrimaryDataJpaDataSourceConfig {
}
