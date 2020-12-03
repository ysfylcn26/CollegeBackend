package com.poland.college.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Bean
    public LiquibaseProperties liquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @DependsOn(value = "entityManagerFactory")
    public SpringLiquibase liquibase() {
        LiquibaseProperties liquibaseProperties = liquibaseProperties();
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/db/db.changelog-master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema("public");
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(false);
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        return liquibase;
    }

    @Autowired
    public SpringLiquibase liquibase;

    @Override
    public void run(String... args) throws Exception {
        liquibase.setShouldRun(true);
        liquibase.afterPropertiesSet();
    }
}
