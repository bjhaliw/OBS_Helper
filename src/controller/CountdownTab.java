package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import javafx.event.ActionEvent;

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
public class CountdownTab {

	private static String directoryPath;
	private TimerController timer;
	private TextField timerHoursField, timerMinutesField, timerSecondsField;

	public CountdownTab(String path, TimerController timer) {
		directoryPath = path;
		this.timer = timer;
		timerHoursField = new TextField();
		timerHoursField.setMaxWidth(50);
		timerHoursField.setAlignment(Pos.CENTER);
		timerHoursField.setText("00");
		timerHoursField.setTooltip(new Tooltip("Insert Number of Hours"));

		timerMinutesField = new TextField();
		timerMinutesField.setMaxWidth(50);
		timerMinutesField.setAlignment(Pos.CENTER);
		timerMinutesField.setText("00");
		timerMinutesField.setTooltip(new Tooltip("Insert Number of Minutes"));

		timerSecondsField = new TextField();
		timerSecondsField.setMaxWidth(50);
		timerSecondsField.setAlignment(Pos.CENTER);
		timerSecondsField.setText("00");
		timerSecondsField.setTooltip(new Tooltip("Insert Number of Seconds"));
	}

	/**
	 * Creates the countdown tab to be used in the main GUI along with the required
	 * buttons
	 * 
	 * @return a tab containing the countdown interface
	 */
	public Tab createCountdownTab() {
		Tab countdown = new Tab("Countdown Timer");
		countdown.setTooltip(new Tooltip("Menu for Countdown"));

		// Creating Countdown Interface
		VBox timerVBox = new VBox(10);
		timerVBox.setAlignment(Pos.CENTER);
		Label timerTitle = new Label("Countdown Timer");
		timerTitle.setFont(new Font(16));

		// Holds the countdown bar
		HBox timerDetails = new HBox(10);
		timerDetails.setAlignment(Pos.CENTER);

		// Hours VBox
		VBox hoursBox = new VBox(5);
		hoursBox.setAlignment(Pos.CENTER);
		Label timerHoursLabel = new Label("Hours");

		hoursBox.getChildren().setAll(timerHoursField, timerHoursLabel);

		// Minutes VBox
		VBox minutesBox = new VBox(5);
		minutesBox.setAlignment(Pos.CENTER);
		Label timerMinutesLabel = new Label("Minutes");
		minutesBox.getChildren().setAll(timerMinutesField, timerMinutesLabel);

		// Seconds VBox
		VBox secondsBox = new VBox(5);
		secondsBox.setAlignment(Pos.CENTER);
		Label timerSecondsLabel = new Label("Seconds");
		secondsBox.getChildren().setAll(timerSecondsField, timerSecondsLabel);

		// Start Button
		Button timerStartButton = new Button("Start Countdown");
		timerStartButton.setTooltip(new Tooltip("Begin the Countdown"));
		timerStartButton.setOnAction(e -> {

			if (!this.timer.getIsRunning()) {
				int hoursInt = 0, minutesInt = 0, secondsInt = 0;

				if (timerHoursField.getText().equals("")) {
					hoursInt = 0;
				} else if (timerMinutesField.getText().equals("")) {
					minutesInt = 0;
				} else if (timerSecondsField.getText().equals("")) {
					secondsInt = 0;
				} else {

					try {
						hoursInt = Integer.parseInt(timerHoursField.getText());
						minutesInt = Integer.parseInt(timerMinutesField.getText());
						secondsInt = Integer.parseInt(timerSecondsField.getText());
					} catch (NumberFormatException exception) {
						timerNumbersOnly();
					}
				}

				this.timer.setTimer(hoursInt, minutesInt, secondsInt);
				this.timer.runTimer(timerHoursField, timerMinutesField, timerSecondsField);
			}

		});

		// Pause Button
		Button timerPauseButton = new Button("Pause Countdown");
		timerPauseButton.setTooltip(new Tooltip("Pause and Keep Countdown"));
		timerPauseButton.setOnAction(e -> {
			this.timer.cancelTimer();
		});

		// Stop Button
		Button timerStopButton = new Button("Stop Countdown");
		timerStopButton.setTooltip(new Tooltip("Stop and Reset Countdown"));
		timerStopButton.setOnAction(e -> {
			this.timer.cancelTimer();
			timerHoursField.setText("0");
			timerMinutesField.setText("0");
			timerSecondsField.setText("0");
		});

		timerDetails.getChildren().addAll(hoursBox, minutesBox, secondsBox);
		timerVBox.getChildren().addAll(timerTitle, timerDetails, createIncDecButtons(), timerStartButton,
				timerPauseButton, timerStopButton);

		countdown.setContent(timerVBox);

		this.timer = new TimerController(directoryPath, 0, 0, 0, timerHoursField, timerMinutesField, timerSecondsField);
		return countdown;
	}

	private VBox createIncDecButtons() {
		VBox mainBox = new VBox(10);
		HBox hoursButtonBox = new HBox(10);
		hoursButtonBox.setAlignment(Pos.CENTER);
		HBox minutesButtonBox = new HBox(10);
		minutesButtonBox.setAlignment(Pos.CENTER);
		HBox secondsButtonBox = new HBox(10);
		secondsButtonBox.setAlignment(Pos.CENTER);

		Label hourLabel = new Label("Hours");
		Label minuteLabel = new Label("Minutes");
		Label secondLabel = new Label("Seconds");

		Button sm1 = new Button("-1");
		Button sm5 = new Button("-5");
		Button sp5 = new Button("+5");
		Button sp1 = new Button("+1");

		secondsButtonBox.getChildren().addAll(sm5, sm1, secondLabel, sp1, sp5);

		sm1.setOnAction(e -> {
			int value = -1;
			handleButton(value, timerSecondsField);
		});
		sm5.setOnAction(e -> {
			int value = -5;
			handleButton(value, timerSecondsField);
		});
		sp1.setOnAction(e -> {
			int value = 1;
			handleButton(value, timerSecondsField);
		});
		sp5.setOnAction(e -> {
			int value = 5;
			handleButton(value, timerSecondsField);
		});

		Button mm1 = new Button("-1");
		Button mm5 = new Button("-5");
		Button mp5 = new Button("+5");
		Button mp1 = new Button("+1");
		minutesButtonBox.getChildren().addAll(mm5, mm1, minuteLabel, mp1, mp5);

		mm1.setOnAction(e -> {
			int value = -1;
			handleButton(value, timerMinutesField);
		});
		mm5.setOnAction(e -> {
			int value = -5;
			handleButton(value, timerMinutesField);
		});
		mp1.setOnAction(e -> {
			int value = 1;
			handleButton(value, timerMinutesField);
		});
		mp5.setOnAction(e -> {
			int value = 5;
			handleButton(value, timerMinutesField);
		});

		Button hm1 = new Button("-1");
		Button hm5 = new Button("-5");
		Button hp5 = new Button("+5");
		Button hp1 = new Button("+1");
		hoursButtonBox.getChildren().addAll(hm5, hm1, hourLabel, hp1, hp5);

		hm1.setOnAction(e -> {
			int value = -1;
			handleButton(value, timerHoursField);
		});
		hm5.setOnAction(e -> {
			int value = -5;
			handleButton(value, timerHoursField);
		});
		hp1.setOnAction(e -> {
			int value = 1;
			handleButton(value, timerHoursField);
		});
		hp5.setOnAction(e -> {
			int value = 5;
			handleButton(value, timerHoursField);
		});

		mainBox.getChildren().addAll(hoursButtonBox, minutesButtonBox, secondsButtonBox);
		return mainBox;
	}

	private final void handleButton(int value, TextField textfield) {
		int num = Integer.parseInt(textfield.getText());
		System.out.println("TextField Value: " + num);
		num += value;
		System.out.println("Adding the parameter: " + num);

		if (num > 0) {
			textfield.setText(Integer.toString(num));

			// Start a new timer automatically if timer is running
			if (this.timer.getIsRunning()) {
				int h = Integer.parseInt(timerHoursField.getText());
				int m = Integer.parseInt(timerMinutesField.getText());
				int s = Integer.parseInt(timerSecondsField.getText());
				this.timer.cancelTimer();
				this.timer.setTimer(h, m, s);
				this.timer.runTimer(timerHoursField, timerMinutesField, timerSecondsField);
			}
		}
	}

	private void useButton(ActionEvent e, ArrayList<Button> buttons) {
		Button button = (Button) e.getSource();
		int selectedValue = buttons.indexOf(button);

		switch (selectedValue) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

	protected void timerNumbersOnly() {
		Alert alert = new Alert(AlertType.INFORMATION,
				"Please check fields for unneeded characters.\nOnly numbers are allowed in the fields.", ButtonType.OK);
		alert.setTitle("Countdown Error");
		alert.setHeaderText("Numeric Values Only in Fields");

		alert.show();
	}
}
