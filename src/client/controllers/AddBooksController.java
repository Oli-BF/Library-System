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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author gaofei
 *
 */
public class AddBooksController 
{
	@FXML
	private Button submitButton;

	@FXML
	private TextField year;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField author;

	@FXML
	private TextField TPS;

	@FXML
	private Button backButton;

	@FXML
	private TextField genre;

	@FXML
	private TextField publisher;

	@FXML
	private TextField BL;

	@FXML
	private TextField BID;

	@FXML
	private TextField title;

	@FXML
	private TextArea blurb;

	private Client client;

	public void setClient(Client client) 
	{
		this.client = client;
	}

	@FXML
	void showLibrarian(ActionEvent event) throws IOException 
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Management.fxml"));
		Parent root = fxmlLoader.load();
		ManagementController managementController = fxmlLoader.getController();
		managementController.setClient(client);
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
		stage = new Stage();
		stage.setTitle("Homepage");
		stage.setScene(new Scene(root, 776, 554));
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	void SubmitAddition(ActionEvent event) throws IOException, InterruptedException 
	{
		if (BID.getText().isEmpty() || title.getText().isEmpty() || author.getText().isEmpty() ||
			genre.getText().isEmpty() || publisher.getText().isEmpty() || blurb.getText().isEmpty() ||
			BL.getText().isEmpty() || year.getText().isEmpty() || TPS.getText().isEmpty())
		{
			String info = "You have not filled out all the fields.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Text fields required");
			alert.show();
		}
		else if (!BID.getText().matches("[0-9]+"))
		{
			String info = "The BookID must only contain numbers.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("bid input error.");
			alert.show();
		}
		else if (!BL.getText().matches("[0-9]+"))
		{
			String info = "The Borrow Length (days) must only contain numbers.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Borrow Length input error.");
			alert.show();
		}
		else if (!year.getText().matches("^\\d{4}$"))
		{
			String info = "The year must only contain a year date, i.e. '2020'.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Year input error.");
			alert.show();
		}
		else if (!BID.getText().isEmpty() || !title.getText().isEmpty() || !author.getText().isEmpty() ||
				 !genre.getText().isEmpty() || !publisher.getText().isEmpty() || !blurb.getText().isEmpty() ||
				 !BL.getText().isEmpty() || !year.getText().isEmpty() || !TPS.getText().isEmpty())
		{
			String[] addBookRequest = new String[10];
			
			addBookRequest[0] = "Add Book";
			addBookRequest[1] = BID.getText();
			addBookRequest[2] = title.getText();
			addBookRequest[3] = author.getText();
			addBookRequest[4] = genre.getText();
			addBookRequest[5] = publisher.getText();
			addBookRequest[6] = blurb.getText();
			addBookRequest[7] = BL.getText();
			addBookRequest[8] = year.getText();
			addBookRequest[9] = TPS.getText();
			
			client.clientRequest(addBookRequest);

			TimeUnit.SECONDS.sleep(5);
			
			try 
			{
				if (client.getVerification() == true) 
				{
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Management.fxml"));
					Parent root = fxmlLoader.load();
					ManagementController managementController = fxmlLoader.getController();
					managementController.setClient(client);
					Stage stage = (Stage) submitButton.getScene().getWindow();
					stage.close();
					stage = new Stage();
					stage.setTitle("Management Page");
					stage.setScene(new Scene(root, 776, 554));
					stage.setResizable(false);
					stage.show();
				} 
				else if (client.getVerification() == false)
				{
					String info = "This book already exists in the system, please try again.";
					Alert alert = new Alert(AlertType.ERROR, info);
					alert.setTitle("Book already exists error");
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
	void CancelAddition(ActionEvent event) 
    {
    	try 
    	{
			Stage primaryStage = (Stage)cancelButton.getScene().getWindow();
			primaryStage.close();
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Management.fxml"));
			Parent root = fxmlLoader.load();
			ManagementController managementController = fxmlLoader.getController();
			managementController.setClient(client);
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
}