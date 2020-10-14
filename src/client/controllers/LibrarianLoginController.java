package client.controllers;

import java.io.IOException;
import java.rmi.UnknownHostException;
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
 * @author gaofei
 *
 */
public class LibrarianLoginController 
{
	@FXML
    private PasswordField password;

    @FXML
    private TextField lid;

    @FXML
    private Hyperlink ReaderLink;

    @FXML
    private Button LibrarianLoginButton;

    private Client client;
    
	public void setClient(Client client) 
	{
		this.client = client;
	}
	
    @FXML
    void librarianlogin(ActionEvent event) throws IOException, InterruptedException 
    {
    	if (lid.getText().isEmpty() || password.getText().isEmpty())
        {
			String info = "You have not entered anything.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Username & password required");
			alert.show();
        }
        else if (!lid.getText().isEmpty() && !password.getText().isEmpty())
        {	
        	String[] librarianLoginRequest = new String[3];
        	librarianLoginRequest[0] = "Librarian Login";
        	librarianLoginRequest[1] = lid.getText();
        	librarianLoginRequest[2] = password.getText();
        	
        	client.clientRequest(librarianLoginRequest);
        	
        	TimeUnit.SECONDS.sleep(5);
        	
        	try
        	{
        		if (client.getVerification() == true)
        		{
        			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Management.fxml"));
        			Parent root = fxmlLoader.load();
        	        ManagementController managementController = fxmlLoader.getController();
        	        managementController.setClient(client);
        	        Stage stage = (Stage) LibrarianLoginButton.getScene().getWindow();
        	        stage.close();
        	        stage = new Stage();
        	        stage.setTitle("Management Page");
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
    void showLogin(ActionEvent event) 
    {
    	try 
    	{
			Stage primaryStage=(Stage)ReaderLink.getScene().getWindow();
			primaryStage.close();
			Stage stage=new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Login.fxml"));
			Parent root = fxmlLoader.load();
			LoginController loginController = fxmlLoader.getController();
			loginController.setClient(client);
			stage.setTitle("Login Page");
			stage.setScene(new Scene(root, 776, 554));
			stage.setResizable(false);
			stage.show();
		} 
    	catch (IOException e) 
    	{
    		e.printStackTrace();
	    }
    }
}