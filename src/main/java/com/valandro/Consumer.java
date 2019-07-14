package com.valandro;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Consumer {

    private CountDownLatch latch = new CountDownLatch(100);

    @RabbitListener(queues = "male-queue")
    public void receiveMale(String message) {
        System.out.println("Received Male <" + message + ">");
        latch.countDown();
    }

    @RabbitListener(queues = "female-queue")
    public void receiveFemale(String message) {
        System.out.println("Received Female <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
