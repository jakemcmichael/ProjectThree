import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MediaMaker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8530627768585182520L;
	String name;
	ArrayList<String> actingTitles = new ArrayList<String>();
	ArrayList<String> actingYears = new ArrayList<String>();
	ArrayList<String> directingTitles = new ArrayList<String>();
	ArrayList<String> directingYears = new ArrayList<String>();
	ArrayList<String> producingTitles = new ArrayList<String>();
	ArrayList<String> producingYears = new ArrayList<String>();
	int actingMovieCredits;
	int actingSeriesCredits;
	int directingMovieCredits;
	int directingSeriesCredits;
	int producingMovieCredits;
	int producingSeriesCredits;
	String output = "";

	public MediaMaker(String name) {
		this.name = name;
	}

	public ArrayList<String> getTitle() {
		return actingTitles;
	}

	public ArrayList<String> getYear() {
		return actingYears;
	}

	public void addActingTitles(ArrayList<String> x) {
		actingTitles = x;
	}

	public void addActingYears(ArrayList<String> x) {
		actingYears = x;
	}

	public void addDirectingTitles(ArrayList<String> x) {
		directingTitles = x;
	}

	public void addDirectingYears(ArrayList<String> x) {
		directingYears = x;
	}

	public void addProducingTitles(ArrayList<String> x) {
		producingTitles = x;
	}

	public void addProducingYears(ArrayList<String> x) {
		producingYears = x;
	}

	public void setActingMovieCredits(int x) {
		actingMovieCredits = x;
	}

	public void setActingSeriesCredits(int x) {
		actingSeriesCredits = x;
	}

	public void setDirectingMovieCredits(int x) {
		directingMovieCredits = x;
	}

	public void setDirectingSeriesCredits(int x) {
		directingSeriesCredits = x;
	}

	public void setProducingMovieCredits(int x) {
		producingMovieCredits = x;
	}

	public void setProducingSeriesCredits(int x) {
		producingSeriesCredits = x;
	}

	public void bakePie() {
		final int SIZE = 500;
		JFrame frame = new JFrame("Line");
		JPanel panel = new PieChart(actingMovieCredits, actingSeriesCredits, directingMovieCredits,
				directingSeriesCredits, producingMovieCredits, producingSeriesCredits);
		panel.setPreferredSize(new Dimension(SIZE, SIZE + 150));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

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