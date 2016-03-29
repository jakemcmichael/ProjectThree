import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Project #3 CS 2334, Section 010 March 28, 2016
 * <P>
 * MediaMaker class. Used to store various data about people who make Media.
 * </P>
 * 
 * @version 1.0
 */
public class MediaMaker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8530627768585182520L;
	/** The name of the MediaMaker */
	String name;
	/** List of acting titles */
	ArrayList<String> actingTitles = new ArrayList<String>();
	/** List of acting years */
	ArrayList<String> actingYears = new ArrayList<String>();
	/** List of directing titles */
	ArrayList<String> directingTitles = new ArrayList<String>();
	/** List of directing years */
	ArrayList<String> directingYears = new ArrayList<String>();
	/** List of producing titles */
	ArrayList<String> producingTitles = new ArrayList<String>();
	/** List of producing years */
	ArrayList<String> producingYears = new ArrayList<String>();
	/** number of movie acting credits */
	int actingMovieCredits;
	/** number of series acting credits */
	int actingSeriesCredits;
	/** number of movie directing credits */
	int directingMovieCredits;
	/** number of series directing credits */
	int directingSeriesCredits;
	/** number of movie producing credits */
	int producingMovieCredits;
	/** number of series producing credits */
	int producingSeriesCredits;
	/** the output to be printed when toString is called */
	String output = "";

	public MediaMaker(String name) {
		this.name = name;
	}

	/**
	 * returns the ArrayList of acting titles
	 * @return the ArrayList of acting titles
	 */
	public ArrayList<String> getTitle() {
		return actingTitles;
	}
	
	/**
	 * returns the ArrayList of acting years
	 * @return the ArrayList of acting years
	 */
	public ArrayList<String> getYear() {
		return actingYears;
	}
	
	/**
	 * Sets the actingTitles ArrayList
	 * @param x ArrayList of Strings
	 */
	public void addActingTitles(ArrayList<String> x) {
		actingTitles = x;
	}
	
	/**
	 * Sets the actingYears ArrayList
	 * @param x ArrayList of Strings
	 */
	public void addActingYears(ArrayList<String> x) {
		actingYears = x;
	}
	
	/**
	 * Sets the directingTitles ArrayList
	 * @param x ArrayList of Strings
	 */
	public void addDirectingTitles(ArrayList<String> x) {
		directingTitles = x;
	}
	
	/**
	 * Sets the directingYears ArrayList
	 * @param x ArrayList of Strings
	 */
	public void addDirectingYears(ArrayList<String> x) {
		directingYears = x;
	}
	
	/**
	 * Sets the producingTitles ArrayList
	 * @param x ArrayList of Strings
	 */
	public void addProducingTitles(ArrayList<String> x) {
		producingTitles = x;
	}
	
	/**
	 * Sets the producingYears ArrayList
	 * @param x ArrayList of Strings
	 */
	public void addProducingYears(ArrayList<String> x) {
		producingYears = x;
	}
	
	/**
	 * Sets the actingMovieCredits value
	 * @param x number of acting movie credits
	 */
	public void setActingMovieCredits(int x) {
		actingMovieCredits = x;
	}
	
	/**
	 * Sets the actingSeriesCredits value
	 * @param x number of acting series credits
	 */
	public void setActingSeriesCredits(int x) {
		actingSeriesCredits = x;
	}
	
	/**
	 * Sets the directingMovieCredits value
	 * @param x number of directing movie credits
	 */
	public void setDirectingMovieCredits(int x) {
		directingMovieCredits = x;
	}
	
	/**
	 * Sets the directingSeriesCredits value
	 * @param x number of directing series credits
	 */
	public void setDirectingSeriesCredits(int x) {
		directingSeriesCredits = x;
	}
	
	/**
	 * Sets the producingMovieCredits value
	 * @param x number of movie producing credits
	 */
	public void setProducingMovieCredits(int x) {
		producingMovieCredits = x;
	}
	
	/**
	 * Sets the producingSeriesCredits value
	 * @param x number of producing series credits
	 */
	public void setProducingSeriesCredits(int x) {
		producingSeriesCredits = x;
	}
	
	/**
	 *Makes a pie chart using the credit data
	 */
	public void bakePie() {
		final int SIZE = 500;
		JFrame frame = new JFrame("PIE CHART");
		JPanel panel = new PieChart(actingMovieCredits, actingSeriesCredits, directingMovieCredits,
				directingSeriesCredits, producingMovieCredits, producingSeriesCredits);
		panel.setPreferredSize(new Dimension(SIZE, SIZE + 150));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Serializes the output
	 */
	public void serializeObject() {
		Search s = new Search(actingTitles, actingYears, directingTitles, directingYears, producingTitles, producingYears);
		try {
			FileOutputStream fileOut = new FileOutputStream("serialized.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(s);
			out.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	/**
	 * Serializes the input
	 */
	public void deserializeObject(){
	 Search s = null;
     try
     {
        FileInputStream fileIn = new FileInputStream("serialized.txt");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        s = (Search) in.readObject();
        in.close();
        fileIn.close();
     }catch(IOException i)
     {
        i.printStackTrace();
        return;
     }catch(ClassNotFoundException c)
     {
        System.out.println("Search class not found");
        c.printStackTrace();
        return;
     }
	}
	/**
	 * Prints out the output
	 * 
	 * @return output
	 */
	public String toString() {
		output += name + "\n";
		output += "ACTING (Movie credits: " + actingMovieCredits + " // Series credits: " + actingSeriesCredits + ")\n";
		for (int x = 0; x < actingTitles.size(); x++)
			output += actingTitles.get(x) + " " + actingYears.get(x) + "\n";
		for (int x = 0; x < 80; x++)
			output += ("-");
		output += "\nDIRECTING (Movie credits: " + directingMovieCredits + " // Series credits: "
				+ directingSeriesCredits + ")\n";
		for (int x = 0; x < directingTitles.size(); x++)
			output += directingTitles.get(x) + " " + directingYears.get(x) + "\n";
		for (int x = 0; x < 80; x++)
			output += ("-");
		output += "\nPRODUCING (Movie credits: " + producingMovieCredits + " // Series credits: "
				+ producingSeriesCredits + ")\n";
		for (int x = 0; x < producingTitles.size(); x++)
			output += producingTitles.get(x) + " " + producingYears.get(x) + "\n";
		for (int x = 0; x < 80; x++)
			output += ("=");
		output+= "\n";
		return output;
	}
}