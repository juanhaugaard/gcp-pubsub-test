package com.macys.msc.mawm.gcppubsub;

import com.macys.msc.mawm.messaging.pubsub.producer.PubsubProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TestPublisher {
    private final PubsubProducer pubsubProducer;

    public TestPublisher(PubsubProducer pubsubProducer) {
        this.pubsubProducer = pubsubProducer;
    }

    public void publishMessage(String message, Map<String, String> attributes) {
        log.info("message to publish: {}, attributes:{}", message, attributes);
        pubsubProducer.publishMessage(message, attributes);
    }
}
