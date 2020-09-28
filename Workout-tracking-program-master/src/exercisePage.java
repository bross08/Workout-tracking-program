import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class exercisePage {
	
	private static Button goBack;
	private static GridPane layout;
	private static Stage window = new Stage();
	private static Scene page;
	private static ArrayList<StrengthExercise> strengthExerciseList;
	private static ArrayList<CardioExercise> cardioExerciseList;
	private static ObservableList<String> observableExercises;
	private static StrengthExercise strength;
	private static CardioExercise cardio;
	private static String style = "    -fx-background-color: radial-gradient(center 50% 50% , radius 80% , #69696b ,   #3a3a3a);" + 
			"    -fx-padding: 10;\n" +
			"    -fx-text-fill:  #c6f5f9 ;\n";

	private static String buttonStyle = " -fx-background-color: rgba(3, 252, 248, 0.4);"
	+ " -fx-background-radius: 10; -fx-text-fill: #c6f5f9; -fx-font: 14px Arial; -fx-font-weight: Bold;";
	private static String textStyle = "-fx-text-fill: #c6f5f9;";
	private static String listStyle = "-fx-control-inner-background: grey; "
			+ "-fx-control-inner-background-alt: derive(-fx-control-inner-background, 20%);" +
			"-fx-font: 13px Arial; -fx-font-weight: Bold;";

	
	//The display method allows the GUI page to be called from other pages
	public static void display() {
		window.setOnCloseRequest(e -> window.close());
		setMainWindow();
	}
	
	//Window settings for the main page of the exercise page
	private static void setMainWindow() {
		
		window.setTitle("Exercise Page");
		window.centerOnScreen();
		layout = createExerciseHomePage();
		page = new Scene(layout, 250, 300);
		window.setScene(page);
		window.show();
			
	}
	
	
	private static void setExerciseWindow(String exerciseType) {
		
		window.setTitle(exerciseType + " exercises");
		window.centerOnScreen();
		layout = createExercisePage(exerciseType);
		page = new Scene(layout, 450, 300);
		window.setScene(page);
		window.show();
		
	}
	
	
	//GridPane and UI controls for the exercise home page
	private static GridPane createExerciseHomePage() {
		
	    GridPane gridPane = new GridPane();
	    gridPane.setStyle(style);
	    
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setVgap(10);
	   
	    RowConstraints row0 = new RowConstraints(60);
	    RowConstraints row1 = new RowConstraints(40);
	    RowConstraints row2 = new RowConstraints(40);
	    RowConstraints row3 = new RowConstraints(40);
	    
	    gridPane.getRowConstraints().addAll(row0, row1, row2, row3);
	    
	    
		Label headerLabel = new Label("Exercises");
		headerLabel.setStyle(textStyle);
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		gridPane.add(headerLabel, 0, 0);
		GridPane.setValignment(headerLabel, VPos.TOP);
		GridPane.setHalignment(headerLabel, HPos.CENTER);

		
		Button cardio = new Button("Cardio exercises");
		cardio.setStyle(buttonStyle);
		GridPane.setValignment(cardio, VPos.TOP);
		GridPane.setHalignment(cardio, HPos.CENTER);
		cardio.setPrefSize(200, 70);
		gridPane.add(cardio, 0, 1);
		cardio.setOnAction(e -> setExerciseWindow("Cardio"));
		
		
		Button strength = new Button("Strength exercises");
		strength.setStyle(buttonStyle);
		strength.setPrefSize(200, 70);
		GridPane.setValignment(strength, VPos.TOP);
		GridPane.setHalignment(strength, HPos.CENTER);

		gridPane.add(strength, 0, 2);
		strength.setOnAction(e -> setExerciseWindow("Strength"));
		
		goBack = new Button("Return");
		goBack.setStyle(buttonStyle);
		GridPane.setValignment(goBack, VPos.TOP);
		goBack.setPrefSize(200, 70);
		goBack.setOnAction(e -> {
			window.close();
			homePage.display();
		});
		GridPane.setHalignment(goBack, HPos.CENTER);
		gridPane.add(goBack, 0, 3);
		

		return gridPane;
	}
	
	
	//Gridpane and UI controls for the strength page
	private static GridPane createExercisePage(String type) {

		
		//Fill the ArrayList with the correct exercises
		if(type.equalsIgnoreCase("strength")) {
			//fileName = strengthFileName;
			strengthExerciseList = StrengthExercise.getAllExercises();
			observableExercises = StrengthExercise.loadExercises();
		}
		
		else {
			//fileName = cardioFileName;
			cardioExerciseList = CardioExercise.getAllExercises();
			observableExercises = CardioExercise.loadExercises();
		}
		
			
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setStyle(style);
		gridPane.setPadding(new Insets(40, 40, 40, 40));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		//Create and fill the list of exercises that can be viewed
		ListView<String> exerciseListView = new ListView<String>();
		exerciseListView.setStyle(listStyle);
		exerciseListView.setItems(observableExercises);
		
		//Settings for the listview style
		GridPane.setHalignment(exerciseListView, HPos.CENTER);
		gridPane.add(exerciseListView, 0, 0);
		ColumnConstraints column0 = new ColumnConstraints(370);
		RowConstraints row0 = new RowConstraints(200); 
		column0.setHalignment(HPos.CENTER);
		gridPane.getColumnConstraints().add(column0);
		gridPane.getRowConstraints().add(row0);
		
		


		Button select = new Button("View Exercise Info");
		select.setStyle(buttonStyle);
		select.setOnAction(e -> {
			if(type.equalsIgnoreCase("Strength")) {
				strength = strengthExerciseList.get(exerciseListView.getSelectionModel().getSelectedIndex());
				viewExercisePage.displayStrength(strength);
			}
			
		
			else { 
				cardio = cardioExerciseList.get(exerciseListView.getSelectionModel().getSelectedIndex());
				viewExercisePage.displayCardio(cardio);
			}
	
	
		});
		
		Button addNew = new Button("Add new exercise");
		addNew.setStyle(buttonStyle);
		GridPane.setHalignment(addNew, HPos.LEFT);
		addNew.setOnAction(e -> {
			window.close();
			if(type.equalsIgnoreCase("strength"))
				addStrengthExerciseWindow.display();
			else 
				addCardioExerciseWindow.display();
		}); 
		
		goBack = new Button("Back");
		goBack.setStyle(buttonStyle);
		goBack.setOnAction(e -> {
			window.close();
			setMainWindow();
		});
		
		
		//Add buttons to an HBox for style purposes 
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(select, addNew, goBack);
		buttonBox.setSpacing(10);
		buttonBox.setAlignment(Pos.BASELINE_CENTER);
		gridPane.add(buttonBox, 0, 1);
		
		return gridPane;
		
	}
	
	

}