package client.controllers;

import javafx.scene.control.TableColumn;
import java.io.IOException;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * @author gaofei & osb807
 *
 */
public class ReturnBooksController {
	@FXML
	private TableColumn<?, ?> AuthorColumn;

	@FXML
	private TableColumn<?, ?> BDColumn;

	@FXML
	private TableColumn<?, ?> ReturnColumn;

	@FXML
	private TableColumn<?, ?> BIDColumn;

	@FXML
	private TableColumn<?, ?> GenreColumn;

	@FXML
	private Button backButton;

	@FXML
	private TableView<?> bookTable;

	@FXML
	private TableColumn<?, ?> TitleColumn;

	@FXML
	private TableColumn<?, ?> BLColumn;

	private Client client;

	public void setClient(Client client) 
	{
		this.client = client;
	}

	@FXML
	void showLibrary(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) backButton.getScene().getWindow();
			primaryStage.close();
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Library.fxml"));
			Parent root = fxmlLoader.load();
			LibraryController libraryController = fxmlLoader.getController();
			libraryController.setClient(client);
			stage.setTitle("Library");
			stage.setScene(new Scene(root, 776, 554));
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
