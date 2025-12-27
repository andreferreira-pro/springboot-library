package com.ferreira.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;


//    @Bean
    public DataSource dataSource(){



        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }


    @Bean
    public DataSource hikariDataSource(){

        log.info("Iniciando conexão com banco na URL {} ", url);

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // máximo de conexões liberadas no poll
        config.setMinimumIdle(1); // tamanho inicial do poll
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); //600 mil ms (10 minutos)
        config.setConnectionTimeout(100000);//timetou para uma reposta de conexão
        config.setConnectionTestQuery("select 1");// query test para validar conexao

        return new HikariDataSource(config);


    }






}
