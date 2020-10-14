import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.lang.*;

public class DatabaseMethodsForum {
    private Connection connection;
    private ResultSet resultSetPrevious;
    private ResultSet resultSetLast;
    private Statement statement;


    public ResultSet getResultSetLast(){
        return this.resultSetLast;
    }


    public Statement getStatement(){
        return this.statement;
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
     * This method adds a comment of a book  by the member.
     * @param title The title of the book.x
     * @param mid The member ID.
     * @param comments The comment.
     * @return Whether the user has added the comment successfully.
     */
    public synchronized boolean addBookComment(String title,String mid, String comments){
        this.run();
        try {

            this.statement.executeUpdate( "insert into commenttitle (mid,title,contents,time) values(" +
                    "'"+mid+"'," +
                    "'"+title+"',"+
                    "'"+comments+"',"+
                    "now());");
            return true;

        }catch(Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }

    }

    /**
     * This method adds a comment of a author  by the member.
     * @param author The author name.
     * @param mid The member ID.
     * @param comments The comment.
     * @return Whether the user has added the comment successfully.
     */
    public synchronized boolean addAuthorComment(String author,String mid, String comments){
        this.run();
        try {

            this.statement.executeUpdate( "insert into commentauthor (mid,author,contents,time) values(" +
                    "'"+mid+"'," +
                    "'"+author+"',"+
                    "'"+comments+"',"+
                    "now());");
            return true;

        }catch(Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * This method adds a comment of a genre  by the member.
     * @param genre The genre name.
     * @param mid The member ID.
     * @param comments The comment.
     * @return Whether the user has added the comment successfully.
     */
    public synchronized boolean addGenreComment(String genre,String mid, String comments){
        this.run();
        try {

            this.statement.executeUpdate( "insert into commentgenre (mid,genre,contents,time) values(" +
                    "'"+mid+"'," +
                    "'"+genre+"',"+
                    "'"+comments+"',"+
                    "now());");
            return true;

        }catch(Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * This method adds a comment of a topic  by the member.
     * @param topic The topic name.
     * @param mid The member ID.
     * @param comments The comment.
     * @return Whether the user has added the comment successfully.
     */
    public synchronized boolean addTopicComment(String topic,String mid, String comments){
        this.run();
        try {

            this.statement.executeUpdate( "insert into commenttopic (mid,topic,contents,time) values(" +
                    "'"+mid+"'," +
                    "'"+topic+"',"+
                    "'"+comments+"',"+
                    "now());");
            return true;

        }catch(Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }


    /**
     * This method finds all the comments of all books.
     * @return All the comments of all books
     */
    public synchronized ArrayList<Bookcomments> establishBookComments(){
        this.run();
        ArrayList<Bookcomments> bookcomments= new ArrayList<>(  );
        try{
            ResultSet resultSet=this.statement.executeQuery( "select * from commenttitle;" );
            int count=0;
            while(resultSet.next()){
                ++count;
                bookcomments.add(new Bookcomments(
                        resultSet.getString( "contents" ),
                        resultSet.getString( "mid" ),
                        String.valueOf(  resultSet.getString("time" ) ),
                        resultSet.getString( "title"  ) )
                );
            }
            if(count!=0)
                return bookcomments;
            return new ArrayList<Bookcomments>(  );

        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return new ArrayList<Bookcomments>();
        }
    }

    /**
     * This method finds all the comments of all authors.
     * @return All the comments of all authors
     */
    public synchronized ArrayList<Authorcomments> establishAuthorComments(){
        this.run();
        ArrayList<Authorcomments> authorcomments= new ArrayList<>(  );
        try{
            ResultSet resultSet=this.statement.executeQuery( "select * from commentauthor;" );
            int count=0;
            while(resultSet.next()){
                ++count;
                authorcomments.add(new Authorcomments(
                        resultSet.getString( "contents" ),
                        resultSet.getString( "mid" ),
                        String.valueOf(  resultSet.getString("time" ) ),
                        resultSet.getString( "author"  ) )
                );
            }
            if(count!=0)
                return authorcomments;
            return new ArrayList<Authorcomments>(  );

        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return new ArrayList<Authorcomments>();
        }
    }

    /**
     * This method finds all the comments of all genres.
     * @return All the comments of all genres
     */
    public synchronized ArrayList<Genrecomments> establishGenreComments(){
        this.run();
        ArrayList<Genrecomments> genrecomments= new ArrayList<>(  );
        try{
            ResultSet resultSet=this.statement.executeQuery( "select * from commentgenre;" );
            int count=0;
            while(resultSet.next()){
                ++count;
                genrecomments.add(new Genrecomments(
                        resultSet.getString( "contents" ),
                        resultSet.getString( "mid" ),
                        String.valueOf(  resultSet.getString("time" ) ),
                        resultSet.getString( "genre"  ) )
                );
            }
            if(count!=0)
                return genrecomments;
            return new ArrayList<Genrecomments>(  );

        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return new ArrayList<Genrecomments>();
        }
    }

    /**
     * This method finds all the comments of all topics.
     * @return All the comments of all topics
     */
    public synchronized ArrayList<Topiccomments> establishTopicComments(){
        this.run();
        ArrayList<Topiccomments> topiccomments= new ArrayList<>(  );
        try{
            ResultSet resultSet=this.statement.executeQuery( "select * from commenttopic;" );
            int count=0;
            while(resultSet.next()){
                ++count;
                topiccomments.add(new Topiccomments(
                        resultSet.getString( "contents" ),
                        resultSet.getString( "mid" ),
                        String.valueOf(  resultSet.getString("time" ) ),
                        resultSet.getString( "topic"  ) )
                );
            }
            if(count!=0)
                return topiccomments;
            return new ArrayList<Topiccomments>(  );

        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return new ArrayList<Topiccomments>();
        }
    }

    public synchronized boolean addNewTopic(String topic) {//Only a member could do this:
        this.run();
        try{
            this.statement.executeUpdate( "insert into topiclist (topic) values ('"+topic+"');" );
            return true;
        }catch (java.sql.SQLException e){
            System.out.println(e.getCause());
            return false;
        }
    }
    public synchronized ArrayList<String> findAllTopics(){
        this.run();
        ArrayList<String> topics= new ArrayList<>(  );
        try{
            ResultSet resultSet=this.statement.executeQuery( "select * from topiclist;" );
            while(resultSet.next()){
                String topic=resultSet.getString( "topic" );
                topics.add( topic);
            }
            return topics;
        }catch (java.sql.SQLException e){
            System.out.println(e.getCause());
            return new ArrayList<String>(  );
        }
    }




    public static void main(String args[]){
        DatabaseMethodsForum d= new DatabaseMethodsForum();
        d.establishBookComments().forEach( e->System.out.println(e.contents) );
        d.establishAuthorComments().forEach( e->System.out.println(e.contents) );
        d.establishGenreComments().forEach( e->System.out.println(e.contents) );
        d.establishTopicComments().forEach( e->System.out.println(e.contents) );


        d.addNewTopic( "Father father, do you love me?" );
        System.out.println();
        d.findAllTopics().forEach( e->System.out.println(e) );

    }



}

