package view;

import java.io.FileNotFoundException;

import controller.CountdownController;
import controller.StopwatchController;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.text.FontWeight;
import javafx.geometry.Orientation;

@SuppressWarnings("restriction")
/**
 * Creates the CountdownTab GUI to be used in the OBS Helper program. The
 * countdown tab is responsible for allow the user to set a specified time
 * through the provided buttons or directly on the individual text fields. The
 * countdown tab coordinates with the TimerController class to write the current
 * countdown time to a text file to be read by OBS software.
 * 
 * @author Brenton Haliw
 *
 */
public class TimeTab {

	// Instance variables for the class
	private CountdownController countdownController;
	private StopwatchController stopwatchController;
	private TextField countdownHoursField, countdownMinutesField, countdownSecondsField;
	private TextField stopwatchHoursField, stopwatchMinutesField, stopwatchSecondsField;

	/**
	 * Constructor for the CountdownTab. Initializes directory path and
	 * TimerController variables with given parameters.
	 * 
	 */
	public TimeTab() {
		this.countdownHoursField = initializeField(true);
		this.countdownMinutesField = initializeField(true);
		this.countdownSecondsField = initializeField(true);
		this.stopwatchHoursField = initializeField(false);
		this.stopwatchMinutesField = initializeField(false);
		this.stopwatchSecondsField = initializeField(false);
		
		this.countdownController = new CountdownController(0, 0, 0, this.countdownHoursField, this.countdownMinutesField,
				this.countdownSecondsField);

		this.stopwatchController = new StopwatchController(this.stopwatchHoursField, this.stopwatchMinutesField, this.stopwatchSecondsField);
	}
	
	/**
	 * Helper method to initialize the TextFields for the TimeTab
	 * @param setEditable - if the TextField can be edited
	 * @return - a TextField with desired properties
	 */
	private TextField initializeField(boolean setEditable) {
		TextField field = new TextField();
		field.setMaxWidth(50);
		field.setAlignment(Pos.CENTER);
		field.setText("00");
		field.setEditable(setEditable);
		return field;
	}


	/**
	 * Creates the countdown tab to be used in the main GUI along with the required
	 * buttons
	 * 
	 * @return - a tab containing the countdown interface
	 */
	public Tab createCountdownTab() {
		Tab countdown = new Tab("Countdown and Stopwatch");
		countdown.setTooltip(new Tooltip("Menu for countdown and steam uptime"));

		HBox box = new HBox(50);
		box.setAlignment(Pos.CENTER);

		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		Separator sep1 = new Separator();

		// Loading the box to display the UI elements
		box.getChildren().addAll(createCountdown(), sep, createStopwatch());

		countdown.setContent(box);

		return countdown;
	}

	/**
	 * Helper method that creates the timer portion of the Time Tab
	 * 
	 * @return - a VBox containing the graphical elements of the timer
	 */
	private VBox createStopwatch() {
		VBox timerBox = new VBox(10);
		timerBox.setAlignment(Pos.CENTER);

		Label title = new Label("Stream Timer");
		title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		title.setStyle("-fx-underline: true ;");

		Label hourLabel = new Label("Hours");
		Label minuteLabel = new Label("Minutes");
		Label secondLabel = new Label("Seconds");

		HBox fieldsBox = new HBox(10);
		fieldsBox.setAlignment(Pos.CENTER);

		VBox hourBox = new VBox(5);
		hourBox.setAlignment(Pos.CENTER);
		hourBox.getChildren().addAll(this.stopwatchHoursField, hourLabel);

		VBox minuteBox = new VBox(5);
		minuteBox.setAlignment(Pos.CENTER);
		minuteBox.getChildren().addAll(this.stopwatchMinutesField, minuteLabel);

		VBox secondBox = new VBox(5);
		secondBox.setAlignment(Pos.CENTER);
		secondBox.getChildren().addAll(this.stopwatchSecondsField, secondLabel);

		fieldsBox.getChildren().addAll(hourBox, minuteBox, secondBox);

		HBox buttonsBox = new HBox(10);
		buttonsBox.setAlignment(Pos.CENTER);

		Button start = new Button("Start");
		start.setOnAction(e -> {
			if(this.stopwatchController.getIsRunning() == false) {
				this.stopwatchController.startStopwatch();
			}
		});
		Button pause = new Button("Pause");
		pause.setOnAction(e -> {
			this.stopwatchController.pauseStopwatch();
		});

		Button reset = new Button("Reset");
		reset.setOnAction(e -> {
			this.stopwatchController.resetStopwatch();
			try {
				this.stopwatchController.writeToFile("00", "00", "00");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.stopwatchHoursField.setText("00");
			this.stopwatchMinutesField.setText("00");
			this.stopwatchSecondsField.setText("00");
		});
		
		Label textFormat = new Label("Text File Format");
		ComboBox<String> stopCombo = new ComboBox<>();
		Button formatHelp = new Button("Format Help");
		
		HBox format = new HBox(10);
		format.setAlignment(Pos.CENTER);
		format.getChildren().addAll(textFormat, stopCombo, formatHelp);
		CheckBox checkBox = new CheckBox("Hide Unused Values");

		buttonsBox.getChildren().addAll(start, pause, reset);

		timerBox.getChildren().addAll(title, fieldsBox, buttonsBox, format, checkBox);
		return timerBox;
	}

	/**
	 * Helper method that creates the countdown portion of the Time tab.
	 * 
	 * @return - a VBox containing the graphical elements of the countdown
	 */
	private VBox createCountdown() {
		// Creating Countdown Interface
		VBox countdownVBox = new VBox(10);
		countdownVBox.setAlignment(Pos.CENTER);
		Label countdownTitle = new Label("Countdown Timer");
		countdownTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		countdownTitle.setStyle("-fx-underline: true ;");

		// Holds the countdown bar
		HBox countdownDetails = new HBox(10);
		countdownDetails.setAlignment(Pos.CENTER);

		// Hours VBox
		VBox hoursBox = new VBox(5);
		hoursBox.setAlignment(Pos.CENTER);
		Label countdownHoursLabel = new Label("Hours");

		hoursBox.getChildren().setAll(this.countdownHoursField, countdownHoursLabel);

		// Minutes VBox
		VBox minutesBox = new VBox(5);
		minutesBox.setAlignment(Pos.CENTER);
		Label countdownMinutesLabel = new Label("Minutes");
		minutesBox.getChildren().setAll(this.countdownMinutesField, countdownMinutesLabel);

		// Seconds VBox
		VBox secondsBox = new VBox(5);
		secondsBox.setAlignment(Pos.CENTER);
		Label countdownSecondsLabel = new Label("Seconds");
		secondsBox.getChildren().setAll(this.countdownSecondsField, countdownSecondsLabel);

		countdownDetails.getChildren().addAll(hoursBox, minutesBox, secondsBox);
		countdownVBox.getChildren().addAll(countdownTitle, countdownDetails, createCountdownButtons());

		return countdownVBox;
	}

	/**
	 * Helper method that creates the buttons for the countdown portion of the Time
	 * Tab. Utilizes another helper method called handleIncDecButtons() in order to
	 * manipulate the countdown.
	 * 
	 * @return - HBox containing the graphical elements of the countdown UI
	 */
	private HBox createCountdownButtons() {
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);

		GridPane pane = new GridPane();
		pane.setHgap(10);
		pane.setVgap(10);

		Label hourLabel = new Label("Hours");
		Label minuteLabel = new Label("Minutes");
		Label secondLabel = new Label("Seconds");

		Button sm1 = new Button("-1");
		Button sm5 = new Button("-5");
		Button sp5 = new Button("+5");
		Button sp1 = new Button("+1");

		sm1.setOnAction(e -> {
			int value = -1;
			handleIncDecButton(value, this.countdownSecondsField);
		});
		sm5.setOnAction(e -> {
			int value = -5;
			handleIncDecButton(value, this.countdownSecondsField);
		});
		sp1.setOnAction(e -> {
			int value = 1;
			handleIncDecButton(value, this.countdownSecondsField);
		});
		sp5.setOnAction(e -> {
			int value = 5;
			handleIncDecButton(value, this.countdownSecondsField);
		});

		Button mm1 = new Button("-1");
		Button mm5 = new Button("-5");
		Button mp5 = new Button("+5");
		Button mp1 = new Button("+1");

		mm1.setOnAction(e -> {
			int value = -1;
			handleIncDecButton(value, this.countdownMinutesField);
		});
		mm5.setOnAction(e -> {
			int value = -5;
			handleIncDecButton(value, this.countdownMinutesField);
		});
		mp1.setOnAction(e -> {
			int value = 1;
			handleIncDecButton(value, this.countdownMinutesField);
		});
		mp5.setOnAction(e -> {
			int value = 5;
			handleIncDecButton(value, this.countdownMinutesField);
		});

		Button hm1 = new Button("-1");
		Button hm5 = new Button("-5");
		Button hp5 = new Button("+5");
		Button hp1 = new Button("+1");

		hm1.setOnAction(e -> {
			int value = -1;
			handleIncDecButton(value, this.countdownHoursField);
		});
		hm5.setOnAction(e -> {
			int value = -5;
			handleIncDecButton(value, this.countdownHoursField);
		});
		hp1.setOnAction(e -> {
			int value = 1;
			handleIncDecButton(value, this.countdownHoursField);
		});
		hp5.setOnAction(e -> {
			int value = 5;
			handleIncDecButton(value, countdownHoursField);
		});

		// Start Button
		Button countdownStartButton = new Button("Start");
		countdownStartButton.setTooltip(new Tooltip("Begin the Countdown"));
		countdownStartButton.setOnAction(e -> {

			if (this.countdownController.getIsRunning() == false) {
				int hoursInt = 0, minutesInt = 0, secondsInt = 0;

				if (this.countdownHoursField.getText().equals("")) {
					hoursInt = 0;
				} else if (this.countdownMinutesField.getText().equals("")) {
					minutesInt = 0;
				} else if (this.countdownSecondsField.getText().equals("")) {
					secondsInt = 0;
				} else {

					try {
						hoursInt = Integer.parseInt(this.countdownHoursField.getText());
						minutesInt = Integer.parseInt(this.countdownMinutesField.getText());
						secondsInt = Integer.parseInt(this.countdownSecondsField.getText());
					} catch (NumberFormatException exception) {
						Alerts.countdownNumbersOnly();
					}
				}

				int value = hoursInt + minutesInt + secondsInt;
				if (value > 0) {
					this.countdownController.setCountdown(hoursInt, minutesInt, secondsInt);
					this.countdownController.runTimer();
				}
			}
		});

		Button countdownPauseButton = new Button("Pause");
		countdownPauseButton.setTooltip(new Tooltip("Pause the Countdown"));
		countdownPauseButton.setOnAction(e -> {
			try {
				this.countdownController.cancelCountdown();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// Stop Button
		Button countdownStopButton = new Button("Reset");
		countdownStopButton.setTooltip(new Tooltip("Stop and Reset Countdown"));
		countdownStopButton.setOnAction(e -> {
			try {
				this.countdownController.cancelCountdown();
				this.countdownController.writeToFile("00", "00", "00");
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			this.countdownHoursField.setText("00");
			this.countdownMinutesField.setText("00");
			this.countdownSecondsField.setText("00");
		});
		
		Label textFormat = new Label("Text File Format");
		ComboBox<String> stopCombo = new ComboBox<>();
		Button formatHelp = new Button("Format Help");

		// Creating the GridPane for the +/- buttons to be added to
		pane.add(hm5, 0, 0, 1, 1);
		pane.add(hm1, 1, 0, 1, 1);
		pane.add(hourLabel, 2, 0, 2, 1);
		pane.add(hp1, 4, 0, 1, 1);
		pane.add(hp5, 5, 0, 1, 1);

		pane.add(mm5, 0, 1, 1, 1);
		pane.add(mm1, 1, 1, 1, 1);
		pane.add(minuteLabel, 2, 1, 2, 1);
		pane.add(mp1, 4, 1, 1, 1);
		pane.add(mp5, 5, 1, 1, 1);

		pane.add(sm5, 0, 2, 1, 1);
		pane.add(sm1, 1, 2, 1, 1);
		pane.add(secondLabel, 2, 2, 2, 1);
		pane.add(sp1, 4, 2, 1, 1);
		pane.add(sp5, 5, 2, 1, 1);

		pane.add(countdownStartButton, 0, 3, 2, 1);
		pane.add(countdownPauseButton, 2, 3, 2, 1);
		pane.add(countdownStopButton, 4, 3, 2, 1);
		
		GridPane.setFillWidth(countdownStartButton, true);
		GridPane.setFillWidth(countdownPauseButton, true);
		GridPane.setFillWidth(countdownStopButton, true);

		// Centering the nodes within the gridpane and then giving them a drop shadow
		for (int i = 0; i < pane.getChildren().size(); i++) {
			GridPane.setHalignment(pane.getChildren().get(i), HPos.CENTER);
			pane.getChildren().get(i)
					.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(135, 206, 250, 0.3), 10, 0, 0, 0);");
		}

		// Removing the drop shadow from the labels because it looks weird
		hourLabel.setStyle("");
		minuteLabel.setStyle("");
		secondLabel.setStyle("");

		// Adding the GridPane to the HBox
		box.getChildren().add(pane);

		return box;
	}

	/**
	 * Helper method so that way we don't repeat a ton of code for the +/- buttons.
	 * 
	 * @param value Primitive integer to be added/subtracted to the TextField value
	 * @param -     textfield TextField object to be manipulated
	 */
	private final void handleIncDecButton(int value, TextField textfield) {
		try {
			int num = Integer.parseInt(textfield.getText());
			System.out.println("TextField Value: " + num);
			num += value;
			System.out.println("Adding the parameter: " + num);

			// If using the value makes the number go into the negatives
			// then set it back to zero
			if (num < 0) {
				num = 0;
				textfield.setText("00");
				System.out.println("Setting text");
			}

			if (num >= 0) {
				textfield.setText(this.countdownController.intToString(num));

				// Start a new timer automatically if timer is running
				if (this.countdownController.getIsRunning()) {
					int h = Integer.parseInt(this.countdownHoursField.getText());
					int m = Integer.parseInt(this.countdownMinutesField.getText());
					int s = Integer.parseInt(this.countdownSecondsField.getText());
					try {
						this.countdownController.cancelCountdown();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.countdownController.setCountdown(h, m, s);

					if (this.countdownController.getInterval() > 0) {
						this.countdownController.runTimer();
					} else {
						try {
							this.countdownController.cancelCountdown();
							this.countdownController.writeToFile("00", "00", "00");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}
		} catch (NumberFormatException e) {
			Alerts.countdownNumbersOnly();
		}

	}

}
