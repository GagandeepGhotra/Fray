package GUIs.ControllerClasses;

import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.jfoenix.controls.JFXButton;

import Libraries.JDBC.JavaDatabaseConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
//import ObjectClasses.Player;
import javafx.stage.WindowEvent;

public class MainPageController {
	
	@FXML
	private JFXButton exit;
	
	@FXML
	private AnchorPane switchPane;
	
	private JavaDatabaseConnector connection = getConnection();
	private Connection con;
	private String playerUsername;
	
	private double x, y, width, height;
	
	@FXML
	void exit(ActionEvent event) {
		try {
  			java.sql.Statement myStat;
			myStat = con.createStatement();
			String sql = "UPDATE " + getConnection().getDatabaseUsername() + ".UserDataTable SET OnlineStatus = 'Offline', InGame = 'No', LookingForAGame = 'No' WHERE Username = '" + getPlayerUsername() + "'";
			myStat.executeUpdate(sql);
			getConnection().getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platform.exit();
	}

	public void passConnectionAndUsername(String playerUsername, JavaDatabaseConnector passConnection, double x, double y, double width, double height) {
		setPlayerUsername(playerUsername);
		setConnection(passConnection);
		try {
			setCon(connection.getCon());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	
	}
	
	public void passConnectionAndUsername2(String playerUsername, JavaDatabaseConnector passConnection) {
		setPlayerUsername(playerUsername);
		setConnection(passConnection);
		try {
			setCon(connection.getCon());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void playGame(ActionEvent event) { // Leads the user to the race selection screen.	
		try {
			FXMLLoader root = new FXMLLoader(getClass().getResource(("/GUIs/FXML/MainPage-CharacterSelectScreenVersion2.fxml")));
			Parent root1 = (Parent) root.load();
			MainPageCharacterScreenControllerVersion2 sec = root.getController();
			
			Stage w = (Stage) ((Node)event.getTarget()).getScene().getWindow();

			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();

			w.setX(bounds.getMinX());
			w.setY(bounds.getMinY());
			w.setWidth(bounds.getWidth());
			w.setHeight(bounds.getHeight() + 40);//;/////////
			int vgapNumber = 0;
			if(w.getHeight() == 992) {
				vgapNumber = 992 - 792;
			}
			
			else if (w.getHeight() == 1050) {
				vgapNumber = 1050 - 892;
			}
			
			else if (w.getHeight() == 1200) {
				vgapNumber = 1200 - 1200;
			}
			
			else if (w.getHeight() == 1024) {
				vgapNumber = 1024 - 792;
			}
			
			else if (w.getHeight() == 900) {
				vgapNumber = 900 - 590;
			}
			
			System.out.println("2 - " + w.getX() + " " + w.getY() + " " + w.getWidth() + " " + w.getHeight());
			w.setScene(new Scene(root1));
			sec.passConnectionAndUsername(getPlayerUsername(), getConnection(), vgapNumber);
			System.out.println(w);
			w.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent event) {
			       //event.consume();
			    	try {
			  			java.sql.Statement myStat;
						myStat = con.createStatement();
						String sql = "UPDATE " + getConnection().getDatabaseUsername() + ".UserDataTable SET OnlineStatus = 'Offline', InGame = 'No', LookingForAGame = 'No' WHERE Username = '" + getPlayerUsername() + "'";
						myStat.executeUpdate(sql);
						getConnection().getCon().close();
						System.out.println("I ran");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Platform.exit();
			    }
			});
			w.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
}