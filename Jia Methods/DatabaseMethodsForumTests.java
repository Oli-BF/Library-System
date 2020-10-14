import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.lang.*;

public class DatabaseMethodsForumTests {
    @Test
    public void test(){

        DatabaseMethodsForum f= new DatabaseMethodsForum();

        // Test addBookComment(String title,String mid, String comments), establishBookComments
        try{
            f.run();
            f.getStatement().executeUpdate( "delete from commenttitle" );
        }catch(java.sql.SQLException e){
            System.out.println(e.getCause());
        }
        f.addBookComment( "Book 1","Member 1","Comment of Book 1 from Member 1" );
        f.addBookComment( "Book 1","Member 1","Comment of Book 1 from Member 1#2" );
        f.addBookComment( "Book 1","Member 1","Comment of Book 1 from Member 1#3" );
        boolean bo=false;

        Iterator<Bookcomments> i=f.establishBookComments().iterator();
        while(i.hasNext()){
            Bookcomments bookcomments=i.next();
            if(bookcomments.contents.matches( "Comment of Book 1 from Member 1" )&&
            bookcomments.mid.matches( "Member 1" )&&
            bookcomments.title.matches( "Book 1" ))
                bo=true;

        }
        assertEquals( true,bo,"the commenttitle is not right somehow" );

        bo=false;
        i=f.establishBookComments().iterator();
        while(i.hasNext()){
            Bookcomments bookcomments=i.next();
            if(bookcomments.contents.matches( "Comment of Book 1 from Member 1#2" )&&
                    bookcomments.mid.matches( "Member 1" )&&
                    bookcomments.title.matches( "Book 1" ))
                bo=true;

        }
        assertEquals( true,bo,"the commenttitle is not right somehow" );

        bo=false;
        i=f.establishBookComments().iterator();
        while(i.hasNext()){
            Bookcomments bookcomments=i.next();
            if(bookcomments.contents.matches( "Comment of Book 1 from Member 1#3" )&&
                    bookcomments.mid.matches( "Member 1" )&&
                    bookcomments.title.matches( "Book 1" ))
                bo=true;

        }
        assertEquals( true,bo,"the commenttitle is not right somehow" );



        // Test addAuthorComment(String author,String mid, String comments), establishAuthorComments
        bo=false;
        try{
            f.run();
            f.getStatement().executeUpdate( "delete from commentauthor" );
        }catch(java.sql.SQLException e){
            System.out.println(e.getCause());
        }
        f.addAuthorComment( "Author 1" ,"Member 2","comment of author 1 from member 2");
        f.addAuthorComment( "Author 2" ,"Member 2","comment of author 2 from member 2#2");
        f.addAuthorComment( "Author 2" ,"Member 2","comment of author 2 from member 2#3");

        Iterator<Authorcomments> j=f.establishAuthorComments().iterator();
        while(j.hasNext()){
            Authorcomments authorcomments=j.next();
            if(authorcomments.contents.matches( "comment of author 1 from member 2" )&&
                    authorcomments.mid.matches( "Member 2" )&&
                    authorcomments.author.matches( "Author 1" ))
                bo=true;

        }
        assertEquals( true,bo,"the commentauthor is not right somehow" );

        bo=false;
        j=f.establishAuthorComments().iterator();
        while(j.hasNext()){
            Authorcomments authorcomments=j.next();
            if(authorcomments.contents.matches( "comment of author 2 from member 2#2" )&&
                    authorcomments.mid.matches( "Member 2" )&&
                    authorcomments.author.matches( "Author 2" ))
                bo=true;

        }
        assertEquals( true,bo,"the commentauthor is not right somehow" );

        bo=false;
        j=f.establishAuthorComments().iterator();
        while(j.hasNext()){
            Authorcomments authorcomments=j.next();
            if(authorcomments.contents.matches( "comment of author 2 from member 2#3" )&&
                    authorcomments.mid.matches( "Member 2" )&&
                    authorcomments.author.matches( "Author 2" ))
                bo=true;

        }
        assertEquals( true,bo,"the commentauthor is not right somehow" );

        // Test addGenreComment(String genre,String mid, String comments), establishGenreComments
        bo=false;
        try{
            f.run();
            f.getStatement().executeUpdate( "delete from commentgenre" );
        }catch(java.sql.SQLException e){
            System.out.println(e.getCause());
        }
        f.addGenreComment( "Genre 1" ,"Member 2","comment of Genre 1 from member 2");
        f.addGenreComment( "Genre 2" ,"Member 2","comment of Genre 2 from member 2#2");
        f.addGenreComment( "Genre 2" ,"Member 2","comment of Genre 2 from member 2#3");

        Iterator<Genrecomments> k=f.establishGenreComments().iterator();
        while(k.hasNext()){
            Genrecomments genrecomments=k.next();
            if(genrecomments.contents.matches( "comment of Genre 1 from member 2" )&&
                    genrecomments.mid.matches( "Member 2" )&&
                    genrecomments.genre.matches( "Genre 1" ))
                bo=true;

        }
        assertEquals( true,bo,"the commentgenre is not right somehow" );

        bo=false;
        k=f.establishGenreComments().iterator();
        while(k.hasNext()){
            Genrecomments genrecomments=k.next();
            if(genrecomments.contents.matches( "comment of Genre 2 from member 2#2" )&&
                    genrecomments.mid.matches( "Member 2" )&&
                    genrecomments.genre.matches( "Genre 2" ))
                bo=true;

        }
        assertEquals( true,bo,"the commentgenre is not right somehow" );

        bo=false;
        k=f.establishGenreComments().iterator();
        while(k.hasNext()){
            Genrecomments genrecomments=k.next();
            if(genrecomments.contents.matches( "comment of Genre 2 from member 2#3" )&&
                    genrecomments.mid.matches( "Member 2" )&&
                    genrecomments.genre.matches( "Genre 2" ))
                bo=true;

        }
        assertEquals( true,bo,"the commentgenre is not right somehow" );


        // Test  addTopicComment(String topic,String mid, String comments), establishTopicComments
        bo=false;
        try{
            f.run();
            f.getStatement().executeUpdate( "delete from commenttopic" );
        }catch(java.sql.SQLException e){
            System.out.println(e.getCause());
        }
        f.addTopicComment( "Topic 1" ,"Member 2","comment of Topic 1 from member 2");
        f.addTopicComment( "Topic 2" ,"Member 2","comment of Topic 2 from member 2#2");
        f.addTopicComment( "Topic 2" ,"Member 2","comment of Topic 2 from member 2#3");

        Iterator<Topiccomments> o=f.establishTopicComments().iterator();
        while(o.hasNext()){
            Topiccomments topiccomments=o.next();
            if(topiccomments.contents.matches( "comment of Topic 1 from member 2" )&&
                    topiccomments.mid.matches( "Member 2" )&&
                    topiccomments.topic.matches( "Topic 1" ))
                bo=true;

        }
        assertEquals( true,bo,"the commenttopic is not right somehow" );

        bo=false;
        o=f.establishTopicComments().iterator();
        while(o.hasNext()){
            Topiccomments topiccomments=o.next();
            if(topiccomments.contents.matches( "comment of Topic 2 from member 2#2" )&&
                    topiccomments.mid.matches( "Member 2" )&&
                    topiccomments.topic.matches( "Topic 2" ))
                bo=true;

        }
        assertEquals( true,bo,"the commenttopic is not right somehow" );


        bo=false;
        o=f.establishTopicComments().iterator();
        while(o.hasNext()){
            Topiccomments topiccomments=o.next();
            if(topiccomments.contents.matches( "comment of Topic 2 from member 2#3" )&&
                    topiccomments.mid.matches( "Member 2" )&&
                    topiccomments.topic.matches( "Topic 2" ))
                bo=true;

        }
        assertEquals( true,bo,"the commenttopic is not right somehow" );

    }
}
