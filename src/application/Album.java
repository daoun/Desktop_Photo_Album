package application;

import java.util.ArrayList;
import java.util.List;

public class Album {

	private String name;
	private List<Photo> photolist = new ArrayList<Photo>();
	
	public Album(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addPhoto(Photo pic){
		this.photolist.add(pic);
	}
	public Photo getPhoto(int index){
		return photolist.get(index);
	}
	public int getPhotolistSize(){
		return photolist.size();
	}
	
	public String toString(){
		String ret = name +": ";
		
		for(int i = 0; i<photolist.size(); i++){
			ret = ret +  photolist.get(i) + ", ";
		}
		return ret;
	}
	
}
