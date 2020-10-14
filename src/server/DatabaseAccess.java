package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author osb807 & TBC
 *
 * JDBC Code
 */
public class DatabaseAccess 
{
	private String url;
	private String username;
	private String password; 
	public ArrayList<String> titles = new ArrayList<>();
	
	/**
	 * Constructor that takes the the file db.properties and loads in the
	 * url, username and password to connect to the database and for use 
	 * in the prepared statements
	 */
	public DatabaseAccess() 
	{
		try (FileInputStream input = new FileInputStream(new File("src/jdbc/db.properties")))
		{
			Properties connProps = new Properties();
			
			connProps.load(input);
			
			this.url = (String) connProps.getProperty("URL");
			this.username = (String) connProps.getProperty("username");
			this.password = (String) connProps.getProperty("password");
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized boolean checkRegister(String mid, String email, String telephone)
	{
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
			 PreparedStatement preparedStatement = connection.prepareStatement(
			 "SELECT mid, email, telephone FROM public.membert WHERE mid = ? AND email = ? AND telephone = ?;"))
		{
			System.out.println("Database Access (checkRegister) has been established.");
			
			preparedStatement.setString(1, mid);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, telephone);
			
			try (ResultSet resultSet = preparedStatement.executeQuery())
			{
				while (resultSet.next())
				{
					String member = resultSet.getString("mid");
					String memberEmail = resultSet.getString("email");
					String memberTelephone = resultSet.getString("telephone");
					
					if (mid.equals(member) || email.equals(memberEmail) || telephone.equals(memberTelephone))
					{
						return false;
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public synchronized void register(String mid, String password, String firstname, String lastname, 
			                          String email, String telephone)
	{
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
				  PreparedStatement preparedStatement = connection.prepareStatement(
				  "INSERT INTO public.membert (mid, password, firstname, lastname, email, telephone)"
				  + " VALUES (?, ?, ?, ?, ?, ?);"))
		{
			 System.out.println("Database Access (Register) has been established.");
			 
			 preparedStatement.setString(1, mid);
			 preparedStatement.setString(2, password);
			 preparedStatement.setString(3, firstname);
			 preparedStatement.setString(4, lastname);
			 preparedStatement.setString(5, email);
			 preparedStatement.setString(6, telephone);
			 preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized boolean login(String mid, String password)
	{
		 try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
			  PreparedStatement preparedStatement = connection.prepareStatement(
			  "SELECT mid, password FROM public.membert WHERE mid = ? AND password = ?;")) 
		 {
			 System.out.println("Database Access (Login) has been established.");
			 
			 preparedStatement.setString(1, mid);
			 preparedStatement.setString(2, password);
			 
			 try (ResultSet resultSet = preparedStatement.executeQuery())
			 {
				 while (resultSet.next())
				 {
					 String member = resultSet.getString("mid");
					 String memberPassword = resultSet.getString("password");
					 
					 if (mid.equals(member) && password.equals(memberPassword))
					 {
						 try
						 {
							 // Only occurs when logged in status is false, prevents asynchronous results.
							 PreparedStatement updateStatement = connection.prepareStatement(
							 "UPDATE public.membert SET logged_in = 't' WHERE mid = ? AND logged_in = 'f';");
							 {
								 updateStatement.setString(1,  mid);
								 updateStatement.executeUpdate();
							 }
						 }
						 catch (SQLException e)
						 {
							 e.printStackTrace();
						 }
						 return true;
					 }
				 }
			 }
		 }
	     catch (SQLException e) 
		 {
	    	 e.printStackTrace();
		 }
		 return false;
    }

	public synchronized boolean librarianLogin(String lid, String password)
	{
		 try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
			  PreparedStatement preparedStatement = connection.prepareStatement(
			  "SELECT lid, password FROM public.librariant " + "WHERE lid = ? AND password = ?;")) 
		 {
			 System.out.println("Database Access (LibrarianLogin) has been established.");
			 
			 preparedStatement.setString(1, lid);
			 preparedStatement.setString(2, password);
			 
			 try (ResultSet resultSet = preparedStatement.executeQuery())
			 {
				 while (resultSet.next())
				 {
					 String librarian = resultSet.getString("lid");
					 String librarianPassword = resultSet.getString("password"); 
					 
					 if (lid.equals(librarian) && password.equals(librarianPassword))
					 {
						 return true;
					 }
				 }
			 }
		 }
	     catch (SQLException e) 
		 {
	    	 e.printStackTrace();
		 }
		 return false;
    }
	
	public synchronized void logout(String mid)
	{
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
			 PreparedStatement preparedStatement = connection.prepareStatement(
		     "UPDATE public.membert SET logged_in = 'f' WHERE mid = ? AND logged_in = 't';"))
		{
			System.out.println("Database Access (Logout) has been established.");
			
			preparedStatement.setString(1, mid);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized boolean checkBook(int bid, String title, String author, String genre, String publisher, 
            							  String blurb, int bl, int year, String tps)
	{
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
			 PreparedStatement preparedStatement = connection.prepareStatement(
			 "SELECT bid, title, author, genre, publisher, blurb, bl, year, tps FROM public.book WHERE "
			 + "bid = ? AND title = ? AND author = ? AND genre = ? AND publisher = ? AND blurb = ? "
			 + "AND bl = ? AND year = ? AND tps = ?;"))
		{
			System.out.println("Database Access (checkBook) has been established.");
			
			preparedStatement.setInt(1, bid);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, author);
			preparedStatement.setString(4, genre);
			preparedStatement.setString(5, publisher);
			preparedStatement.setString(6, blurb);
			preparedStatement.setInt(7, bl);
			preparedStatement.setInt(8, year);
			preparedStatement.setString(9, tps);
			
			try (ResultSet resultSet = preparedStatement.executeQuery())
			{
				while (resultSet.next())
				{
					int bookID = resultSet.getInt("bid");
					String bookTitle = resultSet.getString("title");
					String bookAuthor = resultSet.getString("author");
					String bookGenre = resultSet.getString("genre");
					String bookPublisher = resultSet.getString("publisher");
					String bookBlurb = resultSet.getString("blurb");
					int bookBl = resultSet.getInt("bl");
					int bookYear = resultSet.getInt("year");
					String bookTps = resultSet.getString("tps");
					
					if ((bid == bookID) || title.equals(bookTitle) || author.equals(bookAuthor) ||
						genre.equals(bookGenre) || publisher.equals(bookPublisher) || blurb.equals(bookBlurb) ||
						(bl == bookBl) || (year == bookYear) || tps.equals(bookTps))
					{
						return false;
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public synchronized void addBook(int bid, String title, String author, String genre, String publisher, 
			                         String blurb, int bl, int year, String tps)
	{
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
				  PreparedStatement preparedStatement = connection.prepareStatement(
				  "INSERT INTO public.book (bid, title, author, genre, publisher, blurb, bl, year, tps)"
				  + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"))
		{
			 System.out.println("Database Access (addBook) has been established.");
			 
			preparedStatement.setInt(1, bid);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, author);
			preparedStatement.setString(4, genre);
			preparedStatement.setString(5, publisher);
			preparedStatement.setString(6, blurb);
			preparedStatement.setInt(7, bl);
			preparedStatement.setInt(8, year);
			preparedStatement.setString(9, tps);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized ArrayList<String> getTitles()
	{
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
				  PreparedStatement preparedStatement = connection.prepareStatement(
				  "SELECT DISTINCT title FROM public.book"))
		{
			System.out.println("Database Access (getTitles) has been established.");
		
			try (ResultSet resultSet = preparedStatement.executeQuery())
			{
				while(resultSet.next())
				{
					titles.add(resultSet.getString("title"));
				}
				return titles;
			}
		}
	    catch(java.sql.SQLException e)
	    {
	    	e.printStackTrace();
	    }
		return titles;
	}
}