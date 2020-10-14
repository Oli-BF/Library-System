package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import server.DatabaseAccess;

/**
 * @author osb807
 *
 * TBC
 */
public class Client implements Runnable
{
	/**
	 * Initialising the field variables.
	 */
	private Socket socket;
	private ObjectInputStream serverIn;
	private ObjectOutputStream serverOut;
	private Thread thread;
//	public ArrayList<String> titles = new ArrayList<String>();
//	public ArrayList<String> titles;
	public List<String> titles;
	
	private boolean validation;
	
	/**
	 * Constructor for the client.
	 * 
	 * @param Host host IP address of the host.
	 * @param port Port number of that serve.
	 * @throws IOException
	 */
	public Client(String host, int port) throws IOException
	{	
		try
		{
			socket = new Socket(host, port);
			
			serverIn  = new ObjectInputStream(socket.getInputStream());
			serverOut = new ObjectOutputStream(socket.getOutputStream());
			
			System.out.println("Connected: " + socket);
		
			this.thread = new Thread(this);
			thread.start();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Connection with the server lost.");
			
			String info = "The server is currently unavailable, please try again later.";
			Alert alert = new Alert(AlertType.ERROR, info);
			alert.setTitle("Server Unavailable");
			alert.show();
		}
	}
	
	@Override
	public void run()
	{
		System.out.println("Client has connected to the server.");
		try
		{
			while(true)
			{
				String[] serverObject = (String[]) serverIn.readObject();
//				ArrayList<String> serverObjectArrayList = (ArrayList<String>) serverIn.readObject();
				if (serverObject != null)
				{
					if (serverObject[0].equals("Register Valid"))
					{
						setVerification(true);
					}
					else if (serverObject[0].equals("Register Invalid"))
					{
						setVerification(false);
					}
					else if (serverObject[0].equals("Login Valid"))
					{
						setVerification(true);
					}
					else if (serverObject[0].equals("Login Invalid"))
					{
						setVerification(false);
					}
					else if (serverObject[0].equals("Add Book Valid"))
					{
						setVerification(true);
					}
					else if (serverObject[0].equals("Add Book Invalid"))
					{
						setVerification(false);
					}
					else if (serverObject[0].equals("Find Book Valid"))
					{
//						titles = (ArrayList<String>) serverIn.readObject();
						titles = (ArrayList<String>) serverIn.readObject();
//						System.out.println(titles);
//						ArrayList<String> titlesAll = (ArrayList<String>) serverIn.readObject();
//						titles.addAll(titlesAll);
					}
					else
					{
                        TimeUnit.SECONDS.sleep(8);
					}
				}	
			}
		}
		catch (IOException e) 
		{
            e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void clientRequest(String[] request) throws IOException
	{
		serverOut.writeObject(request); 
	}
	
    public void setVerification(boolean validation)
    {
        this.validation = validation;
    }
    
    public boolean getVerification()
    {

        return validation;
    }
    
    public void logout() throws IOException 
    {
    	// Called in Homepage Controller
        socket.close();
        serverIn.close();
        serverOut.close();
        System.out.println("Current user logged out.");
    }
}