package wscardupdater;

import java.awt.*;
import javax.imageio.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


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
        String[] linkSplit = link.split("\""); //Splits link into 3 sections Middle is what we care about
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
            //This line gets the second link that contains the name and adds it to the final list
            finalCardList.add(cardListLinks.get(i+1)); 
        }
        //System.out.println(finalCardList.size());
        //System.exit(0);
        return finalCardList;
    }
    
    public static void getCardDetails(Element cardLink) throws IOException{
        // This section takes care of the 
        String link = cardLink.toString();
        //System.out.println(link);
        String[] linkSplit = link.split("\"");
        String location = linkSplit[1];
        //System.out.println(location);
        String cardPage = Constants.HeartoftheCards + location;
        //System.out.println(cardPage);

        Document cardp = Jsoup.connect(cardPage).get();
        //Should grab the table with all the details else we may have to try grabbing individual sections
        ArrayList<Element> cardDetails = cardp.select("table[width=95%]"); 
        // Get Titles for the Card
        String[] Titles = linkSplit[2].split("<");
        String engTitle = Titles[0];
        engTitle = engTitle.substring(1); //Strips the ">" from the beginning
        String jpTitle = Titles[1];
        jpTitle = jpTitle.substring(3); //Strips the "br> From the Title"
        //System.out.println(engTitle + " " + jpTitle);
        Card card = new Card(cardPage, engTitle, jpTitle);
        
        String[] fullCardDeets = cardDetails.toString().split("<tr>");
        // Reference webpageOfCard.txt for info of cardDetails example
        
        //Card Number @ ln 22
        String[] rareCardNum = getCardNumberAndRarity(fullCardDeets);
        card.setCardNumber(rareCardNum[0]);
        card.setRarity(rareCardNum[1]);
        // Rarity @ ln 24

        // Color @ ln 28


        System.out.print(fullCardDeets.length);
        // Start getting other card details from the Table
        // Need to get Card Number and Set Name for Image
        // Get the Image and Save it to the folder path
        
        //Image Name will be the Card number



        System.exit(0);
    }
    public static void getCardImage(Card card) throws MalformedURLException{
        Image image = null;
        String imgLoc = Constants.ImageLocation + card.getSetName() + card.getCardNumber() + ".gif";
        URL imageURL = new URL(imgLoc.toLowerCase());
        try {
            image = ImageIO.read(imageURL);
            File output = new File(Settings.folderLocation + card.getSetName() + card.getCardNumber() + ".png" );
        } catch (IOException e) {
            System.out.println("Failed to load image from " + imageURL.toString());
            e.printStackTrace();
        }
        
    }
    /*
    * This Function gets the Card Number and Rarity from the
    * String Array that contains the table with all the card details
    * this function returns both the Card Number and Rarity in a String Array
    * With Card number being First and Rarity being second
    */
    public static String[] getCardNumberAndRarity(String[] cardDeets){
        String val = null;
        for(int i = 0; i < cardDeets.length; i++){
            if(cardDeets[i].contains("Card No.")){
                val = cardDeets[i];
                break;
            }
        }
        if(val == null){
            System.err.println(" Card Number not Found");
            System.exit(3);
        }
        String[] split = val.split("</td>");
        String CardNum = split[1];
        String Rarity = split[3];
        CardNum = CardNum.split(">")[1];
        Rarity = Rarity.split(">")[1];
        System.out.println(CardNum + " " + Rarity);
        String[] retVals = new String[2]; 
        retVals[0] = CardNum;
        retVals[1] = Rarity;
        return retVals;

    }
}
