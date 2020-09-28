import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class loginPage {

	String checkUser, checkPW;
	private static GridPane logPane = createLoginForm();
	private static GridPane signPane = createLoginForm();
	private static Scene logScene = new Scene(logPane, 450, 300);
	private static Scene signScene = new Scene(signPane, 450, 375);
	private static Stage main = new Stage();
	private static File accountFile = new File("Accounts.txt");
	private static File currentAccount = new File("currentAccount.txt");
	private static Account account;
	private static String style = "    -fx-background-color: radial-gradient(center 50% 50% , radius 80% , #69696b ,   #3a3a3a);" + 
			"    -fx-padding: 10;\n" +
			"    -fx-text-fill:  #c6f5f9 ;\n";
	private static String textStyle = "-fx-text-fill: #c6f5f9;";

	private static String buttonStyle = " -fx-background-color: rgba(3, 252, 248, 0.4);"
	+ " -fx-background-radius: 10; -fx-text-fill: #c6f5f9; -fx-font: 14px Arial; -fx-font-weight: Bold;";
	
	public static void display() {
		setLoginWindow();
	}
	
	private static void setLoginWindow() {
		main.setOnCloseRequest(e -> closeWindow());
		main.setTitle("User Login");
		main.centerOnScreen();
		logUIControls(logPane);
		main.setScene(logScene);
		main.show();
	}

	private static GridPane createLoginForm() {
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setStyle(style);
		gridPane.setPadding(new Insets(40, 40, 40, 40));
		
		gridPane.setHgap(10);
		gridPane.setVgap(25);
		
		return gridPane;
	}
	
	private static void logUIControls(GridPane gridPane) {

		gridPane.setStyle(style);
		Label headerLabel = new Label("Login Form");
		headerLabel.setStyle(textStyle);
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		gridPane.add(headerLabel, 0, 0);
		GridPane.setValignment(headerLabel, VPos.TOP);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
		Label userNameLabel = new Label("Username: ");
		userNameLabel.setStyle(textStyle);
		userNameLabel.setPrefWidth(70);

		
		TextField userName = new TextField();
		userName.setPrefHeight(25);
		userName.setPrefWidth(200);
		
		HBox userNameBox = new HBox();
		userNameBox.getChildren().addAll(userNameLabel, userName);
		userNameBox.setAlignment(Pos.CENTER);
		userNameBox.setSpacing(30);
	
		
		Label passwordLabel = new Label("Password: ");
		passwordLabel.setStyle(textStyle);
		passwordLabel.setPrefWidth(70);
		passwordLabel.setPrefHeight(25);

		
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(25);
		passwordField.setPrefWidth(200);

		
		HBox passwordBox = new HBox();
		passwordBox.getChildren().addAll(passwordLabel, passwordField);
		passwordBox.setAlignment(Pos.CENTER);
		passwordBox.setSpacing(30);
	
		
		VBox fields = new VBox();
		fields.getChildren().addAll(userNameBox, passwordBox);
		fields.setSpacing(10);
		gridPane.add(fields, 0, 1);
		

		Button signUpButton = new Button("Sign Up");
		signUpButton.setPrefHeight(30);
		signUpButton.setStyle(buttonStyle);
		signUpButton.setDefaultButton(true);
		signUpButton.setPrefWidth(80);


		Button loginButton = new Button("Login");
		loginButton.setStyle(buttonStyle);
		loginButton.setPrefHeight(30);
		loginButton.setDefaultButton(true);
		loginButton.setPrefWidth(80);
		
		Button goBack = new Button("Back");
		goBack.setStyle(buttonStyle);
		goBack.setPrefHeight(30);
		goBack.setPrefWidth(70);
		goBack.setOnAction(e -> {
			main.close();
			homePage.display();
		});
		
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(signUpButton, loginButton, goBack);
		buttonBox.setSpacing(20);
		buttonBox.setAlignment(Pos.CENTER);
		gridPane.add(buttonBox, 0, 3);

		
		loginButton.setOnAction(e -> {
			try {
				if(signIn(userName.getText(), passwordField.getText()) == true){
					showLoggedInAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Success", "Login Successful");
				}
				else
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error", "Incorrect Username or Password");
			}catch(IOException i) {
				System.out.println("Error");
			}
			
		
		});

		signUpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				signUIControls(signPane);
				main.setScene(signScene);
				main.show();
			}
		
		});
	}

	private static void signUIControls(GridPane gridPane) {
		gridPane.setVgap(10);
		Label headerLabel = new Label("Sign Up Form");
		headerLabel.setStyle(textStyle);
		gridPane.setStyle(style);
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		gridPane.add(headerLabel, 0, 0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
		
		Label userNameLabel = new Label("Username: ");
		userNameLabel.setStyle(textStyle);
		gridPane.add(userNameLabel, 0, 1);
		
		TextField userName = new TextField();
		userName.setStyle("-fx-background-color: green, white, white;");
		userName.setPrefHeight(40);
		gridPane.add(userName, 1, 1);
		
		Label passwordLabel = new Label("Password: ");
		passwordLabel.setStyle(textStyle);
		gridPane.add(passwordLabel, 0, 2);
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(40);
		gridPane.add(passwordField, 1, 2);
		
		Label genderLabel = new Label("Gender: ");
		genderLabel.setStyle(textStyle);
		gridPane.add(genderLabel, 0, 3);
		
		ComboBox<String>genderBox = new ComboBox<String>();
		genderBox.setPrefWidth(250);
		genderBox.getItems().addAll("Male", "Female");
		genderBox.setPrefHeight(40);
		gridPane.add(genderBox, 1, 3);
		
		Label ageLabel = new Label("Age: ");
		ageLabel.setStyle(textStyle);
		gridPane.add(ageLabel, 0, 4);
		
		TextField age = new TextField();
		age.setPrefHeight(40);
		gridPane.add(age, 1, 4);
		
				
		Label userWeightLabel = new Label("Weight(lbs): ");
		userWeightLabel.setStyle(textStyle);
		gridPane.add(userWeightLabel, 0, 5);
		
		TextField userWeight = new TextField();
		userWeight.setPrefHeight(40);
		gridPane.add(userWeight, 1, 5);
		
		Button backButton = new Button("Back");
		backButton.setPrefHeight(40);
		backButton.setStyle(buttonStyle);
		backButton.setDefaultButton(true);
		backButton.setPrefWidth(100);

		backButton.setOnAction(e -> {
			logUIControls(logPane);
			main.setScene(logScene);
			main.show();
		});

		Button submitButton = new Button("Submit");
		submitButton.setStyle(buttonStyle);
		submitButton.setPrefHeight(40);
		submitButton.setDefaultButton(true);
		submitButton.setPrefWidth(100);

		
		HBox signUpButtonBox = new HBox();
		signUpButtonBox.getChildren().addAll(submitButton, backButton);
		signUpButtonBox.setAlignment(Pos.CENTER);
		signUpButtonBox.setSpacing(30);
		gridPane.add(signUpButtonBox, 0, 6, 2, 1);
		
		submitButton.setOnAction(e -> {
			try {
				account = new Account();
				account.setUsername(userName.getText());
				account.setPassword(passwordField.getText());
				account.setAge(Integer.parseInt(age.getText()));
				account.setGender(genderBox.getValue());
				account.setWeight(Integer.parseInt(userWeight.getText()));
			}
			catch(NumberFormatException n) {
				showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Invalid entry", "Invalid information, check entries and try again");
			}
			try {
				saveAccount();
			}
			catch(IOException i) {
				System.out.println("Error");
			}
			
		});
		
		
	}
	
	private static boolean signIn(String userName, String passWord) throws IOException{
		
		ArrayList<Account> accounts = Account.getAllAccounts();
		BufferedWriter writer;
		
		for(int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i).getUsername().equals(userName) && accounts.get(i).getPassword().equals(passWord)) {
				if(!currentAccount.exists()) {
					currentAccount.createNewFile();
					System.out.println("File created: " + accountFile.getName());
				}
				try {
					writer = new BufferedWriter(new FileWriter(currentAccount));
					String currentAccountString = accounts.get(i).getUsername() + "," + accounts.get(i).getPassword() + "," +
							accounts.get(i).getAge() + "," + accounts.get(i).getGender() + "," + accounts.get(i).getWeight();
					writer.write(currentAccountString);
					writer.close();
					
				}catch(IOException e) {
					System.out.println("Error");
				}
				
				return true;
			}
		}
		
		return false;
		
	}
	
	private static void saveAccount() throws IOException {
		
		String accountInfo = "";

		if(!accountFile.exists()) {
			accountFile.createNewFile();
			System.out.println("File created: " + accountFile.getName());
		}

		
		
		if(account.getUsername().isEmpty() || account.getPassword().isEmpty()||
				account.getGender().equals(null) || account.getAge() == 0|| account.getWeight() == 0 ) {
			showAlert(Alert.AlertType.ERROR, signPane.getScene().getWindow(), "Error", "Please enter all information");
			return;
		}
		
		if(Account.checkDuplicates(account) == true) {
			showDuplicateAlert(AlertType.ERROR, signPane.getScene().getWindow(), "Duplicate Account", "This username already exists, please pick a new username");
			return;
		}
		
		try(FileWriter fw = new FileWriter(accountFile, true)) {
			BufferedWriter writer = new BufferedWriter(fw);
			accountInfo = account.getUsername() + "," + account.getPassword() + "," + account.getAge() +
					"," + account.getGender() + "," + account.getWeight() +"\n";
			
			writer.write(accountInfo);
			writer.close();
			showAlert(Alert.AlertType.CONFIRMATION, signPane.getScene().getWindow(), "Success", "Registration Successful");
		
		}catch(IOException e) {
			System.out.println("Error");
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
	
	private static void showLoggedInAlert(Alert.AlertType alertType, Window win, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(win);
		alert.setOnCloseRequest(e ->{
			main.close();
			homePage.display();
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

	public static void closeWindow() {
		main.close();
		homePage.display();
	}


}
	

