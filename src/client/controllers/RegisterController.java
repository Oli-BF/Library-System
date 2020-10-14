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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author gaofei & osb807
 *
 * TBC
 */
public class RegisterController 
{
	@FXML
    private PasswordField password;

    @FXML
    private TextField firstname;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField confirmPass;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField mid;

    @FXML
    private TextField telephone;

    @FXML
    private TextField email;

    @FXML
    private TextField lastname;
    
	private Client client;

	public void setClient(Client client) 
	{
		this.client = client;
	}

	@FXML
	void register(ActionEvent event) throws IOException, InterruptedException 
	{
		if (mid.getText().isEmpty() || password.getText().isEmpty() || confirmPass.getText().isEmpty() ||
			firstname.getText().isEmpty() || lastname.getText().isEmpty() || email.getText().isEmpty() ||
			telephone.getText().isEmpty())
		{
			String info = "You have not filled out all the fields.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Text fields required");
			alert.show();
		}
		else if (!mid.getText().matches("^[a-zA-Z]{6,15}$"))
		{
			String info = "Your username must contain a minimum of six characters and a maximum of fifteen and only"
					       + " use lowercase or uppercase characters.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Password complexity error.");
			alert.show();
		}
		else if (!password.getText().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"))
		{
			String info = "Your password must contain a minimum of six characters, one letter and one number.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Password complexity error.");
			alert.show();
		}
		else if (!confirmPass.getText().equals(password.getText()))
		{
			String info = "Your passwords do not match.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Password match error.");
			alert.show();
		}
		else if (!firstname.getText().matches("[a-zA-Z]+"))
		{
			String info = "Your firstname is not valid, please enter only lowercase or uppercase characters.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Firstname input error.");
			alert.show();
		}
		else if (!lastname.getText().matches("[a-zA-Z]+"))
		{
			String info = "Your lastname is not valid, please enter only lowercase or uppercase characters.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Lastname input error.");
			alert.show();
		}
		else if (!email.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
		{
			String info = "Your email address is not valid.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Email input error.");
			alert.show();
		}
		else if (!telephone.getText().matches("[0-9]{11}"))
		{
			String info = "Your telephone must only contain numbers and have 11 digits.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Telephone input error.");
			alert.show();
		}
		else if (!mid.getText().isEmpty() | !password.getText().isEmpty() | confirmPass.getText().equals(password.getText()) |
				 !firstname.getText().isEmpty() | !lastname.getText().isEmpty() | !email.getText().isEmpty() |
				 !telephone.getText().isEmpty())
		{
			String[] registerRequest = new String[7];
			
			registerRequest[0] = "Register";
			registerRequest[1] = mid.getText();
			registerRequest[2] = password.getText();
			registerRequest[3] = firstname.getText();
			registerRequest[4] = lastname.getText();
			registerRequest[5] = email.getText();
			registerRequest[6] = telephone.getText();
			
			client.clientRequest(registerRequest);

			TimeUnit.SECONDS.sleep(5);
			
			try 
			{
				if (client.getVerification() == true) 
				{
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/views/Login.fxml"));
					Parent root = fxmlLoader.load();
					LoginController loginController = fxmlLoader.getController();
					loginController.setClient(client);
					Stage stage = (Stage) registerButton.getScene().getWindow();
					stage.close();
					stage = new Stage();
					stage.setTitle("Login Page");
					stage.setScene(new Scene(root, 776, 554));
					stage.setResizable(false);
					stage.show();
				} 
				else if (client.getVerification() == false)
				{
					String info = "One or more of your details already exists in the system, please try again.";
					Alert alert = new Alert(AlertType.ERROR, info);
					alert.setTitle("Non-unique registration error");
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
	void cancelRegister(ActionEvent event) 
	{
		try 
		{
			Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
			primaryStage.close();
			Stage stage = new Stage();
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

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() 
	{

	}

	/**
	 * @return the password
	 */
	public PasswordField getPassword() 
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(PasswordField password) 
	{
		this.password = password;
	}

	/**
	 * @return the registerButton
	 */
	public Button getRegisterButton() 
	{
		return registerButton;
	}

	/**
	 * @return the confirmPass
	 */
	public TextField getConfirmPass() 
	{
		return confirmPass;
	}

	/**
	 * @param confirmPass the confirmPass to set
	 */
	public void setConfirmPass(PasswordField confirmPass) 
	{
		this.confirmPass = confirmPass;
	}

	/**
	 * @return the cancleButton
	 */
	public Button getCancelButton() 
	{
		return cancelButton;
	}

	/**
	 * @param cancleButton the cancleButton to set
	 */
	public void setCancelButton(Button cancelButton) 
	{
		
	}

	/**
	 * @return the mid
	 */
	public TextField getMid() 
	{
		return mid;
	}

	/**
	 * @param mid the mid to set
	 */
	public void setMid(TextField mid) 
	{
		this.mid = mid;
	}
}