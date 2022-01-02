package wscardupdater;

//import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.Writer;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Card {
    
    /*
    * This Class will handle all the code for creating a new card into
    * the JSON format with all the fields
    */
    private static String ENGNAME;
    private static String JPNNAME;
    private static String LINK;
    private static String SETNAME;
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

    private static String JPCARDTEXT;
    private static String ENGCARDTEXT;

    private static String JPFLAVORTEXT;
    private static String ENGFLAVORTEXT;

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
    public String getCardNumber(){
        return CARDNUMBER;
    }
    public void setRarity(String rarity){
        RARITY = rarity;
    }
    public void setSetName(String setName){
        SETNAME = setName;
    }
    public String getSetName(){
        return SETNAME;
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
    public void setTraits(String[] engTraits, String[] jpnTraits){
        ENGTRAITS = engTraits;
        JPNTRAITS = jpnTraits;
    }
    public void setTriggers(String[] triggers){
        TRIGGERS = triggers;
    }
    public void setJPCardText(String jpText){
        JPCARDTEXT = jpText;
    }
    public void setENGCardText(String engText){
        ENGCARDTEXT = engText;
    }
    public void setJPFlavorText(String jpText){
        JPFLAVORTEXT = jpText;
    }
    public void setEngFlavorText(String engText){
        ENGFLAVORTEXT = engText;
    }
    public void PrintCardDetails(){
        // Note For Card placement in folder will be set by the SetName and the card number will be the backend String
        File output = new File(Settings.folderLocation + SETNAME.replace("/", "-") + "-" + getCardNumber() + ".json");
        JSONObject card = new JSONObject();
        
        //Set up Name Array for Card
        JSONObject nameEng = new JSONObject();
        JSONObject nameJpn = new JSONObject();
        JSONArray names = new JSONArray();
        nameEng.put("en", ENGNAME);
        nameJpn.put("jp", JPNNAME);
        names.add(nameEng);
        names.add(nameJpn);


        card.put("name", names);
        card.put("color", COLOR);
        card.put("rarity", RARITY);
        card.put("cardNo", SETNAME.replace("/", "-") + "-" + CARDNUMBER);
        card.put("set", SETNAME.replace("/", "-"));
        card.put("side", SIDE);
        card.put("type", TYPE);
        card.put("level", LEVEL);
        card.put("cost", COST);
        card.put("power", POWER);
        card.put("soul", SOUL);

        JSONArray enTraits = new JSONArray();
        JSONArray jpTraits = new JSONArray();
        JSONArray traits = new JSONArray();
        enTraits.add(ENGTRAITS[0]);
        enTraits.add(ENGTRAITS[1]);
        jpTraits.add(JPNTRAITS[0]);
        jpTraits.add(JPNTRAITS[1]);
        JSONObject enTrait = new JSONObject();
        JSONObject jpTrait = new JSONObject();
        enTrait.put("en", enTraits);
        jpTrait.put("jp", jpTraits);
        traits.add(enTrait);
        traits.add(jpTrait);
        card.put("trait", traits);
        
        JSONArray triggers = new JSONArray();
        for(int i = 0; i < TRIGGERS.length; i++){
            triggers.add(TRIGGERS[i]);
        }  
        card.put("triggers", triggers);

        // Link to the Card's Page on Heart of the Cards
        card.put("cardLink", LINK);
        // Link to the Card's Image on Heart of the Cards
        //card.put("cardPicLink", PICTURELINK);

        JSONObject enFlavor = new JSONObject();
        JSONObject jpFlavor = new JSONObject();
        enFlavor.put("en", ENGFLAVORTEXT);
        jpFlavor.put("jp", JPFLAVORTEXT);
        JSONArray flavor = new JSONArray();
        flavor.add(enFlavor);
        flavor.add(jpFlavor);
        card.put("flavor", flavor);

        JSONObject enText = new JSONObject();
        JSONObject jpText = new JSONObject();
        enText.put("en", ENGCARDTEXT);
        jpText.put("jp", JPCARDTEXT);
        JSONArray text = new JSONArray();
        text.add(enText);
        text.add(jpText);
        card.put("text", text);

        try {
            FileWriter myWriter = new FileWriter(output);
            myWriter.write(card.toString());
            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("Failed to write to file for " + ENGNAME);
            e.printStackTrace();
        }
    }
}
