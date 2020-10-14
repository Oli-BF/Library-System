import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.lang.*;

public class DatabaseMethodsUser {
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
     * This method checks if the user is qualified and finds the user type.
     * @param mid The user ID
     * @param password Thee user password
     * @return A Map where the first thing is whether the user exist in the database, the second thing is the type of the user.
     */
    public synchronized boolean checkUser(String mid, String password, String usertype){
        //If usertype= "member", then search from member.
        //If usertype= "librarian", then search from librarian.
        this.run();
        try{
            if(usertype=="member") {
                ResultSet resultSet = this.statement.executeQuery( "select count(*) from membert where mid=" +
                        "'"+mid+"' AND password='" +password+"';" );
                int count = 0;
                while (resultSet.next())
                    ++count;
                if (count != 0)
                    return true;
            }
            else if(usertype=="librarian"){
                ResultSet resultSet = this.statement.executeQuery( "select count(*) from librariant where lid=" +
                        "'"+mid+"' AND password='" +password+"';"  );
                int count = 0;
                while (resultSet.next())
                    ++count;
                if (count != 0)
                    return true;
            }
            return false;
        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }

    }


    /**
     * This method registers a user to the database.
     * @param id The id of the user.
     * @param password The password.
     * @param firstname The first name.
     * @param lastname The last name.
     * @param email The E-mail.
     * @param telephone The telephone.
     * @param usertype The user type: "member" for normal member, "librarian" for librarian.
     * @return Whether the user has been registered successfully.
     */
    public synchronized boolean register(
            //If usertype="member", register as a member.
            //If usertype="librarian", register as a librarian.
            String id, String password, String firstname,String lastname, String email,String telephone, String usertype
    )
    {

        this.run();
        try{
            if(usertype.matches( "member" )) {
                System.out.println("insert into membert (mid,password,firstname,lastname,email,telephone,logged_in)" +
                        "values(" +
                        "'" + id + "'," +
                        "'" + password + "'," +
                        "'" + firstname + "'," +
                        "'" + lastname + "'," +
                        "'" + email + "'," +
                        "'" + telephone + "'," +
                        "false"+");");
                this.statement.executeUpdate( "insert into membert (mid,password,firstname,lastname,email,telephone,logged_in)" +
                        "values(" +
                        "'" + id + "'," +
                        "'" + password + "'," +
                        "'" + firstname + "'," +
                        "'" + lastname + "'," +
                        "'" + email + "'," +
                        "'" + telephone + "'," +
                        "false"+");"
                );

                return true;
            }
            else if(usertype.matches( "librarian" )) {
                System.out.println( "insert into librariant (lid,password,firstname,lastname,email,telephone,logged_in)" +
                        "values(" +
                        "'" + id + "'," +
                        "'" + password + "'," +
                        "'" + firstname + "'," +
                        "'" + lastname + "'," +
                        "'" + email + "'," +
                        "'" + telephone + "'," +
                        "false"+");" );
                this.statement.executeUpdate( "insert into librariant (lid,password,firstname,lastname,email,telephone,logged_in)" +
                        "values(" +
                        "'" + id + "'," +
                        "'" + password + "'," +
                        "'" + firstname + "'," +
                        "'" + lastname + "'," +
                        "'" + email + "'," +
                        "'" + telephone + "'," +
                        "false"+");"
                );

                return true;
            }
            return false;
        }catch( java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }


    public synchronized boolean checkLogin(String mid,String usertype){
        this.run();
        try{
            if(usertype.matches( "member" )) {
                ResultSet resultSet=this.statement.executeQuery( "select logged_in from membert where mid='"+mid+"';" );
                while(resultSet.next()){
                    boolean logged_in=resultSet.getBoolean( "logged_in" );
                    System.out.println(logged_in);
                    return logged_in;

                }
                return false;
            }
            else if(usertype.matches( "librarian" )){
                ResultSet resultSet=this.statement.executeQuery( "select logged_in from librariant where lid='"+mid+"';" );
                while(resultSet.next()){
                    boolean logged_in=resultSet.getBoolean( "logged_in" );
                    System.out.println(logged_in);
                    return logged_in;

                }
                return false;
            }
            else
                return false;
        }catch (java.sql.SQLException e){
            System.out.println( e.getCause() );
            return false;
        }
    }

    /**
     * Current user logs out.
     */
    public synchronized  boolean logout(String id, String password, String usertype){
        this.run();
        try{
            if(usertype.matches( "member" )) {
                this.statement.executeUpdate("update membert set logged_in = false where mid='"+id+"' AND password='"+password+"';");

                return true;
            }
            else if(usertype.matches( "librarian" )) {
                this.statement.executeUpdate("update librariant set logged_in = false where lid='"+id+"' AND password='"+password+"';");

                return true;

            }

            return false;
        }catch( java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * Current user logs out.
     */
    public synchronized  boolean login(String id, String password, String usertype){
        this.run();
        try{
            if(usertype.matches( "member" )) {
                this.statement.executeUpdate("update membert set logged_in = true where mid='"+id+"' AND password='"+password+"';");

                return true;
            }
            else if(usertype.matches( "librarian" )) {
                this.statement.executeUpdate("update librariant set logged_in = true where lid='"+id+"' AND password='"+password+"';");

                return true;

            }

            return false;
        }catch( java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * Add the book:
     */
    public synchronized boolean addBook(String title,int bl,String author,String genre,String publisher, String blurb,int year,String tps){
        this.run();
        int count=0;

        try{
            ResultSet resultSet= statement.executeQuery("select count(*) from book;"  );
            while(resultSet.next())
                count=resultSet.getInt( "count" );


                    statement.executeUpdate( "insert into book (bid,title,bl,author,genre,publisher,blurb,year,tps)" +
                            "values("
                            +String.valueOf(count+1 )+","
                            +"'"+title+"',"
                            +String.valueOf( bl )+","
                            +"'"+author+"',"
                            +"'"+genre+"',"
                            +"'"+publisher+"',"
                            +"'"+blurb+"',"
                            +String.valueOf( year )+","
                            +"'"+tps+"'"+");");
            if(updateTitlelist(title)&&updateGenrelist(genre) &&updateAuthorlist(author) &&updateBookandcopies( title,bl ))
                return true;
            else
                return false;

        }
        catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }

    }

    public  synchronized boolean updateTitlelist(String title){
        this.run();
        try{
            //System.out.println("select * from titlelist where title='"+title+"';" );
            ResultSet resultSet = statement.executeQuery( "select * from titlelist where title='"+title+"';" );
            int count=0;
            while(resultSet.next()){
                ++count;
            }
            if(count==0){
                statement.executeUpdate("insert into titlelist (title) values ("+"'"+title+"');");
            }
            return true;
        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }

    }


    public synchronized boolean updateGenrelist(String genre){
        this.run();
        try{
            ResultSet resultSet = statement.executeQuery( "select * from genrelist where genre='"+genre+"';" );
            int count=0;
            while(resultSet.next()){
                ++count;
            }
            if(count==0){
                statement.executeUpdate("insert into genrelist (genre) values ("+"'"+genre+"');");
            }
            return true;
        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }

    }

    public synchronized boolean updateAuthorlist(String author){
        this.run();
        try{
            ResultSet resultSet = statement.executeQuery( "select * from authorlist where author='"+author+"';" );
            int count=0;
            while(resultSet.next()){
                ++count;
            }
            if(count==0){
                statement.executeUpdate("insert into authorlist (author) values ("+"'"+author+"');");
            }
            return true;
        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }

    }

    public synchronized boolean updateBookandcopies(String title, int bl){
        this.run();
        try{
            ResultSet resultSet = statement.executeQuery( "select title from bookandcopies where title='"+title+"' AND bl="+String.valueOf( bl )+";");
            int count=0;
            while(resultSet.next())
                ++count;
            if(count==0){
                statement.executeUpdate( "insert into bookandcopies (title,bl,copies)values("+
                        "'"+title+"',"+
                        String.valueOf( bl )+",1);");
            }
            else
                statement.executeUpdate( "update bookandcopies set copies=copies+1 where title="+
                        "'"+title+"' AND bl= "+
                        String.valueOf( bl )+";");
            return true;
        }
        catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }




    public static void main(String args[]){
        DatabaseMethodsUser d= new DatabaseMethodsUser();

        d.addBook( "BOOK 9",13,"AUTHOR 9","GENRE 9","P 9","B 9",1809,"T 9" );

        /*
        d.register( "uid341","password34","user3","user3l","e3","t3","librarian" );
        d.register( "uid342","password34","user3","user3l","e3","t3","librarian" );

        d.register( "uid342","password34","user3","user3l","e3","t3","member" );
        d.register( "uid342","password34","user3","user3l","e3","t3","member" );

         */

        d.logout( "uid342","password34","member" );
        d.login("uid342","password34","member"   );

    }

}


