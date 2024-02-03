package com.garytool.counter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Counter {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean stopped;

    public Counter() {
        this.startTime = LocalDateTime.now();
        this.endTime = this.startTime.plusHours(1);
    }

    public boolean checkEnded() {
        return endTime.isBefore(LocalDateTime.now());
    }


}
