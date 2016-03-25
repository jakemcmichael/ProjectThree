/**
 * Project #2
 * CS 2334, Section 010
 * Feb 19, 2016 
 * <P>
 * The Movie class specifies a type of Media with a release type. 
 * This class also provides Movie specific methods for parsing 
 * and converting to String.
 *</P>
 *@version 1.0
 */
public class Movie extends Media {

	/** Stores the release type of the movie as: "Made for TV" or "Direct to video" or "Released to theaters". */
	private String releaseType;


	/**
	 * Creates a new Movie object by passing data to the parse method.
	 * @param title 
	 * @param year 
	 * @param uniqueID 
	 * @param releaseType 
	 */
	public Movie(String title, String year, String uniqueID, String releaseType){
		this.setTitle(title);
		this.setYear(year);
		this.setUiniqueID(uniqueID);
		this.releaseType = releaseType;
	}

	/**
	 * Returns the release type of this Movie.
	 * @return The release type of this Movie.
	 */
	public String getReleaseType(){
		return this.releaseType;
	}

	/**
	 * Returns a string representation of this Movie object.
	 * @return A string representation of this Movie object.
	 */
	public String toString(){
		return "MOVIE" + this.releaseType + ": " + this.getTitle() + " (" + 
				this.getYear() + ")";
	}

}
