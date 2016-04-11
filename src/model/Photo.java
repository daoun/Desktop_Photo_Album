package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Photo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651057460302515220L;
	private List<String> taglist = new ArrayList<String>();;
	private String url;
	private String caption;
	private Date date;
	
	public Photo(String caption){
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getTag(int index) {
		return taglist.get(index);
	}
	public void addTag(String tag) {
		this.taglist.add(tag);
	}
	public int getTaglistSize(){
		return taglist.size();
	}
	
	public String getURL() {
		return url;
	}
	public void setURL(String url) {
		this.url = url;
	}
	
	public Date getDate() {
		return date;
	}
	@SuppressWarnings("deprecation")
	public void setDate(int year, int month, int day) {
		this.date = new Date(year, month, day);
	}
	
	public String toString(){
		return caption;
	}
}
