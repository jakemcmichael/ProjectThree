/**
 * Project #3
 * CS 2334, Section 010
 * March 28, 2016
 * <P>
 * The Episode class specifies a type of Media with a series title, 
 * episode number, and the possibility of being suspended. 
 * This class also provides Episode specific methods for parsing, 
 * comparing to other Episodes and converting to String.
 *</P>
 *@version 1.0
 */
public class Episode extends Media {

	/** Stores the boolean value indicating if this Episode is suspended.*/
	private boolean isSuspended;


	/**
	 * Constructs an Episode object by passing data to the parse method 
	 * and adds that Episode to the specified Series.
	 * @param title the title of the episode
	 * @param year the year
	 * @param uniqueID  the uniqueID
	 * @param isSuspended whether or not it is suspended
	 */
	public Episode(String title, String year, String uniqueID, boolean isSuspended){
		this.setTitle(title);
		this.setYear(year);
		this.setUiniqueID(uniqueID);
		this.isSuspended = isSuspended;
	}

	/**
	 * Returns true if this Episode is suspended and false otherwise.
	 * @return true if this Episode is suspended and false otherwise.
	 */
	public boolean isSuspended(){
		return this.isSuspended;
	}

	/**
	 * Returns a string representation of this Episode object.
	 * @return A string representation of this Episode object.
	 */
	public String toString(){
		String str = "EPISODE: " + this.getTitle() + " (" + 
				this.getYear() + ")";
		return str;
	}


}
