package com.garytool.counter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.garytool.counter.models.DayRecord;
import com.garytool.counter.models.Log;
import com.garytool.counter.models.StudyUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class JsonService {
    private ObjectMapper objectMapper;
    private Log log;

    public JsonService() throws IOException {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        this.log = readJson();
    }

    public Log readJson() throws IOException {
        // TODO: read json file
         return objectMapper.readValue(new File("target/log.json"), Log.class);
    }

    public void writeJson(Object object) throws Exception{
        objectMapper.writeValue(new File("target/log.json"), object);
    }

    public DayRecord readTodayLog() throws IOException {
        if (this.log == null) {
            this.log = readJson();
        }
        return this.log.todayLog();
    }

    public void update(Timer timer) throws Exception{
        DayRecord todayLog = readTodayLog();
        List<StudyUnit> units = todayLog.getUnits();
        StudyUnit unit = new StudyUnit(timer);
        units.add(unit);
        writeJson(this.log);
    }
}
