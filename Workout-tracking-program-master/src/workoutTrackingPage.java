import java.io.IOException;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class workoutTrackingPage {

	private static Stage window = new Stage();
	private static GridPane layout;
	private static Scene page;
	private static Workout thisWorkout;
	private static Workout completedWorkout;
	private static Exercise currentExercise;
	private static StrengthExercise currentStrength;
	private static CardioExercise currentCardio;
	private static int numberOfExercisesInWorkout;
	private static Button exerciseComplete;
	private static CheckBox hasLaps;
	private static CheckBox hasResistance;
	private static CheckBox hasDistance;
	private static int index;
	private static Label nameLabel;
	private static Label lapLabel;
	private static TextField lapLog;
	private static Label resistanceLabel;
	private static TextField resistanceLog;
	private static Label distanceLabel;
	private static TextField distanceLog;
	private static String style = "    -fx-background-color: radial-gradient(center 50% 50% , radius 80% , #69696b ,   #3a3a3a);" + 
			"    -fx-padding: 10;\n" +
			"    -fx-text-fill:  #c6f5f9 ;\n";
	private static String textStyle = "-fx-text-fill: #c6f5f9;";
	private static String buttonStyle = " -fx-background-color: rgba(3, 252, 248, 0.4);"
	+ " -fx-background-radius: 10; -fx-text-fill: #c6f5f9; -fx-font: 14px Arial; -fx-font-weight: Bold;";
	//private static ScrollPane layout;
	
	
	public static void display(Workout w) {
		window.setOnCloseRequest(e -> window.close());
		thisWorkout = w;
		numberOfExercisesInWorkout = thisWorkout.length();
		setWindow();
		
	}
	
	public static void setWindow() {
		
		window.setTitle(thisWorkout.getWorkoutName());
		window.centerOnScreen();
		layout = createWorkoutTrackerPage();
		page = new Scene(layout, 400, 500);
		window.setScene(page);
		window.sizeToScene();
		window.show();
		
	}
	
	public static GridPane createWorkoutTrackerPage() {
		GridPane pane = new GridPane();
		pane.setStyle(style);
		ScrollPane scroll = new ScrollPane();
		scroll.setFitToWidth(true);
		scroll.setFitToHeight(true);
		
		pane.setPadding(new Insets(20,0,20,0));
		pane.setAlignment(Pos.TOP_CENTER);
		
		//pane.setGridLinesVisible(true);
		pane.setVgap(20);
		pane.setHgap(10);
		completedWorkout = new Workout();
		completedWorkout.setWorkoutName(thisWorkout.getWorkoutName());
		
		Label workoutName = new Label(thisWorkout.getWorkoutName());
		workoutName.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 32));
		workoutName.setAlignment(Pos.TOP_CENTER);
		workoutName.setPrefSize(400, 100);
		workoutName.setStyle(textStyle);
		
		GridPane.setHalignment(workoutName, HPos.CENTER);
		
		pane.add(workoutName, 0, 0);
		
		Label description = new Label("Exercises to complete:");
		description.setStyle(textStyle);
		description.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		VBox exerciseNameBox = new VBox();
		exerciseNameBox.setAlignment(Pos.CENTER);
		exerciseNameBox.setSpacing(10);
		exerciseNameBox.getChildren().add(description);
		
		for(int i = 0; i < thisWorkout.length(); i++) {
			Button exerciseName = new Button(thisWorkout.getExercise(i).getName());
			exerciseName.setStyle(buttonStyle);
			int index = i;
			exerciseName.setOnAction(e ->{
				if(thisWorkout.getExercise(index).getType().equals("Strength")) {
					viewExercisePage.displayStrength((StrengthExercise) thisWorkout.getExercise(index)); 
				}
				else {
					viewExercisePage.displayCardio((CardioExercise) thisWorkout.getExercise(index)); 
				}
			});
			exerciseName.setAlignment(Pos.CENTER);
			exerciseName.setPrefWidth(350);
			exerciseName.setFont(Font.font("Arial", FontWeight.BOLD, 22));
			exerciseNameBox.setStyle("-fx-background-color: gray;");
			exerciseNameBox.getChildren().add(exerciseName);
		}
		
		Button beginWorkout = new Button("Begin Workout");
		beginWorkout.setStyle(buttonStyle);
		pane.add(exerciseNameBox, 0, 1);
		beginWorkout.setAlignment(Pos.BOTTOM_CENTER);
		GridPane.setHalignment(beginWorkout, HPos.CENTER);
		pane.add(beginWorkout, 0, 3);

		scroll.setContent(exerciseNameBox);
		scroll.setPrefHeight(500);
		pane.add(scroll, 0, 1);
		
		beginWorkout.setOnAction(e -> {
			index = 0;
			pane.getChildren().remove(scroll);
			pane.getChildren().remove(beginWorkout);
			pane.getChildren().remove(exerciseNameBox);
			pane.getChildren().remove(workoutName);
			logExercises(index, pane);	
			
		});
	//	pane.add(beginWorkout, 0, 2);

		
		return pane;
	}
	
	private static void logExercises(int index, GridPane pane) {
		

		//final long start = System.currentTimeMillis();
		window.setWidth(1000);
		window.setHeight(600);
		
		window.centerOnScreen();

		pane.setAlignment(Pos.TOP_CENTER);
		pane.setStyle(style);
		Label name = new Label("Exercise name");
		name.setStyle(style);
		name.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));


		if(index >= numberOfExercisesInWorkout) {
			Button finishWorkout = new Button("Finish Workout");
			finishWorkout.setStyle(style);
			finishWorkout.setOnAction(e ->{
				try {
					completedWorkout.saveCompletedWorkout();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				window.close();
				workoutPage.display();
			});
			
			GridPane.setHalignment(finishWorkout, HPos.CENTER);
			pane.add(finishWorkout, 0, index + 1, 9, 1);
			return;
		}
		
		
		currentExercise = thisWorkout.getExercise(index);
		nameLabel = new Label(currentExercise.getName());
		nameLabel.setStyle(textStyle);
		nameLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
		
		
		
		
		/////////////////////////////////////////////////////////////
		////////////////Strength Exercises///////////////////////////
		/////////////////////////////////////////////////////////////
		if(currentExercise.getType().equalsIgnoreCase("Strength")) {
			
			currentStrength = (StrengthExercise) currentExercise;
			if(currentStrength.getWeightType().equalsIgnoreCase("Free Weights") || currentStrength.getWeightType().equalsIgnoreCase("Machine Resistance")) {
				
				Label weightUsed = new Label("Weight(lbs):");
				weightUsed.setStyle(textStyle);
				weightUsed.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				resistanceLog = new TextField();
				resistanceLog.setPrefWidth(50);
				pane.add(weightUsed, 1, index + 1);
				pane.add(resistanceLog, 2, index + 1);
				
				
				Label repLabel = new Label("Reps:");
				repLabel.setStyle(textStyle);
				repLabel.setFont(Font.font("Arial", FontWeight.BOLD,14));
				TextField repLog = new TextField();
				repLog.setPrefWidth(50);
				pane.add(repLabel, 3, index + 1);
				pane.add(repLog, 4, index + 1);
				
				
				Label setLabel = new Label("Sets:");
				setLabel.setStyle(textStyle);
				setLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField setField = new TextField();
				setField.setPrefWidth(50);
				pane.add(setLabel, 5, index + 1);
				pane.add(setField, 6, index + 1);
				
				
				exerciseComplete = new Button("Exercise Complete");
				exerciseComplete.setStyle(buttonStyle);
				pane.add(nameLabel, 0, index + 1);
				pane.add(exerciseComplete, 7, index + 1);
				
				
				exerciseComplete.setOnAction(e ->{
					try {
						currentStrength.setWeightUsed(Integer.parseInt(resistanceLog.getText()));
						currentStrength.setRepitions(Integer.parseInt(repLog.getText()));
						currentStrength.setRepitions(Integer.parseInt(setField.getText()));
					}
					catch(NumberFormatException n) {
						showAlert(Alert.AlertType.ERROR, window, "Invalid input", "Invalid input, please enter numeric values only");
						return;
					}
					completedWorkout.addExercise(currentStrength);
					resistanceLog.setDisable(true);
					repLog.setDisable(true);
					setField.setDisable(true);
					exerciseComplete.setText("Completed");
					exerciseComplete.setDisable(true);
					int i = index + 1;
					logExercises(i, pane);
				});
			}
			
			if(currentStrength.getWeightType().equalsIgnoreCase("Body weight")) {
				Label repLabel = new Label("Reps:");
				repLabel.setStyle(textStyle);
				repLabel.setFont(Font.font("Arial", FontWeight.BOLD,14));
				TextField repLog = new TextField();
				repLog.setPrefWidth(50);
				pane.add(repLabel, 1, index + 1);
				pane.add(repLog, 2, index + 1);
				
				Label setLabel = new Label("Sets:");
				setLabel.setStyle(textStyle);
				setLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField setField = new TextField();
				setField.setPrefWidth(50);
				
				pane.add(setLabel, 3, index + 1);
				pane.add(setField, 4, index + 1);
				
				exerciseComplete = new Button("Exercise Complete");
				exerciseComplete.setStyle(buttonStyle);
				pane.add(nameLabel, 0, index + 1);
				pane.add(exerciseComplete, 7, index + 1);
				
				exerciseComplete.setOnAction(e ->{
					try {
						currentStrength.setRepitions(Integer.parseInt(repLog.getText()));
						currentStrength.setSets(Integer.parseInt(setField.getText()));
					}
					catch(NumberFormatException n) {
						showAlert(Alert.AlertType.ERROR, window, "Invalid input", "Invalid input, please enter numeric values only");
						return;
					}
					completedWorkout.addExercise(currentStrength);
					repLog.setDisable(true);
					setField.setDisable(true);
					exerciseComplete.setText("Completed");
					exerciseComplete.setDisable(true);
					int i = index + 1;
					logExercises(i, pane);
				});
			}


		}
		
		////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////
		///////////////////////Cardio Exercises/////////////////////////
		////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////
		else if(currentExercise.getType().equalsIgnoreCase("Cardio")) {
			currentCardio = (CardioExercise) currentExercise;
			
			hasLaps = new CheckBox("Did laps");
			hasResistance = new CheckBox("Used resistance");
			hasDistance = new CheckBox("Tracked distance");
			
			VBox checkBoxes = new VBox();
			checkBoxes.getChildren().addAll(hasLaps, hasResistance, hasDistance);
			
			hasLaps.setOnAction(e ->{
				if(hasLaps.isSelected()) {
					lapLabel = new Label("Laps Completed:");
					lapLog = new TextField();
					lapLog.setPrefWidth(50);
					pane.add(lapLabel, 2, index + 1);
					pane.add(lapLog, 3, index + 1);
				}
				else {
					pane.getChildren().removeAll(lapLabel, lapLog);
				}
			});
			
			hasResistance.setOnAction(e ->{
				if(hasResistance.isSelected()) {
					resistanceLabel = new Label("Resistance Level:");
					resistanceLog = new TextField();
					resistanceLog.setPrefWidth(50);
					pane.add(resistanceLabel, 4, index + 1);
					pane.add(resistanceLog, 5, index + 1);
				}
				else {
					pane.getChildren().removeAll(resistanceLabel, resistanceLog);
				}
			});
			
			hasDistance.setOnAction(e -> {
				if(hasDistance.isSelected()) {
					distanceLabel = new Label("Distance(Miles):");
					distanceLog = new TextField();
					distanceLog.setPrefWidth(50);
					pane.add(distanceLabel, 6, index + 1);
					pane.add(distanceLog, 7, index + 1);
				}
				else {
					pane.getChildren().removeAll(distanceLabel, distanceLog);
				}
			});
			
			pane.add(nameLabel, 0, index + 1);
			pane.add(checkBoxes, 1, index + 1);
			exerciseComplete = new Button("Exercise Complete");
			pane.add(exerciseComplete, 8, index + 1);
			
			
			exerciseComplete.setOnAction(e ->{
				
				if(hasLaps.isSelected()) {
					currentCardio.setHasLaps(true);
					try {
						currentCardio.setLaps(Integer.parseInt(lapLog.getText()));
					}
					catch(NumberFormatException n) {
						showAlert(Alert.AlertType.ERROR, window, "Invalid input", "Please enter numeric only values");
						return;
					}
					hasLaps.setDisable(true);
					lapLog.setDisable(true);
				}
				
				if(hasResistance.isSelected()) {
					currentCardio.setHasResistance(true);
					try {
						currentCardio.setResistanceLevel(Integer.parseInt(resistanceLog.getText()));
					}
					catch(NumberFormatException n) {
						showAlert(Alert.AlertType.ERROR, window, "Invalid input", "Please enter numeric only values");
						return;
					}
					hasResistance.setDisable(true);
					resistanceLog.setDisable(true);

				}
				
				if(hasDistance.isSelected()) {
					currentCardio.setHasDistance(true);
					try {
						currentCardio.setDistance(Double.parseDouble(distanceLog.getText()));
					}
					catch(NumberFormatException n) {
						showAlert(Alert.AlertType.ERROR, window, "Invalid input", "Please enter numeric only values");
						return;
					}
					distanceLog.setDisable(true);
					hasDistance.setDisable(true);
				}
				exerciseComplete.setStyle(buttonStyle);
				exerciseComplete.setText("Completed");
				exerciseComplete.setDisable(true);
				completedWorkout.addExercise(currentCardio);
				int i = index + 1;
				logExercises(i, pane);
			});
		}
		
		
	}
	private static void showAlert(Alert.AlertType alertType, Window win, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(win);
		alert.show();
	}
	
}
