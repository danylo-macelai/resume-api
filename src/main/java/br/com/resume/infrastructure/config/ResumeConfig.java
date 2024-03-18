/*
 * MIT License
 *
 * Copyright (c) 2024.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package br.com.resume.infrastructure.config;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import javax.sql.DataSource;

import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

/**
 * <b>Description:</b> FIXME: Document this type <br>
 * <b>Project:</b> resume-api <br>
 *
 * @author Danylo
 * @date: 12 de mar. de 2024
 * @version $
 */
@Configuration
@EnableTransactionManagement
public class ResumeConfig {

    private static final String DATASOURCE_USERNAME   = "spring.datasource.username";
    private static final String DATASOURCE_PASSWORD   = "spring.datasource.password";
    private static final String DATASOURCE_URL        = "spring.datasource.url";
    private static final String DATASOURCE_DRIVER     = "spring.datasource.driver-class-name";
    private static final String DATASOURCE_TEST_QUERY = "spring.datasource.hikari.connection-test-query";

    private static final String HIBERNATE_DIALECT    = "spring.jpa.properties.hibernate.dialect";
    private static final String HIBERNATE_HBM2DDL    = "spring.jpa.properties.hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_SHOW_SQL   = "spring.jpa.properties.hibernate.show_sql";
    private static final String HIBERNATE_FORMAT_SQL = "spring.jpa.properties.hibernate.format_sql";

    @Bean
    DataSource dataSource(Environment env) {

        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(env.getProperty(DATASOURCE_USERNAME));
        dataSource.setPassword(env.getProperty(DATASOURCE_PASSWORD));
        dataSource.setJdbcUrl(env.getProperty(DATASOURCE_URL));
        dataSource.setDriverClassName(env.getProperty(DATASOURCE_DRIVER));

        dataSource.setPoolName("StandardsPool");
        dataSource.setConnectionTestQuery(env.getProperty(DATASOURCE_TEST_QUERY));
        dataSource.setConnectionTimeout(SECONDS.toMillis(30));
        dataSource.setIdleTimeout(MINUTES.toMillis(10));
        dataSource.setInitializationFailTimeout(SECONDS.toMillis(2));
        dataSource.setMaxLifetime(MINUTES.toMillis(30));
        dataSource.setMaximumPoolSize(20);
        dataSource.setMinimumIdle(4);

        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(env.getProperty(HIBERNATE_SHOW_SQL, Boolean.class));
        vendorAdapter.setDatabasePlatform(env.getProperty(HIBERNATE_DIALECT));

        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("br.com.resume");
        factory.setDataSource(dataSource);

        factory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", env.getProperty(HIBERNATE_HBM2DDL));
        factory.getJpaPropertyMap().put("hibernate.format_sql", env.getProperty(HIBERNATE_FORMAT_SQL));
        factory.getJpaPropertyMap().put("hibernate.ejb.naming_strategy", PhysicalNamingStrategy.class.getName());
        factory.getJpaPropertyMap().put("hibernate.jdbc.lob.non_contextual_creation", false);

        return factory;
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    @Primary
    ResourceBundleMessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(UTF_8.name());
        messageSource.setBasenames("messages", "ValidationMessages");
        return messageSource;
    }

}
