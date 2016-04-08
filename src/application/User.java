package application;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;
	private List<Album> albumlist = new ArrayList<Album>();
	
	public User(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public void addAlbum(Album album){
		this.albumlist.add(album);
	}
	public Album getAlbum(int index){
		return albumlist.get(index);
	}
	public int getAlbumlistSize(){
		return albumlist.size();
	}
	
	public String toString(){
		return name;
	}
	
}
