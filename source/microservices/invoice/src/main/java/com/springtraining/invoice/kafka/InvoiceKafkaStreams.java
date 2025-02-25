package com.springtraining.invoice.kafka;

import com.springtraining.invoice.model.Invoice;
import com.springtraining.invoice.service.InvoiceService;
import com.springtraining.invoice.util.GenericRecordUtil;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
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
    private final GenericAvroSerde genericAvroSerde;
    private final InvoiceService invoiceService;
    private final GenericRecordUtil genericRecordUtil;

    @Bean
    public KStream<String, GenericRecord> invoiceKStream(StreamsBuilder streamsBuilder) {
        KStream<String, GenericRecord> kStream = streamsBuilder.stream(orderTopic, Consumed.with(Serdes.String(),genericAvroSerde));

        kStream.peek((key,order)-> log.info("Deserialize order {} to invoice", order))
                .mapValues(order-> genericRecordUtil.deserialize(order,Invoice.class))
                .peek((key,invoice)-> log.info("Create invoice to db, {}", invoice))
                .peek((key,invoice)-> invoiceService.save(invoice))
                .peek((key,invoice)-> log.info("Serialize invoice {} to GenericRecord",invoice))
                .mapValues(invoice-> genericRecordUtil.serialize(invoice,invoiceTopic))
                .peek((key,invoice)-> log.info("Sending invoice to topic {}, {}",invoiceTopic,invoice))
                .to(invoiceTopic, Produced.with(Serdes.String(), genericAvroSerde));

        return kStream;

    }
}
