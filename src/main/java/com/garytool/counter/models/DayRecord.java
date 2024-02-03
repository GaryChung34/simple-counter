package com.garytool.counter.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class DayRecord {
    private Date date;
    private List<StudyUnit> units;

    public DayRecord() {
        this.date = new Date();
        this.units = new ArrayList<>();
    }
}
