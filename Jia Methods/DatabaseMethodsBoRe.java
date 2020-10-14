import javax.print.attribute.standard.DateTimeAtCreation;
import java.nio.ByteOrder;
import java.sql.*;
import java.lang.*;
import java.util.*;
import java.util.Date;

public class DatabaseMethodsBoRe {
    // Borrowing and Returning books will both touch 3 tables respectively.

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
     * This method borrows a book and records it into databse.
     * @param bid The book ID.
     * @param mid The member ID.
     * @return Whether the user has borrowed the book successfully.
     */
    public synchronized boolean borrowBook(int bid,String mid){
        this.run();
        //Here 3 tables are going to be updated: 1. book 2.bookandcopies 3.borrow 4.returnt

        //Check and update the book table:
        try{
            ResultSet resultSet=this.statement.executeQuery( "select * from book where bid="+String.valueOf( bid )+";" );
            boolean bon=false;
            int count=0;

            while(resultSet.next()){
                ++count;
                bon=resultSet.getBoolean( "bon" );
            }
            if(count!=0&&bon==false) {
                Book book = getBook( bid );
                if(book!=null){
                    try {
                        if(removeCopyNumber( book.bl, book.title )==true){

                            System.out.println("update book set bon=true where" +
                                    " bid="+String.valueOf( bid )+";");
                            this.statement.executeUpdate( "update book set bon=true where" +
                                    " bid="+String.valueOf( bid )+";");
                            updateCopies();

                            Timestamp timestamp = new Timestamp(new Date().getTime());
                            this.statement.executeUpdate( "insert into borrow (mid,bid,bd,bl)" +
                                    "values("+
                                    "'"+mid+"',"+
                                    String.valueOf( bid )+ ",'"+
                                    String.valueOf( timestamp )+"',"+
                                    String.valueOf( book.bl )+");");
                            this.statement.executeUpdate( "insert into returnt (mid,bid,bd,bl,rd)" +
                                    "values("+
                                    "'"+mid+"',"+
                                    String.valueOf( bid )+ ",'"+
                                    String.valueOf( timestamp )+"',"+
                                    String.valueOf( book.bl )+",  '1010-03-18');");
                            return true;

                        }
                        else return false;

                    }catch (java.lang.Exception e){
                        System.out.println(e.getStackTrace());
                        return false;
                    }
                }
                else return false;
            }
            else return false;

        }
        catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }




    /**
     * This methods returns a book and records it into the database.
     * @param bid The book ID.
     * @param mid The user ID.
     * @return Whether the user has returned the book successfully.
     */
    public synchronized boolean returnBook(int bid,String mid){
        this.run();
        //Here 3 tables are going to be updated: 1. book 2.bookandcopies 3.returnt

        //Check and update the book table:
        try{
            ResultSet resultSet=this.statement.executeQuery( "select * from book where bid="+String.valueOf( bid )+";" );
            boolean bon=false;
            int count=0;
            while(resultSet.next()){
                ++count;
                bon=resultSet.getBoolean( "bon" );
            }
            if(count!=0&&bon==true) {
                Book book = getBook( bid );
                if(book!=null){
                    try {
                        if(addCopyNumber( book.bl, book.title )==true){



                            System.out.println("update book set bon=false where" +
                                    " bid="+String.valueOf( bid )+";");
                            this.statement.executeUpdate( "update book set bon=false where" +
                                    " bid="+String.valueOf( bid )+";");
                            updateCopies();

                            Timestamp timestamp = new Timestamp(new Date().getTime());

                            this.statement.executeUpdate( "update returnt set rd='" +String.valueOf( timestamp )+"' WHERE mid='"+
                                    mid+"' AND bid='"+ book.bid+"' AND bl=" + String.valueOf(  book.bl)+";");

                            return true;
                        }
                        else return false;

                    }catch (java.lang.Exception e){
                        System.out.println(e.getStackTrace());
                        return false;
                    }
                }
                else return false;
            }
            else return false;

        }
        catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * This method updates the bookandcopies table.
     * @return Whether the bookandcopies table has been updated successfully.
     */
    public synchronized boolean updateCopies(){
        this.run();
        HashMap<String, HashMap<Integer,Integer>> copyAndLength= new HashMap<>(  );
        try {
            statement = this.connection.createStatement();
            ResultSet resultSet=statement.executeQuery(
                    "select * from book;");
            int copies=0;
            int bl=0;
            String title="";
            Date date;

            while(resultSet.next()) {
                title = resultSet.getString( "title" );
                bl = resultSet.getInt( "bl" );
                boolean bon=resultSet.getBoolean( "bon" );
                if(bon==false) {
                    int tempcopy = 0;
                    HashMap<Integer, Integer> temp = copyAndLength.get( title );
                    if (temp != null) {
                        if (temp.get( bl ) != null) {
                            tempcopy = temp.get( bl );
                            temp.put( bl, tempcopy + 1 );
                        } else
                            temp.put( bl, 1 );
                    } else {
                        HashMap<Integer, Integer> temp2 = new HashMap<>();
                        temp2.put( bl, 1 );
                        copyAndLength.put( title, temp2 );
                    }
                }
            }

        }
        catch (java.sql.SQLException e) {
            System.out.println( e.getStackTrace() );
            return false;
        }

        this.run();

        Set<Map.Entry<String,HashMap<Integer,Integer>>> m2=copyAndLength.entrySet();
        try {
            this.statement.executeUpdate( "delete from bookandcopies;" );
            for(Map.Entry<String,HashMap<Integer,Integer>> m: m2){
                for(Map.Entry<Integer,Integer> j: m.getValue().entrySet()) {
                    for(int k =0;k<j.getValue();++k){
                        addCopyNumber( j.getKey(),m.getKey() );
                    }
                }
            }
        }
        catch(java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
        catch (java.lang.Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    /**
     * This is the help method for updateCopies().
     * @param bl The borrow length of the book.
     * @param title The title of the book.
     * @return If the copy of the book has been added to the bookandcopies table.
     * @throws Exception
     */
    public synchronized boolean addCopyNumber(int bl, String title) throws Exception{
        try{

            ResultSet resultSet = statement.executeQuery(
                    "select * from bookandcopies where title='"+title+"' and bl="+String.valueOf( bl )+";") ;

            int count=0;
            while(resultSet.next())
                ++count;
            if(count!=0){
                statement.executeUpdate( "update bookandcopies set copies=copies+1 " +
                        "where title='"+title+"' and bl="+String.valueOf( bl ) +";");
            }
            else{
                statement.executeUpdate( "insert into bookandcopies (title,bl,copies)" +
                        "values('"+title+"',"+String.valueOf( bl )+",1);" );
            }

            return true;
        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    public synchronized boolean removeCopyNumber(int bl, String title) throws Exception{
        try{

            ResultSet resultSet = statement.executeQuery(
                    "select * from bookandcopies where title='"+title+"' and bl="+String.valueOf( bl )+";") ;

            int count=0;
            int copy=0;
            while(resultSet.next()) {
                ++count;
                copy=resultSet.getInt( "copies" );
            }
            if(count!=0&&copy>0){
                System.out.println("copy: "+copy);
                System.out.println("update bookandcopies set copies=copies-1 " +
                        "where title='"+title+"' and bl="+String.valueOf( bl ) +";");
                statement.executeUpdate( "update bookandcopies set copies=copies-1 " +
                        "where title='"+title+"' and bl="+String.valueOf( bl ) +";");
                System.out.println("successful");
                return true;
            }
            else{
                return false;
            }


        }catch (java.sql.SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }



    /**
     * This methods find the book from the book ID.
     * @param bid The book ID.
     * @return The book with the input book ID.
     */
    public synchronized Book getBook(int bid){
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


    public boolean reset (){
        this.run();
        try{
            this.statement.executeUpdate( "delete from borrow;" );
            this.statement.executeUpdate( "delete from returnt;" );
            this.statement.executeUpdate( "delete from bookandcopies;" );
            ResultSet resultSet=this.statement.executeQuery( "select * from book;"  );

            while(resultSet.next()){
                int bid=resultSet.getInt( "bid");
                setFalse( bid );
            }
            updateCopies();
            return true;
        }catch(java.sql.SQLException e){
            System.out.println(e.getErrorCode());
            return false;
        }
    }


    public boolean setFalse(int bid){
        this.run();
        try{
            this.statement.executeUpdate( "update book set bon=false where bid="+
                    String.valueOf(bid)+";" );
            return true;
        }catch (java.sql.SQLException e){
            System.out.println(e.getCause());
            return false;
        }
    }

    public ArrayList<Borrows>findAllBorrowMid(String mid){
        this.run();
        ArrayList<Borrows> borrows= new ArrayList<>(  );
        try{
            ResultSet resultSet= this.statement.executeQuery( "select * from borrow where mid="+
                    "'"+mid+"';");
            int count=0;
            while(resultSet.next()){
                ++count;
                int bid=resultSet.getInt( "bid" );
                int bl=resultSet.getInt( "bl" );
                String bd= String.valueOf(  resultSet.getTimestamp("bd"  ));
                borrows.add( new Borrows(bid,bl,mid,bd  ) );
            }
            return borrows;
        }catch (java.sql.SQLException e){
            System.out.println(e.getCause());
            return new ArrayList<Borrows>(  );
        }
    }

    public ArrayList<Returns>findAllReturnsMid(String mid){
        this.run();
        ArrayList<Returns> returns= new ArrayList<>(  );
        try{
            ResultSet resultSet= this.statement.executeQuery( "select * from returnt where mid="+
                    "'"+mid+"';");
            int count=0;
            while(resultSet.next()){
                ++count;
                int bid=resultSet.getInt( "bid" );
                int bl=resultSet.getInt( "bl" );
                String rd= String.valueOf(  resultSet.getTimestamp("rd"  ));
                returns.add( new Returns(bid,bl,mid,rd  ) );
            }
            return returns;
        }catch (java.sql.SQLException e){
            System.out.println(e.getCause());
            return new ArrayList<Returns>(  );
        }
    }

    public ArrayList<Borrows> finaAllNotReturned(String mid){
        this.run();
        ArrayList<Borrows> borrows= new ArrayList<>(  );
        try{

            ResultSet resultSet= this.statement.executeQuery(
                    "select * from returnt " +
                    "where rd='1010-03-18 00:00:00' AND mid="+
                    "'"+mid+"';");

            int count=0;
            while(resultSet.next()){
                ++count;
                int bid=resultSet.getInt( "bid" );
                int bl=resultSet.getInt( "bl" );
                String bd= String.valueOf(  resultSet.getTimestamp("bd"  ));
                borrows.add( new Borrows(bid,bl,mid,bd ) );
            }
            return borrows;
        }catch (java.sql.SQLException e){
            System.out.println(e.getCause());
            return new ArrayList<Borrows>(  );
        }
    }


    public ArrayList<Bookandcopies> findBookAndCopies (String title){
        this.run();
        ArrayList<Bookandcopies> bookandcopies= new ArrayList<>(  );
        int count=0;
        try{

            ResultSet resultSet= this.statement.executeQuery( "select  * from bookandcopies where title='"+title+"';" );
            while(resultSet.next()){
                int bl=resultSet.getInt( "bl" );
                int copies=resultSet.getInt( "copies" );
                bookandcopies.add(new Bookandcopies( title, bl,copies));
                ++count;
            }

            if(count!=0)
                return bookandcopies;
            else
                return null;

        }catch (java.sql.SQLException e ){
            System.out.println(e.getCause());
            return null;
        }
    }

    public static void main(String args[]){
        DatabaseMethodsBoRe d= new DatabaseMethodsBoRe();
        d.reset();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        System.out.println(String.valueOf(  timestamp));
        d.borrowBook( 5,"Member 1" );
        d.returnBook( 5,"Member 1" );
        d.borrowBook( 5,"Member 1" );
        d.borrowBook( 6,"Member 1" );

        d.finaAllNotReturned( "Member 1").forEach( e->System.out.println(e.bid) );


    }

}

