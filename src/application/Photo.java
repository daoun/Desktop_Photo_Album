package application;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class Photo {

	private List<String> taglist;
	private URL url;
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
	
	public URL getURL() {
		return url;
	}
	public void setURL(URL url) {
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
