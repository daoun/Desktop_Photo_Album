package controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Photo;

/**
 * This controller controls the Photo stage (for Thumbnail)
 * @author Capki Kim, Daoun Oh
 *
 */
public class PhotoController implements Initializable{
	
	@FXML private Text captionT;
	@FXML private Text dateT;
	@FXML private Text tagT;
	@FXML private ImageView photoIV;
	@FXML private AnchorPane photoAP;
	@FXML private Button beforeBtn;
	@FXML private Button nextBtn;
	
	/**
	 * Stores index of the current photo being shown in the photolist 
	 */
	public static int currentPhoto;
	
	/**
	 * Called by the before Button.
	 * Shows detail of the photo before the current photo in the photolist.
	 */
	public void before(){
		if(currentPhoto == 0){
			return;
		}
		currentPhoto--;
		
		URL loc =null;
		ResourceBundle res=null;
		
		initialize(loc,res);
	}
	
	/**
	 * Called by the next Button.
	 * Shows detail of the photo after the current photo in the photolist.
	 */
	public void next(){
		if(currentPhoto == AdminController.userlist.get(AlbumController.currentUser).
				getAlbum(ThumbnailController.currentAlbum).getPhotolistSize() - 1){
			return;
		}
		currentPhoto++;
		
		URL loc =null;
		ResourceBundle res=null;
		
		initialize(loc,res);
	}
	
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
	}
}
