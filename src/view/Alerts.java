package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

@SuppressWarnings("restriction")
public class Alerts {

	public static void badLoginCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION, "Please reenter your Username and Password.", ButtonType.OK);
		alert.setTitle("Username/Password Error");
		alert.setHeaderText("Incorrect Login Credentials");

		alert.show();
	}

	public static void badVLCConnection() {
		Alert alert = new Alert(AlertType.INFORMATION,
				"Unable to connect to VLC's HTTP server.\nPlease ensure VLC is open with web server enabled\nTools>Preferences>All>Main Interfaces>Web",
				ButtonType.OK);
		alert.setTitle("Connection Error");
		alert.setHeaderText("Unable to connect to HTTP");

		alert.show();
	}

	public static void badVLCDirectoryPath() {
		Alert alert = new Alert(AlertType.INFORMATION,
				"Please check directory path for VLC folder.\nExample: C:\\Program Files\\VideoLAN\\VLC",
				ButtonType.OK);
		alert.setTitle("Bad Directory Path");
		alert.setHeaderText("VLC.exe not found");

		alert.showAndWait();
	}

	public static void countdownNumbersOnly() {
		Alert alert = new Alert(AlertType.INFORMATION,
				"Please check fields for unneeded characters.\nOnly numbers are allowed in the fields.", ButtonType.OK);
		alert.setTitle("Countdown Error");
		alert.setHeaderText("Numeric Values Only in Fields");

		alert.show();
	}

}
