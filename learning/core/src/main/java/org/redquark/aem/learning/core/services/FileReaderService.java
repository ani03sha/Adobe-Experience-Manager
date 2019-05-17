package org.redquark.aem.learning.core.services;

import java.util.List;

import org.redquark.aem.learning.core.models.user.UserDetails;


/**
 * @author Anirudh Sharma
 *
 */
public interface FileReaderService {
	
	/**
	 * This method reads the file uploaded by the user. 
	 * The file should be an excel file (.xls or .xlsx)
	 */
	List<UserDetails> readExcel(String filePath);
}
