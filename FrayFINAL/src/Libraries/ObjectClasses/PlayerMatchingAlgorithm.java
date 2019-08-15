package Libraries.ObjectClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import GUIs.ControllerClasses.MainPageCharacterScreenControllerVersion2;
import GUIs.ControllerClasses.PlayingFieldControllerClass;

import java.sql.DatabaseMetaData;

import Libraries.JDBC.JavaDatabaseConnector;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

public class PlayerMatchingAlgorithm {
	
	private String playerUsername;
	private String opponentUsername;
	private String playerRace;
	private JavaDatabaseConnector connection;
	private Connection con;
	private boolean findingMatch;
	boolean switchToPlayingField = false;
	boolean databaseMadeBefore;
	private Timer timerObjUpdatingGame;
	private ArrayList <FrayCard> frayCardDeck = new ArrayList<FrayCard>();
	
	private AnchorPane anchorPane;
	private StackPane stackPane;
	
	public PlayerMatchingAlgorithm(String playerUsername, String playerRace, JavaDatabaseConnector connection, boolean findingMatch, AnchorPane anchorPane, StackPane stackPane) {	
			this.playerUsername = playerUsername;
			this.playerRace = playerRace;
			this.connection = connection;
			this.findingMatch = findingMatch;
			setAnchorPane(anchorPane);
			this.anchorPane=anchorPane;
			setStackPane(stackPane);
			this.stackPane = stackPane;
	}
	
	public boolean searchForPlayersMethod() {
		try {
			setCon(getConnection().getCon());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(isFindingMatch() == true) {
			timerObjUpdatingGame = new Timer(true);
			timerObjUpdatingGame.schedule(new TimerTask() {

				@Override
				public void run() {
					FrayCardDeckGetter frayCardDeckget = new FrayCardDeckGetter(getConnection());
					frayCardDeck.addAll(frayCardDeckget.addRaceCards(getPlayerRace()));

					try {
						boolean playerFound = false;
						java.sql.Statement myStat4;
						myStat4 = con.createStatement();
						String sql = "UPDATE " + getConnection().getDatabaseUsername() + ".UserDataTable SET LookingForAGame = 'Yes', SelectedRace = '" + getPlayerRace() + "' WHERE Username = '" + getPlayerUsername() + "'";
						myStat4.executeUpdate(sql);
						
						String query = "SELECT * FROM " + getConnection().getDatabaseUsername() + ".UserDataTable";
						Statement myStat = con.createStatement();
						ResultSet myRs = myStat.executeQuery(query);
						
						while((myRs.next()) || (playerFound = false) && (findingMatch = true)) {
							String lookingForAGame = myRs.getString("LookingForAGame");
							String inGameStatus = myRs.getString("InGame");
							String opponentUsername = myRs.getString("Username");
							String onlineStatus = myRs.getString("OnlineStatus");
							
							if((lookingForAGame.equalsIgnoreCase("Yes")) && (inGameStatus.equalsIgnoreCase("No")) && (onlineStatus.equalsIgnoreCase("Online")) && (!opponentUsername.equalsIgnoreCase(getPlayerUsername()))) {
								playerFound = true;
								
								Statement myStat1 = con.createStatement();
								Statement myStat2 = con.createStatement();		
								String query1 = "UPDATE " + getConnection().getDatabaseUsername() + ".UserDataTable SET InGame = 'Yes', LookingForAGame = 'No' WHERE Username = '" + getOpponentUsername() + "'";
								String query2 = "UPDATE " + getConnection().getDatabaseUsername() + ".UserDataTable SET InGame = 'Yes', LookingForAGame = 'No' WHERE Username = '" + getPlayerUsername() + "'";
								myStat1.executeUpdate(query1);
								myStat2.executeUpdate(query2);
								
								setOpponentUsername(opponentUsername);
								
			
										DatabaseMetaData dbm1 = con.getMetaData();
								ResultSet tables1 = dbm1.getTables(null, null, "" + getOpponentUsername() + "_VS_" + getPlayerUsername() + "_FrayCardGame", null);
								//java.sql.SQLSyntaxErrorException
								if(tables1.next()) {
									System.out.println(
									        "   "+tables1.getString("TABLE_CAT") 
									       + ", "+tables1.getString("TABLE_SCHEM")
									       + ", "+tables1.getString("TABLE_NAME")
									       + ", "+tables1.getString("TABLE_TYPE")
									       + ", "+tables1.getString("REMARKS")); 
									timerObjUpdatingGame.cancel();
									timerObjUpdatingGame.purge();
									MainPageCharacterScreenControllerVersion2 w = new MainPageCharacterScreenControllerVersion2();
									setSwitchToPlayingField(true);
									MainPageCharacterScreenControllerVersion2.setSwitchToPlayingField1(true);
								}// Table exists
								else {
									System.out.println("I got here!2");
									if(isDatabaseMadeBefore() == false) {
										createDatabase();
										setSwitchToPlayingField(true);
										MainPageCharacterScreenControllerVersion2 w = new MainPageCharacterScreenControllerVersion2();
										MainPageCharacterScreenControllerVersion2.setSwitchToPlayingField1(true);
									}
									else {
										timerObjUpdatingGame.cancel();
										timerObjUpdatingGame.purge();
										setSwitchToPlayingField(true);
										MainPageCharacterScreenControllerVersion2 w = new MainPageCharacterScreenControllerVersion2();
										MainPageCharacterScreenControllerVersion2.setSwitchToPlayingField1(true);
									}
									
									break;
								}//Table does not exist	
								
								DatabaseMetaData dbm = con.getMetaData();
								ResultSet tables = dbm.getTables(null, null, "" + getOpponentUsername() + "_VS_" + getPlayerUsername() + "_FrayCardGame", null);
								
								if(tables.next()) {
									System.out.println(
									        "   "+tables.getString("TABLE_CAT") 
									       + ", "+tables.getString("TABLE_SCHEM")
									       + ", "+tables.getString("TABLE_NAME")
									       + ", "+tables.getString("TABLE_TYPE")
									       + ", "+tables.getString("REMARKS")); 
									timerObjUpdatingGame.cancel();
									timerObjUpdatingGame.purge();
									setSwitchToPlayingField(true);
									MainPageCharacterScreenControllerVersion2 w = new MainPageCharacterScreenControllerVersion2();
									MainPageCharacterScreenControllerVersion2.setSwitchToPlayingField1(true);
								}// Table exists
								else {
									System.out.println("I got here!");
									if(isDatabaseMadeBefore() == false) {
										createDatabase();
										setSwitchToPlayingField(true);
										MainPageCharacterScreenControllerVersion2 w = new MainPageCharacterScreenControllerVersion2();
										MainPageCharacterScreenControllerVersion2.setSwitchToPlayingField1(true);
									}
									else {
										timerObjUpdatingGame.cancel();
										timerObjUpdatingGame.purge();
										setSwitchToPlayingField(true);
										MainPageCharacterScreenControllerVersion2 w = new MainPageCharacterScreenControllerVersion2();
										MainPageCharacterScreenControllerVersion2.setSwitchToPlayingField1(true);
									}
									
									break;
								}//Table does not exist
								
					
							}
							
							else {
								playerFound = false;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}, 0, 1000);
		}
		/*else if(isFindingMatch() == false) {
			timerObjUpdatingGame.cancel();
			timerObjUpdatingGame.purge();
		}*/

		System.out.println(isSwitchToPlayingField());
		return isSwitchToPlayingField();
	}
	
	public void createDatabase() {
		try {
			String sql = "CREATE TABLE " + getPlayerUsername() + "_VS_" + getOpponentUsername() + "_FrayCardGame LIKE PlayerOne_VS_PlayerTwo_FrayCardGame;";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
		
			String sql11 = "INSERT INTO sql3282320." + getPlayerUsername() + "_VS_" + getOpponentUsername() + "_FrayCardGame(PlayerName,CardPositionInArrayList, CardHealthPoints) VALUES(?,?,?);";
			PreparedStatement ps11 = con.prepareStatement(sql11);
			ps11.setString(1, getPlayerUsername());
			ps11.setString(2, String.valueOf(0));
			ps11.setString(3, String.valueOf(30));
			ps11.execute();
		
			for(int i = 1; i < 9; i++) {
				String sql1 = "INSERT INTO sql3282320." + getPlayerUsername() + "_VS_" + getOpponentUsername() + "_FrayCardGame(PlayerName,CardPositionInArrayList) VALUES(?,?);";
				PreparedStatement ps1 = con.prepareStatement(sql1);
				ps1.setString(1, getPlayerUsername());
				ps1.setString(2, String.valueOf(i));
				ps1.execute();
			}
		
			String sql22 = "INSERT INTO sql3282320." + getPlayerUsername() + "_VS_" + getOpponentUsername() + "_FrayCardGame(PlayerName,CardPositionInArrayList, CardHealthPoints) VALUES(?,?,?);";
			PreparedStatement ps22 = con.prepareStatement(sql22);
			ps22.setString(1, getOpponentUsername());
			ps22.setString(2, String.valueOf(10));
			ps22.setString(3, String.valueOf(30));
			ps22.execute();
			
			for(int i = 1; i < 9; i++) {
				String sql2 = "INSERT INTO sql3282320." + getPlayerUsername() + "_VS_" + getOpponentUsername() + "_FrayCardGame(PlayerName,CardPositionInArrayList) VALUES(?,?);";
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setString(1, getOpponentUsername());
				ps2.setString(2, String.valueOf(i));
				ps2.execute();
			}
			
			switchToPlayingField = true;
		} catch(SQLException e) {e.printStackTrace();}
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

	public String getPlayerUsername() {
		return playerUsername;
	}

	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}

	public String getPlayerRace() {
		return playerRace;
	}

	public void setPlayerRace(String playerRace) {
		this.playerRace = playerRace;
	}

	public boolean isFindingMatch() {
		return findingMatch;
	}

	public void setFindingMatch(boolean findingMatch) {
		this.findingMatch = findingMatch;
	}

	public Timer getTimerObjUpdatingGame() {
		return timerObjUpdatingGame;
	}

	public void setTimerObjUpdatingGame(Timer timerObjUpdatingGame) {
		this.timerObjUpdatingGame = timerObjUpdatingGame;
	}

	/**
	 * @return the opponentUsername
	 */
	public String getOpponentUsername() {
		return opponentUsername;
	}

	/**
	 * @param opponentUsername the opponentUsername to set
	 */
	public void setOpponentUsername(String opponentUsername) {
		this.opponentUsername = opponentUsername;
	}

	public boolean isDatabaseMadeBefore() {
		return databaseMadeBefore;
	}

	public void setDatabaseMadeBefore(boolean databaseMadeBefore) {
		this.databaseMadeBefore = databaseMadeBefore;
	}

	public boolean isSwitchToPlayingField() {
		return switchToPlayingField;
	}

	public void setSwitchToPlayingField(boolean switchToPlayingField) {
		this.switchToPlayingField = switchToPlayingField;
	}

	public AnchorPane getAnchorPane() {
		return anchorPane;
	}

	public void setAnchorPane(AnchorPane anchorPane) {
		this.anchorPane = anchorPane;
	}

	public StackPane getStackPane() {
		return stackPane;
	}

	public void setStackPane(StackPane stackPane) {
		this.stackPane = stackPane;
	}
	
}
