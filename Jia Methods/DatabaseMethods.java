import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.lang.*;

public class DatabaseMethods {
    ;
}

class ChatHistory implements Serializable {
    private static final long serialVersionUID = 7098525355326623206L;
    String talker;//The mid of the talker.
    String reveiver;//The mid of the receiver.
    String message;
    String timestamp;
    ChatHistory(String talker,String message,String timestamp,String reveiver){
        this.talker=talker;
        this.message=message;
        this.timestamp=timestamp;
        this.reveiver=reveiver;
    }

    @Override
    public String toString() {

        return(String.valueOf(  "talker :"+(talker)+" receiver: "+ (reveiver)+ " message: "+message+ " time "+timestamp));
    }
}

class GlobalChatHistory implements Serializable{
    private static final long serialVersionUID = 7198525355326623206L;
    String talker;//The mid of the talker.
    String message;
    String timestamp;
    GlobalChatHistory(String talker,String message,String timestamp){
        this.talker=talker;
        this.message=message;
        this.timestamp=timestamp;

    }

    @Override
    public String toString() {

        return(String.valueOf(  "talker :"+(talker)+"message: "+message+ "time "+timestamp));
    }
}

class FriendListLogin implements Serializable{
    private static final long serialVersionUID = 7298525355326623206L;
    public ArrayList<String> name;
    public ArrayList<Boolean> logged;
    FriendListLogin(ArrayList<String> name,ArrayList<Boolean> logged){
        this.name=name;this.logged=logged;
    }
}

class Book implements Serializable{
    private static final long serialVersionUID = 7398525355326623206L;

    public int bid=0;
    public int bl=0;
    public int year=0;
    public String title="";
    public String author="";
    public String tps="";
    public String blurb="";
    public String publisher="";
    public String genre="";
    public boolean bon=false;

    Book(int bid,int bl,int year,String title,String author,String tps,String blurb,String publisher,String gnere, boolean bon){
        this.bid=bid;this.bl=bl;this.year=year;this.title=title;this.author=author;this.blurb=blurb;this.publisher=publisher;
        this.genre=genre;this.bon=bon;
    }
    Book(){
        ;
    }


}
class Bookcomments implements Serializable{
    private static final long serialVersionUID = 7498525355326623206L;
    String contents;
    String mid;
    String timestamp;
    String title;

    Bookcomments(String contents,String mid,String timestamp,String title){
        this.contents=contents;this.mid=mid;this.timestamp=timestamp;this.title=title;
    }

    Bookcomments(){
        ;
    }

}

class Authorcomments implements Serializable{
    private static final long serialVersionUID = 7598525355326623206L;
    String contents;
    String mid;
    String timestamp;
    String author;

    Authorcomments(String contents,String mid,String timestamp,String author){
        this.contents=contents;this.mid=mid;this.timestamp=timestamp;this.author=author;
    }
    Authorcomments(){}
    ;
}

class Genrecomments implements Serializable{
    private static final long serialVersionUID = 7698525355326623206L;
    String contents;
    String mid;
    String timestamp;
    String genre;

    Genrecomments(String contents,String mid,String timestamp,String genre){
        this.contents=contents;this.mid=mid;this.timestamp=timestamp;this.genre=genre;
    }
    Genrecomments(){
        ;
    }

}

class Topiccomments implements Serializable{
    private static final long serialVersionUID = 7798525355326623206L;
    String contents;
    String mid;
    String timestamp;
    String topic;

    Topiccomments(String contents,String mid,String timestamp,String topic){
        this.contents=contents;this.mid=mid;this.timestamp=timestamp;this.topic=topic;
    }
    Topiccomments(){
        ;
    }
}

class Borrows implements Serializable{
    private static final long serialVersionUID = 7898525355326623206L;
    int bid;
    int bl;
    String mid;
    String bd;

    public Borrows(int bid, int bl, String mid, String bd) {
        this.bid = bid;
        this.bl = bl;
        this.mid = mid;
        this.bd = bd;
    }
}

class Returns implements Serializable{
    private static final long serialVersionUID = 7998525355326623206L;
    int bid;
    int bl;
    String mid;
    String rd;

    public Returns(int bid, int bl, String mid, String rd) {
        this.bid = bid;
        this.bl = bl;
        this.mid = mid;
        this.rd = rd;
    }
}

class Bookandcopies implements Serializable{
    private static final long serialVersionUID = 7898225355326623206L;
    //  title  | bl | copies
    String title;
    int bl;
    int copies;

    public Bookandcopies(String title, int bl, int copies) {
        this.title = title;
        this.bl = bl;
        this.copies = copies;
    }
}