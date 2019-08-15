/**
 * SYST 17796 Project Winter 2019 Base code.
 * Students can modify and extend to implement their game.
 * Add your name as a modifier and the date!
 */
package Libraries.ObjectClasses;

import java.util.ArrayList;

import Libraries.JDBC.JavaDatabaseConnector;

public class Player//abstract class Player
{
    private String playerID; //the unique ID for this player
    private String playerRaceName; // the unique Race for this player
    private int manaValue = 1;
    private JavaDatabaseConnector connection;
    public ArrayList <FrayCard> frayCardDeck = new ArrayList<FrayCard>();

    /**
     * A constructor that allows you to set the player's unique ID
     * @param name the unique ID to assign to this player.
     * @param javaDatabaseConnector 
     */
    public Player(String name, String raceName, JavaDatabaseConnector javaDatabaseConnector) // Used for setting up the race of the player before playing the actual card game
    {
        this.playerID = name;
      
//    	if(raceName.equalsIgnoreCase("Elf")) {
//			this.playerRaceName = raceName;
//			ElfRace elfRaceChoosen = new ElfRace(javaDatabaseConnector);
//			frayCardDeck.addAll(elfRaceChoosen.addRaceCards());
//		}
//		
//		else if (raceName.equalsIgnoreCase("Human")) {
//			this.playerRaceName = raceName;
//			HumanRace humanRaceChoosen = new HumanRace(javaDatabaseConnector);
//			frayCardDeck.addAll(humanRaceChoosen.addRaceCards());
//		}
        
        this.playerRaceName = raceName;
    	
    	this.connection = javaDatabaseConnector;
    }
    
    public ArrayList<FrayCard> getFrayCardDeck() {
		return frayCardDeck;
	}

	public void setFrayCardDeck(ArrayList<FrayCard> frayCardDeck) {
		this.frayCardDeck = frayCardDeck;
	}

	public Player(String name) {// Used for sign up and login
    	playerID = name;
    }

    /**
     * @return the playerID
     */
    public String getPlayerID()
    {
        return playerID;
    }

    /**
     * Ensure that the playerID is unique
     * @param givenID the playerID to set
     */
    public void setPlayerID(String givenID)
    {
        playerID = givenID;
    }
    
	public String getPlayerRaceName() {
		return playerRaceName;
	}

	public void setPlayerRaceName(String playerRaceName) {
		this.playerRaceName = playerRaceName;
	}

	public int getManaValue() {
		return manaValue;
	}

	public void setManaValue(int manaValue) {
		this.manaValue = manaValue;
	}

	public JavaDatabaseConnector getConnection() {
		return connection;
	}

	public void setConnection(JavaDatabaseConnector connection) {
		this.connection = connection;
	}

    /**
     * The method to be instantiated when you subclass the Player class
     * with your specific type of Player and filled in with logic to play your game.
     */
  //  public abstract void play();

}
