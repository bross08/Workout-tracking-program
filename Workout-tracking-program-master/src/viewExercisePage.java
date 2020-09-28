import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class viewExercisePage {

	private static Stage window = new Stage();
	private static GridPane layout;
	private static Scene page;
	private static StrengthExercise strengthExercise;
	private static CardioExercise cardioExercise;
	private static String style = 
			"    -fx-background-color: radial-gradient(center 50% 50% , radius 80% , #69696b ,   #3a3a3a);" + 
			"    -fx-padding: 10;\n" +
			"    -fx-text-fill:  #c6f5f9 ;\n";
	
	private static String style2 = "-fx-background-radius: 0;" +
					"-fx-text-fill: #c6f5f9;";

	
	public static void displayStrength(StrengthExercise exercise) {
		strengthExercise = exercise; 
		setViewWindow("Strength");
		window.setOnCloseRequest(e -> window.close());
	}
	
	public static void displayCardio(CardioExercise exercise) {
		cardioExercise = exercise;
		setViewWindow("Cardio");
		window.setOnCloseRequest(e -> window.close());
	}
	
	private static void setViewWindow(String type) {
		
		window.setTitle(""); 
		window.centerOnScreen();
		if(type.equalsIgnoreCase("Strength"))
			layout = createViewStrengthPage();
		else
			layout = createViewCardioPage();
		page = new Scene(layout, 400, 240);		
		window.setScene(page);
		window.show();
		
	}
	
	private static GridPane createViewStrengthPage() {
		GridPane pane = new GridPane();

		//pane.setGridLinesVisible(true);
		pane.setAlignment(Pos.TOP_CENTER);
		//pane.setGridLinesVisible(true);
		pane.setPadding(new Insets(40,10,20,10));
		pane.setVgap(30);

		
		pane.setStyle(style);
		Label nameLabel = new Label(strengthExercise.getName());
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
		nameLabel.setStyle(style2);
		GridPane.setHalignment(nameLabel, HPos.CENTER);
		GridPane.setHgrow(nameLabel, Priority.ALWAYS);
		pane.add(nameLabel, 0, 0, 2, 1);
		
		Label areaLabel = new Label("Muscle Area: ");
		areaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		areaLabel.setStyle(style2);
		
		Label muscleArea = new Label(strengthExercise.getTargetMuscleArea() + "");
		muscleArea.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		muscleArea.setStyle(style2);
		
		Label muscleLabel = new Label("Target Muscle");
		muscleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		muscleLabel.setStyle(style2);
		
		Label targetMuscle = new Label(strengthExercise.getMainTargetMuscle());
		targetMuscle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		targetMuscle.setStyle(style2);
		
		Label weightLabel = new Label("Resistance type: ");
		weightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		weightLabel.setStyle(style2);

		Label weightType = new Label(strengthExercise.getWeightType());
		weightType.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		weightType.setStyle(style2);
		
		Label equiptmentLabel = new Label("Equipment Needed: ");
		equiptmentLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		equiptmentLabel.setStyle(style2);
		
		
		Label equiptment = new Label(strengthExercise.getEquiptmentNeeded());
		equiptment.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		equiptment.setStyle(style2);
		


		VBox titleBox = new VBox();
		titleBox.setAlignment(Pos.BASELINE_LEFT);
		titleBox.getChildren().addAll(areaLabel, muscleLabel, weightLabel, equiptmentLabel);
		titleBox.setSpacing(10);
		titleBox.setStyle(style2);
		
		VBox exerciseInfoBox = new VBox();
		exerciseInfoBox.setAlignment(Pos.BASELINE_LEFT);
		exerciseInfoBox.getChildren().addAll(muscleArea, targetMuscle, weightType, equiptment);
		exerciseInfoBox.setSpacing(10);
		exerciseInfoBox.setStyle(style2);

		
		HBox info = new HBox();
		info.setSpacing(5);
		info.setStyle(style2);
		info.getChildren().addAll(titleBox, exerciseInfoBox);
		pane.add(info, 0, 1);
		
		return pane;
	}
	
	private static GridPane createViewCardioPage() {
		window.setWidth(300);
		window.setHeight(150);
		GridPane pane = new GridPane();
		pane.setStyle(style);
		//pane.setGridLinesVisible(true);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setPadding(new Insets(40,40,40,40));
		pane.setVgap(20);

		
		
		
		Label nameLabel = new Label(cardioExercise.getName());
		nameLabel.setStyle(style2);
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		GridPane.setHalignment(nameLabel, HPos.CENTER);
		GridPane.setHgrow(nameLabel, Priority.ALWAYS);
		pane.add(nameLabel, 0, 0, 2, 1);
		

		VBox titleBox = new VBox();
		titleBox.setSpacing(10);
		Label equiptmentLabel = new Label("Equiptment needed: ");
		equiptmentLabel.setStyle(style2);
		titleBox.getChildren().addAll(equiptmentLabel);
		pane.add(titleBox, 0, 1, 1, 2);
		
		
		VBox exerciseInfoBox = new VBox();
		Label equiptment = new Label(cardioExercise.getEquiptmentNeeded());
		equiptment.setStyle(style2);
		exerciseInfoBox.getChildren().addAll(equiptment);
		pane.add(exerciseInfoBox, 1, 1, 1, 2);
		
		return pane;
	}
}
