package wscardupdater;

import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.*;
import java.io.IOException;


public class WebScrapper {

    private static final String FullDeckListPath = Constants.HeartoftheCards + Constants.DeckLinksLocation;

    public static ArrayList<Element> getListOfDecks() {
        ArrayList<Element> deckListLinks = null;
        Document doc = null;
        try {
            doc = Jsoup.connect(FullDeckListPath).get();

            ArrayList<Element> hrefList = doc.select("a[href]");
           // System.out.println(hrefList);
            String filter = "<a href=\"/code/cardlist.html?pagetype=ws&amp;cardset=";

            deckListLinks = findProperLinks(hrefList, filter);
            //System.out.println(deckListLinks.size());

        } catch (IOException e) {
            // Failure of connection to
            e.printStackTrace();
            System.err.println("Failed to connect to Heart of the Cards Decks List");
        }
        // System.out.println(doc.toString());
        return deckListLinks;
    }

    public static ArrayList<Element> findProperLinks(ArrayList<Element> list, String filter) {
        ArrayList<Element> hrefListFinal = new ArrayList();
        // System.out.println(list.size());

        for (int i = 0; i < list.size(); i++) {
            Element check = list.get(i);
            // System.out.println(list.get(i));
            if (check.toString().contains(filter)) {
                hrefListFinal.add(check);
            }
            // if(list.get(i))

        }
        //System.out.println(hrefListFinal.size());
        return hrefListFinal;
    }

    public static ArrayList<Element> getListOfCards(Element deckLink) throws IOException {
        String link = deckLink.toString();
        String[] linkSplit = link.split("\"");
        //System.out.println(linkSplit[1]);
        String location = linkSplit[1];
        //Removes the amp; that defaults the list back to the list of Decks
        String[] locationFull = location.split("amp;");
        location = locationFull[0]+locationFull[1];
        String finalLink = Constants.HeartoftheCards + location ;
        //System.out.println(finalLink);

        Document deckPage = Jsoup.connect(finalLink).get();

        ArrayList<Element> cardListLinks = deckPage.select("a[href*=/code/cardlist.html?card=]");
        ArrayList<Element> finalCardList = new ArrayList();
       // System.out.println(cardListLinks.size());
        for (int i = 0; i < cardListLinks.size(); i= i+2) {
            //System.out.println(cardListLinks.get(i));
            //This line gets the second link that contains the name and adds it to the final list
            finalCardList.add(cardListLinks.get(i+1)); 
        }
        //System.out.println(finalCardList.size());
        //System.exit(0);
        return finalCardList;
    }
}
