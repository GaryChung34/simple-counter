package com.garytool.counter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garytool.counter.models.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonServiceTest {
    @Test
    @DisplayName("write file test")
    public void writeFileTest() throws Exception {
        JsonService jsonService = new JsonService();
        ObjectMapper objectMapper = new ObjectMapper();

        Log log = new Log();
        log.setName("gary chung");
        String objStr = objectMapper.writeValueAsString(log);

        jsonService.writeJson(log);
        ClassLoader classLoader = getClass().getClassLoader();
        FileInputStream fis = new FileInputStream("target/log.json");
        String fromFileStr = IOUtils.toString(fis);

        assertEquals(objStr, fromFileStr);
    }

}
