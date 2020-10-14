import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class DatabaseMethodsUserTests {
    @Test
    public void test(){
        DatabaseMethodsUser u= new DatabaseMethodsUser();
        // Test checkUser
        assertEquals(  true,
                u.checkUser( "Member 1","m1pasword","member" ),
                "the user is not checked properly");

        assertEquals(  true,
                u.checkUser( "uid341","password34","librarian" ),
                "the user is not checked properly");

        //Test register
        u.register( "newM1","p1","newf1","newl1","newe1","newt1","member" );
        u.register( "newM2","p2","newf1","newl1","newe1","newt1","member" );
        u.register( "newL1","p3","newf1","newl1","newe1","newt1","librarian" );
        u.register( "newL2","p4","newf1","newl1","newe1","newt1","librarian" );

        assertEquals(  true,
                u.checkUser( "newM1","p1","member" ),
                "the user is not checked properly");
        assertEquals(  true,
                u.checkUser( "newM2","p2","member" ),
                "the user is not checked properly");
        assertEquals(  true,
                u.checkUser( "newL1","p3","librarian" ),
                "the user is not checked properly");
        assertEquals(  true,
                u.checkUser( "newL2","p4","librarian" ),
                "the user is not checked properly");

        //Test login and checkLogin
        u.login( "Member 1" ,"m1password","member");
        u.login( "Member 2" ,"m2password","member");
        u.login( "newL1" ,"p3","librarian");

        assertEquals( true,u.checkLogin( "Member 1", "member") ,"the user is not checked properly");
        assertEquals( true,u.checkLogin( "newL1", "librarian") ,"the user is not checked properly");
        assertEquals( true,u.checkLogin( "Member 2", "member") ,"the user is not checked properly");

        // Test logout:
        u.logout( "Member 1" ,"m1password","member");
        u.logout( "Member 2" ,"m2password","member");
        u.logout( "newL1" ,"p3","librarian");
        assertEquals( false,u.checkLogin( "Member 1", "member") ,"the user is not checked properly");
        assertEquals( false,u.checkLogin( "newL1", "librarian") ,"the user is not checked properly");
        assertEquals( false,u.checkLogin( "Member 2", "member") ,"the user is not checked properly");

        // Test addBook

        u.addBook( "Book 12",19,"Author 12","Genre 12","Publisher 12","Blurb 12",2020,"Tps 12" );
        DatabaseMethodsLib l= new DatabaseMethodsLib();
        assertEquals( true,l.findAllTitle().contains( "Book 12" ),"the book is not added properly");
        assertEquals( true,l.findTitleInGenre("Genre 12").contains( "Book 12" ),"the book is not added properly");
        assertEquals( true,l.findAllGenre().contains( "Genre 12" ),"the book is not added properly");
        assertEquals( true,l.findAllAuthor().contains( "Author 12" ),"the book is not added properly");


        DatabaseMethodsBoRe b= new DatabaseMethodsBoRe();
        assertEquals( 19,(b.findBookAndCopies( "Book 12" ).get(0).bl),"the book is not added properly" );
        u.addBook( "Book 32",190,"Author 12","Genre 12","Publisher 12","Blurb 12",2020,"Tps 12"  );
        assertEquals( 190,(b.findBookAndCopies( "Book 32" ).get(1).bl),"the book is not added properly" );



    }



}
