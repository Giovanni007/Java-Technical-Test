package demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberFinderImpl implements NumberFinder{
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberFinderImpl.class.getName());
	
	@Override
	public List<CustomNumberEntity> readFromFile(String filePath) {
		Gson gson = new Gson();
		List<CustomNumberEntity> list = null;
		try(BufferedReader br = new BufferedReader(new FileReader(filePath)); ){
			Type type = new TypeToken<List<CustomNumberEntity>>(){}.getType();
			list = gson.fromJson(br, type);
		} catch (IOException e) {
			LOGGER.error("File not found exception: {}",e.getMessage());
		}
		return list;
	}
	
	//For the constraint n. 3 I can't implement other comparator so I can't use list.sort() 
	// Because the constructor in CustomNumberEntity is private I can't create an instance of CustomNumber(valueToFind) 
	// I can't use a Map or other trick  because for the constraint 2
	// I can't use the hash of CustomNumberEntity and I can't order the list without converting Sting in int  
	// MUST use the provided FasterComparator.compare
	//O(n)   n/threads * x (x  between 5 and 10 seconds )
	@Override
	public boolean contains(int valueToFind, List<CustomNumberEntity> list) {
		if(list == null || list.isEmpty()) return false;
		LOGGER.debug(list.toString());
		
		int n = list.size() -1;
		int threads = Runtime.getRuntime().availableProcessors() + 1;
		// TO DO: check if it can be useful use partition
		int partition = n/(threads*10) + 1;
		
		ExecutorService  executor = Executors.newFixedThreadPool(threads);
		List<Future<Boolean>> futures = new ArrayList<>();
		
		for (int i = 0; i+ partition <= n ; i= i+ partition){
			Callable<Boolean> callable = new LinearSearch(list.subList(i, i+partition),valueToFind);	 
			Future<Boolean> future = executor.submit(callable);
			 futures.add(future);
		}
		
		
		int j = n%partition;
		Callable<Boolean> callable = new LinearSearch(list.subList(n-j,n + 1),valueToFind);	 
		Future<Boolean> future = executor.submit(callable);
		futures.add(future);

        for(Future<Boolean> fut : futures){
            try {
            	if(fut.get().equals(true)) {
            		return true;
            	}
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("Error searching element",e);
                Thread.currentThread().interrupt();
            }
        }
        executor.shutdown();
        return false;
	}	
	
	//O(n) comparisons with FastestComparator.compare
	public boolean bruteForceSolution(int valueToFind, List<CustomNumberEntity> list) {
			if(list == null) return false;
			WrapperFastestComparator comparator = new WrapperFastestComparator();
			for(CustomNumberEntity element : list) {
				if(comparator.compare(valueToFind, element) == 0) {
					return true;
				}
			}
			return false;
	}

}
