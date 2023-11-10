package org.springframework.samples.petclinic.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.owner.OwnerDto;
import org.springframework.samples.petclinic.rest.rasupport.RaPatchUtil;

import java.util.Collections;

@SpringBootTest
public class MyTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RaPatchUtil raPatchUtil;

    @Test
    void checkRecordProperties() throws JsonProcessingException {
        String json = """
            {"id": 1, "telephone": "155", "city": "Moscow"}
        """;

        objectMapper.readValue(json, MyOwnerDto.class);

        MyOwnerDto dto = new MyOwnerDto(1, "Alex", "Petrov", "109", "Samara", Collections.emptyList());

        objectMapper.readerForUpdating(dto).readValue(json);
        //OwnerDto result = raPatchUtil.patch(json, dto);

        System.out.println(dto);
    }
}
