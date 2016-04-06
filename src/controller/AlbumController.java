package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AlbumController implements Initializable{

	
	@FXML private Button albumAddBtn;
	@FXML private Text albumTitle;
	@FXML private GridPane albumList;
	
	public int numAlbum = 0;
	
	public void addAlbum(ActionEvent event){
		int row = numAlbum/3;
        int col = numAlbum%3;
        BorderPane album = new BorderPane();
        
        ImageView image = new ImageView("/view/no_photo.png");
        image.setFitHeight(100);
        image.setFitWidth(100);
        
        Text albumtitle = new Text("Album 1");
        AnchorPane.setBottomAnchor(albumtitle, 10.0);
        
        album.setCenter(image);
        album.setBottom(albumtitle);
        BorderPane.setAlignment(albumtitle, Pos.CENTER);
        
        albumList.add(album, col, row);
		
		numAlbum++;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		albumTitle.setText(LoginController.username + "'s Album");
		
	}
	
	
	
}
