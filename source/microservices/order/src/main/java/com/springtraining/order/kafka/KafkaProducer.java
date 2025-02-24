package com.springtraining.order.kafka;

import com.springtraining.order.model.Order;
import com.springtraining.order.model.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class KafkaProducer {
    @Value("${spring.kafka.topic.order}")
    private String orderTopic;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;
    private final ModelMapper modelMapper;

    public void sendMessage(Order order){
        OrderDto orderDto = modelMapper.map(order,OrderDto.class);
        ProducerRecord<String,OrderDto> record = new ProducerRecord<>(orderTopic, order.getOrderId().toString(), orderDto);
        kafkaTemplate.send(record)
                .whenCompleteAsync((sentResult,exception)->{
                    if (Objects.nonNull(exception))
                        log.error(exception.getMessage());
                    else
                        log.info("Sended message to topic {}, {}", orderTopic, order);
                });
    }
}
