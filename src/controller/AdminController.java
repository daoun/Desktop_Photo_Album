package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import model.User;

public class AdminController implements Initializable {
	
	public static ObservableList<User> userlist = FXCollections.observableArrayList(); 
	
	@FXML private AnchorPane adminStage;
	@FXML private Button addUser;
	@FXML private Button deleteUser;
	@FXML private ListView<User> userLV;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userLV.setItems(userlist);
	}
	
	public void addUser(ActionEvent e){
		
		User newUser;
		Dialog<String> dialog = new TextInputDialog("Enter a user name.");
		dialog.setHeaderText("Create User");
		dialog.setTitle("Photo Album");
		Optional<String> result = dialog.showAndWait();
		String entered = "";
		if (result.isPresent()) {
			    entered = result.get();
		}
		
		entered = entered.replaceAll("\\s+","");
		
		if(entered.length() != 0){
			newUser = new User(entered);
			userlist.add(newUser);
		}			
	}
	
	public void back(ActionEvent e){
		
		LoginController.adminStage.close();
		PhotoAlbum.loginStage.show();
		
	}
	
	public void deleteUser(ActionEvent e){
		int selected = userLV.getSelectionModel().getSelectedIndex();
		if(selected < 0){
			return;
		}
		userlist.remove(selected);
	}

}
