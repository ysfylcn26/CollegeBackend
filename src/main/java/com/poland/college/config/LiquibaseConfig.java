package com.poland.college.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class LiquibaseConfig implements CommandLineRunner {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return p -> p.setOneIndexedParameters(true);
    }
    private final DataSource dataSource;

    @Bean
    public LiquibaseProperties liquibaseCollegeProperties(){
        return new LiquibaseProperties();
    }


    @Bean
    @DependsOn(value = "entityManagerFactory")
    public SpringLiquibase liquibaseCollege() {
        LiquibaseProperties liquibaseProperties = liquibaseCollegeProperties();
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


    @Override
    public void run(String... args) throws Exception {
        SpringLiquibase liquibase = liquibaseCollege();
        liquibase.setShouldRun(true);
        liquibase.afterPropertiesSet();
    }
}
