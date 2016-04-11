package controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Photo;

public class PhotoController implements Initializable{
	
	public static int currentPhoto;
	
	@FXML private Text captionT;
	@FXML private Text dateT;
	@FXML private TextFlow tagTF;
	@FXML private ImageView photoIV;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Photo photo = AdminController.userlist.get(AlbumController.currentUser).
				getAlbum(ThumbnailController.currentAlbum).getPhoto(currentPhoto);
		
		Image image = new Image(photo.getURL());
		
		photoIV.setImage(image);
		
		captionT.setText(photo.getCaption());
		
		Date date = photo.getDate();
		if(date != null){
			dateT.setText(date.toString());
		}
		
		
		
		String tag = "";
		if(photo.getTaglistSize() > 0){
			int i = 0;
			for(i = 0; i < photo.getTaglistSize()-1; i++){
				tag = tag + "#" + photo.getTag(i) + ", ";
			}
			tag = tag + "#" + photo.getTag(i);
			Text t = new Text(tag);
			t.setFill(Color.WHITE);
		    t.setFont(Font.font("System", FontWeight.NORMAL, 20));
		    tagTF = new TextFlow(t);
		
		}
		
		
		
	}
	
	
	
}
