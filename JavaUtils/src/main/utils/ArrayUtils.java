package utils;

import java.util.HashSet;
import java.util.Set;

public class ArrayUtils {

	public static <T> Set<T> asSet(@SuppressWarnings("unchecked") T... ts) {
		Set<T> set = new HashSet<>();
		for (T t : ts) {
			set.add(t);
		}
		return set;
 	}
	
}
