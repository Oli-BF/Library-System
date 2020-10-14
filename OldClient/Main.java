package client;

import client.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import server.Server;

/**
 * @author osb807
 * 
 * TBC
 */
public class Main extends Application
{
	public Client client;
	
	@Override
	public void start(Stage stage) throws Exception
	{
		client = new Client("localhost", 8888); 
		
//		Parent root = FXMLLoader.load(getClass().getResource("views/Login.fxml"));
//		Scene scene = new Scene(root);
//		stage.setScene(scene);
//		stage.show();
		
//		String fxmlResource = "src/client/views/Login.fxml";
//		Parent panel;
//		panel = FXMLLoader.load(getClass().getResource(fxmlResource));
//		Scene scene = new Scene(panel);
//		Stage stage = new Stage();
//		stage.setScene(scene);
//		stage.show();
		
		/**
		 * To be changed.
		 */
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/Login.fxml"));
		Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setClient(client);
        stage.setTitle("Login Page");
        stage.setScene(new Scene(root, 800, 550));
        stage.setResizable(true);
        stage.show();	
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}