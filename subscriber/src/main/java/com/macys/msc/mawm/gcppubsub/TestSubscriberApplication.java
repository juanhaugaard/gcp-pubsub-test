package com.macys.msc.mawm.gcppubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.macys.msc.mawm")
public class TestSubscriberApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSubscriberApplication.class, args);
    }
}
