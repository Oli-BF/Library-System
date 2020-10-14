import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class DatabaseMethodsLibTests {

    @Test //test the findAllTitle
    public void test1() {
        DatabaseMethodsLib tester = new DatabaseMethodsLib(); // DatabaseMethodsLib is tested
        HashSet<String> titles= new HashSet<>(  );
        titles.add("Book 1");titles.add("Book 2");titles.add("Book 3");titles.add("Book 4");
        HashSet<String> results=new HashSet<>(  );
        results.addAll(tester.findAllTitle());
        assertEquals( titles,results,"The books are not selected successfully" );

    }
    @Test //test the findAllGenre
    public void test2() {
        DatabaseMethodsLib tester = new DatabaseMethodsLib(); // DatabaseMethodsLib is tested
        HashSet<String> titles= new HashSet<>(  );
        titles.add("Genre 1");titles.add("Genre 2");
        HashSet<String> results=new HashSet<>(  );
        results.addAll(tester.findAllGenre());
        assertEquals( titles,results,"The genres are not selected successfully" );

    }

    @Test //test the findAllGenre
    public void test3() {
        DatabaseMethodsLib tester = new DatabaseMethodsLib(); // DatabaseMethodsLib is tested
        HashSet<String> titles= new HashSet<>(  );
        titles.add("Author 1");titles.add("Author 2");
        HashSet<String> results=new HashSet<>(  );
        results.addAll(tester.findAllAuthor());
        assertEquals( titles,results,"The authors are not selected successfully" );

    }

    @Test //test the findTitleInGenre
    public void test4() {
        DatabaseMethodsLib tester = new DatabaseMethodsLib(); // DatabaseMethodsLib is tested
        HashSet<String> titles= new HashSet<>(  );
        titles.add("Book 1");titles.add("Book 4");
        HashSet<String> results=new HashSet<>(  );
        results.addAll(tester.findTitleInGenre("Genre 1"));
        assertEquals( titles,results,"The books are not selected successfully" );

    }

    @Test //test the searchGenre
    public void test5() {
        DatabaseMethodsLib tester = new DatabaseMethodsLib(); // DatabaseMethodsLib is tested
        HashSet<String> titles= new HashSet<>(  );
        titles.add("Genre 1");
        HashSet<String> results=new HashSet<>(  );
        results.addAll(tester.searchGenre("Genre 1"));
        assertEquals( titles,results,"The genres are not selected successfully" );

    }

    @Test //test the findBookByBid
    public void test6() {
        DatabaseMethodsLib tester = new DatabaseMethodsLib(); // DatabaseMethodsLib is tested
        Book b= new Book(  );
        b.author="Author 2";

        Book results=(tester.findBookByBid( 1 ));
        assertEquals( b.author,results.author,"The books are not selected successfully" );

    }

    @Test //test the findBookByTitle
    public void test7() {
        DatabaseMethodsLib tester = new DatabaseMethodsLib(); // DatabaseMethodsLib is tested
        ArrayList<Book> books = new ArrayList<>();

        ArrayList<Book> results = (tester.findBookByTitle( "Book 2" ));
        ArrayList<Integer> bid = new ArrayList<>();
        results.forEach( e -> bid.add( e.bid ) );
        HashSet<Integer> bidset = new HashSet<>();
        bidset.addAll( bid );

        HashSet<Integer> bidexpected = new HashSet<>();
        bidexpected.add( 7 );
        bidexpected.add( 8 );
        bidexpected.add( 9 );

        assertEquals( bidexpected, bidset, "The books are not selected successfully" );

    }




}
