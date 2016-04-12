package controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import model.Album;
import model.Photo;

public class DetailController implements Initializable {

	@FXML private Text albumName;
	@FXML private Text numPhoto;
	@FXML private Text oldestDate;
	@FXML private Text rangeDate;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		albumName.setText(AdminController.userlist.get(AlbumController.currentUser).getAlbum(AlbumController.selected).getName());
		numPhoto.setText(Integer.toString(AdminController.userlist.get(AlbumController.currentUser).getAlbum(AlbumController.selected).getPhotolistSize()));
		
		String od = findOldestDate();
		String nd = findNewestDate();
		
		if(od != null){
			oldestDate.setText(od);
			rangeDate.setText(od + " - " + nd);
		}
	}
	
	/**
	 * Finds the oldest dated photo in the album.
	 * @return date of the oldest photo
	 */
	public String findOldestDate(){
		
		Album currentAlbum = AdminController.userlist.get(AlbumController.currentUser).getAlbum(AlbumController.selected);
		Date oldestDate = null;
		
		for(Photo p : currentAlbum.getPhotolist()){
			if(oldestDate == null){
				oldestDate = p.getDate();
				continue;
			}
			if(p.getDate().before(oldestDate)){
				oldestDate = p.getDate();
			}
		}
		if(oldestDate == null){
			return null;
		}
		return oldestDate.toString();
	}
	
	/**
	 * Finds the newest dated photo in the album.
	 * @return date of the newest photo
	 */
	public String findNewestDate(){
		
		Album currentAlbum = AdminController.userlist.get(AlbumController.currentUser).getAlbum(AlbumController.selected);
		Date newestDate = null;
		
		for(Photo p : currentAlbum.getPhotolist()){
			if(newestDate == null){
				newestDate = p.getDate();
				continue;
			}
			if(p.getDate().after(newestDate)){
				newestDate = p.getDate();
			}
		}
		
		if(newestDate == null){
			return null;
		}
		return newestDate.toString();
	}
}
