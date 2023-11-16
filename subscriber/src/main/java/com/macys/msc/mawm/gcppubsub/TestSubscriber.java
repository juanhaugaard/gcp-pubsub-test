package com.macys.msc.mawm.gcppubsub;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import com.macys.msc.mawm.messaging.pubsub.annotations.SubscribedTopic;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@SubscribedTopic(subscriptionName = "${messaging.pubsub.test.subscriptionName}")
public class TestSubscriber implements MessageReceiver {
    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        Map<String, String> attributes = message.getAttributesMap();
        String messageJson = message.getData().toStringUtf8();
        log.info("*** Received message:{}, attributes:{}", messageJson, attributes);
        consumer.ack();
    }
}
