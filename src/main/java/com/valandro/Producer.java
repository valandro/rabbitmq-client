package com.valandro;


import com.valandro.data.*;
import com.valandro.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class Producer implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Consumer consumer;
    private final Entries100Repository repository;

    @Override
    public void run(String... args) throws Exception {
        List<Entries100> entries = repository.findAll();
        System.out.println("Sending messages...");
        entries.forEach(e -> {
            if (e.getGender().equals("Female")) {
                rabbitTemplate.convertAndSend(Application.topicExchangeName, "female", e.getFirstName());
            } else {
                rabbitTemplate.convertAndSend(Application.topicExchangeName, "male", e.getFirstName());
            }
        });
        consumer.getLatch().await(100, TimeUnit.MILLISECONDS);
    }

}