package com.javaadash.tc2.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.javaadash.tc2.core.board.CardLocation;

/**
 * List of cards location for test purpose
 * 
 * @author b2floo
 * 
 */
public class MockCardLocation 
{
	/**
	 * Use reflection to return a list of location
	 * @return 
	 * @throws Exception
	 */
	public static List<Integer> values() throws Exception {
		List<Integer> fields = new ArrayList<Integer>();
		for(Field f: CardLocation.class.getFields()) {
			fields.add(f.getInt(0));
		}
		return fields;
	}
}
