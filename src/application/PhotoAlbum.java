package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PhotoAlbum extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		
		AnchorPane root = (AnchorPane)loader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photo Album Log in");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
