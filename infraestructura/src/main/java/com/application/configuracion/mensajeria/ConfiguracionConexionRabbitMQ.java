package com.application.configuracion.mensajeria;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class ConfiguracionConexionRabbitMQ {

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUserName;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${spring.rabbitmq.virtual-host}")
    private String rabbitVirtualHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory conexionRabbitMQ = new CachingConnectionFactory();
        conexionRabbitMQ.setConnectionNameStrategy(
                connectionFactory -> "applicationNameConnection");
        conexionRabbitMQ.setHost(rabbitHost);
        conexionRabbitMQ.setUsername(rabbitUserName);
        conexionRabbitMQ.setPassword(rabbitPassword);
        conexionRabbitMQ.setVirtualHost(rabbitVirtualHost);

        return conexionRabbitMQ;
    }

}
