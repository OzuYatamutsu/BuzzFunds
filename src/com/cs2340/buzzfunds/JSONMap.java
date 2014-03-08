package com.cs2340.buzzfunds;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * An object which translates a JSON-encoded String 
 * into a Map of key-value pairs.
 * 
 * @author Sean Collins
 */
public class JSONMap implements Map<String, String> {
	/**
	 * The key-value map to store data in.
	 */
	Map<String, String> map;

	/**
	 * Constructs a new JSONMap and adds all values to this JSONMap.
	 * 
	 * @param jsonString The JSON-encoded String to translate
	 */
	public JSONMap(String jsonString) {
		map = new HashMap<String, String>();
		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject)parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO: Swallows exception for now
		}
	}
	
	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		return map.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return map.containsValue(arg0);
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return map.entrySet();
	}

	@Override
	public String get(Object arg0) {
		return map.get(arg0);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public String put(String arg0, String arg1) {
		return map.put(arg0, arg1);
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> arg0) {
		map.putAll(arg0);
	}

	@Override
	public String remove(Object arg0) {
		return map.remove(arg0);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<String> values() {
		return map.values();
	}
}
