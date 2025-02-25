package com.springtraining.order.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenericRecordUtil {
    private final SchemaRegistryClient schemaRegistryClient;
    private final ObjectMapper objectMapper;

    public <T> GenericRecord create(T entity, String topic) {
        try {
            Map<String, Object> map = objectMapper.convertValue(entity, new TypeReference<>() {});
            Schema schema = new Schema.Parser().parse(schemaRegistryClient.getLatestSchemaMetadata(topic+"-value").getSchema());
            GenericRecord record = new GenericData.Record(schema);
            schema.getFields().forEach(field-> record.put(field.name(),map.get(field.name())));
            return record;
        } catch (IOException | RestClientException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
