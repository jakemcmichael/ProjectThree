import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project #3 CS 2334, Section 010 March 28, 2016
 * <P>
 * Object that contains all of the data to be serialized.
 * </P>
 * 
 * @version 1.0
 */

public class Search implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<String> actingTitles = new ArrayList<String>();
	ArrayList<String> actingYears = new ArrayList<String>();
	ArrayList<String> directingTitles = new ArrayList<String>();
	ArrayList<String> directingYears = new ArrayList<String>();
	ArrayList<String> producingTitles = new ArrayList<String>();
	ArrayList<String> producingYears = new ArrayList<String>();
	
	public Search(ArrayList<String> a,ArrayList<String> b,ArrayList<String> c,ArrayList<String> d,ArrayList<String> e,ArrayList<String> f){
		actingTitles = a;
		actingYears = b;
		directingTitles = c;
		directingYears = d;
		producingTitles = e;
		producingYears = f;
	}
	
	public ArrayList<String> getActingTitles(){
		return actingTitles;
	}
	
	public ArrayList<String> getActingYears(){
		return actingYears;
	}
	
	public ArrayList<String> getDirectingTitles(){
		return directingTitles;
	}
	
	public ArrayList<String> getDirectingYears(){
		return directingYears;
	}
	
	public ArrayList<String> getProducingTitles(){
		return producingTitles;
	}
	
	public ArrayList<String> getProducingYears(){
		return producingYears;
	}
}