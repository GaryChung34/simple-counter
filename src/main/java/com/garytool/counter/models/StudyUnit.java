package com.garytool.counter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.garytool.counter.Timer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyUnit {
    private String timeSlot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime actualEndTime;
    private Long studyTime;
    private int credit;

    public StudyUnit(Timer timer) {
        this.timeSlot = timer.getTimeSlot(false);
        this.startTime = timer.getStartTime();
        this.endTime = timer.getEndTime();
        this.actualEndTime = timer.getActualEndTime();
        this.studyTime = timer.getStudyTime();
        this.credit = timer.getCredit();
    }
}
