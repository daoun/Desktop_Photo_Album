package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Album;
import model.Photo;

public class MoveController implements Initializable{
	@FXML private Button moveBtn;
	@FXML private Button cancelBtn;
	@FXML private ListView<String> listLV;
	
	/**
	 * Stores the list of the names of the albums in the userlist
	 */
	public final ObservableList<String> list = FXCollections.observableArrayList(); 
	
	public void move(){
		int selected = listLV.getSelectionModel().getSelectedIndex();
		
		Photo photo = AdminController.userlist.get(AlbumController.currentUser).
			getAlbum(ThumbnailController.currentAlbum).getPhoto(ThumbnailController.selected);
		
		AdminController.userlist.get(AlbumController.currentUser).
			getAlbum(ThumbnailController.currentAlbum).remove(ThumbnailController.selected);
		
		AdminController.userlist.get(AlbumController.currentUser).
			getAlbum(selected).addPhoto(photo);
		
		cancel();
	}
	
	/**
	 * Called by the cancel Button.
	 * Quits the move job and returns to the thumbnail stage.
	 */
	public void cancel(){		
		ThumbnailController.moveStage.close();
		AlbumController.thumbnailStage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list.remove(0, list.size()-1);
		
		List<Album> albums = AdminController.userlist.get(AlbumController.currentUser).getAlbumlist();
		for(int i = 0; i<albums.size(); i++){
			list.add(albums.get(i).getName());
		}
		listLV.setItems(list);
	}
}
