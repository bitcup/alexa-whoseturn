package com.bitcup.whoseturn.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.bitcup.whoseturn.model.Activity;
import com.bitcup.whoseturn.model.SelectionHistory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author bitcup
 */
public class DataMarshaller implements DynamoDBMarshaller<SelectionHistory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataMarshaller.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addKeySerializer(Activity.class, new ActivitySerializer());
        MapType myMapType = TypeFactory.defaultInstance().constructMapType(HashMap.class, Activity.class, String.class);
        OBJECT_MAPPER.registerModule(module).writerWithType(myMapType);
        LOGGER.info("registered ActivitySerializer");
    }

    @Override
    public String marshall(SelectionHistory gameData) {
        try {
            return OBJECT_MAPPER.writeValueAsString(gameData);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to marshall game data", e);
        }
    }

    @Override
    public SelectionHistory unmarshall(Class<SelectionHistory> clazz, String value) {
        try {
            return OBJECT_MAPPER.readValue(value, new TypeReference<SelectionHistory>() {
            });
        } catch (Exception e) {
            throw new IllegalStateException("Unable to unmarshall game data value", e);
        }
    }

    public static class ActivitySerializer extends JsonSerializer<Activity> {
        @Override
        public void serialize(Activity value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            jsonGenerator.writeFieldName(value.getName());
        }
    }
}
