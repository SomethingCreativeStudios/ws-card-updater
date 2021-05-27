package wscardupdater;

import java.util.ArrayList;

public class Card {
    
    /*
    * This Class will handle all the code for creating a new card into
    * the JSON format with all the fields
    */
    private static String ENGNAME;
    private static String JPNNAME;
    private static String LINK;
    private static String PICTURELINK;
    private static String COLOR;
    private static String CARDNUMBER;
    private static String RARITY;
    private static String TYPE;
    private static String SIDE;

    private static int LEVEL;
    private static int COST;
    private static int SOUL;
    private static int POWER;
    
    private static String[] TRIGGERS; // This may be modified to a list and not strings

    private static String[] ENGTRAITS;
    private static String[] JPNTRAITS;

    /*
    * The constructor takes 3 args 
    * 1: The Link to the card page
    * 2: The English Title of the Card
    * 3: The Japanese Title to the Card
    */
    public Card(String link, String engName, String jpnName){

        ENGNAME = engName;
        JPNNAME = jpnName;
        LINK = link;

    }
    
    public void setColor(String color){
        COLOR = color;
    }
    public void setCardNumber(String cardNum){
        CARDNUMBER = cardNum;
    }
    public void setRarity(String rarity){
        RARITY = rarity;
    }
    public void setType(String type){
        TYPE = type;
    }
    public void setSide(String side){
        SIDE = side;
    }
    public void setLevel(int level){
        LEVEL = level;
    }
    public void setCost(int cost){
        COST = cost;
    }
    public void setSoul(int soul){
        SOUL = soul;
    }
    public void setPower(int power){
        POWER = power;
    }
    public void setPictureLink(String picLink){
        PICTURELINK = picLink;
    }
    public void setTraits(String[] engTraits, String[] jpnTraits){
        ENGTRAITS = engTraits;
        JPNTRAITS = jpnTraits;
    }
    public void setTriggers(String[] triggers){
        TRIGGERS = triggers;
    }
    public void PrintCardDetails(){
        
    }
}
