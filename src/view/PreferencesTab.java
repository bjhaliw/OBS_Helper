package view;

import controller.CountdownController;
import controller.StopwatchController;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.geometry.Orientation;
@SuppressWarnings("restriction")
public class PreferencesTab {

	public PreferencesTab() {
		
	}
	
	public Tab createPreferencesTab() {		
		Tab tab = new Tab("Preferences");
		
		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
		Label title = new Label("Preferences Menu");
		title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		title.setStyle("-fx-underline: true ;");
		
		
		box.getChildren().addAll(title);
		tab.setContent(box);
		return tab;
	}
}
