import java.util.Comparator;

/**
 * Project #3
 * CS 2334, Section 010
 * March 28, 2016 
 * <P>
 * Media is an abstract class defining data and methods common to movies, broadcast series, 
 * and broadcast episodes. It also contains an anonymous comparator for sorting Media objects 
 * by year. 
 *</P>
 *@version 1.0
 */
public abstract class Media implements Comparable<Media> {

	/** The title of the Media */
	private String title;

	/** The release year(s) of the Media */
	private Year year;

	/** The identifier which differentiates Media with identical title and year */
	private String uniqueID;

	/**
	 * Returns the title of this Media object.
	 * @return The title of this Media object.
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * Sets this.title to the value passed in as title.
	 * @param title The title of this Media object.
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Returns the release year(s) of this Media object.
	 * @return The release year(s) of this Media object.
	 */
	public Year getYear(){
		return this.year;
	}

	/**
	 * Sets this.year to the value of the Year object created with year.
	 * @param year The string representation of this Media's publication year.
	 */
	public void setYear(String year){
		this.year = new Year(year);
	}

	/**
	 * Returns the unique identifier of this Media object.
	 * @return The unique identifier of this Media object.
	 */
	public String getUniqueID(){
		return this.uniqueID;
	}

	/**
	 * Sets this.uniquID to the specified value.
	 * @param uniqueID The uniqueID of this Media object.
	 */
	public void setUiniqueID(String uniqueID){
		this.uniqueID = uniqueID;
	}

	/**
	 * Each type of Media will have a unique format of String representation.
	 * @return String representation of this Media object.
	 */
	public abstract String toString();


	/**
	 * Compares two Media objects lexicographically by title and then by unique identifier.
	 * @param otherMedia The Media object to compare this to.
	 * @return The value of this.title.compareTo(otherMedia.title) when it is non-zero and 
	 * this.uniqueID.compareTo(otherMedia.uniqueID) when the titles are equal.
	 */
	public int compareTo(Media otherMedia){
		int comp = this.title.compareTo(otherMedia.title);
		if(comp == 0){
			return this.year.compareTo(otherMedia.year);
		}
		return comp;
	}

	/**
	 * Stores an anonymous comparator object for comparing Media by release year(s).
	 */
	public static final Comparator<Media> YEAR_COMPARATOR = new Comparator<Media>() {
		/**
		 * Lexicographic comparison of the release year(s) of media1 and media2
		 * @param media1 The first Media object.
		 * @param media2 The other Media object.
		 * @return The value of	media1.year.compareTo(media2.year).	
		 */
		public int compare(Media media1, Media media2){
			int comp = media1.year.compareTo(media2.year);
			if(comp == 0){
				return media1.title.compareTo(media2.title);
			}
			return comp;
		}
	};

}
