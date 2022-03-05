package view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.User;
import model.AccessUsers;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;


public class Login{
	public Scene exec(Stage primaryStage){

		BorderPane mainPane = new BorderPane();
		Scene scene = new Scene(mainPane,750,510);
		VBox vertical = new VBox();

		Image logo = new Image("/logo.gif",200,200,false,false);
		ImageView logoView = new ImageView();
		logoView.setImage(logo);

		HBox info_name = new HBox();
		Image nameAvatar = new Image("/user-login.png",25,25,false,false);
		ImageView nameView = new ImageView();
		TextField nameField = new TextField();

		nameView.setImage(nameAvatar);
		info_name.getChildren().addAll(nameView,nameField);
		info_name.setAlignment(Pos.CENTER);

		HBox info_password = new HBox();

		Image passwordAvatar = new Image("/lock.png",25,25,false,false);
		ImageView passwordView = new ImageView();
		PasswordField passField = new PasswordField();
		passwordView.setImage(passwordAvatar);
	    info_password.getChildren().addAll(passwordView,passField);
		info_password.setAlignment(Pos.CENTER);

		Button login = new Button("LOGIN");
        login.setStyle(" -fx-background-radius: 5;" +
				"-fx-font-size:15px;" +
				"-fx-font-weight: bold;" +
				"-fx-background-color:#54428E;" +
				"-fx-text-fill: white;" +
				"-fx-background-insets: 0,1,2;");


		Text err = new Text("Please try again!");
		err.setStroke(Color.WHITE);
		err.getStyleClass().add("error");
		err.setVisible(false);

		Button cancel = new Button("Cancel");
		cancel.getStyleClass().addAll("cancel","login-but");

		vertical.setAlignment(Pos.CENTER);
		vertical.setSpacing(10);
	    vertical.getChildren().addAll(logoView,info_name,info_password,login,err);

		login.setOnAction(e -> {
			String username = nameField.getText();
			String password = passField.getText();

			File file = new File(AccessUsers.filename);
			boolean found = false;
			if(file.exists() && !file.isDirectory())
			{
				AccessUsers fileUsers = new AccessUsers();
				User user = fileUsers.checkUser(username, password);

				if(user != null)
				{
					primaryStage.close();
					primaryStage.setScene((new MainMenu(user)).exec(primaryStage));
					primaryStage.show();
				}
			}

			if(username.equals("admin") && password.equals("admin"))
			{
				User overrideUser = new User(0, "admin", "admin", 1);
				primaryStage.setScene((new MainMenu(overrideUser)).exec(primaryStage));
				//primaryStage.show();
			} else {
				nameField.setText("");
				passField.setText("");
				err.setVisible(true);
			}
		});

		cancel.setOnAction(e -> {
			primaryStage.close();
		});

		mainPane.setCenter(vertical);
	    mainPane.setStyle("-fx-background-image: url('/login2.jpg');");

		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		return scene;
	}
}