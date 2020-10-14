import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.lang.*;

public class DatabaseMethodsLib {
    private Connection connection;
    private ResultSet resultSetPrevious;
    private ResultSet resultSetLast;
    private Statement statement;


    public ResultSet getResultSetLast(){
        return this.resultSetLast;
    }

    public synchronized void run(){
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager
                    .getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/",
                            "adige", "6f4rryoppl");
            this.statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
    }


     /**
     * This method finds all the genres in the database.
     * @return All the genres in the database. If the arraylist is empty, then the number of genres is zero.
     */
    public synchronized ArrayList<String> findAllGenre(){
        this.run();
        ArrayList<String> genres= new ArrayList<>(  );
        try {

            ResultSet resultSet=this.statement.executeQuery( "select distinct genre from book;" );
            int count=0;
            while(resultSet.next()){
                genres.add(resultSet.getString( "genre" ));
                ++count;
            }

            return genres;

        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return genres;
        }
    }

    /**
     * This method finds all the titles in the database.
     * @return All the titles of books in the database. If the arraylist is empty, then the number of titles is zero.
     */
    public synchronized ArrayList<String> findAllTitle(){
        this.run();
        ArrayList<String> titles= new ArrayList<>(  );
        try {

            ResultSet resultSet=this.statement.executeQuery( "select distinct title from book;" );
            int count=0;
            while(resultSet.next()){
                titles.add(resultSet.getString( "title" ));
                ++count;
            }

            return titles;

        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return titles;
        }
    }

    /**
     * This method finds all the authors in the database.
     * @return All the authors in the database. If the arraylist is empty, then the number of authors is zero.
     */
    public synchronized ArrayList<String> findAllAuthor(){
        this.run();
        ArrayList<String> authors= new ArrayList<>(  );
        try {

            ResultSet resultSet=this.statement.executeQuery( "select distinct author from book;" );
            int count=0;
            while(resultSet.next()){
                authors.add(resultSet.getString( "author" ));
                ++count;
            }

            return authors;

        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return authors;
        }
    }


    /**
     * This method searches on genre from the database.
     * @param genre The genre that is to be searched.
     * @return The genre that is required. If the genre is empty, then the genre is not found.
     */
    public synchronized ArrayList<String> searchGenre(String genre){
        this.run();
        ArrayList<String> genres= new ArrayList<>(  );
        try {
            ResultSet resultSet=this.statement.executeQuery( "select distinct genre from book where genre ='"+genre+"';" );
            while(resultSet.next()){
                genres.add(resultSet.getString( "genre" ));
            }
            return genres;
        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return genres;
        }
    }

    /**
     * This method establishes all the books in one genre.
     * @param genre The genre for book.
     * @return All the books in the input genre.
     */
    public synchronized ArrayList<String> findTitleInGenre(String genre){
        this.run();
        ArrayList<String> genres= new ArrayList<>(  );
        try {
            ResultSet resultSet=this.statement.executeQuery( "select distinct title from book where genre ='"+genre+"';" );
            while(resultSet.next()){
                genres.add(resultSet.getString( "title" ));
            }
            return genres;
        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return genres;
        }
    }




    /**
     * This methods finds the book from the book bid.
     * @param bid The book ID.
     * @return The book with the input book ID. If the return is null, then the book is not found.
     */
    public synchronized Book findBookByBid(int bid){
        this.run();
        try{
            ResultSet resultSet= this.statement.executeQuery( "select * from book where bid="+
                    String.valueOf( bid )+";");
            int count=0;
            while(resultSet.next()) {
                ++count;
                if (count != 0) {

                    //Book(int bid,int bl,int year,String title,String author,String tps,
                    // String blurb,String publisher,String gnere, boolean bon)
                        return new Book(
                                resultSet.getInt( "bid" ),
                                resultSet.getInt( "bl" ),
                                resultSet.getInt( "year" ),
                                resultSet.getString( "title" ),
                                resultSet.getString( "author" ),
                                resultSet.getString( "tps" ),
                                resultSet.getString( "blurb" ),
                                resultSet.getString( "publisher" ),
                                resultSet.getString( "genre" ),
                                resultSet.getBoolean( "bon" )
                        );
                }
            }

        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return null;
        }
        return null;
    }

    /**
     * This methods finds the book from the book title.
     * @param title The book title.
     * @return The book with the input book title. If the return is null, then the book is not found.
     */
    public synchronized ArrayList<Book> findBookByTitle(String title){
        this.run();
        ArrayList<Book> books= new ArrayList<>(  );
        try{
            ResultSet resultSet= this.statement.executeQuery( "select * from book where title="+
                    "'"+title+"';");

            int count=0;
            while(resultSet.next()) {
                ++count;
                if (count != 0) {

                    //Book(int bid,int bl,int year,String title,String author,String tps,
                    // String blurb,String publisher,String gnere, boolean bon)
                    books.add( new Book(
                            resultSet.getInt( "bid" ),
                            resultSet.getInt( "bl" ),
                            resultSet.getInt( "year" ),
                            resultSet.getString( "title" ),
                            resultSet.getString( "author" ),
                            resultSet.getString( "tps" ),
                            resultSet.getString( "blurb" ),
                            resultSet.getString( "publisher" ),
                            resultSet.getString( "genre" ),
                            resultSet.getBoolean( "bon" )
                    ));
                }
            }
            if(count!=0)
                return books;
            else return null;

        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return null;
        }

    }





    public static void main(String args[]){
        DatabaseMethodsLib d= new DatabaseMethodsLib();
        d.findAllGenre();
        d.findTitleInGenre( "Genre 2" ).forEach( e-> System.out.println(e));
        System.out.println(d.findBookByBid( 1 ).blurb);
        d.findBookByTitle( "Book 1" ).forEach( e->System.out.println(e.bid));

    }

}

