import java.util.ArrayList;

public class MediaMaker{
	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> year = new ArrayList<String>();
	
	public MediaMaker(ArrayList<String> titles, ArrayList<String> years){
		title = titles;
		year = years;
	}
	
	public ArrayList<String> getTitle(){
		return title;
	}
	
	public ArrayList<String> getYear(){
		return year;
	}
	
}