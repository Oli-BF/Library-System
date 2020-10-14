package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author osb807
 * 
 * TBC
 */
public class Server
{
    public static final int MAX_TREADS = 10; // No. TBC
    public static final int PORT = 8888;
    
    /**
     * Constructor for the Server...
     */
    public Server(int port)
    {
    	
        /**
         * This initialises a ServerSocket object, that is used to accept 
         * new client connections.
         */
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Creating server listening port at: " + port);

            /**
             * Initialises a fixed thread pool that allows up to 'MAX_THREADS'
             * concurrently running.
             */
            ExecutorService threadPool = Executors.newFixedThreadPool(MAX_TREADS);

            /**
             * An infinite loop to accept clients connecting forever (on the
             * main thread). 
             */
            while (true)
            {
                System.out.println("Waiting for a client to connect.");
                
                /**
                 * Calls '.accept' to wait for a new client to connect.
                 * A new socket object is returned by .accept when the new 
                 * client successfully connects.
                 */
                Socket newClientSocket = serverSocket.accept();

                /**
                 * Passes the socket created for the client to a separate
                 * clientHandlerThread object and execute it on the thread 
                 * pool.
                 */
                threadPool.execute(new ClientHandlerThread(newClientSocket));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private class ClientHandlerThread implements Runnable
    {
        private Socket clientSocket;
        private String mid;

        /**
         * This constructor takes the socket created by the 
         * ServerSocket.accept() method.
         * 
         * @param clientSocket The socket created by ServerSocket.accept() 
         *                     which communicates to a specific client.
         */
        public ClientHandlerThread(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
        }

        /**
         * Everything that happens within this run method will execute on a
         * new thread.
         */
        @Override
        public void run() 
        {
            System.out.println("Server has connected to a Client.");
            
            try (ObjectOutputStream clientOut = new ObjectOutputStream(
                 clientSocket.getOutputStream());
                 ObjectInputStream clientIn = new ObjectInputStream(
                 clientSocket.getInputStream());)
            {
                try
                {
                    DatabaseAccess databaseAccess = new DatabaseAccess();
    				while (true)
    				{
    					String[] clientRequest = (String[]) clientIn.readObject();
    					if (clientRequest != null)
    					{
    						String request = clientRequest[0];
    						String mid;
    						String lid;
    						String password;
    						String firstname;
    						String lastname;
    						String email;
    						String telephone;
    						
    						int bid;
    						String title;
    						String author;
    						String genre;
    						String publisher;
    						String blurb;
    						int bl;
    						int year;
    						String tps;
    						
    						if (request.equals("Register"))
    						{
    							mid = clientRequest[1];
    							password = clientRequest[2];
    							firstname = clientRequest[3];
    							lastname = clientRequest[4];
    							email = clientRequest[5];
    							telephone = clientRequest[6];
    							
    							if (databaseAccess.checkRegister(mid, email, telephone) == true)
    							{
    								databaseAccess.register(mid, password, firstname, lastname, email, telephone);
    								
    								String[] valid = new String[1];
    								valid[0] = "Register Valid";
    								clientOut.writeObject(valid);
    							}
    							else if (databaseAccess.checkRegister(mid, email, telephone) == false)
    							{
    								String[] invalid = new String[1];
    								invalid[0] = "Register Invalid";
    								clientOut.writeObject(invalid);
    							}
    						}
    						else if (request.equals("Login"))
    						{
    							mid = clientRequest[1];
    							this.mid = mid;
    							password = clientRequest[2];
    							
    							if (databaseAccess.login(mid, password) == true)
    							{
    								String[] valid = new String[1];
    								valid[0] = "Login Valid";
    								clientOut.writeObject(valid);
    							}
    							else if (databaseAccess.login(mid, password) == false)
    							{
    								String[] invalid = new String[1];
    								invalid[0] = "Login Invalid";
    								clientOut.writeObject(invalid);
    							}
    						}
    						else if (request.equals("Librarian Login"))
    						{
    							lid = clientRequest[1];
    							password = clientRequest[2];
    							
    							if (databaseAccess.librarianLogin(lid, password) == true)
    							{
    								String[] valid = new String[1];
    								valid[0] = "Login Valid";
    								clientOut.writeObject(valid);
    							}
    							else if (databaseAccess.librarianLogin(lid, password) == false)
    							{
    								String[] invalid = new String[1];
    								invalid[0] = "Login Invalid";
    								clientOut.writeObject(invalid);
    							}
    						}
    						else if (request.equals("Logout"))
    						{
    							databaseAccess.logout(this.mid); 
    							
//    							TO KEEP?
//    							System.out.println("The User: " + this.mid = " has logged out."); 
//    							clientSocket.close();
    						}
    						else if (request.equals("Add Book"))
    						{
    							bid = Integer.parseInt(clientRequest[1]);
    							title = clientRequest[2];
    							author = clientRequest[3];
    							genre = clientRequest[4];
    							publisher = clientRequest[5];
    							blurb = clientRequest[6];
    							bl = Integer.parseInt(clientRequest[7]);
    							year = Integer.parseInt(clientRequest[8]);
    							tps = clientRequest[9];
    							
    							if (databaseAccess.checkBook(bid, title, author, genre, publisher, blurb, bl, 
    								                          year, tps) == true)
    							{
    								databaseAccess.addBook(bid, title, author, genre, publisher, blurb, bl, 
    	    								               year, tps);
    								
    								String[] valid = new String[1];
    								valid[0] = "Add Book Valid";
    								clientOut.writeObject(valid);
    							}
    							else if (databaseAccess.checkBook(bid, title, author, genre, publisher, blurb, bl, 
				                                                  year, tps) == false)
    							{
    								String[] invalid = new String[1];
    								invalid[0] = "Add Book Invalid";
    								clientOut.writeObject(invalid);
    							}
    						}
    						else if (request.equals("Find Book"))
    						{
								String[] valid = new String[1];
								valid[0] = "Find Book Valid";
								clientOut.writeObject(valid);
								
    							databaseAccess.getTitles();
    							clientOut.writeObject(databaseAccess.titles);
    						}
    					}
    				}
                }
                catch (IOException e)
                {
                    System.out.println("Error communicating with the client.");
                    e.printStackTrace();
                } 
                catch (ClassNotFoundException e) 
                {
					e.printStackTrace();
				}
            }
            catch (IOException e)
            {
                System.out.println("Connection with the client has been lost.");
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args)
    {
        new Server(PORT);
    }
}