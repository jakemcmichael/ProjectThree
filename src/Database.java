import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Project #3 CS 2334, Section 010 March 28, 2016
 * <P>
 * The Database class contains all of the methods and data necessary to load
 * data on MediaMakers and Media.
 * </P>
 * 
 * @version 1.0
 */

public class Database implements Serializable, Iterable<Media> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5047111720571243857L;
	/** Stores the Media objects in this Database. */
	private ArrayList<Media> list;
	/** Stores the names for temporary use */
	private ArrayList<String> names = new ArrayList<String>();
	/** Stores the data on all of the MediaMakers */
	private LinkedHashMap<String, MediaMaker> list2 = new LinkedHashMap<String, MediaMaker>();
	/** Tracks the current name of the MediaMaker */
	private String curName = "";

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
	 *             throws an IO exception
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
	 * 
	 * @return series returns the series object
	 */
	public Series seriesParser(String data) {
		String year = data.substring(data.length() - 9);
		int titleEnd = data.lastIndexOf("\"");
		String title = data.substring(1, titleEnd);
		String uniqueID = data.substring(titleEnd + 1, data.length() - 10).trim();
		Series series = new Series(title, year, uniqueID);
		this.add(series);
		return series;
	}

	/**
	 * Parses a line from the TV file and constructs the appropriate Episode
	 * object, adding it to the Database.
	 * 
	 * @param data
	 *            A line from the TV file specifying data on an Episode.
	 * @return
	 */
	public Episode episodeParser(String data) {
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
		return episode;
	}

	/**
	 * Parses a line from the Movie file and constructs the appropriate Movie
	 * object, adding it to the Database.
	 * 
	 * @param data
	 *            A line from the Movie file specifying data on a Movie.
	 * @return movie returns the movie object
	 */
	public Movie movieParser(String data) {
		String year = data.substring(data.length() - 4);
		int IDStart = data.indexOf("(" + year);
		String title = data.substring(0, IDStart - 1).trim();
		String uniqueID = data.substring(IDStart, data.indexOf(")", IDStart) + 1);
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
		return movie;
	}

	/**
	 * Parses all of the data found within the Actor file.
	 * 
	 * @param actorFile
	 *            File containing data on actors
	 * 
	 * @throws IOException
	 *             throws an IO exception
	 */
	void actingParser(String actorFile) throws IOException {
		ArrayList<String> titleList = new ArrayList<String>();
		ArrayList<String> yearList = new ArrayList<String>();
		FileReader actfr = new FileReader(actorFile);
		BufferedReader actbr = new BufferedReader(actfr);
		String line = actbr.readLine();
		ArrayList<ArrayList<String>> titleInception = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> yearInception = new ArrayList<ArrayList<String>>();
		int counter = 0;
		int movieCount = 0;
		int seriesCount = 0;
		boolean add = false;
		// Reads each line of the Actor file
		while (line != null) {
			boolean check1 = false;
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
			for (int i = 0; i < tokens.length; i++) {
				if (line.indexOf('"') == -1 && check1 == false) {
					title += "MOVIE: ";
					check1 = true;
				}
				if (line.indexOf('"') >= 0 && check1 == false) {
					if (line.indexOf('#') >= 0)
						title += "EPISODE: ";
					else
						title += "SERIES: ";
					check1 = true;
				}

				if (tokens[i].equals("(TV)")) {
					title += "(TV) ";
				}
				if (tokens[i].equals("(V)")) {
					title += "(V) ";
				}
			}
			for (int i = marker; i < marker2; i++) {
				if (i != tokens.length - 1)
					title += tokens[i] + " ";
			}
			year = tokens[marker2];

			if (!name.isEmpty()) {
				curName = name;
			}
			if (!line.isEmpty()) {
				titleList.add(title);
				yearList.add(year);
				if (line.indexOf('"') == -1)
					movieCount++;
				else
					seriesCount++;
			}
			if (line.isEmpty()) {
				titleInception.add(new ArrayList<String>());
				for (int i = counter; i < titleInception.size(); i++) {
					for (int x = 0; x < titleList.size(); x++) {
						titleInception.get(i).add(titleList.get(x));
					}
				}
				yearInception.add(new ArrayList<String>());
				for (int i = counter; i < yearInception.size(); i++) {
					for (int x = 0; x < yearList.size(); x++) {
						yearInception.get(i).add(yearList.get(x));
					}
				}
				if (list2.get(curName) == null) {
					list2.put(curName, new MediaMaker(curName));
					names.add(curName);
				}
				list2.get(curName).addActingTitles(titleInception.get(counter));
				list2.get(curName).addActingYears(yearInception.get(counter));
				list2.get(curName).setActingMovieCredits(movieCount);
				list2.get(curName).setActingSeriesCredits(seriesCount);
				titleList.clear();
				yearList.clear();
				counter++;
				movieCount = 0;
				seriesCount = 0;
				add = false;
			}
			line = actbr.readLine();
		}
		titleInception.add(new ArrayList<String>());
		yearInception.add(new ArrayList<String>());
		for (int i = titleInception.size() - 1; i < titleInception.size(); i++) {
			for (int x = 0; x < titleList.size(); x++) {
				titleInception.get(i).add(titleList.get(x));
			}
		}
		for (int i = yearInception.size(); i < yearInception.size(); i++) {
			for (int x = 0; x < yearList.size(); x++) {
				yearInception.get(i).add(yearList.get(x));
			}
		}
		if (list2.get(curName) == null) {
			list2.put(curName, new MediaMaker(curName));
			names.add(curName);
		}
		list2.get(curName).addActingTitles(titleList);
		list2.get(curName).addActingYears(yearList);
		list2.get(curName).setActingMovieCredits(movieCount);
		list2.get(curName).setActingSeriesCredits(seriesCount);
		actbr.close();
		curName = "";
	}

	/**
	 * Parses all of the data found within the Director file.
	 * 
	 * @param directorFile
	 *            File containing data on directors
	 * 
	 * @throws IOException
	 *             throws an IO exception
	 */
	void directingParser(String directorFile) throws IOException {
		ArrayList<String> titleList = new ArrayList<String>();
		ArrayList<String> yearList = new ArrayList<String>();
		FileReader actfr = new FileReader(directorFile);
		BufferedReader actbr = new BufferedReader(actfr);
		String line = actbr.readLine();
		ArrayList<ArrayList<String>> titleInception = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> yearInception = new ArrayList<ArrayList<String>>();
		int counter = 0;
		int movieCount = 0;
		int seriesCount = 0;
		// Reads each line of the Director file
		while (line != null) {
			String name = "";
			String title = "";
			String year = "";
			int marker = 0;
			boolean check1 = false;
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
			for (int i = 0; i < tokens.length; i++) {
				if (line.indexOf('"') == -1 && check1 == false) {
					title += "MOVIE: ";
					check1 = true;
				}
				if (line.indexOf('"') > -1 && check1 == false) {
					title += "SERIES: ";
					check1 = true;
				}
				if (tokens[i].equals("(TV)")) {
					title += "(TV) ";
				}
				if (tokens[i].equals("(V)")) {
					title += "(V) ";
				}
			}
			for (int i = marker; i < marker2; i++) {
				if (i != tokens.length - 1)
					title += tokens[i] + " ";
			}
			year = tokens[marker2];

			if (!name.isEmpty()) {
				curName = name;
			}
			if (!line.isEmpty()) {
				titleList.add(title);
				yearList.add(year);
				if (line.indexOf('"') == -1)
					movieCount++;
				else
					seriesCount++;
			}
			if (line.isEmpty()) {
				titleInception.add(new ArrayList<String>());
				for (int i = counter; i < titleInception.size(); i++) {
					for (int x = 0; x < titleList.size(); x++) {
						titleInception.get(i).add(titleList.get(x));
					}
				}
				yearInception.add(new ArrayList<String>());
				for (int i = counter; i < yearInception.size(); i++) {
					for (int x = 0; x < yearList.size(); x++) {
						yearInception.get(i).add(yearList.get(x));
					}
				}
				if (list2.get(curName) == null) {
					list2.put(curName, new MediaMaker(curName));
					names.add(curName);
				}
				list2.get(curName).addDirectingTitles(titleInception.get(counter));
				list2.get(curName).addDirectingYears(yearInception.get(counter));
				list2.get(curName).setDirectingMovieCredits(movieCount);
				list2.get(curName).setDirectingSeriesCredits(seriesCount);
				titleList.clear();
				yearList.clear();
				counter++;
				movieCount = 0;
				seriesCount = 0;
			}
			line = actbr.readLine();
		}
		titleInception.add(new ArrayList<String>());
		yearInception.add(new ArrayList<String>());
		for (int i = titleInception.size() - 1; i < titleInception.size(); i++) {
			for (int x = 0; x < titleList.size(); x++) {
				titleInception.get(i).add(titleList.get(x));
			}
		}
		for (int i = yearInception.size(); i < yearInception.size(); i++) {
			for (int x = 0; x < yearList.size(); x++) {
				yearInception.get(i).add(yearList.get(x));
			}
		}
		if (list2.get(curName) == null) {
			list2.put(curName, new MediaMaker(curName));
			names.add(curName);
		}
		list2.get(curName).addDirectingTitles(titleList);
		list2.get(curName).addDirectingYears(yearList);
		list2.get(curName).setDirectingMovieCredits(movieCount);
		list2.get(curName).setDirectingSeriesCredits(seriesCount);
		actbr.close();
		curName = "";
	}

	/**
	 * Parses all of the data found within the Producer file.
	 * 
	 * @param producingFile
	 *            File containing data on producers
	 * 
	 * @throws IOException
	 *             throws an IO exception
	 */
	void producingParser(String producingFile) throws IOException {
		ArrayList<String> titleList = new ArrayList<String>();
		ArrayList<String> yearList = new ArrayList<String>();
		FileReader actfr = new FileReader(producingFile);
		BufferedReader actbr = new BufferedReader(actfr);
		String line = actbr.readLine();
		ArrayList<ArrayList<String>> titleInception = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> yearInception = new ArrayList<ArrayList<String>>();
		int counter = 0;
		int movieCount = 0;
		int seriesCount = 0;
		// Reads each line of the Producer file
		while (line != null) {
			String name = "";
			String title = "";
			String year = "";
			int marker = 0;
			boolean check1 = false;
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
			for (int i = 0; i < tokens.length; i++) {
				if (line.indexOf('"') == -1 && check1 == false) {
					title += "MOVIE: ";
					check1 = true;
				}
				if (line.indexOf('"') > -1 && check1 == false) {
					title += "SERIES: ";
					check1 = true;
				}
				if (tokens[i].equals("(TV)")) {
					title += "(TV) ";
				}
				if (tokens[i].equals("(V)")) {
					title += "(V) ";
				}
			}
			for (int i = marker; i < marker2; i++) {
				if (i != tokens.length - 1)
					title += tokens[i] + " ";
			}
			year = tokens[marker2];

			if (!name.isEmpty()) {
				curName = name;
			}
			if (!line.isEmpty()) {
				titleList.add(title);
				yearList.add(year);
				if (line.indexOf('"') == -1)
					movieCount++;
				else
					seriesCount++;
			}
			if (line.isEmpty()) {
				titleInception.add(new ArrayList<String>());
				for (int i = counter; i < titleInception.size(); i++) {
					for (int x = 0; x < titleList.size(); x++) {
						titleInception.get(i).add(titleList.get(x));
					}
				}
				yearInception.add(new ArrayList<String>());
				for (int i = counter; i < yearInception.size(); i++) {
					for (int x = 0; x < yearList.size(); x++) {
						yearInception.get(i).add(yearList.get(x));
					}
				}
				if (list2.get(curName) == null) {
					list2.put(curName, new MediaMaker(curName));
					names.add(curName);
				}
				list2.get(curName).addProducingTitles(titleInception.get(counter));
				list2.get(curName).addProducingYears(yearInception.get(counter));
				list2.get(curName).setProducingMovieCredits(movieCount);
				list2.get(curName).setProducingSeriesCredits(seriesCount);
				titleList.clear();
				yearList.clear();
				counter++;
				movieCount = 0;
				seriesCount = 0;
			}
			line = actbr.readLine();
		}
		titleInception.add(new ArrayList<String>());
		yearInception.add(new ArrayList<String>());
		for (int i = titleInception.size() - 1; i < titleInception.size(); i++) {
			for (int x = 0; x < titleList.size(); x++) {
				titleInception.get(i).add(titleList.get(x));
			}
		}
		for (int i = yearInception.size(); i < yearInception.size(); i++) {
			for (int x = 0; x < yearList.size(); x++) {
				yearInception.get(i).add(yearList.get(x));
			}
		}
		if (list2.get(curName) == null) {
			list2.put(curName, new MediaMaker(curName));
			names.add(curName);
		}
		list2.get(curName).addProducingTitles(titleList);
		list2.get(curName).addProducingYears(yearList);
		list2.get(curName).setProducingMovieCredits(movieCount);
		list2.get(curName).setProducingSeriesCredits(seriesCount);
		actbr.close();
		curName = "";
	}

	/**
	 * Searches through the HashMap for a key.
	 * 
	 * @param in
	 *            Name to search through with.
	 */
	void searchMap(String in) {
		Scanner scan = new Scanner(System.in);
		String output = "";
		String response = "";
		boolean cont = true;
		if (list2.get(in) != null) {
			while (cont) {
				System.out.println("Print [g]raphical or [t]ext data?");
				response = scan.nextLine();
				if (response.toLowerCase().equals("g")) {
					list2.get(in).bakePie();
					cont = false;
				} else if (response.toLowerCase().equals("t")) {
					output += list2.get(in).toString();
					System.out.println(output);
					cont = false;
					System.out.println("Save? (y/n)");
					response = scan.nextLine();
					if (response.equals("y")) {
						System.out.println("Save [b]inary or [t]ext?");
						response = scan.nextLine();
						if (response.equals("b")) {
							list2.get(in).serializeObject();
							System.out.println("Saved as serialized.txt");
						} else if (response.equals("t")) {
							System.out.println("File name? Please add '.txt'");
							String option = scan.nextLine();
							try {
								FileWriter fw = new FileWriter(option, true);
								BufferedWriter bw = new BufferedWriter(fw);
								String output2 = list2.get(in).toString();
								bw.write(output2);
								bw.close();
								output2 = "";
							} catch (IOException ioe) {
								System.err.println("IOException: " + ioe.getMessage());
							}
						}
					}
				} else {
					System.out.println("Please enter a valid response.");
				}
			}
		} else
			System.out.println("No such name.");
	}

	/**
	 * Searches through the HashMap for a key.
	 * 
	 * @param in
	 *            Name to search through with.
	 * 
	 */
	void partialMapSearch(String in) {
		Scanner scan = new Scanner(System.in);
		boolean found = false;
		boolean cont = true;
		int count = 0;
		String output = "";
		String response = "";
		for (int x = 0; x < names.size(); x++) {
			if (names.get(x).contains(in)) {
				if (list2.get(names.get(x)) != null) {
					output += list2.get(names.get(x)).toString();
					count++;
					found = true;
				}
			}
		}
		if (count == 1) {
			while (cont) {
				System.out.println("Print [g]raphical or [t]ext data?");
				response = scan.nextLine();
				if (response.toLowerCase().equals("g")) {
					list2.get(in).bakePie();
					cont = false;
				} else if (response.toLowerCase().equals("t")) {
					System.out.println(output);
					cont = false;
				} else {
					System.out.println("Please enter a valid response.");
				}
			}
		} else
			System.out.println(output);
		if (!found)
			System.out.println("No such name!");
	}

	/**
	 * Adds a Media object to the ArrayList in this Database.
	 * 
	 * @param mediaToAdd
	 *            The Media object to be added.
	 */
	void add(Media mediaToAdd) {
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
	 * Controls all of the user input
	 */
	public void userInput() {
		System.out.println("Welcome to the Media Database!");
		System.out.println();
		Scanner scan = new Scanner(System.in);
		Database x = new Database();
		boolean cont = false;
		boolean media = false;
		boolean people = false;
		String option = "";
		while (!cont) {
			cont = true;
			System.out.println("Read from [t]ext file or [b]inary?");
			option = scan.nextLine();
			if (option.toLowerCase().equals("b"))
				System.out.println("We could not figure out how to load binary data correctly. I am sorry.");
			System.out.println("Please enter the filename of the movies file");
			String movieFile = scan.nextLine();
			System.out.println("Please enter the filename of the series file");
			String seriesFile = scan.nextLine();
			System.out.println("Please enter the filename of the actor file");
			String actorFile = scan.nextLine();
			try {
				actingParser(actorFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Please enter the filename of the director file");
			String directorFile = scan.nextLine();
			try {
				directingParser(directorFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Please enter the filename of the producer file");
			String producerFile = scan.nextLine();
			try {
				producingParser(producerFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				x = new Database(movieFile, seriesFile);
			} catch (IOException e) {
				cont = false;
				System.out.println("A file was not found,");
			}
		}
		boolean quit = false;
		while (quit == false) {
			cont = false;
			while (!cont) {
				System.out.println("Search [m]edia or [p]eople?");
				option = scan.nextLine();
				if (option.toLowerCase().equals("m")) {
					media = true;
					people = false;
					cont = true;
				} else if (option.toLowerCase().equals("p")) {
					media = false;
					people = true;
					cont = true;
				} else {
					System.out.println("Please enter a valid response");
					cont = false;
				}

				if (media && cont) {
					Year input = new Year("x");
					String titleSearch = "";
					String yearSearch = "";
					String output = "";
					boolean[] bools = new boolean[6];
					cont = false;
					while (!cont) {
						System.out.println("Search [m]ovies, [s]eries, or [b]oth?");
						option = scan.nextLine();
						if (option.toLowerCase().equals("m")) {
							bools[0] = true;
							cont = true;
						} else if (option.toLowerCase().equals("s")) {
							bools[1] = true;
							cont = true;
						} else if (option.toLowerCase().equals("b")) {
							bools[0] = true;
							bools[1] = true;
							cont = true;
						} else {
							System.out.println("Please enter a valid response.");
						}
					}
					cont = false;
					while (!cont) {
						System.out.println("Search [t]itle, [y]ear, or [b]oth?");
						option = "";
						option = scan.nextLine();
						if (option.toLowerCase().equals("t")) {
							bools[3] = true;
							cont = true;
						} else if (option.toLowerCase().equals("y")) {
							bools[4] = true;
							cont = true;
						} else if (option.toLowerCase().equals("b")) {
							bools[3] = true;
							bools[4] = true;
							cont = true;
						} else
							System.out.println("Please enter a valid response.");
					}
					option = "";
					cont = false;
					while (!cont) {
						if (bools[3]) {
							System.out.println("Search for [e]xact or [p]artial matches?");
							option = scan.nextLine();
							if (option.toLowerCase().equals("e")) {
								bools[5] = false;
								cont = true;
							} else if (option.toLowerCase().equals("p")) {
								bools[5] = true;
								cont = true;
							} else
								System.out.println("Please enter a valid response");
						} else
							cont = true;
					}
					cont = false;

					while (!cont && (bools[1] && bools[3])) {
						option = "";
						if (bools[1] && bools[3]) {
							System.out.println("Include episode titles in search and output? (y/n)");
							option = scan.nextLine();
							if (option.toLowerCase().equals("y")) {
								bools[2] = true;
								cont = true;
							} else if (option.toLowerCase().equals("n")) {
								bools[2] = false;
								cont = true;
							} else
								System.out.println("Please enter a valid response");
						}
					}
					option = "";
					if (bools[3]) {
						System.out.println("Title?");
						titleSearch = scan.nextLine();
					}

					option = "";
					if (bools[4]) {
						System.out.println("Year(s)?");
						yearSearch = scan.nextLine();
						input = new Year(yearSearch);
					}

					option = "";
					System.out.println("Sort by [t]itle or [y]ear?");
					option = scan.nextLine();
					if (option.toLowerCase().equals("t"))
						x.sortByTitle();
					else if (option.equals("y"))
						x.sortByYear();

					System.out.println();
					System.out.print("SEARCHED:");
					output += "SEARCHED:";
					if (bools[0]) {
						System.out.print(" MOVIES");
						output += " MOVIES";
					}
					if (bools[1]) {
						System.out.print(" SERIES");
						output += " SERIES";
					}
					if (bools[2]) {
						System.out.print(" EPISODES");
						output += " EPISODES";
					}
					System.out.println();
					output += "\n";

					if (bools[5] && bools[3]) {
						if (!bools[4]) {
							System.out.println("PARTIAL TITLE: " + titleSearch);
							System.out.println("YEARS: Any");
							output += "PARTIAL TITLE: " + titleSearch + "\n";
							output += "YEARS: Any\n";
						} else {
							System.out.println("PARTIAL TITLE: " + titleSearch);
							System.out.println("YEARS: " + yearSearch);
							output += "PARTIAL TITLE: " + titleSearch + "\n";
							output += "YEARS: " + yearSearch + "\n";
						}
					} else if (!bools[5] && bools[3]) {
						if (!bools[4]) {
							System.out.println("EXACT TITLE: " + titleSearch);
							System.out.println("YEARS: Any");
							output += "EXACT TITLE: " + titleSearch + "\n";
							output += "YEARS: Any\n";
						} else {
							System.out.println("EXACT TITLE: " + titleSearch);
							System.out.println("YEARS: " + yearSearch);
							output += "EXACT TITLE: " + titleSearch + "\n";
							output += "YEARS: " + yearSearch + "\n";
						}
					} else if (bools[4]) {
						System.out.println("TITLE: Any");
						System.out.println("YEARS: " + yearSearch);
						output += "TITLE: Any\n";
						output += "YEARS: " + yearSearch + "\n";
					}

					if (option.toLowerCase().equals("t")) {
						System.out.println("SORTED BY TITLE");
						output += "SORTED BY TITLE\n";
					}
					if (option.toLowerCase().equals("y")) {
						System.out.println("SORTED BY YEAR");
						output += "SORTED BY YEAR\n";
					}

					for (int q = 0; q < 80; q++) {
						System.out.print("=");
						output += "=";
					}
					System.out.println();
					output += "\n";

					if (bools[3]) {
						String result = x.search(titleSearch, input, bools).toString();
						if (result.isEmpty()) {
							System.out.println("NO MATCHES");
							output += "NO MATCHES\n";
						} else {
							System.out.println(x.search(titleSearch, input, bools));
							output += result;
						}
					} else if (bools[4]) {
						String result = x.search("", input, bools).toString();
						if (result.isEmpty()) {
							System.out.println("NO MATCHES");
							output += "NO MATCHES\n";
						} else {
							System.out.println(x.search("", input, bools));
							output += result;
						}
					}
					cont = false;
					while (!cont) {
						System.out.println("Would you like to write your output to a file? (y/n)");
						option = scan.nextLine();
						if (option.toLowerCase().equals("y")) {
							System.out.println("File name? Please add '.txt'");
							option = scan.nextLine();
							try {
								FileWriter fw = new FileWriter(option, true);
								BufferedWriter bw = new BufferedWriter(fw);
								String[] outputArray = output.split("\n");
								for (String s : outputArray) {
									bw.write(s);
									bw.newLine();
								}
								bw.close();
								output = "";
							} catch (IOException ioe) {
								System.err.println("IOException: " + ioe.getMessage());
							}
							cont = true;
						} else if (option.equals("n"))
							cont = true;
						else
							System.out.println("Please enter a valid response.");
					}

					cont = true;
					while (cont) {
						System.out.println("Continue? (y/n)");
						option = scan.nextLine();
						if (option.equals("y")) {
							quit = false;
							cont = false;
						} else if (option.equals("n")) {
							quit = true;
							cont = false;
							System.out.println("Thanks for using the MDb!");
							System.out.println("Closing . . .");
							System.exit(0);
						} else
							System.out.println("Please enter a valid response.");
					}
					cont = false;
					for (int j = 0; j < bools.length; j++)
						bools[j] = false;

				}
				if (people && cont) {
					while (cont && !quit) {
						System.out.println("Search for [e]xact or [p]artial matches?");
						String choice = scan.nextLine();
						if (choice.toLowerCase().equals("e")) {
							System.out.println("Please enter a name.");
							choice = scan.nextLine();
							searchMap(choice);
							cont = false;
						} else if (choice.toLowerCase().equals("p")) {
							System.out.println("Please enter a name.");
							choice = scan.nextLine();
							partialMapSearch(choice);
							cont = false;
						} else {
							System.out.println("Please enter a valid response.");
						}
						cont = true;
						while (cont) {
							System.out.println("Continue? (y/n)");
							option = scan.nextLine();
							if (option.equals("y")) {
								quit = false;
								cont = false;
							} else if (option.equals("n")) {
								quit = true;
								cont = false;
								System.out.println("Thanks for using the MDb!");
								System.out.println("Closing . . .");
								System.exit(0);
							} else
								System.out.println("Please enter a valid response.");
						}
					}
				}
			}
		}
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
