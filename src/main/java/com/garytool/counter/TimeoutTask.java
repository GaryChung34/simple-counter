package com.garytool.counter;

import java.util.Timer;
import java.util.TimerTask;

public class TimeoutTask extends TimerTask {
    private Thread thread;
    private Timer timer;

    public TimeoutTask(Thread thread, Timer timer) {
        this.thread = thread;
        this.timer = timer;
    }

    @Override
    public void run() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
            timer.cancel();
        }
    }
}
