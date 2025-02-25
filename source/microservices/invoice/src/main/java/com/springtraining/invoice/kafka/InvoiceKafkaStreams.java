package com.springtraining.invoice.kafka;

import com.springtraining.invoice.model.Invoice;
import com.springtraining.invoice.service.InvoiceService;
import com.springtraining.order.model.dto.OrderDto;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvoiceKafkaStreams {

    @Value("${spring.kafka.topic.order}")
    private String orderTopic;
    @Value("${spring.kafka.topic.invoice}")
    private String invoiceTopic;
    private final SpecificAvroSerde<OrderDto> orderDtoSpecificAvroSerde;
    private final InvoiceService invoiceService;
    private static final String ORDER_STATUS_PROCESSING = "PROCESSING";

    @Bean
    public KStream<String, OrderDto> invoiceKStream(StreamsBuilder streamsBuilder) {
        KStream<String, OrderDto> ordersToProcessingKStream = streamsBuilder
                .stream(orderTopic, Consumed.with(Serdes.String(),orderDtoSpecificAvroSerde))
                .filter((k,orderDto)-> orderDto.getStatus().equals(ORDER_STATUS_PROCESSING));

        ordersToProcessingKStream.mapValues(Invoice::from)
                .peek((k,invoice)-> log.info("Convert order to invoice, {}", invoice))
                .mapValues((k,invoice)-> invoiceService.save(invoice))
                .peek((k,invoice)-> log.info("Create invoice to db, {}", invoice));
        return ordersToProcessingKStream;
    }
}
