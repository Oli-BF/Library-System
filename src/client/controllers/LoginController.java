package client.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.UnknownHostException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author gaofei & osb807
 *
 */
public class LoginController 
{
	@FXML
	private PasswordField password;
	
	@FXML
	private TextField mid;
	
	@FXML // fx:id="RegisterLink"
	private Hyperlink RegisterLink; // Value injected by FXMLLoader
	
	@FXML // fx:id="LibrarianLink"
	private Hyperlink LibrarianLink;
	
	@FXML // fx:id="loginButton"
	private Button LoginButton; // Value injected by FXMLLoader

	private Client client;
	
	public void setClient(Client client) 
	{
		this.client = client;
	}

	@FXML
	void showRegister(ActionEvent event) throws IOException
	{
		try 
		{
			Stage primaryStage=(Stage)RegisterLink.getScene().getWindow();
			primaryStage.close();
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Register.fxml"));
			Parent root = fxmlLoader.load();
			RegisterController registerController = fxmlLoader.getController();
			registerController.setClient(client);
			stage.setTitle("Register Page");
			stage.setScene(new Scene(root, 776, 554));
			stage.setResizable(false);
			stage.show();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
	    }
	}

	@FXML
	public void login(ActionEvent event) throws IOException, InterruptedException 
	{
		if (mid.getText().isEmpty() || password.getText().isEmpty())
        {
			String info = "You have not entered anything.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Username & password required");
			alert.show();
        }
        else if (!mid.getText().isEmpty() && !password.getText().isEmpty())
        {	
        	String[] loginRequest = new String[3];
        	loginRequest[0] = "Login";
        	loginRequest[1] = mid.getText();
        	loginRequest[2] = password.getText();
        	
        	client.clientRequest(loginRequest);
        	
        	TimeUnit.SECONDS.sleep(5);
        	
        	try
        	{
        		if (client.getVerification() == true)
        		{
        			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Homepage.fxml"));
        			Parent root = fxmlLoader.load();
        	        HomepageController homepageController = fxmlLoader.getController();
        	        homepageController.setClient(client);
//        	        client.setVerification(homepageController.getVerification());
        	        Stage stage = (Stage) LoginButton.getScene().getWindow();
        	        stage.close();
        	        stage = new Stage();
        	        stage.setTitle("Homepage");
        	        stage.setScene(new Scene(root, 776, 554));
        	        stage.setResizable(false);
        	        stage.show();	
        		}
        		else
        		{
        			String info = "Your details were not recognised by the system. Please try again.";
        			Alert alert = new Alert(AlertType.ERROR, info);
        			alert.setTitle("Invalid details");
        			alert.show();
                	
        		}
        	}
        	catch (UnknownHostException e)
        	{
        		e.printStackTrace();
        	}
        }
	}

	 @FXML
	 void showLibrarian(ActionEvent event) 
	 {
		 try 
		 {
			 Stage primaryStage=(Stage)LibrarianLink.getScene().getWindow();
			 primaryStage.close();
			 Stage stage=new Stage();
			 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/LibrarianLogin.fxml"));
			 Parent root = fxmlLoader.load();
			 LibrarianLoginController loginController = fxmlLoader.getController();
			 loginController.setClient(client);
			 stage.setTitle("Librarian Login Page");
			 stage.setScene(new Scene(root, 776, 554));
			 stage.setResizable(false);
			 stage.show();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	 }

	// form validation after input user name and password
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
	}
}