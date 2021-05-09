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
            String filter = "<a href=\"/code/cardlist.html?pagetype=ws&amp;cardset=";

            deckListLinks = findProperLinks(hrefList, filter);
            System.out.println(deckListLinks.size());

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
        System.out.println(hrefListFinal.size());
        return hrefListFinal;
    }

    public static ArrayList<Element> getListOfCards(Element deckLink) throws IOException {
        String link = deckLink.toString();
        String[] linkSplit = link.split("\"");
        System.out.println(linkSplit[1]);

        String finalLink = Constants.HeartoftheCards + linkSplit[1];
        System.out.println(finalLink);

        Document deckPage = Jsoup.connect(finalLink).get();
        ArrayList<Element> hrefList = deckPage.select("a[href*=card]");
        ArrayList<Element> cardTable = deckPage.getElementsByClass("cardlist");
        // NEeds to be fixed can't find table with class=cardlist
        System.out.println(hrefList);
        System.exit(0);
        String filter = "<a href=\"/code/cardlist.html?card=";

        ArrayList<Element> cardListLinks = findProperLinks(cardTable, filter);
        for (int i = 0; i < cardListLinks.size(); i++) {
            System.out.println(cardListLinks.get(i));
        }
        System.exit(0);
        return cardListLinks;
    }
}
