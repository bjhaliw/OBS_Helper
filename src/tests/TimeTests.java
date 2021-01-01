package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import controller.ReadAndWrite;

public class TimeTests {

	@Test
	public void testTimeAllValuesClockFormat() {
		String h = "02", m = "15", s = "09";
		String[] format = new String[] { "[hour]:[minute]:[second]", "[hour] hour(s), [minute] minute(s), [second] second(s)"};
		
		String result = ReadAndWrite.writeToFile(null, h, m, s, format[0], true);
		String correctResult = "2:15:09";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeNoHourClockFormat() {
		String h = "00", m = "15", s = "09";
		String[] format = new String[] { "[hour]:[minute]:[second]", "[hour] hour(s), [minute] minute(s), [second] second(s)"};
		
		String result = ReadAndWrite.writeToFile(null, h, m, s, format[0], true);
		String correctResult = "15:09";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeNoMinuteClockFormat() {
		String h = "00", m = "00", s = "09";
		String[] format = new String[] { "[hour]:[minute]:[second]", "[hour] hour(s), [minute] minute(s), [second] second(s)"};
		
		String result = ReadAndWrite.writeToFile(null, h, m, s, format[0], true);
		String correctResult = "9";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeAllValuesWordFormat() {
		String h = "02", m = "15", s = "09";
		String[] format = new String[] { "[hour]:[minute]:[second]", "[hour] hour(s), [minute] minute(s), [second] second(s)"};
		
		String result = ReadAndWrite.writeToFile(null, h, m, s, format[1], true);
		String correctResult = "2 hour(s), 15 minute(s), 9 second(s)";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeNoHourWordFormat() {
		String h = "00", m = "15", s = "09";
		String[] format = new String[] { "[hour]:[minute]:[second]", "[hour] hour(s), [minute] minute(s), [second] second(s)"};
		
		String result = ReadAndWrite.writeToFile(null, h, m, s, format[1], true);
		String correctResult = "15 minute(s), 9 second(s)";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
	
	@Test
	public void testTimeNoMinuteWordFormat() {
		String h = "00", m = "00", s = "09";
		String[] format = new String[] { "[hour]:[minute]:[second]", "[hour] hour(s), [minute] minute(s), [second] second(s)"};
		
		String result = ReadAndWrite.writeToFile(null, h, m, s, format[1], true);
		String correctResult = "9 second(s)";
		
		System.out.println(result);
		
		assertTrue(result.equals(correctResult));
	}
}
