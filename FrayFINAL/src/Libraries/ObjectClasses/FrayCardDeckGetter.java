package Libraries.ObjectClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Libraries.JDBC.JavaDatabaseConnector;
import Libraries.ObjectClasses.FrayCard;

public class FrayCardDeckGetter {
	
	private ArrayList <FrayCard> frayCardDeck = new ArrayList<FrayCard>();
	private JavaDatabaseConnector connection = getConnection();
	private Connection con;
	public FrayCardDeckGetter (JavaDatabaseConnector connection) {
		setConnection(connection);
		try {
			setCon(connection.getCon());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<FrayCard> addRaceCards(String race) {
		try {		
			String sql = "SELECT CardName, CardAttackPoints, CardHealthPoints, CardArmorPoints, CardType, EnergyCost, CardState, SpellValueHealth, SpellValueAttack, SpellValueCost, SpellValueArmor FROM sql3282320.FrayCardTable WHERE CardRace = '" + race + "';";
			PreparedStatement ps = getCon().prepareStatement(sql);
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
				
				frayCardDeck.add(new FrayCard(null, cardName, cardAttackPoints, cardArmorPoints, cardHealthPoints, "", cardType, energyCost, cardState, spellValueHealth, spellValueAttack, spellValueCost, spellValueArmor));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return frayCardDeck;
	}
	
	public JavaDatabaseConnector getConnection() {
		return connection;
	}

	public void setConnection(JavaDatabaseConnector connection) {
		this.connection = connection;
	}
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
	

}
