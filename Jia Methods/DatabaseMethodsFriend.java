import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.lang.*;

public class DatabaseMethodsFriend {
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

    public synchronized boolean addFriend(String mid , String friendid){
        this.run();
        try{
            this.statement.executeUpdate("insert into friendlist (mid,friendid) values(" +
                    "'"+mid+"'," +
                    "'"+friendid+"');"  );
            this.statement.executeUpdate("insert into friendlist (mid,friendid) values(" +
                    "'"+friendid+"'," +
                    "'"+mid+"');"  );
            return true;
        }catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * This method returns the chat history of the mid and the friend.
     * @param mid The mid.
     * @param friendid The mid of the friend.
     * @return An arraylist of ChatHistory of the chat history and the records are sorted so that the most recent chat will appear at the head of the arraylist.
     */
    public synchronized ArrayList<ChatHistory> getChatHistory(String mid, String friendid){
        this.run();
        ArrayList<ChatHistory> chathistory = new ArrayList<>(  );
        ChatHistory chatHistory;
        try{
            ResultSet resultSet = this.statement.executeQuery("select * from friendlist where mid='"+mid+"' and friendid='"+friendid+"';");
            int count=0;
            while(resultSet.next())
                count++;
            if(count!=0){
                ResultSet resultSet1=this.statement.executeQuery( "select * from chathistory where "+
                        "(mid='"+mid+"' and friendid='"+friendid+"') OR " +
                        "(mid='"+friendid+"' and friendid='"+mid+"');" );
                while(resultSet1.next()) {
                    String talker = resultSet1.getString( "mid" );
                    String receiver = resultSet1.getString( "friendid" );
                    String time   = String.valueOf(  resultSet1.getTimestamp( "time" ));
                    String message = resultSet1.getString( "chat" );
                    chatHistory= new ChatHistory( talker,message ,time,receiver);

                    chathistory.add( chatHistory );
                }
            }
            chathistory.sort((e1,e2)->e2.timestamp.compareTo( e1.timestamp ));// The most recent message will appear at the top.
            return chathistory;

        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return new ArrayList<ChatHistory> ( );
        }

    }

    /**
     * This method returns the chat history of global chat.
     * @return An arraylist of ChatHistory of the chat history and the records are sorted so that the most recent chat will appear at the head of the arraylist.
     */
    public synchronized ArrayList<GlobalChatHistory> getGlobalChatHistory(){
        this.run();
        ArrayList<GlobalChatHistory> chathistory = new ArrayList<>(  );
        GlobalChatHistory chatHistory;
        try {
            ResultSet resultSet = this.statement.executeQuery( "select * from globalhistory;" );
            int count = 0;
            while (resultSet.next()) {
                count++;

                String talker = resultSet.getString( "mid" );
                String time = String.valueOf( resultSet.getTimestamp( "time" ) );
                String message = resultSet.getString( "contents" );
                chatHistory = new GlobalChatHistory( talker, message, time );

                chathistory.add( chatHistory );

            }
            if(count!=0) {
                chathistory.sort( (e1, e2) -> e2.timestamp.compareTo( e1.timestamp ) );// The most recent message will appear at the top.
                return chathistory;
            }
            else return new ArrayList<GlobalChatHistory> ( );
        }
        catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return new ArrayList<GlobalChatHistory> ( );
        }

    }

    /**
     * This method adds a chat message to the database.
     * @param mid The mid of the user.
     * @param friendid The mid of the friend.
     * @param chat The Chatting message
     * @return Whether the message is added to the database successfully.
     */
    public synchronized boolean addChat(String mid, String friendid, String chat){
        this.run();

        try{
            ResultSet resultSet = this.statement.executeQuery("select * from friendlist where mid='"+mid+"' and friendid='"+friendid+"';");
            int count=0;
            while(resultSet.next())
                count++;
            if(count!=0){
                this.statement.executeUpdate( "insert into chathistory (mid,friendid,chat,time)values( "+
                        "'"+mid+"','"+friendid+"',"+"'"+chat+"',now());");
                return true;

            }
            return false;

        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    public synchronized boolean addGlobalChat(String mid,  String chat){
        this.run();

        try{
            ResultSet resultSet = this.statement.executeQuery("select * from membert where mid='"+mid+"';");

            int count=0;
            while(resultSet.next())
                count++;
            if(count!=0){

                this.statement.executeUpdate( "insert into globalhistory (mid,contents,time)values( "+
                        "'"+mid+"',"+"'"+chat+"',now());");
                return true;
            }
            return false;

        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    public synchronized boolean clearAll(){
        this.run();
        try{

            this.statement.executeUpdate( "delete from chathistory;" );
            this.statement.executeUpdate( "delete from globalhistory;" );
            this.statement.executeUpdate( "delete from friendlist" );
            return true;
        }catch (java.sql.SQLException e){
            System.out.println(e.getCause());
            return false;
        }
    }


    public synchronized FriendListLogin getFriendListAndLogin(String mid){
        this.run();
        //   mid    | friendid | logged
        ArrayList<FriendListLogin> friendListLogins=new ArrayList<>(  );
        ArrayList<String> names= new ArrayList<>(  );
        ArrayList<Boolean> logins=new ArrayList<>(  );
        try{
            ResultSet resultSet=this.statement.executeQuery( "select * from friendlist where mid='"+mid+"';" );
            while(resultSet.next()){
                String name= resultSet.getString( "friendid" );
                boolean login=resultSet.getBoolean( "logged" );
                names.add(name);
                logins.add(login);


            }
            return new FriendListLogin( names,logins );

        }catch ( java.sql.SQLException e){
            System.out.println( e.getCause() );
            return null;
        }

    }
    public static void main(String args[]){
        DatabaseMethodsFriend d= new DatabaseMethodsFriend();

        d.getChatHistory( "Member 3","Member 2" ).forEach( e->System.out.println(e) );
        d.getGlobalChatHistory().forEach( e-> System.out.println(e));

        d.getFriendListAndLogin( "Member 1" ).name.forEach( e->System.out.println(e) );

    }

}

