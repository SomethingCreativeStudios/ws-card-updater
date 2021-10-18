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
        engTitle = engTitle.substring(1).trim(); //Strips the ">" from the beginning
        String jpTitle = Titles[1];
        jpTitle = jpTitle.substring(3).trim(); //Strips the "br> From the Title"
        //System.out.println(engTitle + " " + jpTitle);
        Card card = new Card(cardPage, engTitle, jpTitle);
        
        String[] fullCardDeets = cardDetails.toString().split("<tr>");
        // Reference webpageOfCard.txt for info of cardDetails example
        

        String[] rareCardNum = getCardNumberAndRarity(fullCardDeets);
        String[] cardSetandNum = rareCardNum[0].split("-");
        card.setSetName(cardSetandNum[0].trim());
        card.setCardNumber(cardSetandNum[1].trim());
        card.setRarity(rareCardNum[1].trim());
        
        
        System.out.println(card.getCardNumber() + " " + card.getSetName());
        
        // Color @ ln 28

        String[] cardColorSide = getCardColorAndSideStrings(fullCardDeets);
        card.setColor(cardColorSide[0].trim());
        card.setSide(cardColorSide[1].trim());

        String[] cardTypeLevel = getCardTypeAndLevelStrings(fullCardDeets);
        card.setType(cardTypeLevel[0].trim());
        int valLevel = Integer.parseInt(cardTypeLevel[1].trim());//Converts String to Int
        card.setLevel(valLevel);

        String[] cardPowerCost = getCardPowerAndCostStrings(fullCardDeets);
        card.setPower(Integer.parseInt(cardPowerCost[0].trim()));
        card.setCost(Integer.parseInt(cardPowerCost[1].trim()));

        //Done: Soul and Trait 1
        String[] cardSoulTraitOne = getCardSoulAndFirstTraitStrings(fullCardDeets);
        card.setSoul(Integer.parseInt(cardSoulTraitOne[0].trim()));
        
        String[] jpnVSEng = cardSoulTraitOne[1].split(" "); 
        String engTraitFinal = jpnVSEng[1].split("\\)")[0].split("\\(")[1];
        //System.out.println(engTraitFinal);
        String[] jpnTraits = new String[2]; 
        String[] engTraits = new String[2];
        jpnTraits[0] = jpnVSEng[0];
        engTraits[0] = engTraitFinal;
        
        //Done: Triggers Trait 2
        
        String[] cardTriggersTraitTwo = getCardTriggersAndSecondTraitStrings(fullCardDeets);
        String[] triggers = cardTriggersTraitTwo[0].split(" ");
        for(int i = 0; i < triggers.length; i++){
            triggers[i] = triggers[i].trim();
        }
        if(triggers.length > 1){
            try {
                //System.out.println("in try");
                int test = Integer.parseInt(triggers[0]);
                //System.out.println("after int parse");
                triggers[0] = triggers[1];
            }catch (NumberFormatException e){
                System.out.println("in catch");
            }
        }
        card.setTriggers(triggers);
        
        String[] jpnVSEng2 = cardTriggersTraitTwo[1].split(" "); 
        String engTraitFinal2 = jpnVSEng2[1].split("\\)")[0].split("\\(")[1];
        System.out.println(engTraitFinal2 + " " + jpnVSEng2[0]);
        jpnTraits[1] = jpnVSEng2[0];
        engTraits[1] = engTraitFinal2;
        
        card.setTraits(engTraits, jpnTraits);

        card.PrintCardDetails();
        //System.out.print(cardDetails);
        
        // Start getting other card details from the Table
        // Need to get Card Number and Set Name for Image
        // Get the Image and Save it to the folder path
        
        //Image Name will be the Card number



        System.exit(0);
    }
    public static void getCardImage(Card card) throws MalformedURLException{
        Image image = null;
        String imgLoc = Constants.ImageLocation + card.getSetName() + "-" + card.getCardNumber() + ".gif";
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
        //System.out.println(CardNum + " " + Rarity);
        String[] retVals = new String[2]; 
        retVals[0] = CardNum;
        retVals[1] = Rarity;
        return retVals;

    }
    /*
    * This Function gets the Card Color and Side from the
    * String Array that contains the table with all the card details
    * this function returns both the Card Color and Side in a String Array
    * With Card Color being First and Side being second
    *
    * Note: Side in this case refers to it Being Weiss or Schwarz
    */
    public static String[] getCardColorAndSideStrings(String[] cardDeets){
        String val = null;
        for(int i = 0; i < cardDeets.length; i++){
            if(cardDeets[i].contains("Color:")){
                val = cardDeets[i];
                break;
            }
        }
        if(val == null){
            System.err.println(" Card Color not Found");
            System.exit(3);
        }
        String[] split = val.split("</td>");
        String CardColor = split[1];
        String Side = split[3];
        CardColor = CardColor.split(">")[1];
        Side = Side.split(">")[1];
        //System.out.println(CardNum + " " + Rarity);
        String[] retVals = new String[2]; 
        retVals[0] = CardColor;
        retVals[1] = Side;
        return retVals;

    }
     /*
    * This Function gets the Card Type and Level from the
    * String Array that contains the table with all the card details
    * this function returns both the Card Typer and Level in a String Array
    * With Card Type being First and Level being second
    */
    public static String[] getCardTypeAndLevelStrings(String[] cardDeets){
        String val = null;
        for(int i = 0; i < cardDeets.length; i++){
            if(cardDeets[i].contains("Type:")){
                val = cardDeets[i];
                break;
            }
        }
        if(val == null){
            System.err.println(" Card Type not Found");
            System.exit(3);
        }
        String[] split = val.split("</td>");
        String CardType = split[1];
        String Level = split[3];
        CardType = CardType.split(">")[1];
        Level = Level.split(">")[1];
        //System.out.println(CardNum + " " + Rarity);
        String[] retVals = new String[2]; 
        retVals[0] = CardType;
        retVals[1] = Level;
        return retVals;

    }
     /*
    * This Function gets the Card Power and Cost from the
    * String Array that contains the table with all the card details
    * this function returns both the Card Power and Cost in a String Array
    * With Card Power being First and Cost being second
    */
    public static String[] getCardPowerAndCostStrings(String[] cardDeets){
        String val = null;
        for(int i = 0; i < cardDeets.length; i++){
            if(cardDeets[i].contains("Power:")){
                val = cardDeets[i];
                break;
            }
        }
        if(val == null){
            System.err.println(" Card Power not Found");
            System.exit(3);
        }
        String[] split = val.split("</td>");
        String CardPower = split[1];
        String Cost = split[3];
        CardPower = CardPower.split(">")[1];
        Cost = Cost.split(">")[1];
        //System.out.println(CardNum + " " + Rarity);
        String[] retVals = new String[2]; 
        retVals[0] = CardPower;
        retVals[1] = Cost;
        return retVals;

    }
    /*
    * This Function gets the Card Soul and it's first assigned Trait from the
    * String Array that contains the table with all the card details
    * this function returns both the Card Soul and it's first assigned Trait in a String Array
    * With Card Soul being First and it's first assigned Trait being second
    */
    public static String[] getCardSoulAndFirstTraitStrings(String[] cardDeets){
        String val = null;
        for(int i = 0; i < cardDeets.length; i++){
            if(cardDeets[i].contains("Soul:")){
                val = cardDeets[i];
                break;
            }
        }
        if(val == null){
            System.err.println(" Card Power not Found");
            System.exit(3);
        }
        String[] split = val.split("</td>");
        String CardSoul = split[1];
        String Trait = split[3];
        CardSoul = CardSoul.split(">")[1];
        Trait = Trait.split(">")[1];
        //System.out.println(CardNum + " " + Rarity);
        String[] retVals = new String[2]; 
        retVals[0] = CardSoul;
        retVals[1] = Trait;
        return retVals;

    }
    public static String[] getCardTriggersAndSecondTraitStrings(String[] cardDeets){
        String val = null;
        for(int i = 0; i < cardDeets.length; i++){
            if(cardDeets[i].contains("Triggers:")){
                val = cardDeets[i];
                break;
            }
        }
        if(val == null){
            System.err.println(" Card Power not Found");
            System.exit(3);
        }
        String[] split = val.split("</td>");
        String CardTriggers = split[1];
        String Trait2 = split[3];
        CardTriggers = CardTriggers.split(">")[1];
        Trait2 = Trait2.split(">")[1];
        //System.out.println(CardNum + " " + Rarity);
        String[] retVals = new String[2]; 
        retVals[0] = CardTriggers;
        retVals[1] = Trait2;
        return retVals;

    }
}
