package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.PhotoAlbum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	@FXML private Button loginBtn;
	@FXML private TextField usernameTF;
	@FXML private AnchorPane loginStage;

	public static Stage adminStage;
	public static Stage albumStage;
	
	public static String username = "";
	
	/**
	 * Called by the loginBtn.
	 * It logs in the inputed user if the user exists in the userlist.
	 * If input is admin, it opens the admin stage.
	 * If input is any other, it opens the list of albums stage.
	 */
	public void LoginBtn(){
		if(usernameTF.getText().equals("admin")){
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/admin.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
	        	
				Stage stage = new Stage();
	            Scene scene = new Scene(root);
				
	            stage.setScene(scene);  
	            stage.setResizable(false);  
	            stage.setTitle("Admin Page");
	            
	            adminStage = stage;
	            stage.show();
	            PhotoAlbum.loginStage.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else{
			username = usernameTF.getText();
			int i=0;
			for(i = 0; i < AdminController.userlist.size(); i++){
				if(AdminController.userlist.get(i).getName().equals(username)){
					AlbumController.currentUser = i;
					break;
				}
			}
			
			if(i == AdminController.userlist.size()){
				userDoesNotExist();
				return;
			}
            try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/album.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				Stage stage = new Stage();
	            Scene scene = new Scene(root);
				
	            stage.setScene(scene);  
	            stage.setResizable(false);  
	            stage.setTitle(usernameTF.getText());
	            
	            albumStage = stage;
	            stage.show();
	            PhotoAlbum.loginStage.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Helper function that alerts the user that inputed user does not exist.
	 */
	public void userDoesNotExist(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid User");
		alert.setHeaderText(null);
		alert.setContentText("This user does not exist!");

		alert.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
