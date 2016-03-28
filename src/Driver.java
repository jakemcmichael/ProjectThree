import java.util.Scanner;

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

	public static void main(String[] args){
		Database x = new Database();
		Scanner scan = new Scanner(System.in);
		x.userInput();
	}
}