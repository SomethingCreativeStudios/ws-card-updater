package wscardupdater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.json.simple.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.*;

/**
 * Hello world!
 *
 */
public class Main 
{
    
    public static void main( String[] args ) throws IOException
    {
        
        //System.out.println( "Hello World!" );
        ArrayList<Element> decks = WebScrapper.getListOfDecks();
        
        for(int i = 0; i < decks.size(); i++ ){
            
            ArrayList<Element> cardList = WebScrapper.getListOfCards(decks.get(i));
            //System.out.println(deckPage);
            System.exit(0);
        }
    }
}