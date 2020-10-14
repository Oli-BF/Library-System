package client.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * @author gaofei
 *
 */
public class HomepageController
{
	
	    private Client client;
	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    public boolean verification;

	    
	    public void setClient(Client client)
	    {
	        this.client = client;
	    }
//	    
	    @FXML
		private void Logout(ActionEvent event) 
	    {
//	    	 try {
//		            LoginController logout = (LoginController) replaceSceneContent("Login.fxml");
//		            logout.setClient(this);
//		        } catch (Exception ex) {
//		            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//		        }
	    }
	    
	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() 
	    {

	    }
	    
	    public boolean getVerification()
	    {
	        return this.verification;
	    }
	    
//	    private Initializable replaceSceneContent(String fxml) throws Exception {
//	        FXMLLoader loader = new FXMLLoader();
//	        InputStream in = Client.class.getResourceAsStream(fxml);
//	        loader.setBuilderFactory(new JavaFXBuilderFactory());
//	        loader.setLocation(Client.class.getResource(fxml));
//	        AnchorPane page;
//	        try {
//	            page = (AnchorPane) loader.load(in);
//	        } finally {
//	            in.close();
//	        } 
//	        Scene scene = new Scene(page, 776, 554);
//	        stage.setScene(scene);
//	        stage.sizeToScene();
//	        return (Initializable) loader.getController();
//	    }
}








// Old Class

///**
// * 
// */
//package client.controllers;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//import client.Client;
//import javafx.fxml.Initializable;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//
//private Client client;
//
//public class HomepageController implements Initializable
//{
//	    @FXML // ResourceBundle that was given to the FXMLLoader
//	    private ResourceBundle resources;
//
//	    @FXML // URL location of the FXML file that was given to the FXMLLoader
//	    private URL location;
//		
//		public void setClient(Client client)
//	    {
//	        this.client = client;
//	    }
//		
//		@Override
//		public void initialize(URL location, ResourceBundle resources) 
//		{
//			// Required
//		}
//		
////		@Override
////		public void initialize(URL arg0, ResourceBundle arg1) 
////		{
////			// Required.
////		}
//}