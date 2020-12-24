package view;

import java.io.FileNotFoundException;

import controller.TimerController;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.text.FontWeight;

@SuppressWarnings("restriction")
/**
 * Creates the CountdownTab GUI to be used in the OBS Helper program.
 * The countdown tab is responsible for allow the user to set a specified
 * time through the provided buttons or directly on the individual text fields.
 * The countdown tab coordinates with the TimerController class to write 
 * the current countdown time to a text file to be read by OBS software.
 * @author Brenton Haliw
 *
 */
public class CountdownTab {

	// Instance variables for the class
	private static String directoryPath;
	private TimerController timer;
	private TextField timerHoursField, timerMinutesField, timerSecondsField;

	/**
	 * Constructor for the CountdownTab. Initializes directory path and TimerController
	 * variables with given parameters.
	 * @param path
	 * @param timer
	 */
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
		
		this.timer = new TimerController(directoryPath, 0, 0, 0, timerHoursField, timerMinutesField, timerSecondsField);
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
		timerTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		timerTitle.setStyle("-fx-underline: true ;");

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

		timerDetails.getChildren().addAll(hoursBox, minutesBox, secondsBox);
		timerVBox.getChildren().addAll(timerTitle, timerDetails, createButtons());

		countdown.setContent(timerVBox);

		return countdown;
	}

	private HBox createButtons() {
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

		// Start Button
		Button timerStartButton = new Button("Start");
		timerStartButton.setTooltip(new Tooltip("Begin the Countdown"));
		timerStartButton.setOnAction(e -> {

			if (this.timer.getIsRunning() == false) {
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

				int value = hoursInt + minutesInt + secondsInt;
				if (value > 0) {
					this.timer.setTimer(hoursInt, minutesInt, secondsInt);
					this.timer.runTimer(timerHoursField, timerMinutesField, timerSecondsField);
					System.out.println(timer.getIsRunning());
				}
			}
		});

		Button timerPauseButton = new Button("Pause");
		timerPauseButton.setTooltip(new Tooltip("Pause the Countdown"));
		timerPauseButton.setOnAction(e -> {
			try {
				this.timer.cancelTimer();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// Stop Button
		Button timerStopButton = new Button("Reset");
		timerStopButton.setTooltip(new Tooltip("Stop and Reset Countdown"));
		timerStopButton.setOnAction(e -> {
			try {
				this.timer.cancelTimer();
				this.timer.writeToFile("00", "00", "00");
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			timerHoursField.setText("00");
			timerMinutesField.setText("00");
			timerSecondsField.setText("00");
		});

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

		pane.add(timerStartButton, 0, 3, 2, 1);
		pane.add(timerPauseButton, 2, 3, 2, 1);
		pane.add(timerStopButton, 4, 3, 2, 1);
		
		GridPane.setFillWidth(timerStartButton, true);
		GridPane.setFillWidth(timerPauseButton, true);
		GridPane.setFillWidth(timerStopButton, true);
		
		// Centering the nodes within the gridpane and then giving them a drop shadow
		for (int i = 0; i < pane.getChildren().size(); i++) {
			GridPane.setHalignment(pane.getChildren().get(i), HPos.CENTER);
			pane.getChildren().get(i).setStyle("-fx-effect: dropshadow(three-pass-box, rgba(135, 206, 250, 0.3), 10, 0, 0, 0);");
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
	 * @param textfield TextField object to be manipulated
	 * @throws FileNotFoundException 
	 */
	private final void handleButton(int value, TextField textfield)  {
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
				textfield.setText(this.timer.intToString(num));

				// Start a new timer automatically if timer is running
				if (this.timer.getIsRunning()) {
					int h = Integer.parseInt(timerHoursField.getText());
					int m = Integer.parseInt(timerMinutesField.getText());
					int s = Integer.parseInt(timerSecondsField.getText());
					try {
						this.timer.cancelTimer();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.timer.setTimer(h, m, s);
					
					if(this.timer.getInterval() > 0) {
						this.timer.runTimer(timerHoursField, timerMinutesField, timerSecondsField);
					} else {
						try {
							this.timer.cancelTimer();
							this.timer.writeToFile("00", "00", "00");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}
		} catch (NumberFormatException e) {
			timerNumbersOnly();
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
