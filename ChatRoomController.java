/**
 * 
 */
package client.controllers;

import java.io.IOException;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * @author gaofei
 *
 */
public class ChatRoomController {
	@FXML
    private ListView<?> ChatWindow;

    @FXML
    private Label Header;

    @FXML
    private Button backButton;

    @FXML
    private Button sendButton;

    
    private Client client;
    
    public void setClient(Client client){
        this.client = client;
    }
    
    @FXML
    void showForum(ActionEvent event) {
    	try {
			Stage primaryStage=(Stage)backButton.getScene().getWindow();
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
		} catch (IOException e) {
	           e.printStackTrace();
	       }
    }


    @FXML
    void SendMsg(ActionEvent event) {

    }
}
