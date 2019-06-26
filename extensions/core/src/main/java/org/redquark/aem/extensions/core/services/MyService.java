package org.redquark.aem.extensions.core.services;

/**
 * @author Anirudh Sharma
 */
public interface MyService {

	/**
	 * This method returns the entered password into a hash equivalent with some
	 * properties passed by the user
	 * 
	 * @param type
	 * @return {@link String}
	 */
	String getPassword(String type);
}
