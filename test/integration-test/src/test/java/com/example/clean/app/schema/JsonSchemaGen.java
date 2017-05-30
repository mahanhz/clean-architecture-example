package com.example.clean.app.schema;

import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

import java.io.FileWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class JsonSchemaGen {

    private static final String SUFFIX = ".json";

    public static void main(String[] args) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);

        pojos().entrySet().forEach(entry -> {
            final JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(entry.getKey());
            String jsonSchemaAsString = "";

            final URL resource = JsonSchemaGen.class.getClassLoader().getResource("json-schema/" + entry.getValue());

            try {
                jsonSchemaAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);

                try (FileWriter file = new FileWriter(resource.getFile())) {
                    file.write(jsonSchemaAsString);

                    System.out.println("Successfully wrote json schema to " + resource.getFile() + ". This should be copied over to src/test/resources/json-schema.");
                }
            } catch (Exception ex) {
                System.out.println("Unfortuantely and exception occurred: " + ex);
            }
        });
    }

    private static Map<Class, String> pojos() {
        final Map<Class, String> map = new HashMap<>();

        map.put(CustomerDTOSchema.class, CustomerDTOSchema.class.getSimpleName() + SUFFIX);
        map.put(CustomersDTOSchema.class, CustomersDTOSchema.class.getSimpleName() + SUFFIX);

        return map;
    }
}
