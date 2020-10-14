/**
 * 
 */
package client.controllers;

import java.io.IOException;

import client.Client;
import javafx.event.ActionEvent;

/**
 * @author gaofei
 *
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ForumController {

    @FXML
    private Button enterChatRoomButton;

    @FXML
    private Button FriendListButton;

    @FXML
    private Button backButton;

    private Client client;
    
    public void setClient(Client client){
        this.client = client;
    }
    
    @FXML
    void EnterChatRoom(ActionEvent event) {

    }


    @FXML
    void showFriendList(ActionEvent event) {

    }



    @FXML
    void showHomepage(ActionEvent event) {
    	try {
			Stage primaryStage=(Stage)backButton.getScene().getWindow();
			primaryStage.close();
			Stage stage=new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Homepage.fxml"));
			Parent root = fxmlLoader.load();
			HomepageController homepageController = fxmlLoader.getController();
			homepageController.setClient(client);
			stage.setTitle("Library");
			stage.setScene(new Scene(root, 776, 554));
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
	           e.printStackTrace();
	       }
    }


}

