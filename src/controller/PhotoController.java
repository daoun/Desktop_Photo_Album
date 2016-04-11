package controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Photo;

public class PhotoController implements Initializable{
	
	public static int currentPhoto;
	
	@FXML private Text captionT;
	@FXML private Text dateT;
	@FXML private Text tagT;
	@FXML private ImageView photoIV;
	@FXML private AnchorPane photoAP;
	
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
			tag = TagController.putTogetherTags(photo.getTaglist());
		    tagT.setText(tag);
		
		}
		
		photoIV.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode()==KeyCode.RIGHT) { // don't use toString here!!!
					System.out.println("right");
				} else if (event.getCode() == KeyCode.LEFT) {
					System.out.println("left");
				}
			}
		});

		photoIV.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
					System.out.println("both");
				}
			}
		});
		
		
		/*
		 * photoAP.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
            	if(ke.getCode().equals(KeyCode.LEFT)){
            		System.out.println("left");
            	}
            	else if(ke.getCode().equals(KeyCode.RIGHT)){
            		System.out.println("right");
            	}
            	
            }
        });
		 */
		
	}
	
	
	
}
