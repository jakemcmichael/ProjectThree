import static org.junit.Assert.*;

import org.junit.Test;

public class TVParseTest {

	@Test
	public void test() {
		String[] titles = {"Doctor Who", "Doctor Who", "Doctor Who: A Bargain of Necessity",
						   "Doctor Who: (#10.1)", "Doctor Who: A Ghost Story for Christmas",
						   "Doctor Who: Blink"};
		String[] years = {"1963-1989", "2005-UNSPECIFIED", "1964", "2016", "2009", "UNSPECIFIED"};
		String[] uniqueIDs = {"(1963)", "(2005)", " (1963)", " (2005)", " (2005)", " (1963)"};
		boolean[] suspended = {false, false, false, false, false, true};
		String[] data = {"\"Doctor Who\" (1963) 1963-1989",
						 "\"Doctor Who\" (2005) 2005-????",
						 "\"Doctor Who\" (1963) {A Bargain of Necessity (#1.41)} 1964",
						 "\"Doctor Who\" (2005) {(#10.1)} 2016",
						 "\"Doctor Who\" (2005) {A Ghost Story for Christmas} 2009",
						 "\"Doctor Who\" (1963) {Blink} {{SUSPENDED}} ????"};
		Database testDb = new Database();
		Series sTest;
		for(int i = 0; i < 2; ++i){
			sTest = testDb.seriesParser(data[i]);
			assertEquals(titles[i], sTest.getTitle());
			assertEquals(years[i], sTest.getYear().toString());
			assertEquals(uniqueIDs[i], sTest.getUniqueID());
		}
		Episode eTest;
		for(int i = 2; i < 6; ++i){
			eTest = testDb.episodeParser(data[i]);
			assertEquals(titles[i], eTest.getTitle());
			assertEquals(years[i], eTest.getYear().toString());
			assertEquals(uniqueIDs[i], eTest.getUniqueID());
			assertEquals(suspended[i], eTest.isSuspended());
		}
	}

}
