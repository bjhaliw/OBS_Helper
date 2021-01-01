package controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

/**
 * This class is responsible for the creation, reading, and writing of the text
 * files that will be used for the overlay in OBS software. The class also
 * contains helper methods dealing with the formatting and manipulation of the
 * text that the user wants to overlay.
 * 
 * @author Brenton Haliw
 *
 */
@SuppressWarnings("restriction")
public class ReadAndWrite {

	/**
	 * Writes the required input to the designated file
	 * 
	 * @param filePath - Location where text should be written
	 * @param input    - string to be written to the file
	 */
	public static void writeToFileTimeValue(String filePath, String input) {
		try {
			PrintWriter writer = new PrintWriter(filePath);
			writer.println(input);
			System.out.println("Writing " + input + " to " + filePath);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Looks through the desired format of the time values and removes any that are
	 * currently not being used. Example: 00:15:13 will be 15:13 instead.
	 * 
	 * @param h      - String of the hour value
	 * @param m      - String of the minute value
	 * @param s      - String of the second value
	 * @param format - String of the desired format
	 * @return - A new formatted string without the unused time values
	 */
	public static String removeUnusedTimeValues(String h, String m, String s, String format) {
		int hour = Integer.parseInt(h);
		int minute = Integer.parseInt(m);
		int second = Integer.parseInt(s);

		int totalTime = (hour * 3600) + (minute * 60) + second;

		if (format == null || format.equals("")) {
			format = "[hour]:[minute]:[second]";
		}

		String newFormat = format;

		if (totalTime < 3600) {
			if (format.equals("[hour]:[minute]:[second]")) {
				newFormat = newFormat.substring("[hour]:".length());
			} else if (format.equals("[hour] hour(s), [minute] minute(s), [second] second(s)")) {
				newFormat = newFormat.substring("[hour] hour(s), ".length());
			}
		}

		if (totalTime < 60) {
			if (format.equals("[hour]:[minute]:[second]")) {
				newFormat = newFormat.substring("[minute]:".length());
			} else if (format.equals("[hour] hour(s), [minute] minute(s), [second] second(s)")) {
				newFormat = newFormat.substring("[minute] minute(s), ".length());
			}
		}

		if (totalTime <= 0) {
			newFormat = "";
		}

		return newFormat;
	}

	/**
	 * Replaces the time value tags in the desired formatted string.
	 * 
	 * @param h      - hour time value
	 * @param m      - minute time value
	 * @param s      - second time value
	 * @param format - String with tags to be replaced by values
	 * @return - a String with the tags replaced by the time values
	 */
	public static String replaceTimeFormattedString(String h, String m, String s, String format) {

		if (format == null || format.equals("")) {
			format = "[hour]:[minute]:[second]";
		}

		String output = new String(format);

		if (output.contains("[hour]")) {
			output = output.replace("[hour]", h);
		}

		if (output.contains("[minute]")) {
			output = output.replace("[minute]", m);
		}

		if (output.contains("[second]")) {
			output = output.replace("[second]", s);
		}

		return output;
	}

	/**
	 * Removes the leading zeroes from the TimeTab.
	 * 
	 * @param value - String of the time value to be manipulated
	 * @return - String of the time value without a leading zero
	 * @throws - NumberFormatException
	 */
	public static String removeLeadingZeroes(String value) throws NumberFormatException {
		int temp = Integer.MIN_VALUE;
		String output = "";

		temp = Integer.parseInt(value);

		output = Integer.toString(temp);

		return output;
	}

	/**
	 * Converts an integer to a String and concatenates a 0 to the front if the
	 * value is under 10
	 * 
	 * @param value - String to be converted
	 * @return - String representation of the primitive integer
	 */
	public static String formatIntToString(int value) {
		String string = Integer.toString(value);

		if (value < 10) {
			string = "0" + string;
		}

		return string;
	}
	

	/**
	 * Starts the process of writing to the required filepath
	 * @param filepath
	 * @param hour
	 * @param minute
	 * @param second
	 * @param format
	 * @param removeUnused
	 * @return
	 */
	public static String writeToFile(String filepath, String hour, String minute, String second, String format, boolean removeUnused) {
		
		int totalTime = getTimeSeconds(hour, minute, second);
		
		// If the time is less than 10 hours
		if (totalTime < 36000) {
			hour = ReadAndWrite.removeLeadingZeroes(hour);
		}
		
		// If the time is less than 10 minutes
		if (totalTime < 600) {
			minute = ReadAndWrite.removeLeadingZeroes(minute);
		}
		
		// If the time is less than 10 seconds
		if (totalTime < 10) {
			second = ReadAndWrite.removeLeadingZeroes(second);
		}
		
		
		if (removeUnused) {
			format = ReadAndWrite.removeUnusedTimeValues(hour, minute, second, format);
		}
		
		
		format = ReadAndWrite.replaceTimeFormattedString(hour, minute, second, format);
		
		
		
		return format;
	}
	
	private static int getTimeSeconds(String h, String m, String s) {
		int hour = Integer.parseInt(h);
		int minute = Integer.parseInt(m);
		int second = Integer.parseInt(s);

		int totalTime = (hour * 3600) + (minute * 60) + second;
		
		return totalTime;
	}

}
