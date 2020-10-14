package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
				String[] validOrInvalid = (String[]) serverIn.readObject();
				if (validOrInvalid != null && validOrInvalid.length > 0)
				{
//					String response = valid[0];
					if (validOrInvalid[0].equals("Valid Login") && validOrInvalid[1].equals("1"))
					{
						setVerification(true);
					}
					else if (validOrInvalid[0].equals("Failed Login"))
					{
						setVerification(false);
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
	
	public void serverRequest(String[] request) throws IOException
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
    
//    public void closeClientConnection() throws IOException 
//    {
//        socket.close();
//        serverOut.close();
//        serverIn.close();
//        System.out.println("The user has logged off");
//    }
}