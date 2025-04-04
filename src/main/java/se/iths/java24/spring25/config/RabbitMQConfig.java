package se.iths.java24.spring25.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import se.iths.java24.spring25.domain.entity.Playground;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Exchange exchange(){
        return new DirectExchange("my-exchange");
    }

    @Bean
    public Queue queue(){
        return new Queue("my-queue");
    }

    @Bean
    public Binding binding(Exchange exchange, Queue queue){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("routing-key")
                .noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        return jsonConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("playground", Playground.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
}
