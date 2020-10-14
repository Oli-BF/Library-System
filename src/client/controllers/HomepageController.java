package client.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author gaofei & osb807
 * 
 * TBC
 */
public class HomepageController
{
	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="LibraryPage"
	    private Button LibraryPage; // Value injected by FXMLLoader

	    @FXML // fx:id="logoutButton"
	    private Button logoutButton; // Value injected by FXMLLoader

	    @FXML // fx:id="ForumPage"
	    private Button ForumPage; // Value injected by FXMLLoader
	    
	    public boolean verification;
	    
	    private Client client;
	    
	    public void setClient(Client client)
	    {
	        this.client = client;
	    }
	    
	    @FXML
	    private void showLibrary(ActionEvent event) 
	    {
	    	try 
	    	{
				Stage primaryStage=(Stage)LibraryPage.getScene().getWindow();
				primaryStage.close();
				Stage stage=new Stage();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Library.fxml"));
				Parent root = fxmlLoader.load();
				LibraryController libraryController = fxmlLoader.getController();
				libraryController.setClient(client);
				stage.setTitle("Library");
				stage.setScene(new Scene(root, 776, 554));
				stage.setResizable(true);
				stage.show();
			} 
	    	catch (IOException e) 
	    	{
	    		e.printStackTrace();
		    }
	    }

	    @FXML
	    private void showForum(ActionEvent event) 
	    {
	    	try 
	    	{
				Stage primaryStage=(Stage)ForumPage.getScene().getWindow();
				primaryStage.close();
				Stage stage=new Stage();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Forum.fxml"));
				Parent root = fxmlLoader.load();
				ForumController forumController = fxmlLoader.getController();
				forumController.setClient(client);
				stage.setTitle("Forum");
				stage.setScene(new Scene(root, 776, 554));
				stage.setResizable(true);
				stage.show();
			} 
	    	catch (IOException e) 
	    	{
	    		e.printStackTrace();
	    	}
	    }

	    @FXML
		private void Logout(ActionEvent event) throws IOException
	    {
        	String[] logoutRequest = new String[1];
        	logoutRequest[0] = "Logout";
        	
        	client.clientRequest(logoutRequest);
        	
//        	To KEEP?
//        	client.logout();
	    	try 
	    	{
				Stage primaryStage=(Stage)logoutButton.getScene().getWindow();
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