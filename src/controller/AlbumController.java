package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AlbumController implements Initializable{

	
	@FXML private Button albumAddBtn;
	@FXML private Text albumTitle;
	@FXML private GridPane albumListGP;
	@FXML private ScrollPane albumListSP;
	
	@FXML private ComboBox<?> albumOption;
	
	public int numAlbum = 0;
	
	public void addAlbum(ActionEvent event){
		
		String name = createAlbum();
		if(name == null){
			return;
		}
		
		int row = numAlbum/3;
        int col = numAlbum%3;
        BorderPane album = new BorderPane();
        
        ImageView image = new ImageView("/view/no_photo.png");
        image.setFitHeight(100);
        image.setFitWidth(100);
        
        Text albumtitle = new Text(name);
        AnchorPane.setBottomAnchor(albumtitle, 10.0);
        
        album.setCenter(image);
        album.setBottom(albumtitle);
        BorderPane.setAlignment(albumtitle, Pos.CENTER);
        
        albumListGP.add(album, col, row);
		
		numAlbum++;
	}
	
	public String createAlbum(){
		Dialog<String> dialog = new TextInputDialog("Enter a user name.");
		dialog.setHeaderText("Create Album");
		dialog.setTitle("Photo Album");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			//System.out.println(result);
			return result.get();
		}
		else{
			return null;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		albumTitle.setText(LoginController.username + "'s Album");
		
	}
	
	
	
}
