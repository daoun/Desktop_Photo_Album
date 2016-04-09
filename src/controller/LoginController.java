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
	
	public void LoginBtn(ActionEvent e){
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
				// TODO Auto-generated catch block
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
			System.out.println(AlbumController.currentUser);
			if(i == AdminController.userlist.size()){
				userDoesNotExist();
				return;
			}
            try {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/album.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				//Stage currentStage = (Stage) loginStage.getScene().getWindow();
	        	
				Stage stage = new Stage();
	            Scene scene = new Scene(root);
				
	            stage.setScene(scene);  
	            stage.setResizable(false);  
	            stage.setTitle(usernameTF.getText());
	            
	            albumStage = stage;
	            stage.show();
	            PhotoAlbum.loginStage.close();
	            
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void userDoesNotExist(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid User");
		alert.setHeaderText(null);
		alert.setContentText("This user does not exist!");

		alert.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

}
