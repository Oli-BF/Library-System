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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author osb807
 *
 * TBC
 */
public class LoginController implements Initializable 
{
	@FXML private TextField mid;
    @FXML private PasswordField password;
    @FXML private Button loginButton;
	
	private Client client;
//	private boolean verification;
	
	@FXML
	public void register(ActionEvent event)
	{
		// TBC
	}
	
    @FXML
    public void login(ActionEvent event) throws IOException, InterruptedException 
    {
        if (mid.getText().equals("") || password.getText().equals(""))
        {
        	Label message = new Label("You have not entered anything.");
        	Stage stage = new Stage();
            stage.setTitle("Username & password required");
            stage.setScene(new Scene(message));
            stage.show();
        }
        else if (!mid.getText().equals("") && !password.getText().equals(""))
        {	
        	String[] loginRequest = new String[3];
        	loginRequest[0] = "login";
        	loginRequest[1] = mid.getText();
        	loginRequest[2] = password.getText();
        	
        	client.serverRequest(loginRequest);
        	
        	TimeUnit.SECONDS.sleep(5);
        	
        	try
        	{
        		if (client.getVerification() == true)
        		{
        			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/Homepage.fxml"));
        			Parent root = fxmlLoader.load();
        	        HomepageController homepageController = fxmlLoader.getController();
        	        homepageController.setClient(client);
        	        client.setVerification(homepageController.getVerification());
        	        Stage stage = (Stage) loginButton.getScene().getWindow();
        	        stage.close();
        	        stage = new Stage();
        	        stage.setTitle("Homepage");
        	        stage.setScene(new Scene(root, 800, 550));
        	        stage.setResizable(true);
        	        stage.show();	
        		}
        		else if (client.getVerification() == false)
        		{
                	Label message = new Label("Details not recognised by the system. Try again!");
                	Stage stage = new Stage();
                    stage.setTitle("Invalid details");
                    stage.setScene(new Scene(message));
                    stage.show();
        		}
        	}
        	catch (UnknownHostException e)
        	{
        		e.printStackTrace();
        	}
        }
    }
	
	@FXML
	public void clearText(ActionEvent event)
	{
		// TBC
	}
    
    public void setClient(Client client)
    {
        this.client = client;
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// Required
	}
}
