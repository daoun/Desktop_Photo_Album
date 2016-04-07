package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.User;
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

public class AdminController implements Initializable {
	
	public static final ObservableList<User> userlist = FXCollections.observableArrayList(); 
	
	@FXML private AnchorPane adminStage;
	@FXML private Button addUser;
	@FXML private Button deleteUser;
	@FXML private ListView<User> userLV;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO set songlist to song list view (the content changes when songlist is modified)
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
		
		//Can't have spaces in the username
		entered = entered.replaceAll("\\s+","");
		
		System.out.println("/"+entered+"/");
		
		if(entered.length() != 0){
			newUser = new User(entered);
			userlist.add(newUser);
			
			
		}
				
	}
	
	public void deleteUser(ActionEvent e){
		
	}

}
