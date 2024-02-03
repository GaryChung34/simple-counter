package com.garytool.counter.ui;

import com.garytool.counter.models.StudyUnit;

public class SmallBattery {
    private final STATE state;
    enum STATE {
        EMPTY("░░░"),
        ONE("█░░"),
        TWO("██░"),
        FULL("███");
        public final String figure;

        private STATE(String figure) {
            this.figure = figure;
        }
    }

    public SmallBattery() {
        this.state = STATE.EMPTY;
    }

    public SmallBattery(StudyUnit studyUnit) {
        int credit = studyUnit.getCredit();
        if (credit == 10) {
            this.state = STATE.FULL;
        } else if (credit >= 8) {
            this.state = STATE.TWO;
        } else if (credit >= 5) {
            this.state = STATE.ONE;
        } else {
            this.state = STATE.EMPTY;
        }
    }

    public void toBattery() {
        System.out.println(this.state.figure);
    }

    public String toBatteryStr() {
        return this.state.figure;
    }
}
