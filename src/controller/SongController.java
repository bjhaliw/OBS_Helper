package controller;

import javafx.scene.control.TextField;
import view.Alerts;
import view.MainGUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javafx.application.Platform;

@SuppressWarnings("restriction")
public class SongController {
	private String directoryPath;
	private boolean isRunning;
	private Timer timer;

	/**
	 * Default constructor for the SongController class. Initializes instance
	 * variables and then creates a new song.txt file if required.
	 */
	public SongController() {
		this.directoryPath = MainGUI.directoryPath + "\\song.txt";
		File file = new File(directoryPath);

		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.isRunning = false;
		this.timer = new Timer();
	}

	/**
	 * Creates a HashMap<String, String[2]> that matches with the values that the
	 * user added in the "format" parameter. Loads the HashMap with the required
	 * values as the keys and then a String array containing the HTML tag and the
	 * associated text.
	 * 
	 * @param format - A String representing how the user wants the text displayed
	 * @return a HashMap<String, String[]> with the required values loaded into it
	 */
	public HashMap<String, String[]> createHashMap(String format) {
		HashMap<String, String[]> map = new HashMap<>();

		// Default will always be the file name
		map.put("[filename]", new String[] { "<info name=\"filename\">", "" });

		// Null if there was no ComboBox selection or the user entered an empty string
		if (format == null || format.equals("")) {
			return map; // Just the map with the [filename] key
		} else {

			// Note, it's not a guarantee that these tags will be found in the XML file
			// Output will be blank if it is not found in the main loop
			if (format.contains("[artist]")) {
				map.put("[artist]", new String[] { "<info name=\"artist\">", "" });
			}

			if (format.contains("[title]")) {
				map.put("[title]", new String[] { "<info name=\"title\">", "" });
			}

			if (format.contains("[album]")) {
				map.put("[album]", new String[] { "<info name=\"album\">", "" });
			}

			if (format.contains("[track number]")) {
				map.put("[track number]", new String[] { "<info name=\"track_number\">", "" });
			}

			if (format.contains("[copyright]")) {
				map.put("[copyright]", new String[] { "<info name=\"copyright\">", "" });
			}

			if (format.contains("[genre]")) {
				map.put("[genre]", new String[] { "<info name=\"genre\">", "" });
			}

			if (format.contains("[date]")) {
				map.put("[date]", new String[] { "<info name=\"date\">", "" });
			}

			return map;

		}

	}

	/**
	 * Starts the music collection process. Reads the HTML from VLC's web server and
	 * then parses through the HTML for the tags requested by the user. Once found,
	 * they are added to a HashMap and then manipulated in order to produce desired
	 * output. Writes the output to the TextField passed in and then writes to the
	 * song.txt file.
	 * 
	 * @param textField TextField from the GUI to have its text updated
	 * @param userName  Required for login to VLC Media Player's HTML server
	 * @param password  Required for login to VLC Media Player's HTML server
	 * @param format    A string representing how the user wants the format
	 * @param values    HashMap<String, String[]>,
	 */
	public void runMusicCapture(TextField textField, String userName, String password, String format,
			HashMap<String, String[]> values) {
		int delay = 1000;
		int period = 1000;
		this.timer = new Timer();
		this.isRunning = true;
		System.out.println("Keys: " + values.keySet().toString());
		this.timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				String url = "http://localhost:8080/requests/status.xml";
				String authString = userName + ":" + password;
				try {
					// Log into the website
					Document document = Jsoup.connect(url).header("Authorization",
							"Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authString.getBytes())).get();

					
					// Create a toString of the HTML
					final String docString = document.toString();
					
					String mediaStatus = new String(docString);
					
					if (mediaStatus.contains("<state>")) {
						System.out.println("Found state");
						mediaStatus = mediaStatus.substring(mediaStatus.indexOf("<state>"));
						
						mediaStatus = mediaStatus.substring("<state>".length(), mediaStatus.indexOf("</state>"));
						mediaStatus = mediaStatus.trim();
						System.out.println("Status: " + mediaStatus);
					}
					
					//System.out.println(docString + "\n\n");

					if (mediaStatus.equals("stopped")) {
						System.out.println("Stopped");
						Platform.runLater(() -> {
							textField.setText("");
						});
						writeToFile("");
					} else {

						// Read to manipulate the docString to find required tags
						String curr;
						String tag;
						Iterator<Entry<String, String[]>> it = values.entrySet().iterator();
						Map.Entry<String, String[]> pair;
						String output = format;
						int counter = 0;

						if (format == null) {
							output = "";
						}
						/*
						 * Iterates through the HashMap looking for the required tags. Initializes the
						 * second index of the array with the name.
						 */
						while (it.hasNext()) {
							curr = new String(docString);
							pair = (Map.Entry<String, String[]>) it.next();
							tag = pair.getValue()[0];

							// If the current tag is found within the document
							if (curr.contains(tag)) {
								// Remove all HTML text before the tag we want to find
								curr = curr.substring(curr.indexOf(tag));
								// Narrow down the text
								curr = curr.substring(tag.length(), curr.indexOf("</info>"));
								// Removing leading and trailing whitespace. This is our targeted text
								curr = curr.trim();

								// Adding the targeted text to the required key's value
								values.get(pair.getKey())[1] = curr;
								output = output.replace(pair.getKey(), curr);
								System.out.println("Map: " + values.get(pair.getKey())[1]);

							} else { // Current tag was not found in the document
								// Replace the tag with a blank string
								output = output.replace(pair.getKey(), "");
								// Increase the counter to state how many tags were not found
								counter++;
								System.out.println("Counter: " + counter + "Map Size: " +  values.size());
							}
						}

						// Creating a final String to display the output to the TextField in the
						// MusicTab
						final String finaloutput;

						if (output.equals("") || counter > values.size() - 2) { // None of the tags worked
							finaloutput = values.get("[filename]")[1];
						} else {
							finaloutput = output;
						}

						// Editing the text of the TextField from the MusicTab
						Platform.runLater(() -> {
							textField.setText(finaloutput);
						});

						// Write to the text file song.txt
						writeToFile(finaloutput);
						counter = 0;
					}

				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ConnectException e1) {
					Platform.runLater(() -> {
						Alerts.badVLCConnection();
					});	
					e1.printStackTrace();
					stopTimer();
				} catch (HttpStatusException e1) {
					Platform.runLater(() -> {
						Alerts.badLoginCredentials();
					});
					stopTimer();
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}, delay, period);
	}

	/**
	 * Stops and deletes the current timer that is being used for the class. Writes
	 * and empty string to the song.txt file
	 */
	public void stopTimer() {
		this.timer.cancel();
		this.timer.purge();
		this.isRunning = false;
		try {
			PrintWriter writer = new PrintWriter(directoryPath);
			writer.println("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Writes the updated format to the song.txt file in the directory that the .exe
	 * file is located.
	 * 
	 * @param input - Formatted string for the user's song information
	 * @throws FileNotFoundException
	 */
	public void writeToFile(String input) throws FileNotFoundException {
		System.out.println("Writing " + input + " to file in " + directoryPath);
		// Writing to timer.txt file
		PrintWriter writer = new PrintWriter(directoryPath);
		writer.println(input);
		writer.close();
	}

	/**
	 * Getter method for the isRunning instance variable. True if the program is
	 * actively monitoring the music information and false if it is not.
	 * 
	 * @return boolean depending on if currently capturing music info
	 */
	public boolean getIsRunning() {
		return this.isRunning;
	}
	
	public String getDirectoryPath() {
		return this.directoryPath;
	}

}
