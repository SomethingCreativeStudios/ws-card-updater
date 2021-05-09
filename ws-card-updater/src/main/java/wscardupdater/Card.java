package wscardupdater;

public class Card {
    
    /*
    * This Class will handle all the code for creating a new card into
    * the JSON format with all the fields
    */
    private static String NAMEeng;
    private static String NAMEjp;
    private static String LINK;

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
}
