package com.macys.msc.mawm.gcppubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.macys.msc.mawm.itemintegration.service,com.macys.msc.mawm.gcppubsub")
public class TestPublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestPublisherApplication.class, args);
    }
}
