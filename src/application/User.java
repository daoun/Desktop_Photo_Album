package application;

import java.util.List;

public class User {

	private String name;
	private List<Album> albumlist;
	
	public User(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public void addAlbum(Album pic){
		this.albumlist.add(pic);
	}
	public Album getAlbum(int index){
		return albumlist.get(index);
	}
	
	public String toString(){
		return name;
	}
	
}
