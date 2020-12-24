package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javafx.application.Platform;

@SuppressWarnings("restriction")
public class SongController {
	private String songName, directoryPath;
	private boolean isRunning;
	private Timer timer;

	public SongController(String directoryPath) {
		this.directoryPath = directoryPath + "\\song.txt";
		File file = new File(directoryPath);

		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.songName = "No Song Playing";
		this.isRunning = false;
		this.timer = new Timer();
	}

	public void runTimer(TextField textField, String userName, String password) {
		int delay = 1000;
		int period = 1000;
		this.timer = new Timer();
		this.isRunning = true;
		this.timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				String url = "http://localhost:8080/requests/status.xml";
				String authString = userName + ":" + password;
				org.jsoup.nodes.Element line;
				String string;
				try {
					Document document = Jsoup.connect(url).header("Authorization",
							"Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authString.getBytes())).get();

					for (int i = 0; i < document.getAllElements().size(); i++) {
						line = document.getAllElements().get(i);
						string = line.html();
						if (string.contains("<info name=\"title\">")) {
							final String songName = setSongName(string);
							writeToFile();

							Platform.runLater(() -> {
								textField.setText(songName);
							});
							break;
						}
					}
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

	public final String setSongName(String string) {
		String name;
		string = string.substring(string.indexOf("<info name=\"title\">"));
		string = string.substring(20, string.indexOf("</info>") - 2);
		name = string.trim();
		this.songName = name;

		return name;
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

	public void writeToFile() throws FileNotFoundException {
		// Writing to timer.txt file
		PrintWriter writer = new PrintWriter(directoryPath);
		writer.println(this.songName);
		writer.close();
	}

	public boolean getIsRunning() {
		return this.isRunning;
	}

	public String getSongName() {
		return this.songName;
	}

}
