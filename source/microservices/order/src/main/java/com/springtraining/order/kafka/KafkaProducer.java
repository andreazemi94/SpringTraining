package com.springtraining.order.kafka;

import com.springtraining.order.model.Order;
import com.springtraining.order.util.GenericRecordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class KafkaProducer {
    @Value("${spring.kafka.topic.order}")
    private String orderTopic;
    private final KafkaTemplate<String, GenericRecord> kafkaTemplate;
    private final GenericRecordUtil genericRecordUtil;

    public void sendMessage(Order order) {
        ProducerRecord<String,GenericRecord> record = new ProducerRecord<>(
                orderTopic,
                String.valueOf(order.getOrderId()),
                genericRecordUtil.create(order,orderTopic)
        );
        kafkaTemplate.send(record)
                .thenAcceptAsync(sentResult-> log.info("Sended message to topic {}, {}", orderTopic, order))
                .exceptionallyAsync(exception-> {log.error(exception.getMessage()); return null;});
    }
}
