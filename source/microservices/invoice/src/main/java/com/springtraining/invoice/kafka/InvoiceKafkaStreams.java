package com.springtraining.invoice.kafka;

import com.springtraining.order.model.dto.OrderDto;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InvoiceKafkaStreams {

    @Value("${spring.kafka.topic.order}")
    private String orderTopic;
    @Value("${spring.kafka.topic.invoice}")
    private String invoiceTopic;

    @Bean
    public KStream<String, OrderDto> invoiceKStream(StreamsBuilder streamsBuilder, SpecificAvroSerde<OrderDto> orderDtoSpecificAvroSerde) {
        KStream<String, OrderDto> stream = streamsBuilder
                .stream(orderTopic, Consumed.with(Serdes.String(),orderDtoSpecificAvroSerde));

        stream.foreach((k,o)-> log.info(o.toString()));
        return stream;
    }
}
