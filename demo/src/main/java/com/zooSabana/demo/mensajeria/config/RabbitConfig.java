package com.zooSabana.demo.mensajeria.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_exchange");
    }

    @Bean
    public Queue eventosAuditoriaQueue() {
        return new Queue("eventos_auditoria", true);
    }

    @Bean
    public Queue revisionesPendientesQueue() {
        return new Queue("revisiones_pendientes", true);
    }

    @Bean
    public Binding bindingEventosAuditoria(Queue eventosAuditoriaQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(eventosAuditoriaQueue).to(directExchange).with("auditoria");
    }

    @Bean
    public Binding bindingRevisionesPendientes(Queue revisionesPendientesQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(revisionesPendientesQueue).to(directExchange).with("notificaciones");
    }
}
