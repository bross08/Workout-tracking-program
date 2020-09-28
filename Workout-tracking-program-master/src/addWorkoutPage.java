import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class addWorkoutPage {
	
	private static Stage window = new Stage();
	private static GridPane layout;
	private static Scene page;
	private static ObservableList<String> selectedViewableExercises;
	private static ObservableList<String> allViewableExercises;
	private static ArrayList<StrengthExercise> strengthExerciseList = StrengthExercise.getAllExercises(); 
	private static ArrayList<CardioExercise> cardioExerciseList = CardioExercise.getAllExercises();
	private static boolean isStrength;
	private static Workout thisWorkout = new Workout();
	private static Exercise selectedExercise;
	private static String style = "    -fx-background-color: radial-gradient(center 50% 50% , radius 80% , #69696b ,   #3a3a3a);" + 
			"    -fx-padding: 10;\n" +
			"    -fx-text-fill:  #c6f5f9 ;\n";
	private static String textStyle = "-fx-text-fill: #c6f5f9;";
	private static String buttonStyle = " -fx-background-color: rgba(3, 252, 248, 0.4);"
	+ " -fx-background-radius: 10; -fx-text-fill: #c6f5f9; -fx-font: 14px Arial; -fx-font-weight: Bold;";
	private static String listStyle = "-fx-control-inner-background: grey; "
			+ "-fx-control-inner-background-alt: derive(-fx-control-inner-background, 20%);" +
			"-fx-font: 13px Arial; -fx-font-weight: Bold;";

	
	public static void display() {
		window.setOnCloseRequest(e -> window.close());
		setAddWindow();
	}
	
	public static void setAddWindow(){
		
		window.setTitle("Add new exercise");
		window.centerOnScreen();
		layout = createAddLayout();
	    page = new Scene(layout, 500, 700);
		window.setScene(page);
		window.show();
	}
	
	private static GridPane createAddLayout() {
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);

		//Set the style settings for the pane
		gridPane.setPadding(new Insets(20, 20, 20, 20));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		//Add label, buttons, and controls for the pane
		gridPane = addUIControls(gridPane);

		return gridPane;
		
	}
	
	private static GridPane addUIControls(GridPane pane) {


		pane.setAlignment(Pos.TOP_CENTER);
		pane.setStyle(style);
		GridPane.setHalignment(pane, HPos.CENTER);
		GridPane.setValignment(pane, VPos.TOP);
		Label headerLabel = new Label("Create New Workout Routine");
		headerLabel.setStyle(textStyle);
		headerLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

	
		Label nameLabel = new Label("Workout Name: ");
		nameLabel.setStyle(textStyle);
		Label typeLabel = new Label("What type of exercise to add?");
		typeLabel.setStyle(textStyle);

		nameLabel.setPrefHeight(30);
		typeLabel.setPrefHeight(30);
		
		Label exercisesAdded = new Label("Exercises in this workout plan");
		exercisesAdded.setStyle(textStyle);
		Label exercisesToAdd = new Label("Select an exercise to add and press \"Add Exercise\"");
		exercisesToAdd.setStyle(textStyle);
		

		
		TextField nameField = new TextField();
		nameField.setPrefHeight(30);
		nameField.setPrefWidth(400);
		
		
		
		ListView<String> allExercises = new ListView<String>();
		allExercises.setStyle(listStyle);
		allExercises.setPrefHeight(100);
		
		
		////////////////////////////////////////////////////////////
		////////////Controls to fill all exercises//////////////////
		////////////////////////////////////////////////////////////
		Button showStrength = new Button("Show Strength Exercises");
		showStrength.setStyle(buttonStyle);
		showStrength.setOnAction(e -> {
			allViewableExercises = StrengthExercise.loadExercises();
			allExercises.setItems(allViewableExercises);
			isStrength = true;
		});
		
		
		Button showCardio = new Button("Show Cardio Exercises");
		showCardio.setStyle(buttonStyle);
		showCardio.setOnAction(e ->{
			allViewableExercises = CardioExercise.loadExercises();
			allExercises.setItems(allViewableExercises);
			isStrength = false;
		});
		
		
		
		HBox showButtons = new HBox();
		showButtons.setSpacing(50);
		showButtons.setAlignment(Pos.CENTER);
		showButtons.getChildren().addAll(showStrength, showCardio);
		
		
		////////////////////////////////////////////////////////////
		////////////End of fill all exercise controls///////////////
		////////////////////////////////////////////////////////////

		
		ListView<String> selectedExercises = new ListView<String>();
		selectedExercises.setStyle(listStyle);
		selectedExercises.setPrefHeight(100);
		
		Button addExercise = new Button("Add exercise");
		addExercise.setStyle(buttonStyle);
		addExercise.setOnAction(e -> {
			if(isStrength == true) {
				strengthExerciseList = StrengthExercise.getAllExercises();
				selectedExercise = strengthExerciseList.get(allExercises.getSelectionModel().getSelectedIndex());
				thisWorkout.addExercise(selectedExercise);
				selectedViewableExercises = thisWorkout.loadExercises();
				selectedExercises.setItems(selectedViewableExercises);
			}
			else {
				cardioExerciseList = CardioExercise.getAllExercises();
				selectedExercise = cardioExerciseList.get(allExercises.getSelectionModel().getSelectedIndex());
				thisWorkout.addExercise(selectedExercise);
				selectedViewableExercises = thisWorkout.loadExercises();
				selectedExercises.setItems(selectedViewableExercises);
			}
		});
		
		Button removeExercise = new Button("Remove exercise");
		removeExercise.setStyle(buttonStyle);
		removeExercise.setOnAction(e ->{
			thisWorkout.remove(selectedExercises.getSelectionModel().getSelectedIndex());
			selectedViewableExercises = thisWorkout.loadExercises();
			selectedExercises.setItems(selectedViewableExercises);
		});
		

		Button goBack = new Button("Back");
		goBack.setStyle(buttonStyle);
		goBack.setOnAction(e -> {
			thisWorkout = new Workout();
			window.close();
			workoutPage.display();
		});
		
		Button saveWorkout = new Button("Save workout");
		saveWorkout.setStyle(buttonStyle);
		saveWorkout.setOnAction(e -> {
			if(nameField.getText().isEmpty()) {
				showAlert(Alert.AlertType.ERROR, layout.getScene().getWindow(), "Name Missing" , "Please enter a name.");
				return;
			}
			if(thisWorkout.isEmpty()) {
				showAlert(Alert.AlertType.ERROR, layout.getScene().getWindow(), "No exercises added", "Add at leat one exercise to create a workout");
				return;
			}
			thisWorkout.setWorkoutName(nameField.getText());
				
			try {
				if(thisWorkout.saveRoutine() == true) {
					thisWorkout = new Workout();
					showCreatedAlert(Alert.AlertType.CONFIRMATION, layout.getScene().getWindow(), "Workout Added", "Workout successfully added");
				}
				else
					showDuplicateAlert(Alert.AlertType.ERROR, layout.getScene().getWindow(), "Workout exists", "Workout exists with this name, please choose a different name");
				
			}catch(IOException i) {
				System.out.println("IO Exception");
			}

		});
		
		
		removeExercise.setPrefWidth(150);
		goBack.setPrefWidth(150);
		saveWorkout.setPrefWidth(150);
		addExercise.setPrefWidth(150);
		
		
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll( addExercise, removeExercise);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(30);
		
		HBox secondRowButtons = new HBox();
		secondRowButtons.getChildren().addAll(saveWorkout, goBack);
		secondRowButtons.setAlignment(Pos.CENTER);
		secondRowButtons.setSpacing(30);
		
	
		Button viewExerciseInfo = new Button("View Selected Exercise Info");
		viewExerciseInfo.setStyle(buttonStyle);
		viewExerciseInfo.setOnAction(e ->{
			if(isStrength) {
				StrengthExercise selectedStrength = strengthExerciseList.get(allExercises.getSelectionModel().getSelectedIndex());
				viewExercisePage.displayStrength(selectedStrength);
			}
			else {
				CardioExercise selectedCardio = cardioExerciseList.get(allExercises.getSelectionModel().getSelectedIndex());
				viewExercisePage.displayCardio(selectedCardio);
			}
		});
		viewExerciseInfo.setPrefSize(300, 40);
		
		VBox labelBox = new VBox();
		labelBox.setSpacing(15);
		labelBox.setAlignment(Pos.CENTER);
		labelBox.getChildren().addAll(headerLabel, nameLabel, nameField, typeLabel, showButtons , exercisesToAdd, allExercises, viewExerciseInfo,
				exercisesAdded, selectedExercises, buttonBox, secondRowButtons);
		GridPane.setHalignment(labelBox, HPos.CENTER);
		pane.add(labelBox, 0, 0);
		
		
		return pane;
	}
	
	private static void showAlert(Alert.AlertType alertType, Window win, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(win);
		alert.show();
	}
	
	private static void showCreatedAlert(Alert.AlertType alertType, Window win, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(win);
		alert.setOnCloseRequest(e ->{
			window.close();
			startWorkoutPage.display();
		});
		alert.show();
	}
	
	private static void showDuplicateAlert(Alert.AlertType alertType, Window win, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(win);
		alert.show();
	}
	

}
