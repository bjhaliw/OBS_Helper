package view;

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

	private Tab createWelcomeTab() {
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

	private Tab createFirstTimeTab() {
		Tab tab = new Tab("First Time Use");
		
		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
		Label title = new Label("First Time Use");
		title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		title.setStyle("-fx-underline: true ;");
		
		Label msg = new Label("To start using the tool, ensure that you have set \nup your VLC Media Player to connect to the Internet.\n"
				+ "This can be done by: \nTools>Preferences>Show Settings All (bottom left)>Main Interfaces>Select \"Web\">Lua>Enter a password ");
		
		box.getChildren().addAll(title, msg);
		
		tab.setContent(box);

		return tab;
	}

	private Tab createCountdownTab() {
		Tab tab = new Tab("Countdown");

		return tab;
	}

	private Tab createStopwatchTab() {
		Tab tab = new Tab("Stopwatch");

		return tab;
	}

	private Tab createMusicTab() {
		Tab tab = new Tab("Music");

		return tab;
	}

}
