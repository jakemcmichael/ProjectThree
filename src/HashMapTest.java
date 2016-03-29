import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Test;

public class HashMapTest {

	@Test
	public void test() {
		LinkedHashMap<String, MediaMaker> h = new LinkedHashMap<String, MediaMaker>();
		MediaMaker one = new MediaMaker("Jack Black");
		MediaMaker two = new MediaMaker("Paul Rudd");
		ArrayList<String> titles1 = new ArrayList<String>();
		ArrayList<String> titles2 = new ArrayList<String>();
		titles1.add("TITLE");
		titles2.add("TITLE2");
		one.addActingTitles(titles1);
		two.addActingTitles(titles2);
		h.put(one.name, one);
		h.put(two.name, two);
		assertEquals(titles1, h.get(one.name).getTitle());
		assertEquals(titles2, h.get(two.name).getTitle());
	}

}
