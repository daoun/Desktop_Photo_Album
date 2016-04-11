package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class TagController implements Initializable{

	@FXML private TextArea tagTA;
	@FXML private Button saveBtn;
	@FXML private Button cancelBtn;
	
	public void save(){
		String s = tagTA.getText();
		System.out.println("gettext="+s);
		List<String> list = splitTags(s);
		System.out.println("listofsplittagg="+list);
		
		AdminController.userlist.get(AlbumController.currentUser).
			getAlbum(ThumbnailController.currentAlbum).
			getPhoto(ThumbnailController.selected).setTaglist(list);
		cancel();
	}
	
	public void cancel(){
		tagTA.setText("");
		
		ThumbnailController.tagStage.close();
		AlbumController.thumbnailStage.show();
	}
	
	public static List<String> splitTags(String s){
		List<String> list = new ArrayList<String>();
		
		s=s.replaceAll("\\s+","");
		s=s.replaceAll("\\s","");
		
		System.out.println("strip="+s);
		
		for (String retval: s.split("#")){
			System.out.println(retval);
			list.add(retval);
		}
		for(int i = list.size()-1; i >= 0; i--){
			if(list.get(i).equals("") || list.get(i).equals(" ")){
				list.remove(i);
			}
		}

		return list;
	}
	
	public static String putTogetherTags(List<String> list){
		String all = "";
		for(int i = 0; i <list.size(); i++){
			all = all + "#" + list.get(i) + " ";
		}
		System.out.println(all);
		return all;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		List<String> list = AdminController.userlist.get(AlbumController.currentUser).
				getAlbum(ThumbnailController.currentAlbum).getPhoto(ThumbnailController.selected).getTaglist();
		//tagTA = new TextArea();
		
		if(list.size() == 0){
			System.out.println("Here?");
			return;
		}
		else{
			System.out.println("OR Here?");
			tagTA.appendText(putTogetherTags(list));
		}
	}

}
