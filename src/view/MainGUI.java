package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import controller.ReadAndWrite;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.*;

@SuppressWarnings("restriction")
public class MainGUI extends Application {

	private Stage window;
	private BorderPane pane;
	public static String directoryPath = System.getProperty("user.dir");
	public static boolean darkMode;
	private TimeTab timeTab;
	private MusicTab musicTab;
	private PreferencesTab prefTab;

	/**
	 * Default Constructor that initializes instance variables
	 */
	public MainGUI() {
		this.window = new Stage();
		this.pane = new BorderPane();
		darkMode = true;
		this.timeTab = new TimeTab();
		this.musicTab = new MusicTab();
		this.prefTab = new PreferencesTab();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		int sceneWidth = 650, sceneHeight = 500;

		// If somehow there is no directory path
		if (directoryPath == null) {
			directoryPathNotFound();
			launchDirectoryChooser();
		}

		this.pane.setTop(createMenuBar());
		this.pane.setCenter(createTabs());

		System.out.println(directoryPath);
		Scene scene = new Scene(this.pane, sceneWidth, sceneHeight);

		this.window.setTitle("OBS Helper");
		this.window.setScene(scene);
		this.window.getScene().getRoot().setStyle("-fx-base:black");
		this.window.show();

		// User closes main window
		this.window.setOnCloseRequest(e -> closeProgram());
	}
	
	public void closeProgram() {
		try {
			ReadAndWrite.writeToFile(this.timeTab.getCountdownController().getFilePath(), "00", "00", "00",
					null, true);
			ReadAndWrite.writeToFile(this.timeTab.getStopwatchController().getFilePath(), "00", "00", "00",
					null, true);
			this.musicTab.getSongController().writeToFile("");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}

	/**
	 * Creates the menu bar for the Stage
	 * 
	 * @return a MenuBar object
	 */
	public MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();

		// Create Menu for File
		Menu file = new Menu("File");
		MenuItem save = new MenuItem("Save");
		MenuItem dark = new MenuItem("Disable Dark Mode");
		MenuItem exit = new MenuItem("Exit");

		// Create Menu for Help
		Menu help = new Menu("Help");
		MenuItem learnMore = new MenuItem("How to use");
		MenuItem about = new MenuItem("About");

		// Load MenuBar with menus
		menuBar.getMenus().addAll(file, help);
		file.getItems().addAll(save, dark, exit);
		help.getItems().addAll(learnMore, about);

		about.setOnAction(e -> {
			credits();
		});

		learnMore.setOnAction(e -> {
			helpMenu();
		});

		save.setOnAction(e -> {
			System.out.println("Save Button pressed");
		});

		dark.setOnAction(e -> {
			if (darkMode) {
				this.window.getScene().getRoot().setStyle("");
				darkMode = false;
				dark.setText("Enable Dark Mode");
			} else {
				this.window.getScene().getRoot().setStyle("-fx-base:black");
				darkMode = true;
				dark.setText("Disable Dark Mode");
			}

		});

		exit.setOnAction(e -> {
			System.exit(0);
		});

		return menuBar;
	}
	
	/**
	 * Creates all the tabs required for the GUI
	 * 
	 * @return a TabPane containing required Tabs
	 */
	public TabPane createTabs() {
		TabPane pane = new TabPane();
		pane.getTabs().add(this.timeTab.createTimeTab());
		pane.getTabs().add(this.musicTab.createMusicTab());
		pane.getTabs().add(this.prefTab.createPreferencesTab());

		pane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		return pane;
	}

	/**
	 * Creates the music tab
	 * 
	 * @return a tab containing the music interface
	 */
	public Tab musicTab() {
		Tab music = new Tab("Stream Music");
		music.setTooltip(new Tooltip("Menu for Stream Music"));

		VBox musicInformation = new VBox(10);
		musicInformation.setAlignment(Pos.CENTER);
		Label musicTitle = new Label("Stream Music Information");
		musicTitle.setFont(new Font(16));

		HBox musicDetails = new HBox(10);
		musicDetails.setAlignment(Pos.CENTER);
		Label songTitle = new Label("Current Song:");
		TextField songField = new TextField();
		songField.setEditable(false);

		Button stopButton = new Button("Stop");
		stopButton.setOnAction(e -> {

		});

		Button pausePlayButton = new Button("Play");
		pausePlayButton.setOnAction(e -> {

		});

		Button selectSongButton = new Button("Select Song");
		selectSongButton.setOnAction(e -> {

		});

		musicDetails.getChildren().addAll(songTitle, songField, stopButton, pausePlayButton, selectSongButton);
		musicInformation.getChildren().addAll(musicTitle, musicDetails);

		music.setContent(musicInformation);

		return music;
	}

	/**
	 * An alert that is ran if the required files are not found in the current
	 * directory
	 * 
	 * @return an alert about how the directory path is not correct
	 */
	protected Alert directoryPathNotFound() {
		Alert alert = new Alert(AlertType.INFORMATION,
				"Unable to find the directory containing the Twitch Stream files"
						+ "\nPlease select the directory containing the files, or create a new directory.",
				ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("No Directory Path");
		alert.setHeaderText("Unable to locate Twitch Stream directory");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.CANCEL) {
			alert = new Alert(AlertType.INFORMATION, "Unable to start program. Please select a directory.");
			alert.setTitle("Closing Program");
			alert.setHeaderText("Directory Required");
			alert.showAndWait();
			System.exit(0);
		}

		return alert;
	}

	protected void credits() {
		Alert alert = new Alert(AlertType.INFORMATION,
				"Created by Brenton Haliw\nBrenton.Haliw@gmail.com\nhttps://www.github.com/bjhaliw", ButtonType.OK);
		alert.setTitle("Credits");
		alert.setHeaderText("Thank you for trying me!");

		alert.show();
	}

	public void helpMenu() {
		HelpMenu menu = new HelpMenu();
		Scene scene = new Scene(menu.createHelpMenu(), 500, 500);
		Stage stage = new Stage();
		stage.setScene(scene);
		
		if (MainGUI.darkMode) {
			stage.getScene().getRoot().setStyle("-fx-base:black");
		}
		stage.setTitle("How to Use");
		stage.show();
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

	/**
	 * Main method to run the OBS Helper program
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

}
