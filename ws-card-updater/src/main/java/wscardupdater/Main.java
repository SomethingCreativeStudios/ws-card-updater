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
        String folderLocation = "E:/Card-Game/WSCards";  //temp directory for now 
        if(args.length != 0){
            folderLocation = args[0];
            if(!folderLocation.endsWith("/")){
                folderLocation = folderLocation + "/";
            }
            Settings.folderLocation = folderLocation;

        }
        
        //System.out.println( "Hello World!" );
        ArrayList<Element> decks = WebScrapper.getListOfDecks();
        
        for(int i = 0; i < decks.size(); i++ ){
            
            ArrayList<Element> cardList = WebScrapper.getListOfCards(decks.get(i));
            for(int j = 0; j < cardList.size(); j++){
                WebScrapper.getCardDetails(cardList.get(j));
            }
            System.exit(0);
        }
    }
}
