package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class TagController implements Initializable{

	@FXML private TextArea tagTA;
	
	
	public List<String> splitTags(String s){
		List<String> list = new ArrayList<String>();
		
		for (String retval: s.split("#")){
			list.add(retval);
		}

		return list;
	}
	
	public String putTogetherTags(List<String> list){
		String all = "";
		for(int i = 0; i <list.size(); i++){
			all = all + "#" + list.get(i);
		}
		return all;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		List<String> list = AdminController.userlist.get(AlbumController.currentUser).
				getAlbum(ThumbnailController.currentAlbum).getPhoto(ThumbnailController.selected).getTaglist();
		
		if(list.size() == 0){
			return;
		}
		else{
			
			tagTA.setText(putTogetherTags(list));
		}
	}

}
