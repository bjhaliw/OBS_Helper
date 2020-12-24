package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import controller.SongController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.text.FontWeight;

@SuppressWarnings("restriction")
public class MusicTab {

	private static String directoryPath;
	private static String songName = "";
	private SongController songController;

	public MusicTab(String path) {
		this.songController = new SongController(path);
	}

	/**
	 * Creates the music tab
	 * 
	 * @return a tab containing the music interface
	 */

	public Tab createMusicTab() {
		Tab music = new Tab("Stream Music");
		music.setTooltip(new Tooltip("Menu for Stream Music"));

		VBox musicInformation = new VBox(10);
		musicInformation.setAlignment(Pos.CENTER);
		Label musicTitle = new Label("Stream Music Information");
		musicTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		musicTitle.setStyle("-fx-underline: true ;");

		// Creating Directory Information Box
		HBox pathDetails = new HBox(10);
		pathDetails.setAlignment(Pos.BASELINE_CENTER);
		VBox pathBox = new VBox(2);
		pathBox.setAlignment(Pos.CENTER);
		Label vlcDir = new Label("VLC Directory Path");
		TextField pathField = new TextField();
		Label examplePath = new Label("Example: C:\\Program Files\\VideoLAN\\VLC");
		pathField.setEditable(false);
		pathBox.getChildren().addAll(pathField, examplePath);

		Button selectPath = new Button("Select Path");
		selectPath.setOnAction(e -> {
			launchDirectoryChooser();
			pathField.setText(directoryPath);
		});

		pathDetails.getChildren().addAll(vlcDir, pathBox, selectPath);

		GridPane gPane = new GridPane();
		Label userName = new Label("Username");
		Label passWord = new Label("Password");
		TextField userField = new TextField();
		PasswordField passField = new PasswordField();

		gPane.add(userName, 0, 0);
		gPane.add(userField, 1, 0);
		gPane.add(passWord, 0, 1);
		gPane.add(passField, 1, 1);
		gPane.setHgap(10);
		gPane.setVgap(10);
		gPane.setAlignment(Pos.CENTER);

		// Creating Current Song Box
		HBox songDetails = new HBox(10);
		songDetails.setAlignment(Pos.CENTER);
		TextField songField = new TextField();
		songField.setEditable(false);

		Button start = new Button("Start Music Capture");
		start.setOnAction(e -> {
			if (this.songController.getIsRunning() == false) {
				this.songController.runTimer(songField, userField.getText(), passField.getText());
			}

		});

		Button stop = new Button("Stop Music Capture");
		stop.setOnAction(e -> {
			this.songController.stopTimer();
		});

		songDetails.getChildren().addAll(songField, start, stop);

		musicInformation.getChildren().addAll(musicTitle, pathDetails, gPane, songDetails);

		music.setContent(musicInformation);

		return music;
	}

	/**
	 * Launches a directory chooser for the user to set where their files are
	 */
	public void launchDirectoryChooser() {
		Stage stage = new Stage();

		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Select Twitch Application Directory");
		stage.setAlwaysOnTop(true);

		File directory = chooser.showDialog(stage);

		if (directory == null) {
			System.out.println("User backed out without selecting a directory");
			return;
		}

		File filePath = new File(directory.getAbsolutePath());
		directoryPath = filePath.getAbsolutePath();
		if (!filePath.exists()) {
			try {
				Files.createDirectories(Paths.get(directoryPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
