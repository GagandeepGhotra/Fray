package GUIs.ControllerClasses;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	
	@FXML
	private Stage passStage;

	public Stage getPassStage() {
		return passStage;
	}

	public void setPassStage(Stage passStage) {
		this.passStage = passStage;
	}
	
	private double xOffset = 0;
	private double yOffset = 0;
	
	@FXML
	private VBox vbox = new VBox();
	
	@Override
	public void start(Stage primaryStage) throws IllegalArgumentException, IOException {
			
			FXMLLoader root = new FXMLLoader(getClass().getResource("/GUIs/FXML/LoginClass.fxml"));
			Parent root1 = (Parent) root.load();
			
			LoginClassController sec = ((FXMLLoader) root).getController();
			String loginConnect = "Login.php?Username=z&Password=c";
			sec.myFunction(loginConnect);
			
			Scene s = new Scene(root1, Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setTitle("");	
			primaryStage.setScene(s);
			primaryStage.setMaximized(true);
			root1.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
			
			root1.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                primaryStage.setX(event.getScreenX() - xOffset);
	                primaryStage.setY(event.getScreenY() - yOffset);
	            }
	        });
			
			primaryStage.show();
	}
	
	public static void main(String args[]) {launch(args);}

}
