package wscardupdater;

import java.util.LinkedList;
import org.jsoup.nodes.Document;
import org.jsoup.*;
import java.io.IOException;


public class WebScrapper {
    
    private static final String FullDeckListPath = Constants.HeartoftheCards + Constants.DeckLinksLocation;

    

    public static LinkedList<String> getListOfDecks(){
        LinkedList<String> listOfDecks = null;
        Document doc = null;
        try {
            doc = Jsoup.connect(FullDeckListPath).get();
            
            System.out.println();
        } catch (IOException e) {
            // Failure of connection to 
            e.printStackTrace();
            System.err.println("Failed to connect to Heart of the Cards Decks List");
        }
        System.out.println(doc.toString());
        return listOfDecks;
    }
}
