package wscardupdater;

import java.util.ArrayList;

public class Card {
    
    /*
    * This Class will handle all the code for creating a new card into
    * the JSON format with all the fields
    */
    private static String NAMEeng;
    private static String NAMEjp;
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
    
    private static String TRIGGERS; // This may be modified to a list and not strings

    private static String TRAITONEeng;
    private static String TRAITTWOeng;
    private static String TRAITONEjp;
    private static String TRAITTWOjp;
    /*
    * The constructor takes 3 args 
    * 1: The Link to the card page
    * 2: The English Title of the Card
    * 3: The Japanese Title to the Card
    */
    public Card(String l, String e, String j){

        NAMEeng = e;
        NAMEjp = j;
        LINK = l;

    }
    
    public void setColor(String c){
        COLOR = c;
    }
    public void setCardNumber(String cn){
        CARDNUMBER = cn;
    }
    public void setRarity(String r){
        RARITY = r;
    }
    public void setType(String t){
        TYPE = t;
    }
    public void setSide(String s){
        SIDE = s;
    }
    public void setLevel(int l){
        LEVEL = l;
    }
    public void setCost(int c){
        COST = c;
    }
    public void setSoul(int s){
        SOUL = s;
    }
    public void setPower(int p){
        POWER = p;
    }
    public void setPictureLink(String pl){
        PICTURELINK = pl;
    }
    public void setTraits(String TOne, String TTwo){
        
    }
    public void setTriggers(){

    }
}
