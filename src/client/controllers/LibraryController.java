package client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import client.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author gaofei & osb807
 *
 */
public class LibraryController 
{
    @FXML
    private Button searchButton;
    
    @FXML
    private TextField searchTitle;
    
    @FXML
    private TableView<String> bookTable;
    
    @FXML
//    private TableColumn<S, String> col;
    
    @FXML
    private Button backButton;

    @FXML
    private Button returnBooksButton;
    
    private Client client;
    
    public void setClient(Client client)
    {
        this.client = client;
    }

    @FXML
    void showHomepage(ActionEvent event) throws IOException 
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Homepage.fxml"));
		Parent root = fxmlLoader.load();
        HomepageController homepageController = fxmlLoader.getController();
        homepageController.setClient(client);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        stage = new Stage();
        stage.setTitle("Homepage");
        stage.setScene(new Scene(root, 776, 554));
        stage.setResizable(true);
        stage.show();	
    }

    @FXML
    void showReturnBook(ActionEvent event) throws IOException 
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/ReturnBooks.fxml"));
		Parent root = fxmlLoader.load();
        ReturnBooksController returnBooksController = fxmlLoader.getController();
        returnBooksController.setClient(client);
        Stage stage = (Stage) returnBooksButton.getScene().getWindow();
        stage.close();
        stage = new Stage();
        stage.setTitle("Return Books");
        stage.setScene(new Scene(root, 776, 554));
        stage.setResizable(true);
        stage.show();	
    }

    @FXML
    void SearchBook(ActionEvent event) throws IOException, InterruptedException 
    {
		if (searchTitle.getText().isEmpty())
        {
			String info = "You have not entered anything.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Username & password required");
			alert.show();
        }
		else if (!searchTitle.getText().isEmpty())
		{
        	String[] searchBookRequest = new String[1];
        	searchBookRequest[0] = "Find Book";
//        	searchBookRequest[1] = searchTitle.getText();
        	
        	client.clientRequest(searchBookRequest);
        	
        	TimeUnit.SECONDS.sleep(5);
        	
//        	ArrayList<String> books = (ArrayList<String>) client.titles;
        	
//        	bookTable.setEditable(true);
//          ObservableList<String> books = FXCollections.observableArrayList(client.titles);
//          bookTable.setItems(books);
            
        	TableColumn col1 = new TableColumn("Title");
//        	bookTable.getColumns().addAll(col1);
//        	ObservableList<String> books;
        	for (int i = 0; i < client.titles.size(); i++)
        	{	
//        		TableRow row = new TableRow(this);
//        		String title = client.titles.get(i);
//        		textView titleView = new TextView(this);
        		
//        		books = FXCollections.observableArrayList(client.titles.get(i));
//        		
//        		col1.setCellValueFactory(cellData -> cellData.setValue().client.titles.get(i));
//        		col1.setCellValueFactory(new PropertyValueFactory(client.titles.get(i)));
        		
        		final int j = i;
        		TableColumn col = new TableColumn(client.titles.get(i));
        		col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
        		{                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) 
                    {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
        		bookTable.getColumns().addAll(col); 
//              System.out.println("Column ["+i+"] ");
        	}
//        	return books;
        	
		}
    }
}