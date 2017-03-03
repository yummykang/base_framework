package com.ehu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-03 10:52.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "mysql2EntityManagerFactory",
        transactionManagerRef = "mysql2TransactionManager",
        basePackages = {"com.ehu.repository.another"}) //设置Repository所在位置
public class AnotherDataJpaDataSourceConfig {
}
