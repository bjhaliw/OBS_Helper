package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.*;
import view.MainGUI;
import javafx.application.Platform;

/**
 * This class creates the controller to keep track of the countdown timer.
 * Ensures that the GUI is continuously updated with real time information on
 * the countdown.
 * 
 * @author Brenton Haliw
 *
 */
@SuppressWarnings("restriction")
public class CountdownController {
	private int interval;
	private Timer timer;
	private File file;
	private boolean isRunning;
	private String filePath;
	private TextField hoursField, minutesField, secondsField;

	/**
	 * Constructor method for the TimerController class. Takes in primitive integers
	 * for the hours, minutes, and seconds of the countdown. Also takes in a String
	 * representation of the filePath where the program should create a text file to
	 * keep track of the current time.
	 * 
	 * @param filePath String representation of the file to be written
	 * @param hours    Primitive integer of the number of hours
	 * @param minutes  Primitive integer of the number of minutes
	 * @param seconds  Primitive integer of the number of seconds
	 */
	public CountdownController(int hours, int minutes, int seconds, TextField hoursField, TextField minutesField,
			TextField secondsField) {
		seconds += (minutes * 60) + (hours * 3600);

		// Creating a new text file for the timer if required
		this.filePath = MainGUI.directoryPath + "\\countdown.txt";
		this.file = new File(this.filePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Init instance variables
		this.interval = seconds;
		this.timer = new Timer();
		this.isRunning = false;
		this.hoursField = hoursField;
		this.minutesField = minutesField;
		this.secondsField = secondsField;
	}

	/**
	 * Cancels the current timer and then sets the instance variable isRunning to
	 * false. Allows the TextFields in GUI class to become editable once more.
	 * 
	 * @throws FileNotFoundException
	 */
	public void cancelCountdown() {
		this.timer.cancel();
		this.timer.purge();
		this.isRunning = false;
		this.hoursField.setEditable(true);
		this.minutesField.setEditable(true);
		this.secondsField.setEditable(true);
	}

	/**
	 * Primary function of the TimerController class. Takes in the TextFields from
	 * the GUI to update them in real time. Has a nested run() function to run the
	 * timer on a separate thread. If the amount of time in seconds is greater than
	 * zero then the boolean instance variable isRunning is set to true, and then
	 * set to false at the end of the method. Sets TextFields ability to be edited
	 * to false
	 * 
	 */
	public synchronized void runTimer() {
		int delay = 1000;
		int period = 1000;

		this.hoursField.setEditable(false);
		this.minutesField.setEditable(false);
		this.secondsField.setEditable(false);

		if (this.interval > 0) {
			this.isRunning = true;
			this.timer.scheduleAtFixedRate(new TimerTask() {

				public void run() {

					try {
						// System.out.println(isRunning);
						int hours = 0, minutes = 0, seconds = 0, time = 0;
						time = setInterval();
						// System.out.println("Time:" + time);

						// Updating amount of hours left
						hours = time / 3600;
						time -= (hours * 3600);

						// Updating amount of minutes left
						minutes = time / 60;
						time -= (minutes * 60);

						// Updating amount of seconds left
						seconds = time;

						// Updating the TextFields from the GUI
						String h = ReadAndWrite.formatIntToString(hours);
						String m = ReadAndWrite.formatIntToString(minutes);
						String s = ReadAndWrite.formatIntToString(seconds);

						Platform.runLater(() -> {
							hoursField.setText(h);
							minutesField.setText(m);
							secondsField.setText(s);
						});

						writeToFile(h, m, s);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, delay, period);

		}
	}

	public void writeToFile(String h, String m, String s) throws FileNotFoundException {
		// Writing to timer.txt file
		PrintWriter writer = new PrintWriter(filePath);
		writer.println(h + ":" + m + ":" + s);
		writer.close();
	}


	/**
	 * Helper method ran inside of the runTimer method. Decrements time by one
	 * second. If at the end of the timer, cancels it.
	 * 
	 * @return Primitive integer representing seconds left
	 * @throws FileNotFoundException
	 */
	private final int setInterval() throws FileNotFoundException {
		// Check if at the end of the time
		if (this.interval == 1) {
			writeToFile("00", "00", "00");
			cancelCountdown();
		}
		return --this.interval;
	}

	public int getInterval() {
		return this.interval;
	}

	/**
	 * Tells whether or not the countdown is currently running
	 * 
	 * @return Boolean isRunning instance variable, true if running, false if not.
	 */
	public boolean getIsRunning() {
		return this.isRunning;
	}

	/**
	 * User input to set a new Countdown with updated time.
	 * 
	 * @param hours   - Primitive integer representing number of hours
	 * @param minutes - Primitive integer representing number of minutes
	 * @param seconds - Primitive integer representing number of seconds
	 */
	public void setCountdown(int hours, int minutes, int seconds) {
		seconds += (minutes * 60) + (hours * 3600);
		this.interval = seconds;
		timer = new Timer();
	}

}