package wscardupdater;

import java.io.IOException;
import java.util.LinkedList;
import org.json.simple.*;
import org.jsoup.nodes.Document;
import org.jsoup.*;

/**
 * Hello world!
 *
 */
public class Main 
{
    
    public static void main( String[] args )
    {
        
        //System.out.println( "Hello World!" );
        LinkedList<String> decks = WebScrapper.getListOfDecks();
        
    }
}
