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
	 * Replaces the time value tags in the desired formatted string.
	 * 
	 * @param h      - hour time value
	 * @param m      - minute time value
	 * @param s      - second time value
	 * @param format - String with tags to be replaced by values
	 * @return - a String with the tags replaced by the time values
	 */
	public static String replaceTimeFormattedString(String h, String m, String s, String format) {
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
}
