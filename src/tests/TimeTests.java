package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import controller.ReadAndWrite;

public class TimeTests {
	
	String[] format = new String[] { "[hour]:[minute]:[second]", "[hour] hours, [minute] minutes, [second] seconds"};

	@Test
	public void testTimeAllValuesClockFormat() {
		String h = "02", m = "15", s = "09";

		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[0], true);
		String correctResult = "2:15:09";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeNoHourClockFormat() {
		String h = "00", m = "15", s = "09";

		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[0], true);
		String correctResult = "15:09";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeNoMinuteClockFormat() {
		String h = "00", m = "00", s = "09";
		
		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[0], true);
		String correctResult = "9";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeAllValuesWordFormat() {
		String h = "02", m = "15", s = "09";
	
		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[1], true);
		String correctResult = "2 hours, 15 minutes, 9 seconds";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeNoHourWordFormat() {
		String h = "00", m = "15", s = "09";
		
		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[1], true);
		String correctResult = "15 minutes, 9 seconds";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeNoMinuteWordFormat() {
		String h = "00", m = "00", s = "09";
		
		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[1], true);
		String correctResult = "9 seconds";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeSingleValuesWordFormat() {
		String h = "01", m = "01", s = "01";
		
		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[1], true);
		String correctResult = "1 hour, 1 minute, 1 second";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeSingleValuesClockFormat() {
		String h = "00", m = "05", s = "05";
		
		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[0], false);
		String correctResult = "0:05:05";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeZeroValuesClockFormat() {
		String h = "00", m = "00", s = "00";
		
		String result = ReadAndWrite.writeTimeToFile(null, h, m, s, format[0], false);
		String correctResult = "0:00:00";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
}
