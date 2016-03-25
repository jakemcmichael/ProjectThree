import java.util.ArrayList;

public class MediaMaker{
	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> year = new ArrayList<String>();
	
	public MediaMaker(ArrayList<String> title, ArrayList<String> year){
		this.title = title;
		this.year = year;
	}
	
	public String getTitle(){
		return title.get(0);
	}
	
	public ArrayList<String> getYear(){
		return year;
	}
	
}