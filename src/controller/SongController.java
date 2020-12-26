package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import view.MainGUI;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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

					// Read to manipulate the docString to find required tags
					String curr;
					String tag;
					Iterator<Entry<String, String[]>> it = values.entrySet().iterator();
					Map.Entry<String, String[]> pair;
					String output = format;
					
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

						if (curr.contains(tag)) {
							// Remove all HTML text before the tag we want to find
							curr = curr.substring(curr.indexOf(tag));
							// Narrow down the text
							curr = curr.substring(tag.length(), curr.indexOf("</info>"));
							// Removing leading and trailing whitespace. This is our targeted text
							curr = curr.trim();
							
							System.out.println("Output is currently: " + output);

							System.out.println("Output is now: " + output);
							System.out.println("Curr: " + curr);
							// Creating the regex to remove the [....] from the output string
							String re = pair.getKey();
							System.out.println("Regex: " + re);
							// Need to replace [ ] with \[ and \] for the use of regex
							re = re.replace("[", "\\[");
							System.out.println("Regex: " + re);
							re = re.replace("]", "\\]");
							System.out.println("Regex: " + re);
							values.get(pair.getKey())[1] = curr;
							output = output.replace(pair.getKey(), curr);
							System.out.println("Map: " + values.get(pair.getKey())[1]);
						}
					}
					
					System.out.println(values.keySet().toString());

					final String finaloutput;
					
					if (output.equals("")) { // None of the tags worked
						finaloutput = values.get("[filename]")[1];
					} else {
						finaloutput = output;
					}
					// Now that we have an ArrayList full of the text, we will now replace the []
					// tags in the format string

					Platform.runLater(() -> {
						textField.setText(finaloutput);
					});

					// Write to the text file song.txt
					writeToFile(finaloutput);

				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HttpStatusException e1) {
					Platform.runLater(() -> {

						Alert alert = new Alert(AlertType.INFORMATION, "Please reenter your Username and Password.",
								ButtonType.OK);
						alert.setTitle("Username/Password Error");
						alert.setHeaderText("Incorrect Login Credentials");

						alert.show();
					});
					stopTimer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, delay, period);
	}

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

	public void writeToFile(String input) throws FileNotFoundException {
		System.out.println("Writing " + input + " to file in " + directoryPath);
		// Writing to timer.txt file
		PrintWriter writer = new PrintWriter(directoryPath);
		writer.println(input);
		writer.close();
	}

	public boolean getIsRunning() {
		return this.isRunning;
	}

}
