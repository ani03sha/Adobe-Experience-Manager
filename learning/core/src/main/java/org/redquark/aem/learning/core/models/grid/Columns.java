package org.redquark.aem.learning.core.models.grid;

import java.util.Map;

/**
 * This class represents columns for the two Column Component. Contains
 * information like colSpan value for both the column in desktop and tablet
 * view.
 * 
 * @author Anirudh Sharma
 */
public class Columns {

	private int count;
	private int value;
	private int desktopValue;
	private int tabValue;
	private Map<String, String> classAttributeOne;
	private Map<String, String> classAttributeTwo;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * The count to set
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * The value to set
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the desktopValue
	 */
	public int getDesktopValue() {
		return desktopValue;
	}

	/**
	 * The desktopValue to set
	 * 
	 * @param desktopValue
	 */
	public void setDesktopValue(int desktopValue) {
		this.desktopValue = desktopValue;
	}

	/**
	 * @return the tabValue
	 */
	public int getTabValue() {
		return tabValue;
	}

	/**
	 * The tabValue to set
	 * 
	 * @param tabValue
	 */
	public void setTabValue(int tabValue) {
		this.tabValue = tabValue;
	}

	/**
	 * @return the classAttributeOne
	 */
	public Map<String, String> getClassAttributeOne() {
		return classAttributeOne;
	}

	/**
	 * The classAttributeOne to set
	 * 
	 * @param classAttributeOne
	 */
	public void setClassAttributeOne(Map<String, String> classAttributeOne) {
		this.classAttributeOne = classAttributeOne;
	}

	/**
	 * @return the classAttributeTwo
	 */
	public Map<String, String> getClassAttributeTwo() {
		return classAttributeTwo;
	}

	/**
	 * The classAttributeTwo to set
	 * 
	 * @param classAttributeTwo
	 */
	public void setClassAttributeTwo(Map<String, String> classAttributeTwo) {
		this.classAttributeTwo = classAttributeTwo;
	}

}
