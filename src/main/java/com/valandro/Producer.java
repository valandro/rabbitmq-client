package com.valandro;


import com.valandro.data.Entries100;
import com.valandro.data.Entries1000;
import com.valandro.repository.Entries1000Repository;
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
    private final Entries1000Repository repository;

    @Override
    public void run(String... args) throws Exception {
        List<Entries1000> entries = repository.findAll();
        System.out.println("Sending messages...");
        entries.forEach(e -> {
            rabbitTemplate.convertAndSend(Application.topicExchangeName, "foo.bar.baz", e.getFirstName());
        });
        consumer.getLatch().await(100, TimeUnit.MILLISECONDS);
    }

}