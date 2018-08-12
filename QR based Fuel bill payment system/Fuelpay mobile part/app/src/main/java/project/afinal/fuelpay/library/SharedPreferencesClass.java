package project.afinal.fuelpay.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SharedPreferencesClass {

	private SharedPreferences preferences;

	public SharedPreferencesClass(Context appContext) {
		preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
	}

	// Getters

	/**
	 * Get int value from SharedPreferences at 'key'. If key not found, return
	 * 'defaultValue'
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param defaultValue
	 *            int value returned if key was not found
	 * @return int value at 'key' or 'defaultValue' if key not found
	 */
	public int getInt(String key, int defaultValue) {
		return preferences.getInt(key, defaultValue);
	}

	/**
	 * Get parsed ArrayList of Integers from SharedPreferences at 'key'
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @return ArrayList of Integers
	 */
	public ArrayList<Integer> getListInt(String key) {
		String[] myList = TextUtils.split(preferences.getString(key, ""),
				"‚‗‚");
		ArrayList<String> arrayToList = new ArrayList<String>(
				Arrays.asList(myList));
		ArrayList<Integer> newList = new ArrayList<Integer>();

		for (String item : arrayToList)
			newList.add(Integer.parseInt(item));

		return newList;
	}

	/**
	 * Get long value from SharedPreferences at 'key'. If key not found, return
	 * 'defaultValue'
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param defaultValue
	 *            long value returned if key was not found
	 * @return long value at 'key' or 'defaultValue' if key not found
	 */
	public long getLong(String key, long defaultValue) {
		return preferences.getLong(key, defaultValue);
	}

	/**
	 * Get float value from SharedPreferences at 'key'. If key not found, return
	 * 'defaultValue'
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param defaultValue
	 *            float value returned if key was not found
	 * @return float value at 'key' or 'defaultValue' if key not found
	 */
	public float getFloat(String key, float defaultValue) {
		return preferences.getFloat(key, defaultValue);
	}

	/**
	 * Get double value from SharedPreferences at 'key'. If exception thrown,
	 * return 'defaultValue'
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param defaultValue
	 *            double value returned if exception is thrown
	 * @return double value at 'key' or 'defaultValue' if exception is thrown
	 */
	public double getDouble(String key, double defaultValue) {
		String number = getString(key);

		try {
			return Double.parseDouble(number);

		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Get parsed ArrayList of Double from SharedPreferences at 'key'
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @return ArrayList of Double
	 */
	public ArrayList<Double> getListDouble(String key) {
		String[] myList = TextUtils.split(preferences.getString(key, ""),
				"â€šâ€—â€š");
		ArrayList<String> arrayToList = new ArrayList<String>(
				Arrays.asList(myList));
		ArrayList<Double> newList = new ArrayList<Double>();

		for (String item : arrayToList)
			newList.add(Double.parseDouble(item));

		return newList;
	}

	/**
	 * Get String value from SharedPreferences at 'key'. If key not found,
	 * return ""
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @return String value at 'key' or "" (empty String) if key not found
	 */
	public String getString(String key) {
		return preferences.getString(key, "");
	}

	/**
	 * Get parsed ArrayList of String from SharedPreferences at 'key'
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @return ArrayList of String
	 */
	public ArrayList<String> getListString(String key) {
		return new ArrayList<String>(Arrays.asList(TextUtils.split(
				preferences.getString(key, ""), "‚‗‚")));
	}

	/**
	 * Put int value into SharedPreferences with 'key' and save
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param value
	 *            int value to be added
	 */
	public void putInt(String key, int value) {
		preferences.edit().putInt(key, value).apply();
	}

	/**
	 * Put ArrayList of Integer into SharedPreferences with 'key' and save
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param intList
	 *            ArrayList of Integer to be added
	 */
	public void putListInt(String key, ArrayList<Integer> intList) {
		Integer[] myIntList = intList.toArray(new Integer[intList.size()]);
		preferences.edit()
				.putString(key, TextUtils.join("‚‗‚", myIntList)).apply();
	}

	/**
	 * Put String value into SharedPreferences with 'key' and save
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param value
	 *            String value to be added
	 */
	public void putString(String key, String value) {
		preferences.edit().putString(key, value).apply();
		
	}
	
	/*
	 * delete preference
	 */
	public void deleteString(String key, String value) {
//		preferences.edit().putString(key, value).apply();
//		SharedPreferences settings = context.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
	//	settings.edit().remove("KeyName").commit();
	}

	/**
	 * Put ArrayList of String into SharedPreferences with 'key' and save
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param stringList
	 *            ArrayList of String to be added
	 */
	public void putListString(String key, ArrayList<String> stringList) {
		String[] myStringList = stringList
				.toArray(new String[stringList.size()]);
		preferences.edit()
				.putString(key, TextUtils.join("‚‗‚", myStringList))
				.apply();
	}

	/**
	 * Put boolean value into SharedPreferences with 'key' and save
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param value
	 *            boolean value to be added
	 */
	public void putBoolean(String key, boolean value) {
		preferences.edit().putBoolean(key, value).apply();
	}

	/**
	 * Put ArrayList of Boolean into SharedPreferences with 'key' and save
	 * 
	 * @param key
	 *            SharedPreferences key
	 * @param boolList
	 *            ArrayList of Boolean to be added
	 */
	public void putListBoolean(String key, ArrayList<Boolean> boolList) {
		ArrayList<String> newList = new ArrayList<String>();

		for (Boolean item : boolList) {
			if (item) {
				newList.add("true");
			} else {
				newList.add("false");
			}
		}

		putListString(key, newList);
	}

	/**
	 * Remove SharedPreferences item with 'key'
	 * 
	 * @param key
	 *            SharedPreferences key
	 */
	public void remove(String key) {
		preferences.edit().remove(key).apply();
	}

	/**
	 * Delete image file at 'path'
	 * 
	 * @param path
	 *            path of image file
	 * @return true if it successfully deleted, false otherwise
	 */
	public boolean deleteImage(String path) {
		return new File(path).delete();
	}

	/**
	 * Clear SharedPreferences (remove everything)
	 */
	public void clear() {
		preferences.edit().clear().apply();
	}

	/**
	 * Retrieve all values from SharedPreferences. Do not modify collection
	 * return by method
	 * 
	 * @return a Map representing a list of key/value pairs from
	 *         SharedPreferences
	 */
	public Map<String, ?> getAll() {
		return preferences.getAll();
	}

	/**
	 * Register SharedPreferences change listener
	 * 
	 * @param listener
	 *            listener object of OnSharedPreferenceChangeListener
	 */
	public void registerOnSharedPreferenceChangeListener(
			SharedPreferences.OnSharedPreferenceChangeListener listener) {

		preferences.registerOnSharedPreferenceChangeListener(listener);
	}

	/**
	 * Unregister SharedPreferences change listener
	 * 
	 * @param listener
	 *            listener object of OnSharedPreferenceChangeListener to be
	 *            unregistered
	 */
	public void unregisterOnSharedPreferenceChangeListener(
			SharedPreferences.OnSharedPreferenceChangeListener listener) {

		preferences.unregisterOnSharedPreferenceChangeListener(listener);
	}

	/**
	 * Check if external storage is writable or not
	 * 
	 * @return true if writable, false otherwise
	 */
	public static boolean isExternalStorageWritable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * Check if external storage is readable or not
	 * 
	 * @return true if readable, false otherwise
	 */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();

		return Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}
}
