package controller;

import java.util.Optional;

import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

public class AdminController {
	
	public static final ObservableList<User> userlist = FXCollections.observableArrayList();        

	
	@FXML private AnchorPane adminStage;
	@FXML private Button addUser;
	@FXML private Button deleteUser;
	
	public void addUser(ActionEvent e){
		
		Dialog<String> dialog = new TextInputDialog("Enter a user name.");
		dialog.setHeaderText("Create User");
		dialog.setTitle("Photo Album");
		Optional<String> result = dialog.showAndWait();
		String entered = "";
		if (result.isPresent()) {
			    entered = result.get();
		}
		
	}
	
	public void deleteUser(ActionEvent e){
		
	}

}
