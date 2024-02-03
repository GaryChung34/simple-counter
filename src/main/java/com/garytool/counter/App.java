package com.garytool.counter;

import com.garytool.counter.models.DayRecord;
import com.garytool.counter.models.Log;
import com.garytool.counter.models.StudyUnit;
import com.garytool.counter.ui.SmallBattery;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws Exception {
		Scanner input = new Scanner(System.in);
		JsonService jsonService = new JsonService();
		SmallBattery smallBattery = new SmallBattery();
		LanternaUI ui = new LanternaUI();

		// test ui
		ui.runUI();

		print();
		String prompt = "";

		while (!prompt.equals("exit")) {
			smallBattery.toBattery();
			printHome();
			printTodayUnit(jsonService);
			prompt = input.nextLine();
			if(prompt.equals("")) {
				// timeService timer start
				Timer timer = new Timer();
				timer.start();
				printTimer(timer);
				while (!prompt.equals("stop")) {
					prompt = input.nextLine();
					switch (prompt) {
						case "":
							// resume or suspend
							if (!timer.isSuspend()) {
								timer.suspend();
								print("timer is suspended, press enter to resume ...");
							} else {
								timer.resume();
								print("press enter to pause, stop to end ...");
							}
							break;
						case "stop":
							// finish and update log
							timer.complete();
							jsonService.update(timer);
							break;
						default:
							break;
					}
				}
			}
		}


		// method 1 (for auto stop clock)
//		Thread thread = new Thread(new LongRunningTask());
//		thread.start();
//
//		Timer timer = new Timer();
//		TimeoutTask timeoutTask = new TimeoutTask(thread, timer);
//		timer.schedule(timeoutTask, 5000);

		// method 2 (for auto stop clock)
//		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
//		Future future = executor.submit(new LongRunningTask());
//		Runnable cancelTask = () -> future.cancel(true);
//
//		executor.schedule(cancelTask, 5000, TimeUnit.MILLISECONDS);
//		executor.shutdown();
    }

	public static void print(String msg) {
		System.out.println(msg);
	}

	public static void print () {
	 	System.out.println();
	}

	public static void printHome() {
		print(" ==== study counter ====");
		print("current time: " + new Timer().getStartTimeStr());	// change to date only
		// maybe show some study unit
		print("press enter to start, exit ot end ...");
	}

	// printTodayUnit function
	public static void printTodayUnit(JsonService jsonService) throws IOException {
		DayRecord dayRecord = jsonService.readTodayLog();
		List<StudyUnit> units = dayRecord.getUnits();
		StringBuilder sb = new StringBuilder();
		units.forEach(unit -> {
			SmallBattery battery = new SmallBattery(unit);
			sb.append(unit.getTimeSlot()).append(battery.toBatteryStr()).append(" ");
		});
		print(sb.toString());
	}

	public static void printTimer(Timer timer) {
		print(" ==== start counting ==== ");
		print("start time: " + timer.getStartTimeStr());
		print("planed end time: " + timer.getEndTimeStr());
		print("time slot: " + timer.getTimeSlot(true));
		print("press enter to pause, stop to end ...");
	}
	// printTimer function
}
