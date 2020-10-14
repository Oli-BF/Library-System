import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
public class DatabaseMethodsBoReTests {
    @Test //test the borrow
    public void test1() {
        DatabaseMethodsBoRe d= new DatabaseMethodsBoRe();
        d.reset();
        d.borrowBook( 1,"Member 1");
        d.borrowBook( 5,"Member 1");
        d.borrowBook( 7,"Member 1");
        d.borrowBook( 2,"Member 2" );
        d.borrowBook( 3,"Member 3" );

        ArrayList<Borrows> borrows=d.findAllBorrowMid( "Member 1");
        HashSet<Integer> results= new HashSet<>(  );
        borrows.forEach( e->results.add( e.bid ) );
        HashSet<Integer> shoulds=new HashSet<>(  );
        shoulds.add( 1 );shoulds.add( 5 );shoulds.add( 7 );
        assertEquals( shoulds, results,"The books are not borrowed properly");
        d.returnBook( 5,"Member 1");
        d.returnBook( 7,"Member 1");
        d.returnBook( 1,"Member 1");
        d.findAllBorrowMid( "Member 1" ).forEach( e->System.out.println(e.bid) );
        d.finaAllNotReturned( "Member 1" ).forEach( e->System.out.println(e.bid) );
        d.findAllReturnsMid( "Member 1" ).forEach( e->System.out.println(e.bid) );
    }
}
