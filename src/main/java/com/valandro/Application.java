package com.valandro;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {
    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueFisrtName = "female-queue";
    static final String queueLastName = "male-queue";

    @Bean
    Queue firstNameQueue() {
        return new Queue(queueFisrtName, false);
    }

    @Bean
    Queue lastNameQueue() { return new Queue(queueLastName, false); }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding bindingLastName(TopicExchange exchange) {
        return BindingBuilder.bind(firstNameQueue()).to(exchange).with("female");
    }

    @Bean
    Binding bindingFirstName(TopicExchange exchange) {
        return BindingBuilder.bind(lastNameQueue()).to(exchange).with("male");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueFisrtName, queueLastName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Consumer consumer) {
        return new MessageListenerAdapter(consumer, "receiveMessage");
    }

    public static void main(String[] args) throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        SpringApplication.run(Application.class, args).close();
        Long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime));
    }
}
