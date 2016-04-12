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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.User;

/**
 * This controller controls the Admin stage
 * @author Capki Kim, Daoun Oh
 *
 */
public class AdminController implements Initializable {
	/**
	 * Stores list of users.
	 */
	public static ObservableList<User> userlist = FXCollections.observableArrayList(); 
	
	@FXML private AnchorPane adminStage;
	@FXML private Button addUser;
	@FXML private Button deleteUser;
	@FXML private ListView<User> userLV;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userLV.setItems(userlist);
	}
	
	/**
	 * Called by the addUser button.
	 * Adds the prompted user to the list of users.
	 * Checks for duplicate user names.
	 */
	public void addUser(){
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
		
		if(result.isPresent()){
			if(!duplicates(result.get())){
				if(entered.length() != 0){
					newUser = new User(entered);
					userlist.add(newUser);
				}
				else{
					noNameAlert();
				}
			}
		}
	}
	
	
	
	/**
	 * Checks for duplicate user name stored in userlist.
	 * 
	 * @param name the name of the new user
	 * @return true if there is a duplicate user,
	 * 			false otherwise
	 */
	public boolean duplicates(String name){
		for(int i = 0; i < userlist.size(); i++){
			if(userlist.get(i).getName().equals(name)){
				warnDuplicate();
				return true;
			}
			else if(userlist.get(i).getName().equals("admin")){
				checkAdmin();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Alerts the user if there is a duplicate user name.
	 */
	public void warnDuplicate(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Duplicate Warning");
		alert.setHeaderText("Invalid");
		alert.setContentText("There exists a user with the same name.");

		alert.showAndWait();
	}
	
	/**
	 * Alerts the user if the new user name was admin.
	 */
	public void checkAdmin(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Invalid Name Warning");
		alert.setHeaderText("Invalid");
		alert.setContentText("You cannot set ADMIN as your user name.");

		alert.showAndWait();
	}
	
	/**
	 * Alerts the user if there is empty input for name.
	 */
	public void noNameAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("No Name Warning");
		alert.setHeaderText("Invalid");
		alert.setContentText("You need to enter at least one character.");

		alert.showAndWait();
	}
	
	/**
	 * Called by the back button.
	 * Brings user back to the log in stage.
	 */
	public void back(){
		LoginController.adminStage.close();
		PhotoAlbum.loginStage.show();
	}
	
	/**
	 * Called by the delete button.
	 * Deletes the selected user.
	 */
	public void deleteUser(){
		int selected = userLV.getSelectionModel().getSelectedIndex();
		if(selected < 0){
			return;
		}
		userlist.remove(selected);
	}
}
