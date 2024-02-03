package com.garytool.counter.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private String name;
    private LocalDateTime lastUpdate;
    private List<DayRecord> records;

    public DayRecord todayLog() {
        Date today = new Date();
        if(records == null) {
            this.records = new ArrayList<>();
            DayRecord dayRecord = new DayRecord();
            this.records.add(dayRecord);
            return dayRecord;
        } else {
            DayRecord result = records.stream().filter(dayRecord -> {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMDD");
                // date inside 00 - 03
                SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm");
                boolean isTomorrow = false;
                try {
                    Date three = timeFmt.parse("03:00");
                    Date now =  timeFmt.parse(timeFmt.format(today));
                    Calendar c = Calendar.getInstance();
                    c.setTime(dayRecord.getDate());
                    c.add(Calendar.DATE, 1);
                    String dbAddOneDay = fmt.format(c.getTime());
                    if (now.before(three) && fmt.format(today).equals(dbAddOneDay)) {
                        isTomorrow = true;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                return fmt.format(today).equals(fmt.format(dayRecord.getDate())) || isTomorrow;
            }).findFirst().orElse(null);
            if (result == null) {
                DayRecord dayRecord = new DayRecord();
                this.records.add(dayRecord);
                return dayRecord;
            } else {
                return result;
            }
        }
    }

    public void updateTodayLog(DayRecord record) {

    }
}
