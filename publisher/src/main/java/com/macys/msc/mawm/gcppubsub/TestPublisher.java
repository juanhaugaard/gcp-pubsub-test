package com.macys.msc.mawm.gcppubsub;

import com.google.api.core.ApiFutureCallback;
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
        pubsubProducer.publishMessage(message, attributes, getApiFutureCallback());
    }

    ApiFutureCallback<String> getApiFutureCallback() {
        return new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Publication failure: {}", throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(String s) {
                log.debug("Publication success: {}", s);
            }
        };
    }

}
