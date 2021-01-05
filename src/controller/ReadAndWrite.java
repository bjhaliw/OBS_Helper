package controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This class is responsible for the creation, reading, and writing of the text
 * files that will be used for the overlay in OBS software. The class also
 * contains helper methods dealing with the formatting and manipulation of the
 * text that the user wants to overlay.
 * 
 * @author Brenton Haliw
 *
 */
public class ReadAndWrite {

	/**
	 * Starts the process of writing to the required filepath
	 * 
	 * @param filepath     - Location of the text file
	 * @param hour         - Hour time value
	 * @param minute       - Minute time value
	 * @param second       - Second time value
	 * @param format       - How the text should be displayed
	 * @param removeUnused - Remove time values if they are 0
	 * @return - String representing the text to be displayed
	 */
	public static String writeTimeToFile(String filepath, String hour, String minute, String second, String format,
			boolean removeUnused) {
		if (format == null || format.equals("")) {
			format = "[hour]:[minute]:[second]";
		}

		if (format.equals("[hour] hours, [minute] minutes, [second] seconds")) {
			hour = ReadAndWrite.removeLeadingZeroes(hour);
			minute = ReadAndWrite.removeLeadingZeroes(minute);
			second = ReadAndWrite.removeLeadingZeroes(second);

		} else {
			int totalTime = getTimeSeconds(hour, minute, second);

			// If the time is less than 10 hours
			if (totalTime < 36000) {
				hour = ReadAndWrite.removeLeadingZeroes(hour);
			}

			// If the time is less than 10 minutes
			if (totalTime < 600 && removeUnused) {
				minute = ReadAndWrite.removeLeadingZeroes(minute);
			}

			// If the time is less than 10 seconds
			if (totalTime < 10 && removeUnused) {
				second = ReadAndWrite.removeLeadingZeroes(second);
			}
		}

		if (removeUnused) {
			format = ReadAndWrite.removeUnusedTimeValues(hour, minute, second, format);
		}

		System.out.println("Current format is: " + format);

		if (hour.equals("1")) {
			format = format.replace("hours", "hour");
		}

		if (minute.equals("1")) {
			format = format.replace("minutes", "minute");
		}

		if (second.equals("1")) {
			format = format.replace("seconds", "second");
		}

		format = ReadAndWrite.replaceTimeFormattedString(hour, minute, second, format);

		writeTimeToFileAux(filepath, format);

		return format;
	}

	/**
	 * Writes the required input to the designated file
	 * 
	 * @param filePath - Location where text should be written
	 * @param input    - string to be written to the file
	 */
	private static void writeTimeToFileAux(String filePath, String input) {

		if (filePath != null && input != null) {
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
	private static String removeUnusedTimeValues(String h, String m, String s, String format) {
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
			} else if (format.equals("[hour] hours, [minute] minutes, [second] seconds")) {
				newFormat = newFormat.substring("[hour] hours, ".length());
			}
		}

		if (totalTime < 60) {
			if (format.equals("[hour]:[minute]:[second]")) {
				newFormat = newFormat.substring("[minute]:".length());
			} else if (format.equals("[hour] hours, [minute] minutes, [second] seconds")) {
				newFormat = newFormat.substring("[minute] minutes, ".length());
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
	private static String replaceTimeFormattedString(String h, String m, String s, String format) {
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
	 * Helper method to calculate the time in seconds
	 * 
	 * @param h - Hour time value
	 * @param m - Minute time value
	 * @param s - Second time value
	 * @return - Primitive integer representing number of seconds
	 */
	private static int getTimeSeconds(String h, String m, String s) {
		int hour = Integer.parseInt(h);
		int minute = Integer.parseInt(m);
		int second = Integer.parseInt(s);

		int totalTime = (hour * 3600) + (minute * 60) + second;

		return totalTime;
	}

}
