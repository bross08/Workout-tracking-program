import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class accountPage {
	
	private static Stage window = new Stage();
	private static GridPane layout;
	private static Scene page;
	private static Account account;
//	private static Account changedAccount;
	private static String style = "    -fx-background-color: radial-gradient(center 50% 50% , radius 80% , #69696b ,   #3a3a3a);" + 
			"    -fx-padding: 10;\n" +
			"    -fx-text-fill:  #c6f5f9 ;\n";
	private static String textStyle = "-fx-text-fill: #c6f5f9;";
	private static String buttonStyle = " -fx-background-color: rgba(3, 252, 248, 0.4);"
	+ " -fx-background-radius: 10; -fx-text-fill: #c6f5f9; -fx-font: 14px Arial; -fx-font-weight: Bold;";
	
	public static void display() {
		account = Account.getCurrentAccount();
		setAccountWindow();
	}
	
	
	private static void setAccountWindow() {
		window.setTitle("User Account");
		window.centerOnScreen();
		layout = createAccountMainPage();
		page = new Scene(layout, 220, 325); 
		window.setScene(page);
		window.show();
	}
	
	private static GridPane createAccountMainPage() {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setVgap(5);

		
		pane.setStyle(style);
		
		Label nameLabel = new Label("" +account.getUsername());
		nameLabel.setStyle(textStyle);
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		pane.add(nameLabel, 0, 0);
		GridPane.setHalignment(nameLabel, HPos.CENTER);
		GridPane.setValignment(nameLabel, VPos.TOP);
		GridPane.setHgrow(nameLabel, Priority.ALWAYS);

		
		VBox accountOptionsBox = new VBox();
		accountOptionsBox.setAlignment(Pos.CENTER);
		accountOptionsBox.setSpacing(5);
		Button changeAccountName = new Button("Change Name");
		changeAccountName.setPrefSize(200, 70);
		changeAccountName.setStyle(buttonStyle);
		changeAccountName.setOnAction(e ->{ 
			changeInfoWindow("Name");
		});
		
		
		Button changePass = new Button("Change Password");
		changePass.setPrefSize(200, 70);
		changePass.setStyle(buttonStyle);
		changePass.setOnAction(e ->{
			changeInfoWindow("Password");
		});
		
		
		Button updateAge = new Button("Update Age");
		updateAge.setPrefSize(200, 70);
		updateAge.setStyle(buttonStyle);
		updateAge.setOnAction(e ->{
			changeInfoWindow("Age");
		});
		
		
		Button updateWeight = new Button("Update Weight");
		updateWeight.setPrefSize(200, 70);
		updateWeight.setStyle(buttonStyle);
		updateWeight.setOnAction(e ->{
			changeInfoWindow("Weight");
		});
		
		Button logOut = new Button("Log out");
		logOut.setStyle(buttonStyle);
		logOut.setPrefSize(200, 70);
		logOut.setOnAction(e ->{
			showLogOutAlert(Alert.AlertType.CONFIRMATION, window, "Logged Out", "Account logged out");
		});
		
		Button goBack = new Button("Back");
		goBack.setPrefSize(200, 70);
		goBack.setStyle(buttonStyle);
		goBack.setOnAction(e -> {
			window.close();
			homePage.display();
		});
		accountOptionsBox.getChildren().addAll(changeAccountName, changePass, updateAge, updateWeight, logOut, goBack);
		
		pane.add(accountOptionsBox, 0 , 1);

		return pane;
	}
	
	private static void changeInfoWindow(String field) {
		Stage userNameWindow = new Stage();
		GridPane userPane = new GridPane();
		userPane.setStyle(style);
		userPane.setAlignment(Pos.CENTER);
		Label change = new Label();
		change.setStyle(textStyle);
		TextField newInfo = new TextField();
		PasswordField passwordField = new PasswordField();
		VBox infoBox = new VBox();
		Button submit = new Button("Submit");
		submit.setStyle(buttonStyle);
		Account newAccount = new Account();
		newAccount.setUsername(account.getUsername());
		newAccount.setPassword(account.getPassword());
		newAccount.setAge(account.getAge());
		newAccount.setGender(account.getGender());
		newAccount.setWeight(account.getWeight());
	
		
		if(field.equalsIgnoreCase("Name")) {
			change.setText("Enter your new username: ");
			submit.setOnAction(e ->{
				newAccount.setUsername(newInfo.getText());
				Account.changeAccountInfo(account, newAccount);
			});
		}
		
		if(field.equalsIgnoreCase("Password")) {
			change.setText("Enter your new password: ");
			submit.setOnAction(e ->{
				newAccount.setPassword(newInfo.getText());
				Account.changeAccountInfo(account, newAccount);
			});
		}
		
		if(field.equalsIgnoreCase("Age")) {
			change.setText("Enter your current age: ");
			submit.setOnAction(e ->{
				newAccount.setAge(Integer.parseInt(newInfo.getText()));
				Account.changeAccountInfo(account, newAccount);
			});
		}
		
		if(field.equalsIgnoreCase("Weight")) {
			change.setText("Enter your current weight: ");
			submit.setOnAction(e ->{
				newAccount.setWeight(Integer.parseInt(newInfo.getText()));
				Account.changeAccountInfo(account, newAccount);
			});
		}
		
		change.setFont(Font.font("Arial", FontWeight.BLACK, 18));
		userPane.setVgap(40);
		userPane.add(change, 0, 0);
		Button back = new Button("Back");
		back.setStyle(buttonStyle);
		back.setOnAction(e ->{
			userNameWindow.close();
		});
		
		submit.setPrefSize(100, 30);
		back.setPrefSize(100, 30);
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(40);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(submit, back);
		
		if(field.equalsIgnoreCase("Password")) 
			infoBox.getChildren().addAll(passwordField, buttonBox);
		
		else
			infoBox.getChildren().addAll(newInfo, buttonBox);
		
		infoBox.setSpacing(20);
		userPane.add(infoBox, 0, 1);
		
		
		
		Scene changePage = new Scene(userPane, 270, 280);
		userNameWindow.setScene(changePage);
		userNameWindow.show();
		
		
		
	}
	
	private static void showLogOutAlert(Alert.AlertType alertType, Window win, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(win);
		alert.show();
		alert.setOnCloseRequest(e -> {
			Account.logOut();
			window.close();
			homePage.display();
		});
		
	}
	
	
	

}
