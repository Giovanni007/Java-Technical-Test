package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//FastestComparator.compare cannot be modified 
// and for the constraint 3 I can't create other comparator method
// but exception are not managed 
// so I have to wrap the class
public class WrapperFastestComparator {
	private static final Logger LOGGER = LoggerFactory.getLogger(WrapperFastestComparator.class.getName());
	
	public int compare(int firstValue, CustomNumberEntity secondValue){
		FastestComparator comparator = new FastestComparator();
		
		if(secondValue == null || !isInteger(secondValue.getNumber()))
			return -1;
		
		return comparator.compare(firstValue, secondValue);
	}
	
	private boolean isInteger(String input) {
	    try {
	        Integer.parseInt(input);
	        return true;
	    }
	    catch(NumberFormatException e) {
	    	LOGGER.debug("["+ input + "] is not a number");
	        return false;
	    }
	}
}
