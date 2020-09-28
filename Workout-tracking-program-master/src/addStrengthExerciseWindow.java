import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class addStrengthExerciseWindow{
	
	private static Stage window = new Stage();
	private static GridPane layout;
	private static Scene page;
	private static TextField name;
	private static ComboBox<String> muscleAreaBox;
	private static ComboBox<String> weightTypeBox;
	private static ComboBox<String> equiptmentBox;
	private static ComboBox<String> targetMuscleBox;
	private static File exerciseFile = new File("strengthexercises.txt");
	private static String style = "    -fx-background-color: radial-gradient(center 50% 50% , radius 80% , #69696b ,   #3a3a3a);" + 
			"    -fx-padding: 10;\n" +
			"    -fx-text-fill:  #c6f5f9 ;\n";

	private static String textStyle = "-fx-text-fill: #c6f5f9;";
	private static String buttonStyle = " -fx-background-color: rgba(3, 252, 248, 0.4);"
	+ " -fx-background-radius: 10; -fx-text-fill: #c6f5f9; -fx-font: 14px Arial; -fx-font-weight: Bold;";
	
	
	//The display method allows the GUI page to be called from other pages
	public static void display() {
		
		window.setOnCloseRequest(e ->{
			window.close();
		});
		
		setAddWindow();
		
	}

	//This method will set the settings for the window 
	private static void setAddWindow(){
		
		window.setTitle("Add new exercise");
		window.centerOnScreen();
		layout = createAddLayout();
	    page = new Scene(layout, 450, 400);
		window.setScene(page);
		window.show();
	}
	
	//Create the layout for the add exercise window
	private static GridPane createAddLayout() {
		
		GridPane gridPane = new GridPane();
		

		//Set the style settings for the pane
		gridPane.setPadding(new Insets(50, 40, 40, 40));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		ColumnConstraints columnOneConstrains = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstrains.setHalignment(HPos.RIGHT);
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);
		gridPane.getColumnConstraints().addAll(columnOneConstrains, columnTwoConstrains);
		
		
		//Add label, buttons, and controls for the pane
		gridPane = addUIControls(gridPane);

		return gridPane;
		
	}
	
	
	private static GridPane addUIControls(GridPane pane) {
		GridPane gridPane = pane;
		gridPane.setStyle(style);
		
		///////////////////////////////////////////////////////
		//////////////Create labels and text fields////////////
		///////////////////////////////////////////////////////
		Label headerLabel = new Label("Add New Exercise");
		headerLabel.setStyle(textStyle);
		headerLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
		
		gridPane.add(headerLabel, 0, 0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets( 0, 0, 20, 0));
		
		
		Label nameLabel = new Label("Exercise Name: ");
		nameLabel.setStyle(textStyle);
		GridPane.setHalignment(nameLabel, HPos.LEFT);
		gridPane.add(nameLabel, 0, 1);
		
		name = new TextField();
		gridPane.add(name, 1, 1);
		
		Label typeLabel = new Label("Upper or Lower Body: ");
		typeLabel.setStyle(textStyle);
		GridPane.setHalignment(typeLabel, HPos.LEFT);
		GridPane.setHgrow(typeLabel, Priority.ALWAYS );
		gridPane.add(typeLabel, 0, 2);
		
		muscleAreaBox = new ComboBox<String>();
		muscleAreaBox.setPrefWidth(250);
		muscleAreaBox.getItems().addAll("Upper Body", "Lower Body");
		gridPane.add(muscleAreaBox, 1, 2);
		
		
		Label weightType = new Label("Weight Type: ");
		weightType.setStyle(textStyle);
		GridPane.setHalignment(weightType, HPos.LEFT);
		gridPane.add(weightType, 0, 3);
		
		weightTypeBox = new ComboBox<String>();
		weightTypeBox.setPrefWidth(250);
		weightTypeBox.getItems().addAll("Body Weight", "Free Weights", "Machine Resistance");
		gridPane.add(weightTypeBox, 1, 3);
		
		Label equiptment = new Label("Equipment Needed: ");
		equiptment.setStyle(textStyle);
		GridPane.setHalignment(equiptment, HPos.LEFT);
		gridPane.add(equiptment, 0, 4);
		
		
		equiptmentBox = new ComboBox<String>();
		equiptmentBox.setPrefWidth(250);
		weightTypeBox.setOnAction(e -> {
			
			equiptmentBox.getItems().clear();
			if(weightTypeBox.getValue().equalsIgnoreCase("Body Weight"))
				equiptmentBox.getItems().addAll("None", "Pull Up Bar", "Dip Bar");
			
			if(weightTypeBox.getValue().equalsIgnoreCase("Free Weights"))
				equiptmentBox.getItems().addAll("Dumbells", "Weighted Barbell", "Kettle Bell", "Curl bar", "Medicine Ball");
			
			if(weightTypeBox.getValue().equalsIgnoreCase("Machine Resistance"))
				equiptmentBox.getItems().addAll("Chest Press Machine", "Chest Fly Machine", "Shoulder Press Machine", 
						"Cable Machine", "Lat Pull Down Machine", "Leg Extension Machine", "Leg Curl Machine", "Smith Machine", 
						"Leg Press Machine", "Other");
			
		});

		gridPane.add(equiptmentBox, 1, 4);
		
		Label targetArea = new Label("Target Area: ");
		targetArea.setStyle(textStyle);
		GridPane.setHalignment(targetArea, HPos.LEFT);
		gridPane.add(targetArea, 0, 5);
		
		targetMuscleBox = new ComboBox<String>();
		targetMuscleBox.setPrefWidth(250);
		
		
		
		//Fill the targetMuscleBox with the correct exercises based on the body area choice
		muscleAreaBox.setOnAction(e -> {
			
			targetMuscleBox.getItems().clear();
			if(muscleAreaBox.getValue().equalsIgnoreCase("Upper Body")) {
				
				for(int i = 0; i < StrengthExercise.upperMuscleGroups.length; i++) {
					targetMuscleBox.getItems().add(StrengthExercise.upperMuscleGroups[i]);
				}
			}
			if(muscleAreaBox.getValue().equalsIgnoreCase("Lower Body")) {
				
				for(int i = 0; i < StrengthExercise.lowerMuscleGroups.length; i++) {
					targetMuscleBox.getItems().add(StrengthExercise.lowerMuscleGroups[i]);
				}
			}
			
		});
	
		
		gridPane.add(targetMuscleBox, 1, 5);
		///////////////////////////////////////////////////////
		///////////////////////////////////////////////////////
		///////////////////////////////////////////////////////
		
		
		
		
		
		///////////////////////////////////////////////////////
	    ////////////////Create Buttons////////////////////////
		///////////////////////////////////////////////////////
		Button save = new Button("Save Exercise");
		save.setStyle(buttonStyle);
		save.setOnAction(e -> {
			try {
				saveExercise();
			}
			catch(IOException i) {
				System.out.println("Error");
			}
			name.setText("");
			muscleAreaBox.setValue("");
			weightTypeBox.setValue("");
			equiptmentBox.setValue("");
			targetMuscleBox.setValue("");
		});
		
		Button goBack = new Button("Back");
		goBack.setStyle(buttonStyle);
		goBack.setOnAction(e -> {
			window.close();
			exercisePage.display();
		});
		///////////////////////////////////////////////////////
		///////////////////////////////////////////////////////
		///////////////////////////////////////////////////////
		
		
		
		
		
		
		///////////////////////////////////////////////////////
		/////////////Add buttons to an HBox////////////////////
		///////////////////////////////////////////////////////
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(save, goBack);
		buttonBox.setSpacing(20);
		buttonBox.setAlignment(Pos.BASELINE_LEFT);
		GridPane.setRowIndex(gridPane, 4);
		GridPane.setRowSpan(gridPane, 2);
		gridPane.add(buttonBox, 1, 8);
		///////////////////////////////////////////////////////
		///////////////////////////////////////////////////////
		///////////////////////////////////////////////////////
		
		return gridPane;
	}
	
	//This method will save the entered exercise to the text file to be
	// added to the exercise page
	private static void saveExercise() throws IOException {
		
		
		//Check if the file already exists
		if(exerciseFile.exists()) {
			System.out.println("File already exists");
		
		}
		//If the file does not already exist, create a new file to save the exercise on
		else {
			System.out.println("File created: " + exerciseFile.getName());
			exerciseFile.createNewFile();
		}

		
	    //Verify that all necessary information is added
		if(name.getText().isEmpty() || muscleAreaBox.getValue().isEmpty() ||
			weightTypeBox.getValue().isEmpty() || equiptmentBox.getValue().isEmpty() ||
			targetMuscleBox.getValue().isEmpty()) {
				showAlert(Alert.AlertType.ERROR, layout.getScene().getWindow(), "Error", "Enter all information");
				return;
		}
		
		//Create new exercise object with the information given in the text box	
		StrengthExercise newExercise = new StrengthExercise();
		newExercise.setName(name.getText());
		newExercise.setTargetMuscleArea(muscleAreaBox.getValue());
		newExercise.setMainTargetMuscle(targetMuscleBox.getValue());
		newExercise.setWeightType(weightTypeBox.getValue());
		newExercise.setEquiptmentNeeded(equiptmentBox.getValue());

		
		//Verify if the exercise already exists
		if(checkDuplicates(newExercise) == true) {
			showAlert(Alert.AlertType.ERROR, layout.getScene().getWindow(), "Error", "Exercise already exists");
			return;
		}
		
		//Add exercise to text file to be saved
		try(FileWriter fw = new FileWriter(exerciseFile.getAbsoluteFile(), true);
			BufferedWriter writer = new BufferedWriter(fw)){
		
			writer.write(Account.getCurrentUserName() + "," + newExercise.getName() + "," + newExercise.getTargetMuscleArea() + "," +
			newExercise.getMainTargetMuscle() + "," + newExercise.getWeightType() + "," + newExercise.getEquiptmentNeeded() + "\n");
			writer.close();
			showAlert(Alert.AlertType.CONFIRMATION, layout.getScene().getWindow(), "Success", "Exercise added");
			
			
		} catch(IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}

		
	}
	
	
	
	//This method will check that the exercise being added is not a duplicate that already exists in the file
	private static boolean checkDuplicates(StrengthExercise e) {
		
		//TO DO: Finish method and get it to correctly verify if the exercise exists
		boolean exists = false;
		int count = 0;
		//Fill an array list with exercises from the strength exercise file
		ArrayList <StrengthExercise> allExercises = StrengthExercise.getAllExercises();
		
		//Check if the new exercise is already listed 
		for(int i = 0; i < allExercises.size(); i++) {
			if(allExercises.get(i).equals(e)) {
				count++;
			}
		}
		if(count > 0)
			exists = true;
	    
	    return exists;
		
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
