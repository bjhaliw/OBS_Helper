package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import controller.SongController;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.collections.*;

@SuppressWarnings("restriction")
public class MusicTab {

	public static String VLCdirectoryPath;
	private SongController songController;

	public MusicTab() {
		this.songController = new SongController();
	}

	/**
	 * Creates the music tab
	 * 
	 * @return a tab containing the music interface
	 */
	public Tab createMusicTab() {
		Tab music = new Tab("Music Information");
		music.setTooltip(new Tooltip("Menu for Stream Music"));

		VBox musicInformation = new VBox(10);
		musicInformation.setAlignment(Pos.CENTER);
		Label musicTitle = new Label("Stream Music Information");
		musicTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		musicTitle.setStyle("-fx-underline: true ;");
		musicTitle.setAlignment(Pos.CENTER);

		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(10);
		Label username = new Label("Username");
		TextField userField = new TextField();
		pane.add(username, 0, 0);
		pane.add(userField, 1, 0);
		GridPane.setHalignment(username, HPos.RIGHT);

		Label password = new Label("Password");
		PasswordField passField = new PasswordField();
		pane.add(password, 0, 1);
		pane.add(passField, 1, 1);
		GridPane.setHalignment(password, HPos.RIGHT);

		Label VLCPath = new Label("VLC Directory Path");
		VBox box = new VBox(2);
		box.setAlignment(Pos.CENTER);
		TextField pathField = new TextField();
		Label examplePath = new Label("Example: C:\\Program Files\\VideoLAN\\VLC");
		box.getChildren().addAll(pathField, examplePath);
		Button select = new Button("Select Path");
		select.setTooltip(new Tooltip("Location of VLC folder"));
		pane.add(VLCPath, 0, 2);
		pane.add(box, 1, 2);
		pane.add(select, 2, 2);
		GridPane.setValignment(VLCPath, VPos.BASELINE);
		GridPane.setValignment(box, VPos.BASELINE);
		GridPane.setValignment(select, VPos.TOP);
		GridPane.setHalignment(VLCPath, HPos.RIGHT);
		select.setOnAction(e -> {
			launchDirectoryChooser();
			pathField.setText(VLCdirectoryPath);
		});

		HBox buttonBox = new HBox(20);
		buttonBox.setAlignment(Pos.CENTER);
		Button openVLC = new Button("Open VLC Player");
		openVLC.setTooltip(new Tooltip("Open VLC Media Player"));
		buttonBox.getChildren().addAll(openVLC);
		pane.add(buttonBox, 0, 3, 3, 1);
		openVLC.setOnAction(e -> {
			try {
				Runtime.getRuntime().exec(VLCdirectoryPath + "\\vlc.exe");
			} catch (IOException e1) {
				Alerts.badVLCDirectoryPath();
				launchDirectoryChooser();
				pathField.setText(VLCdirectoryPath);
			}
		});

		Separator sep = new Separator();

		GridPane bottomPane = new GridPane();
		bottomPane.setHgap(10);
		bottomPane.setVgap(10);
		bottomPane.setAlignment(Pos.CENTER);

		Label note = new Label("NOTE");
		note.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
		Label warning = new Label("Some songs may not have desired parameters. Output may be blank.");

		Label format = new Label("Text File Format");
		ComboBox<String> combo = new ComboBox<>();
		combo.setMaxWidth(Double.MAX_VALUE);
		combo.setItems(createComboList());
		combo.setEditable(true);
		combo.setPromptText("Type or Select Format");
		Button help = new Button("Format Help");
		help.setTooltip(new Tooltip("New window for format help"));
		bottomPane.add(format, 0, 0, 1, 1);
		bottomPane.add(combo, 1, 0, 1, 1);
		bottomPane.add(help, 2, 0, 1, 1);
		GridPane.setHalignment(format, HPos.RIGHT);
		help.setOnAction(e -> {
			launchFormatStage();
		});

		Label currentLabel = new Label("Current Song");
		TextField currentField = new TextField();
		currentField.setEditable(false);
		bottomPane.add(currentLabel, 0, 1, 1, 1);
		bottomPane.add(currentField, 1, 1, 2, 1);
		GridPane.setHalignment(currentLabel, HPos.RIGHT);

		HBox startStopBox = new HBox(20);
		startStopBox.setAlignment(Pos.CENTER);
		Button start = new Button("Start Music Capture");
		Button stop = new Button("Stop Music Capture");
		start.setTooltip(new Tooltip("Begin recording song capture"));
		stop.setTooltip(new Tooltip("Stop recording song capture"));

		startStopBox.getChildren().addAll(start, stop);
		bottomPane.add(startStopBox, 0, 3, 3, 1);

		Label running = new Label("Program is Running");
		running.setVisible(false);
		Label notRunning = new Label("Program is Not Running");
		bottomPane.add(notRunning, 0, 4, 3, 1);
		bottomPane.add(running, 0, 4, 3, 1);
		GridPane.setHalignment(notRunning, HPos.CENTER);
		GridPane.setHalignment(running, HPos.CENTER);

		start.setOnAction(e -> {
			HashMap<String, String[]> map = this.songController.createHashMap(combo.getValue());

			this.songController.runMusicCapture(currentField, userField.getText(), passField.getText(),
					combo.getValue(), map);

			running.setVisible(true);
			notRunning.setVisible(false);
			start.setDisable(true);

		});

		stop.setOnAction(e -> {
			this.songController.stopTimer();
			notRunning.setVisible(true);
			running.setVisible(false);
			start.setDisable(false);
		});

		musicInformation.getChildren().addAll(musicTitle, pane, sep, note, warning, bottomPane);
		music.setContent(musicInformation);
		return music;
	}

	/**
	 * Creates an ObservableList<String> object containing example formats for the
	 * user to choose how they want the music capture to print out.
	 * 
	 * @return ObservableList<String> containing predefined example formats
	 */
	public ObservableList<String> createComboList() {
		ObservableList<String> list = FXCollections.observableArrayList();

		list.add("[artist] - [title]");
		list.add("[title]");
		list.add("Now Playing: [artist] - [title]");
		list.add("Current song: [title]");

		return list;
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
		VLCdirectoryPath = filePath.getAbsolutePath();
		if (!filePath.exists()) {
			try {
				Files.createDirectories(Paths.get(VLCdirectoryPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Launches a new window with information on the different markdown
	 * tags used to pull song data from VLC media player.
	 */
	public void launchFormatStage() {
		BorderPane pane = new BorderPane();
		Label title = new Label("Song Capturing Format");
		title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		title.setStyle("-fx-underline: true ;");

		VBox box = new VBox(25);
		box.setAlignment(Pos.CENTER);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		Label albumLabel = new Label("[album]");
		Label artistLabel = new Label("[artist]");
		Label copyrightLabel = new Label("[copyright]");
		Label dateLabel = new Label("[date]");
		Label filenameLabel = new Label("[filename]");
		Label genreLabel = new Label("[genre]");
		Label titleLabel = new Label("[title]");
		Label tracknumberLabel = new Label("[track number]");

		grid.add(albumLabel, 0, 0);
		grid.add(artistLabel, 0, 1);
		grid.add(copyrightLabel, 0, 2);
		grid.add(dateLabel, 0, 3);
		grid.add(filenameLabel, 0, 4);
		grid.add(genreLabel, 0, 5);
		grid.add(titleLabel, 0, 6);
		grid.add(tracknumberLabel, 0, 7);

		GridPane.setHalignment(albumLabel, HPos.RIGHT);
		GridPane.setHalignment(artistLabel, HPos.RIGHT);
		GridPane.setHalignment(copyrightLabel, HPos.RIGHT);
		GridPane.setHalignment(dateLabel, HPos.RIGHT);
		GridPane.setHalignment(filenameLabel, HPos.RIGHT);
		GridPane.setHalignment(genreLabel, HPos.RIGHT);
		GridPane.setHalignment(titleLabel, HPos.RIGHT);
		GridPane.setHalignment(tracknumberLabel, HPos.RIGHT);

		Label albumText = new Label("Displays the album's name.");
		Label artistText = new Label("Displays the artist's name.");
		Label copyrightText = new Label("Displays copyright information.");
		Label dateText = new Label("Displays the date associated with the song.");
		Label filenameText = new Label("Displays the file's name.");
		Label genreText = new Label("Displays the genre the song belongs to.");
		Label titleText = new Label("Displays the name of the song.");
		Label tracknumberText = new Label("Displays what number the song is on the album.");

		grid.add(albumText, 1, 0, 2, 1);
		grid.add(artistText, 1, 1, 2, 1);
		grid.add(copyrightText, 1, 2, 2, 1);
		grid.add(dateText, 1, 3, 2, 1);
		grid.add(filenameText, 1, 4, 2, 1);
		grid.add(genreText, 1, 5, 2, 1);
		grid.add(titleText, 1, 6, 2, 1);
		grid.add(tracknumberText, 1, 7, 2, 1);

		box.getChildren().addAll(title, grid);
		pane.setCenter(box);

		Scene scene = new Scene(pane, 500, 300);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setTitle("Text Format Help");

		if (MainGUI.darkMode) {
			stage.getScene().getRoot().setStyle("-fx-base:black");
		}

		stage.show();
	}

}
