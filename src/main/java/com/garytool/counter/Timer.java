package com.garytool.counter;

import lombok.Data;
import org.apache.commons.lang3.time.StopWatch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public
class Timer {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private LocalDateTime actualEndTime;
	private StopWatch stopWatch;
	private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("E, MMM dd HH:mm:ss");

	public Timer() {
		this.startTime = LocalDateTime.now();
		this.endTime = this.startTime.plusHours(1);
		this.stopWatch = new StopWatch();
	}

	public Timer(String timeStr) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime time = LocalDateTime.parse(timeStr, format);
		this.startTime = time;
		this.endTime = this.startTime.plusHours(1);
		this.stopWatch = new StopWatch();
	}

	public String getStartTimeStr() {
		return this.startTime.format(this.dtFormatter);
	}

	public String getEndTimeStr() {
		return this.endTime.format(this.dtFormatter);
	}

	
	public String getHour(double hour, boolean is12) {
		if(is12) {
			if (hour == 24) 
				return "0 a.m.";
			if (hour > 12) {
				return String.valueOf(hour - 12) + " p.m.";
			} else {
				return String.valueOf(hour) + " a.m.";
			}
		} else {
			return String.valueOf(hour);
		}
	}

	public String getTimeSlot(boolean is12) {
		double hour = (double) this.startTime.getHour();
		int minute = this.startTime.getMinute();
		if (minute >= 45) {
			hour++;
		} else if (minute >= 30) {
			hour = hour + 0.5;
		}
		return getHour(hour, is12);
	}

	public void endCount() {
		this.actualEndTime = LocalDateTime.now();
	}

	public void start() {
		this.stopWatch.start();
	}

	public boolean isSuspend() {
		return stopWatch.isSuspended();
	}

	public void suspend() {
		this.stopWatch.suspend();
	}

	public void resume() {
		this.stopWatch.resume();
	}

	public void complete() {
		this.stopWatch.stop();
		this.actualEndTime = LocalDateTime.now();
	}

	public long getStudyTime() {
		return this.stopWatch.getTime();
	}

	public int getCredit() {
		long studyTime = getStudyTime();
		return Math.round((float) studyTime / 1000 / 60 / 6);
	}
}
		
