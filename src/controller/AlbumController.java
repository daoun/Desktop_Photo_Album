package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AlbumController implements Initializable{

	
	@FXML private Button albumAddBtn;
	@FXML private Text albumTitle;
	@FXML private GridPane albumList;
	
	public int numAlbum = 0;
	
	public void addAlbum(ActionEvent event){
		Text scenetitle = new Text("Welcome");
        scenetitle.setId("welcome-text");
        albumList.add(scenetitle, 0, 0, 2, 1);
		
		numAlbum++;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		albumTitle.setText(LoginController.username + "'s Album");
		
	}
	
	
	
}
