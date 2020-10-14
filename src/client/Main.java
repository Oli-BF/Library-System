package client;

import client.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
		
		/**
		 * To be simplified?
		 */
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/Login.fxml"));
		Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setClient(client);
        stage.setTitle("Login Page");
        stage.setScene(new Scene(root, 776, 554));
        stage.setResizable(false); //?
        stage.show();	
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}