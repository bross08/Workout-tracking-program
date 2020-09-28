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


public class addCardioExerciseWindow{
	
	private static Stage window = new Stage();
	private static GridPane layout;
	private static Scene page;
	private static TextField name;
	private static CardioExercise newExercise;
	private static ComboBox<String> equiptmentBox;
	private static File exerciseFile = new File("cardioexercises.txt");
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
	public static void setAddWindow(){
		
		window.setTitle("Add new exercise");
		window.centerOnScreen();
		layout = createAddLayout();
	    page = new Scene(layout, 450, 350);
		window.setScene(page);
		window.show();
	}
	
	//Create the layout for the add exercise window
	public static GridPane createAddLayout() {
		
		GridPane gridPane = new GridPane();
		
		//Set the style settings for the pane
		gridPane.setPadding(new Insets(40, 40, 40, 40));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		ColumnConstraints columnOneConstrains = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstrains.setHalignment(HPos.RIGHT);
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);
		gridPane.getColumnConstraints().addAll(columnOneConstrains, columnTwoConstrains);
		
		

		//Add the labels, buttons, and controls for the pane
		gridPane = addUIControls(gridPane);
		
		
		return gridPane;
		
	}
	
	//This method contains all of the UI controls for the page
	private static GridPane addUIControls(GridPane pane) {
		
		GridPane gridPane = pane;
		pane.setStyle(style);
		
		
		///////////////////////////////////////////////////////
		//////////////Create labels and text fields////////////
		///////////////////////////////////////////////////////
		Label headerLabel = new Label("Add new exercise");
		headerLabel.setStyle(textStyle);
		headerLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
		
		gridPane.add(headerLabel, 0, 0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets( 20, 0, 20, 0));
		

		Label nameLabel = new Label("Exercise Name: ");
		nameLabel.setStyle(textStyle);
		GridPane.setHalignment(nameLabel, HPos.LEFT);
		gridPane.add(nameLabel, 0, 1);
		
		name = new TextField();
		gridPane.add(name, 1, 1);
		
		
		Label equiptmentLabel = new Label("Equipment Needed: ");
		equiptmentLabel.setStyle(textStyle);
		GridPane.setHalignment(equiptmentLabel, HPos.LEFT);
		GridPane.setHgrow(equiptmentLabel, Priority.ALWAYS);
		gridPane.add(equiptmentLabel, 0, 2);
		
		equiptmentBox = new ComboBox<String>();
		equiptmentBox.setPrefWidth(250);
		equiptmentBox.getItems().addAll("None", "Treadmill", "Elliptical", "Stationary Bike", "Bicycle", 
				"Rowing Machine", "Free weights", "Other");
		gridPane.add(equiptmentBox, 1, 2);
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
			//type.setText("");
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
		gridPane.add(buttonBox, 1, 4);
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
		//If the file does not already exist, create a new file to save the exercises on
		else {
			System.out.println("File created: " + exerciseFile.getName());
			exerciseFile.createNewFile();
		}

		
		//Verify that all necessary information is added
		if(name.getText().isEmpty() || equiptmentBox.getValue().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, layout.getScene().getWindow(), "Error", "Enter all information");
			return;
		}
		
		newExercise = new CardioExercise();
		newExercise.setName(name.getText());
		newExercise.setEquiptmentNeeded(equiptmentBox.getValue());
		
		//Use the check duplicates method to verify that the exercise being added does not currently exist in the file
		if(checkDuplicates(newExercise) == true) {
			showAlert(Alert.AlertType.ERROR, layout.getScene().getWindow(), "Error", "Exercise already exists");
			return;
		}
		
		//If the exercise does not exist in the file, save all exercise information to the file
			try(FileWriter fw = new FileWriter(exerciseFile.getAbsoluteFile(), true);
					BufferedWriter writer = new BufferedWriter(fw)){
			
				writer.write(Account.getCurrentUserName() + "," + newExercise.getName()+ "," + newExercise.getEquiptmentNeeded() + "\n");
				
				writer.close();
				showAlert(Alert.AlertType.CONFIRMATION, layout.getScene().getWindow(), "Success", "Exercise added");
				
				
			} catch(IOException e) {
				System.out.println("Error");
				e.printStackTrace();
			}

	}
	
	//This method will check that the exercise being added is not a duplicate that already exists in the file
	private static boolean checkDuplicates(CardioExercise e) {
		
		//TO DO: Finish method and get it to correctly verify if the exercise exists
		boolean exists = false;
		int count = 0;
		ArrayList <CardioExercise> allExercises = CardioExercise.getAllExercises();
		
		for(int i = 0; i < allExercises.size(); i++) {
			if(allExercises.get(i).equals(e)) {
				count++;
			}
		}
		if(count > 0)
			exists = true;
	    
	    return exists;
		
	}




	
	
	//This method will show an alert box to produce confirmation or error messages.
	private static void showAlert(Alert.AlertType alertType, Window win, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(win);
		alert.show();
	}
	
		

}
