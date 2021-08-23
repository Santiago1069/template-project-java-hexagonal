package com.application.configuracion.bd;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Profile("!test")
@Configuration
public class ConfiguracionBaseDatos extends JdbcTemplateAutoConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("oracle")
    @ConfigurationProperties("oracle.spring.datasource")
    public DataSourceProperties dataSourceOracleProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("oracle.spring.datasource.configuration")
    public HikariDataSource dataSourceOracle(@Qualifier("oracle") DataSourceProperties properties, ConnectionPoolService connectionPoolService) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(connectionPoolService.obtainSize(
                "oracle.spring.datasource.pool-size"));
        return dataSource;
    }

    @Bean("oracleJdbcTemplate")
    JdbcTemplate jdbcTemplate(@Qualifier("oracle") DataSourceProperties properties, ConnectionPoolService connectionPoolService) {
        return new JdbcTemplate(dataSourceOracle(properties, connectionPoolService));
    }

    @Bean("oracleNamedParameterJdbcTemplate")
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("oracle") DataSourceProperties properties, ConnectionPoolService connectionPoolService){
        return new NamedParameterJdbcTemplate(dataSourceOracle(properties, connectionPoolService));
    }

    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
