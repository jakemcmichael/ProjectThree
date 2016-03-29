import static org.junit.Assert.*;

import org.junit.Test;

public class MovieParseTest {

	@Test
	public void testMovieParser() {
		String[] titles = {"America Loves... Star Trek", "Programming Structures and Abstractions (Java 2)", 
						   "Java 2", "Java 2", "'Star Trek: Deep Space Nine': Behind the Scenes", 
						   "'Beyond the Five Year Mission: The Evolution of Star Trek - The Next Generation"};
		String[] years = {"UNSPECIFIED", "2016", "2016", "2016", "1993", "2014"};
		String[] uniqueIDs = {"(????)", "(2016)", "(2016/I)", "(2016/II)", "(1993)", "(2014)"};
		String[] releaseTypes = {" (TV)", " (straight to video)", "", "", " (straight to video)", ""};
		String[] data = {"America Loves... Star Trek (????) (TV)          ????",
						 "Programming Structures and Abstractions (Java 2) (2016) (V) 2016",
						 "Java 2 (2016/I)			2016",
						 "Java 2 (2016/II)		2016",
						 "'Star Trek: Deep Space Nine': Behind the Scenes (1993) (V)  1993",
						 "'Beyond the Five Year Mission: The Evolution of Star Trek - The Next Generation  (2014)  2014"};
		Database testDb = new Database();
		Movie test;
		for(int i = 0; i < 6; ++i){
			test = testDb.movieParser(data[i]);
			assertEquals(titles[i], test.getTitle());
			assertEquals(years[i], test.getYear().toString());
			assertEquals(uniqueIDs[i], test.getUniqueID());
			assertEquals(releaseTypes[i], test.getReleaseType());
		}
	}

}
