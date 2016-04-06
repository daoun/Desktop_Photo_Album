package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	@FXML private Button loginBtn;
	@FXML private TextField usernameTF;
	
	@FXML private AnchorPane loginStage;
	
	public void LoginBtn(ActionEvent e){
		if(usernameTF.getText().equals("admin")){
			
		}
		else{
			
			try {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/album.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				//Stage currentStage = (Stage) loginStage.getScene().getWindow();
	        	
				Stage stage = new Stage();
	            Scene scene = new Scene(root);
				
	            stage.setScene(scene);  
	            stage.setResizable(false);  
	            stage.setTitle(usernameTF.getText()+"'s Album");
	            
	            //currentStage.close();
	            stage.show();
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

}
