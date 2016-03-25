import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Project #2 CS 2334, Section 010 Feb 19, 2016
 * <P>
 * This class contains the main method and methods for handling user
 * interaction.
 * </P>
 * 
 * @version 1.0
 */
public class Driver {

	public static void main(String[] args) throws IOException {
		Database x = new Database();
		try {
			x = new Database("StarTrekMovies.txt", "StarTrekTV.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		x.mediaMakerParser("SomeActors.txt");
		x.searchMap("William Shatner");
		x.searchMap("Sigourney Weaver");
		
		
	}
}