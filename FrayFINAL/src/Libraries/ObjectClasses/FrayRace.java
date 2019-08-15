package Libraries.ObjectClasses;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Libraries.Interfaces.Fray_Race;
import Libraries.JDBC.JavaDatabaseConnector;

public class FrayRace implements Fray_Race {
	
	private Blob raceImage;
	private String raceName = "";
	private String racePassive = "";
	private String raceDescription = "";
	private int raceHealth = 0;
	private JavaDatabaseConnector connection = getConnection();
	private Connection con;
	private ArrayList <FrayCard> frayRaceCardDeck = new ArrayList<>();
	
	public FrayRace(Blob raceImage, String raceName, String racePassive, String raceDescription, int raceHealth, JavaDatabaseConnector javaDatabaseConnector) {
		this.raceImage = raceImage;
		this.raceName = raceName;
		this.racePassive = racePassive;
		this.raceDescription = raceDescription;
		this.raceHealth = raceHealth;
		
		setConnection(javaDatabaseConnector);
		try {
			setCon(connection.getCon());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.frayRaceCardDeck = addRaceCards();
	}

	@Override
	public ArrayList<FrayCard> addRaceCards() {
		try {
			Connection con1 = getCon();
			String sql = "SELECT CardName, CardAttackPoints, CardHealthPoints, CardArmorPoints, CardType, EnergyCost, CardState, SpellValueHealth, SpellValueAttack, SpellValueCost, SpellValueArmor FROM sql3282320.FrayCardTable WHERE CardRace = '" + getRaceName() + "';";
			PreparedStatement ps = con1.prepareStatement(sql);
			ResultSet myRs = ps.executeQuery();
			
			while(myRs.next()) {
				String cardName = myRs.getString("CardName");
				int cardAttackPoints = myRs.getInt("CardAttackPoints");
				int cardHealthPoints = myRs.getInt("CardHealthPoints");
				int cardArmorPoints = myRs.getInt("CardArmorPoints");
				String cardType = myRs.getString("CardType");
				int energyCost = myRs.getInt("EnergyCost");
				String cardState = myRs.getString("CardState");
				int spellValueHealth = myRs.getInt("SpellValueHealth");
				int spellValueAttack = myRs.getInt("SpellValueAttack");
				int spellValueCost = myRs.getInt("SpellValueCost");
				int spellValueArmor = myRs.getInt("SpellValueArmor");
				
				frayRaceCardDeck.add(new FrayCard(null, cardName, cardAttackPoints, cardArmorPoints, cardHealthPoints, "", cardType, energyCost, cardState, spellValueHealth, spellValueAttack, spellValueCost, spellValueArmor));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return frayRaceCardDeck;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ArrayList<FrayCard> getFrayRaceCardDeck() {
		return frayRaceCardDeck;
	}

	public void setFrayRaceCardDeck(ArrayList<FrayCard> frayRaceCardDeck) {
		this.frayRaceCardDeck = frayRaceCardDeck;
	}

	public String getRaceName() {
		return raceName;
	}

	public String getRacePassive() {
		return racePassive;
	}

	public int getRaceHealth() {
		return raceHealth;
	}

	public void setRaceHealth(int raceHealth) {
		this.raceHealth = raceHealth;
	}
	
	public JavaDatabaseConnector getConnection() {
		return connection;
	}

	public void setConnection(JavaDatabaseConnector passconnection) {
		connection = passconnection;	
	}

	@Override
	public String toString() {
		return "FrayRace [raceName=" + raceName + ", racePassive=" + racePassive + ", raceHealth=" + raceHealth
				+ ", connection=" + connection + ", con=" + con + ", frayRaceCardDeck=" + frayRaceCardDeck + "]";
	}

	public String getRaceDescription() {
		return raceDescription;
	}

	public void setRaceDescription(String raceDescription) {
		this.raceDescription = raceDescription;
	}

	public Blob getRaceImage() {
		return raceImage;
	}

	public void setRaceImage(Blob raceImage) {
		this.raceImage = raceImage;
	}

}