package client.controllers;

import java.io.IOException;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author gaofei
 *
 */
public class ManagementController 
{	
    @FXML
    private Button addBookButton;

    @FXML
    private Button issuesButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button membersButton;

    @FXML
    private Button booksButton;
    
	public boolean verification;
    
    private Client client;
    
    public void setClient(Client client)
    {
        this.client = client;
    }

    @FXML
    void showAddBook(ActionEvent event) 
    {
    	try 
    	{
			Stage primaryStage = (Stage)addBookButton.getScene().getWindow();
			primaryStage.close();
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/AddBooks.fxml"));
			Parent root = fxmlLoader.load();
			AddBooksController addBooksController = fxmlLoader.getController();
			addBooksController.setClient(client);
			stage.setTitle("Add Books Page");
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
    void showMembers(ActionEvent event)
    {

    }
    
    @FXML
    void showIssues(ActionEvent event) 
    {

    }

    @FXML
    void showBooks(ActionEvent event) 
    {

    }
    
    @FXML
    void LibrarianLogout(ActionEvent event) 
    {
    	try 
    	{
			Stage primaryStage = (Stage)logoutButton.getScene().getWindow();
			primaryStage.close();
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/LibrarianLogin.fxml"));
			Parent root = fxmlLoader.load();
			LibrarianLoginController loginController = fxmlLoader.getController();
			loginController.setClient(client);
			stage.setTitle("LibLogin Page");
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