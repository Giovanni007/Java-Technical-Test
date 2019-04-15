package demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class NumberFinderImplTest {
	static List<CustomNumberEntity> list;
	static NumberFinderImpl numberFinder;
	private static final String JSON_PATH = "../Java Technical Test/resources/test_Json.json";
	
	@BeforeAll
    public static void setUp() {
         list = new ArrayList<>();
         numberFinder = new NumberFinderImpl();
    }
	
	@Test
	void readFromFileTest_RightJson() {
		list = numberFinder.readFromFile(JSON_PATH);
		assertThat(list, hasSize(9));
	}
	
	@Test
	void readFromFileTest_EmptyJson() {
		list = numberFinder.readFromFile("../Java Technical Test/resources/test_EmptyJson.json");
		assertThat(list, nullValue());
		
	}
	
	@Test
	void readFromFileTest_WrongJson() {
		list = numberFinder.readFromFile("../Java Technical Test/resources/test_WrongJson.json");
		assertThat(list, nullValue());
		
	}	
	
	@Test
	void containsTest_Null() {
		assertThat(numberFinder.contains(2, null), is(false));
	}
	
	@Test
	void containsTest_IsPresent() {
		list = numberFinder.readFromFile(JSON_PATH);
		assertThat(numberFinder.contains(3, list), is(true));
	}
	
	@Test
	void containsTest_IsNotPresent() {
		list = numberFinder.readFromFile(JSON_PATH);
		assertThat(numberFinder.contains(-2, list), is(false));
	}
	
	@Disabled
	@Test
	void containsTest_ListIsEmpty() {
		NumberFinderImpl numberFinder = new NumberFinderImpl();
		assertEquals(numberFinder.contains(2, list),false);
	}
	
	@Disabled
	@Test
	void bruteForceSolution_TestNull() {
		assertThat(numberFinder.bruteForceSolution(2, null), is(false));
	}
	
	@Disabled
	@Test
	void bruteForceSolutionTest_IsPresent() {
		list = numberFinder.readFromFile(JSON_PATH);
		assertThat(numberFinder.bruteForceSolution(45, list), is(true));
	}
	
	@Disabled
	@Test
	void bruteForceSolutionTest_IsNotPresent() {
		list = numberFinder.readFromFile(JSON_PATH);
		assertThat(numberFinder.bruteForceSolution(-2, list), is(false));
	}
	
	@Disabled
	@Test
	void bruteForceSolutionTest_ListIsEmpty() {
		NumberFinderImpl numberFinder = new NumberFinderImpl();
		assertEquals(numberFinder.bruteForceSolution(2, list),false);
	}



}
