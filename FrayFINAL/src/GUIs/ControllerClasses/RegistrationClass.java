package GUIs.ControllerClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Libraries.JDBC.JavaDatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RegistrationClass {
	
	@FXML
	private AnchorPane pane;

	@FXML
	private JFXTextField uTF, ppTF; // User name, Password
	
	@FXML
	private JFXButton continueToNextStepBtn;
	
	@FXML
	private Pane paneLogin;
	
	public static Stage stage = null;
	
	private JavaDatabaseConnector connection = getConnection();
	private Connection con;
	
	@FXML
	private void backToLoginScreen() {
		try {
			FXMLLoader root =  new FXMLLoader(getClass().getResource("/GUIs/FXML/Login.fxml"));
			Parent root1 = (Parent) root.load(); 
			LoginController sec = root.getController();
			sec.myFunction(getConnection());
			getPaneLogin().getChildren().clear();
			getPaneLogin().getChildren().setAll(root1);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public AnchorPane getPane() {
		return pane;
	}

	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}

	@FXML
    private void mousePressed(ActionEvent event) throws IOException {
    	boolean error = false;
    	String errors = "";
    	
    	if ((uTF.getText().equals(null) || uTF.getText().trim().isEmpty())) {
    		error = true;
    		errors += " User Name,";
    	}
    
    	if ((ppTF.getText().equals(null) || ppTF.getText().trim().isEmpty())) {
    		error = true;
    		errors += " Password,";
    	}
    	
    	else {
    		registerCredintials();
    		JOptionPane.showMessageDialog(null, "Sign up successful!");
    		FXMLLoader root = new FXMLLoader(getClass().getResource("/GUIs/FXML/MainPage.fxml"));
			Parent root1 = (Parent) root.load();
		
			MainPageController sec = root.getController();
			Stage w = (Stage) ((Node)event.getTarget()).getScene().getWindow();
			//w.setMaximized(true);
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();

			w.setX(bounds.getMinX());
			w.setY(bounds.getMinY());
			w.setWidth(bounds.getWidth());
			w.setHeight(bounds.getHeight() + 40);
		System.out.println(w.getX() + " " + w.getY() + " " + w.getWidth() + " " + w.getHeight());
			sec.passConnectionAndUsername(uTF.getText(), getConnection(), w.getX(), w.getY(), w.getWidth(), w.getHeight());
			
			w.setScene(new Scene(root1));

			w.show();
    	}
    	
    	if (error == true) {
    		Alert fail = new Alert(AlertType.ERROR);
	        fail.setHeaderText("Failure");
	        fail.setContentText("Empty Fields: " + errors);
	        fail.showAndWait();
    	}

    }
    
	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		RegistrationClass.stage = stage;
	}

	private void registerCredintials() throws IOException {
			try {
				
				String sql = "INSERT INTO " + getConnection().getDatabaseName() + ".UserDataTable " 
				+ "(Username, Password) VALUES(?, ?);"; 
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, uTF.getText());
				ps.setString(2, String.valueOf(ppTF.getText()));
				ps.execute();
				
				 /*
       			 * This part of code updates the online status on the server.
       			 */
       			java.sql.Statement myStat;
    			myStat = con.createStatement();
    		    String sql1 = "UPDATE " + getConnection().getDatabaseUsername() + ".UserDataTable SET OnlineStatus = 'Online' WHERE Username = '" + uTF.getText() + "'";
       			myStat.executeUpdate(sql1);

			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Connection Error! Please check your internet connection!", "Connection Error", 0);
				e1.printStackTrace();
		}
	}
	
	public void myFunction(Pane pane2, JavaDatabaseConnector newJDBC) {
		setPaneLogin(pane2);
		setConnection(newJDBC);
		try {
			setCon(connection.getCon());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public JavaDatabaseConnector getConnection() {
		return connection;
	}

	public void setConnection(JavaDatabaseConnector passconnection) {
		connection = passconnection;	
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
	
	public Pane getPaneLogin() {
		return paneLogin;
	}
	
	public void setPaneLogin(Pane paneL) {
		this.paneLogin = paneL;
	}
}
