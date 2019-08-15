package Libraries.ObjectClasses;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Libraries.JDBC.JavaDatabaseConnector;

public class FrayRaceGetter {
	private ArrayList <FrayRace> frayRaces = new ArrayList<>();
	private JavaDatabaseConnector connection = getConnection();
	private Connection con;
	
	public FrayRaceGetter(JavaDatabaseConnector passConnection) {
		setConnection(passConnection);
	}
	
	public ArrayList<FrayRace> frayRaceGetterMethod() {
		try {
			Connection con = getConnection().getCon();
			String sql = "SELECT * FROM sql3282320.FrayRaceTable";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet myRs = ps.executeQuery();
		
			while(myRs.next()) {
				Blob raceImage = myRs.getBlob("RaceImage");
				String raceName = myRs.getString("RaceName");
				String racePassive = myRs.getString("RacePassive");
				String raceDescription = myRs.getString("RaceDescription");
				int raceHealth = myRs.getInt("RaceHealth");
				frayRaces.add(new FrayRace(raceImage, raceName, racePassive, raceDescription, raceHealth, getConnection()));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return frayRaces;
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
