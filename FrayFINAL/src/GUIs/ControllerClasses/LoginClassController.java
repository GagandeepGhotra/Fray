package GUIs.ControllerClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginClassController  {

	@FXML
	private ImageView close;
	
	@FXML
	private Stage stage;
	
	@FXML
	private AnchorPane FullLoginScreen;
	
	@FXML
	private ScrollPane scrollPane;

	private String loginConnect;
	
	public void myFunction(String loginConnect) {
		setConnection(loginConnect);
		try {
			FXMLLoader root = new FXMLLoader(getClass().getResource("/GUIs/FXML/Login.fxml"));
			Parent root1 = (Parent) root.load();
			LoginController sec = root.getController();
			sec.myFunction(getConnection());
			scrollPane.setContent(root1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getConnection() {
		return loginConnect;
	}

	public void setConnection(String passconnection) {
		loginConnect = passconnection;	
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
}