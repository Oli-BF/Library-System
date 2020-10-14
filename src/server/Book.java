package server;

import java.io.Serializable;

class Book implements Serializable
{
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

    Book(int bid,int bl,int year,String title,String author,String tps,String blurb,String publisher,String genre, boolean bon)
    {
        this.bid=bid;
        this.bl=bl;
        this.year=year;
        this.title=title;
        this.author=author;
        this.blurb=blurb;
        this.publisher=publisher;
        this.genre=genre;
        this.bon=bon;
    }
    
    Book()
    {
        ;
    }
}