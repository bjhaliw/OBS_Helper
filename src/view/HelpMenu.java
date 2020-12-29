package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
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
public class HelpMenu {

	public TabPane createHelpMenu() {
		TabPane tabPane = new TabPane();

		tabPane.getTabs().addAll(createWelcomeTab(), createFirstTimeTab(), createCountdownTab(), createStopwatchTab(),
				createMusicTab());
		
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		return tabPane;
	}

	public Tab createWelcomeTab() {
		Tab tab = new Tab("Welcome");

		
		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
		Label title = new Label("OBS Helper Tutorial");
		title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		title.setStyle("-fx-underline: true ;");

		Label msg = new Label(
				"Welcome to the OBS Helper tool.\n\nThis program allows users to create text documents in order to overlay\ntext on "
						+ "their screens through the use of OBS software.\n\nPlease select one of the following tabs to learn more about how the tool works.");

		box.getChildren().addAll(title, msg);
		
		tab.setContent(box);
		return tab;
	}

	public Tab createFirstTimeTab() {
		Tab tab = new Tab("First Time Use");

		return tab;
	}

	public Tab createCountdownTab() {
		Tab tab = new Tab("Countdown");

		return tab;
	}

	public Tab createStopwatchTab() {
		Tab tab = new Tab("Stopwatch");

		return tab;
	}

	public Tab createMusicTab() {
		Tab tab = new Tab("Music");

		return tab;
	}

	public Tab createCountdownTabHelp() {
		Tab countdown = new Tab("Countdown");

		return countdown;
	}

}
