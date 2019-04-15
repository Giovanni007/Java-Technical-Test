package demo;

import java.util.List;
import java.util.concurrent.Callable;

public class LinearSearch implements Callable<Boolean> {
	volatile boolean result = false;
	private List<CustomNumberEntity> list;
	private int key;


	public LinearSearch(List<CustomNumberEntity> list, int key) {
		this.list = list;
		this.key = key;
	}

	@Override
	public Boolean call() throws Exception {
		WrapperFastestComparator comparator = new WrapperFastestComparator();	
		for (CustomNumberEntity number : list) {
			if (result == true || comparator.compare(key, number) == 0) {
				return true;
			}
		}
		return false;
	}


}
