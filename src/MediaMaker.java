import java.util.ArrayList;

public class MediaMaker{
	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> year = new ArrayList<String>();
	
	public MediaMaker(ArrayList<String> title, ArrayList<String> year){
		this.title = title;
		this.year = year;
	}
	
	public void getTitle(){
		for(int x = 0; x < title.size(); x++)
			System.out.println(title.get(x));
	}
	
	public ArrayList<String> getYear(){
		return year;
	}
	
}