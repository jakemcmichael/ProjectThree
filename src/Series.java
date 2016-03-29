/**
 * Project #3
 * CS 2334, Section 010
 * March 28, 2016 
 * <P>
 * The Series class specifies a type of Media with a list of episodes. 
 * This class also provides Series specific methods for parsing, 
 * searching the list of Episodes, and converting to String.
 *</P>
 *@version 1.0
 */

public class Series extends Media{

	/**
	 * Constructs a Series object by passing data to the parse method.
	 * @param title 
	 * @param year 
	 * @param uniqueID 
	 */
	public Series(String title, String year, String uniqueID){
		this.setTitle(title);
		this.setYear(year);
		this.setUiniqueID(uniqueID);
	}

	/**
	 * Returns a string representation of this Series object.
	 * @return A string representation of this Series object.
	 */
	public String toString(){
		return "SERIES: " + this.getTitle() + " (" + this.getYear() + ")";
	}

}
