package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import view.MainGUI;

/**
 * Allows for the control of the stopwatch portion of the TimeTab and for
 * the user to keep track of how long their stream has been active for.
 * @author Brenton Haliw
 *
 */
@SuppressWarnings("restriction")
public class StopwatchController  {
	private Timer timer;
	private File file;
	private boolean isRunning;
	private String filePath;
	private final TextField hoursField, minutesField, secondsField;
	private int interval;
	
	/**
	 * Constructor for the StopwatchController class. Creates a new file called stopwatch.txt
	 * for the time to be saved to.
	 * @param hoursField
	 * @param minutesField
	 * @param secondsField
	 */
	public StopwatchController(TextField hoursField, TextField minutesField, TextField secondsField) {
		
		this.filePath = MainGUI.directoryPath + "\\stopwatch.txt";
		file = new File(this.filePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.hoursField = hoursField;
		this.minutesField = minutesField;
		this.secondsField = secondsField;
		this.interval = 0;
		this.timer = new Timer();
	}
	
	/**
	 * Creates a new Timer and runs the new thread.
	 */
	public void startStopwatch() {
		int delay = 1000;
		int period = 1000;
		
		this.timer = new Timer();
		this.isRunning = true;
		this.timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {

				try {
					// System.out.println(isRunning);
					int hours = 0, minutes = 0, seconds = 0, time = 0;
					time = ++interval;

					System.out.println("Time:" + time);

					// Updating amount of hours left
					hours = time / 3600;
					time -= (hours * 3600);

					// Updating amount of minutes left
					minutes = time / 60;
					time -= (minutes * 60);

					// Updating amount of seconds left
					seconds = time;

					// Getting String representations of the current
					// hours, minutes, and seconds
					String h = ReadAndWrite.formatIntToString(hours);
					String m = ReadAndWrite.formatIntToString(minutes);
					String s = ReadAndWrite.formatIntToString(seconds);

					// Writes to the applicable TextFields
					Platform.runLater(() -> {
						hoursField.setText(h);
						minutesField.setText(m);
						secondsField.setText(s);
					});

					// Writing to the stopwatch.txt file
					writeToFile(h, m, s);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, delay, period);
	}
	
	/**
	 * Writes the hours, minutes, and seconds to the file 
	 * @param h - Hours
	 * @param m - Minutes
	 * @param s - Seconds
	 * @throws FileNotFoundException
	 */
	public void writeToFile(String h, String m, String s) throws FileNotFoundException {
		// Writing to timer.txt file
		PrintWriter writer = new PrintWriter(filePath);
		writer.println(h + ":" + m + ":" + s);
		writer.close();
	}
	
	/**
	 * Setter for the interval instance variable
	 * @param interval - seconds of the interval
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public int getInterval() {
		return this.interval;
	}
	
	/**
	 * Pauses the Stopwatch but keeps the interval
	 */
	public void pauseStopwatch() {
		this.timer.cancel();
		this.timer.purge();
		this.isRunning = false;
	}
	
	/**
	 * Pauses the Stopwatch and resets the interval to 0
	 */
	public void resetStopwatch() {
		pauseStopwatch();
		this.interval = 0;
	}
	
	public boolean getIsRunning() {
		return this.isRunning;
	}

}
