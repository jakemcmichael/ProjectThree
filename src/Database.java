import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Project #2 CS 2334, Section 010 Feb 19, 2016
 * <P>
 * The Database class contains an ArrayList of Media as well as providing
 * methods for sorting and searching the Media in a variety of ways.
 * </P>
 * 
 * @version 1.0
 */

public class Database implements Iterable<Media> {

	/** Stores the Media objects in this Database. */
	private ArrayList<Media> list;
	private LinkedHashMap<String, MediaMaker> list2 = new LinkedHashMap<String, MediaMaker>();
	private String curName = "";
	private ArrayList<String> titles = new ArrayList<String>();
	private ArrayList<String> years = new ArrayList<String>();
	private ArrayList<ArrayList<String>> makers = new ArrayList<ArrayList<String>>();
	int count = 0;

	/** Constructs an empty Database object. */
	public Database() {
		this.list = new ArrayList<Media>();
	}

	/**
	 * Constructs a Database object and populates it with the Media specified by
	 * the provided files.
	 * 
	 * @param movieFile
	 *            File containing data on Movies.
	 * @param TVFile
	 *            File containing data on Series and Episodes.
	 * @throws IOException
	 */
	public Database(String movieFile, String TVFile) throws IOException {
		this.list = new ArrayList<Media>();
		FileReader moviefr = new FileReader(movieFile);
		BufferedReader moviebr = new BufferedReader(moviefr);
		String line = moviebr.readLine();
		// Reads each line of the movie file and parses it as a new Movie in the
		// ArrayList
		while (line != null) {
			movieParser(line);
			line = moviebr.readLine();
		}
		moviebr.close();

		FileReader tvfr = new FileReader(TVFile);
		BufferedReader tvbr = new BufferedReader(tvfr);
		line = tvbr.readLine();
		// Reads each line of the TV file and parses it as a new Series or
		// Episode in the ArrayList
		while (line != null) {
			TVParser(line);
			line = tvbr.readLine();
		}
		tvbr.close();

	}

	/**
	 * Constructs a Database object and populates it with the Media stored in
	 * list.
	 * 
	 * @param list
	 *            An ArrayList containing the Media to populate this Database.
	 */
	public Database(ArrayList<Media> list) {
		this.list = list;
	}

	/**
	 * Parses data from the TV file by determining the desired type (Series or
	 * Episode) and calling the correct parse method.
	 * 
	 * @param data
	 *            A line of data from the TV file.
	 */
	public void TVParser(String data) {
		if (data.contains("{")) {// if the data specifies an Episode
			episodeParser(data);
		} else {
			seriesParser(data);
		}
	}

	/**
	 * Parses a line from the TV file and constructs the appropriate Series
	 * object, adding it to the Database.
	 * 
	 * @param data
	 *            A line from the TV file specifying data on a Series.
	 */
	public void seriesParser(String data) {
		String year = data.substring(data.length() - 9); // last 9 char are the
															// year range
		int titleEnd = data.lastIndexOf("\"");
		String title = data.substring(1, titleEnd); // isolates the title
													// without the quotes
		String uniqueID = data.substring(titleEnd + 1, data.length() - 10).trim(); // this
																					// is
																					// the
																					// starting
																					// year
																					// in
																					// parentheses
		Series series = new Series(title, year, uniqueID);
		this.add(series);
	}

	/**
	 * Parses a line from the TV file and constructs the appropriate Episode
	 * object, adding it to the Database.
	 * 
	 * @param data
	 *            A line from the TV file specifying data on an Episode.
	 */
	public void episodeParser(String data) {
		String year = data.substring(data.length() - 4); // year is the last
															// four char
		int seriesTitleEnd = data.lastIndexOf("\"");
		String seriesTitle = data.substring(1, seriesTitleEnd); // title of
																// series is
																// isolated
		int titleStart = data.indexOf("{");
		int titleEnd = data.indexOf("}", titleStart + 1);
		String title = data.substring(titleStart + 1, titleEnd); // episode
																	// title is
																	// isolated
		if (title.contains("(#") && !title.startsWith("(#")) { // if the episode
																// has a title,
																// remove the
																// episode
																// number
			title = title.substring(0, title.indexOf("(#") - 1);
		}
		title = seriesTitle + ": " + title; // concat the series and episode
											// titles as specified in the
											// charter
		String uniqueID = data.substring(seriesTitleEnd + 1, titleStart - 1); // Series
																				// start
																				// year
																				// in
																				// parentheses
		boolean isSuspended = data.contains("{{"); // it only contains {{ when
													// suspended
		Episode episode = new Episode(title, year, uniqueID, isSuspended);
		this.add(episode);
	}

	/**
	 * Parses a line from the Movie file and constructs the appropriate Movie
	 * object, adding it to the Database.
	 * 
	 * @param data
	 *            A line from the Movie file specifying data on a Movie.
	 */
	public void movieParser(String data) {
		String year = data.substring(data.length() - 4); // year is the last
															// four char
		int IDStart = data.indexOf("(" + year); // ID starts with (#### where
												// #### is the year
		String title = data.substring(0, IDStart - 1).trim(); // title is
																// everything
																// before the ID
		String uniqueID = data.substring(IDStart, data.indexOf(")", IDStart) + 1); // ID
																					// is
																					// everything
																					// in
																					// those
																					// parentheses
		String releaseType;
		if (data.contains("(V)")) {
			releaseType = " (straight to video)";
		} else if (data.contains("(TV)")) {
			releaseType = " (TV)";
		} else {
			releaseType = "";
		}
		Movie movie = new Movie(title, year, uniqueID, releaseType);
		this.add(movie);
	}

	public void mediaMakerParser(String actorFile) throws IOException {
		FileReader actfr = new FileReader(actorFile);
		BufferedReader actbr = new BufferedReader(actfr);
		String line = actbr.readLine();
		// Reads each line of the Actor file
		while (line != null) {
			String name = "";
			String title = "";
			String year = "";
			int marker = 0;
			String[] tokens = line.split("\\s+");
			if (!line.isEmpty()) { 
				if (tokens[2].contains("(")) {
					if (tokens[0].contains(",")) {
						name = tokens[1] + " " + tokens[0].substring(0, tokens[0].indexOf(',')) + " " + tokens[2];
						marker = 3;
					}
				} else if (!tokens[0].contains(",")) {
					marker = 0;
				} else {
					name = tokens[1] + " " + tokens[0].substring(0, tokens[0].indexOf(','));
					marker = 2;
				}
			}
			int marker2 = 0;
			for (int x = marker; x < tokens.length; x++) {
				if (tokens[x].contains("(")) {
					marker2 = x;
					break;
				}
			}
			for (int i = marker; i < marker2; i++) {
				title += tokens[i] + " ";
			}
			year = tokens[marker2];
				
			if(!name.isEmpty()){
				curName = name;
			}
			if(!line.isEmpty()){
				titles.add(title);
				count++;
				years.add(year);
			}
			if(line.isEmpty()){
				System.out.println(count);
				System.out.println(line);
				MediaMaker maker = new MediaMaker(titles, years);
				list2.put(curName, maker);
				titles.clear();
				years.clear();
			}
			line = actbr.readLine();
		}
		MediaMaker maker = new MediaMaker(titles, years);
		list2.put(curName, maker);
		actbr.close();
	}
	
	
	public void searchMap(String in){
		System.out.println(list2);
	}
	
	public String get(){
		return curName;
	}

	/**
	 * Adds a Media object to the ArrayList in this Database.
	 * 
	 * @param mediaToAdd
	 *            The Media object to be added.
	 */
	public void add(Media mediaToAdd) {
		this.list.add(mediaToAdd);
	}

	/**
	 * Searches this Database for Media matching the given title and year as
	 * specified by the boolean array search.
	 * 
	 * @param title
	 *            The string representing the desired title.
	 * @param year
	 *            The Year object representing the desired year(s).
	 * @param search
	 *            A boolean array specifying the desired type of search. The
	 *            data should be true if the search should include a search of:
	 *            {Movies, Series, Episodes, Titles, Years, Partial titles}.
	 * @return A Database object containing all Media from this Database which
	 *         matches the specifications of the search.
	 */
	public Database search(String title, Year year, boolean[] search) {
		if (search[3]) { // If we are searching titles:
			if (search[5]) { // If we are including partial titles, do a partial
								// search
				return this.partialSearch(title, year, search);
			} else { // otherwise use exact search
				return this.exactSearch(title, year, search);
			}
		} else {
			return this.yearSearch(year, search);
		}
	}

	/**
	 * Performs a search for partial title matches in this Database to the
	 * provide title, as well as limiting by year if applicable.
	 * 
	 * @param title
	 *            The title or partial title to search for.
	 * @param year
	 *            The year(s) searched for.
	 * @param search
	 *            A boolean array specifying the type of search to perform.
	 * @return A Database object containing all Media from this Database which
	 *         matches the specifications of the search.
	 */
	private Database partialSearch(String title, Year year, boolean[] search) {
		Database results = new Database();
		for (Media m : this) {
			if (m.getTitle().toLowerCase().contains(title)) { // If the title is
																// a partial
																// match
				if (m instanceof Movie && search[0]) { // If m is a Movie and we
														// are searching Movies
					results.add(m);
				}
				if (m instanceof Series && search[1]) { // If m is a Series and
														// we are searching
														// Series
					results.add(m);
				}
				if (m instanceof Episode && search[2]) { // If m is an Episode
															// and we are
															// searching
															// Episodes
					results.add(m);
				}
			}
		}
		if (search[4]) { // If we are also searching for years
			return results.yearSearch(year, search);
		}
		return results;
	}

	/**
	 * Performs a search for exact matches to the provided title in this
	 * Database as well as limiting by year if applicable.
	 * 
	 * @param title
	 *            The exact title to search for.
	 * @param year
	 *            The year(s) searched for.
	 * @param search
	 *            A boolean array specifying the type of search to perform.
	 * @return A Database object containing all Media from this Database which
	 *         matches the specifications of the search.
	 */
	private Database exactSearch(String title, Year year, boolean[] search) {
		Database results = new Database();
		this.sortByTitle();
		int index = Collections.binarySearch(this.list, new Series(title, "", null));
		index = -index - 1; // This will be the index of the first media object
							// whose title matches title if such exists
		Media m = index < this.list.size() ? this.list.get(index) : this.list.get(index - 1); // to
																								// avoid
																								// indexOutOfBounds
		while (m.getTitle().equals(title)) {
			if (m instanceof Movie && search[0]) {
				results.add(m);
			}
			if (m instanceof Series && search[1]) {
				results.add(m);
			}
			if (m instanceof Episode && search[2]) {
				results.add(m);
			}
			++index;
			m = index < this.list.size() ? this.list.get(index) : new Series("", "", null); // to
																							// avoid
																							// indexOutOfBounds
		}
		if (search[4]) { // If we are also searching for years
			return results.yearSearch(year, search);
		}
		return results;
	}

	/**
	 * Performs a search of this Database for media produced in the specified
	 * year(s).
	 * 
	 * @param year
	 *            The year(s) searched for.
	 * @param search
	 *            search A boolean array specifying the type of search to
	 *            perform.
	 * @return A Database object containing all Media from this Database which
	 *         matches the specifications of the search.
	 */
	private Database yearSearch(Year year, boolean[] search) {
		Database results = new Database();
		for (Media m : this) {
			if (m.getYear().equals(year)) {
				if (m instanceof Movie && search[0]) {
					results.add(m);
				}
				if (m instanceof Series && search[1]) {
					results.add(m);
				}
				if (m instanceof Episode && search[2]) {
					results.add(m);
				}
			}
		}
		return results;
	}

	/** Sorts the objects in this Database by their title lexicographically. */
	public void sortByTitle() {
		Collections.sort(this.list);
	}

	/** Sorts the objects in this Database by their release year(s). */
	public void sortByYear() {
		Collections.sort(this.list, Media.YEAR_COMPARATOR);
	}

	/**
	 * Specifies the iterator for this Database.
	 * 
	 * @return An Iterator object for Media.
	 */
	@Override
	public Iterator<Media> iterator() {
		return this.list.iterator();
	}

	/**
	 * Returns a String representation of this Database object.
	 * 
	 * @return A String representation of this Database object.
	 */
	public String toString() {
		String str = "";
		for (Media m : this) {
			str = str.concat(m.toString() + "\n");
		}
		return str;
	}
}
