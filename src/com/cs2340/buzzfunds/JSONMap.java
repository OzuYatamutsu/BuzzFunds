package com.cs2340.buzzfunds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

/**
 * An object which translates a JSON-encoded String 
 * into a Map array of key-value pairs.
 * 
 * @author Sean Collins
 */
public class JSONMap {
	/**
	 * The key-value map List to store data in.
	 * Each index refers to another Account.
	 */
	List<Map<String, String>> map;

	/**
	 * Constructs a new JSONMap and adds all values to this JSONMap.
	 * 
	 * @param jsonString The JSON-encoded String to translate
	 */
	@SuppressWarnings("unchecked")
	public JSONMap(String jsonString) {
		List<Map<String, String>> jsonParser = (JSONArray) JSONValue.parse(jsonString);
		map = new ArrayList<Map<String, String>>();
		
		/*for (int i = 0; i < jsonParser.size(); i++) {
			if (jsonParser.get(i) != null) {
				nonNullValues++; // For some reason, JSONArrays have null values
			}
		}
		
		map*/
		
		/*for (int i = 0; i < nonNullValues; i++) {
			// Adds all accounts
			map.get(i) = (Map<String, String>) jsonParser.get(i);
		}*/
		
		for (int i = 0; i < jsonParser.size(); i++) {
			map.add(i, (Map<String, String>) jsonParser.get(i));
		}
	}
	
	/**
	 * Clears this JSONMap.
	 */
	public void clear() {
		for (int i = 0; i < map.size(); i++) {
			map.get(i).clear();
		}
	}

	/**
	 * Searches this JSONMap for a value associated with a given key.
	 * 
	 * @param key The key to search for
	 * @return true if this key exists in this JSONMap; false otherwise
	 */
	public boolean containsKey(String key) {
		boolean result = false;
		
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).containsKey(key)) {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * Searches this JSONMap for a value.
	 * 
	 * @param key The value to search for
	 * @return true if this value exists in this JSONMap; false otherwise
	 */
	public boolean containsValue(String input) {
		boolean result = false;
		
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).containsValue(input)) {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * Gets a key from this JSONMap identified by an ID.
	 * 
	 * @param id The ID (referring to an index of map) to search
	 * @param key The key associated with the desired value
	 * @return The value of the key, or null if not found
	 */
	public String get(String id, String key) {
		String result = null;
		int index = returnIndexOfId(id);
		if (index != -1) {
			result = map.get(index).get(key);
		}
		
		return result;
	}

	/**
	 * Returns if this JSONMap is empty.
	 * 
	 * @return true if this JSONMap is empty; false otherwise
	 */
	public boolean isEmpty() {
		boolean result = false;
		for (int i = 0; i < map.size(); i++) {
			result = map.get(i).isEmpty();
		}
		
		return result;
	}

	/**
	 * Adds a key-value pair to the map associated with a given ID.
	 * 
	 * @param id The ID to add the key-value pair to
	 * @param key The key to associate with the value
	 * @param value The value to add
	 * @return The String mapping added, or null if unsuccessful
	 */
	public String put(String id, String key, String value) {
		int index = returnIndexOfId(id);
		String result = null;
		if (index != -1) {
			result = map.get(index).put(key, value);
		}
		
		return result;
	}


	/**
	 * Removes a value associated with a key identified by a given ID.
	 * 
	 * @param id The ID to remove the key-value pair from
	 * @param key The key associated with the removed data
	 * @return The removed data, or null if unsuccessful
	 */
	public String remove(String id, String key) {
		int index = returnIndexOfId(id);
		String result = null;
		if (index != -1) {
			result = map.get(index).remove(key);
		}
		
		return result;
	}

	/**
	 * Returns the size of the map array.
	 * 
	 * @return The size of the map array
	 */
	public int size() {
		return map.size();
	}
	
	/**
	 * Returns the total size of this JSONMap (sum of all Map sizes).
	 * 
	 * @return The total size of this JSONMap (sum of all Map sizes).
	 */
	public int totalSize() {
		int size = 0;
		for (int i = 0; i < map.size(); i++) {
			size += map.get(i).size();
		}
		
		return size;
	}
	
	/**
	 * Returns the index of a given id in map.
	 * 
	 * @param id The ID to search for
	 * @return The index of the array which contains id, or -1 if not found
	 */
	private int returnIndexOfId(String id) {
		int result = -1;
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).containsValue(id)) {
				result = i;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns all values associated with a given key.
	 * 
	 * @param key The key associated with desired values
	 * @return A String[] with all values associated with 
	 * the key across all members of map, or null if not found
	 */
	public String[] returnAllValues(String key) {
		String[] values = null;
		
		if (containsKey(key)) {
			// Assumed all entries in map are consistant and all contain key
			values = new String[size()];
			for (int i = 0; i < size(); i++) {
				values[i] = map.get(i).get(key);
			}
		}
		
		return values;
	}
}
