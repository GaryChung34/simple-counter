package com.garytool.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class TimeTest
{

    @Test
	@DisplayName("print correct time string")
    public void timePrintCorrectMsgTest()
    {
		Timer time = new Timer("2024-01-13 16:00");
		Timer time2 = new Timer("2024-01-14 08:00");
        assertEquals("Sat, Jan 13 16:00:00", time.getStartTimeStr());
		assertEquals("Sun, Jan 14 08:00:00", time2.getStartTimeStr());
    }

	@Test
	@DisplayName("test getHour() function in Time class")
	public void testGetHourFunctionInTimeClass() {
		Timer time = new Timer();
		assertEquals("0 a.m.", time.getHour(24, true));
		assertEquals("8.5 p.m.", time.getHour(20.5, true));
		assertEquals("8.0 a.m.", time.getHour(8, true));
		assertEquals("20.5", time.getHour(20.5, false));
	}

	@Test
	@DisplayName("'Time.class' getTimeSlot() test")
	public void timeClassGetTimeSlotTest() {
		Timer time1 = new Timer("2024-01-13 08:00");
		Timer time2 = new Timer("2024-01-13 09:45");
		Timer time3 = new Timer("2024-01-13 16:30");
		Timer time4 = new Timer("2024-01-13 17:15");
		assertEquals("8.0 a.m.", time1.getTimeSlot(true));
		assertEquals("10.0 a.m.", time2.getTimeSlot(true));
		assertEquals("4.5 p.m.", time3.getTimeSlot(true));
		assertEquals("5.0 p.m.", time4.getTimeSlot(true));
		assertEquals("8.0", time1.getTimeSlot(false));
		assertEquals("10.0", time2.getTimeSlot(false));
		assertEquals("16.5", time3.getTimeSlot(false));
		assertEquals("17.0", time4.getTimeSlot(false));
	}

}
