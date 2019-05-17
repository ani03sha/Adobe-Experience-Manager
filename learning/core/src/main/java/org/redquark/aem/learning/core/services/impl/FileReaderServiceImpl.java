package org.redquark.aem.learning.core.services.impl;

import static org.redquark.aem.learning.core.constants.AppConstants.DEFAULT_GROUP;
import static org.redquark.aem.learning.core.constants.AppConstants.DEFAULT_PASSWORD;
import static org.redquark.aem.learning.core.constants.AppConstants.EMPTY_STRING;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.osgi.service.component.annotations.Component;
import org.redquark.aem.learning.core.models.user.UserDetails;
import org.redquark.aem.learning.core.services.FileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = FileReaderService.class, immediate = true)
public class FileReaderServiceImpl implements FileReaderService {

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private List<UserDetails> users;

	/**
	 * Overridden method to read the uploaded excel file
	 */
	@Override
	public List<UserDetails> readExcel(String filePath) {

		try {

			// Instantiating the users' list
			users = new LinkedList<>();

			// Creating a workbook from the excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(new File(filePath));

			// Getting the number of sheets
			int numberOfSheets = workbook.getNumberOfSheets();

			for (int i = 0; i < numberOfSheets; i++) {

				// Getting the sheet at the ith position
				Sheet sheet = workbook.getSheetAt(i);

				log.info("Reading sheet: {}", sheet.getSheetName());

				log.info("Iterating over rows and columns");

				// Reading sheets now
				Iterator<Row> rowIterator = sheet.iterator();

				UserDetails user = null;

				// Looping over all the rows
				while (rowIterator.hasNext()) {

					// Getting the current row
					Row row = rowIterator.next();

					// Creating an object of the UserDetails model class
					user = new UserDetails();

					log.info("Reading user details from the Row object");

					// Reading first name and setting it
					String firstName = row.getCell(0).toString();
					if (firstName != null && !firstName.isEmpty()) {
						user.setFirstname(firstName);
					} else {
						user.setFirstname(EMPTY_STRING);
					}

					// Reading last name and setting it
					String lastName = row.getCell(1).toString();
					if (lastName != null && !lastName.isEmpty()) {
						user.setLastname(lastName);
					} else {
						log.error("This user cannot be created as it does not have a valid last name");
						break;
					}

					// Reading the email and setting it
					String email = row.getCell(2).toString();
					if (email != null && !email.isEmpty()) {
						user.setEmail(email);
					} else {
						log.error("This user cannot be created as it does not have a valid email address");
						break;
					}

					// Reading the password and setting it
					String password = row.getCell(3).toString();
					if (password != null && !password.isEmpty()) {
						user.setPassword(password);
					} else {
						log.error("Valid password for the user is not present. Hence deault password {} is being set",
								DEFAULT_PASSWORD);
						user.setPassword(DEFAULT_PASSWORD);
					}

					// Reading the associated group and setting it
					String group = row.getCell(4).toString();
					if (group != null && !group.isEmpty()) {
						user.setGroup(group);
					} else {
						log.error("Valid group is not present. Adding to default group: {}", DEFAULT_GROUP);
						user.setGroup(DEFAULT_GROUP);
					}
					
					String username = email.split("@")[0];
					if(username != null && !username.isEmpty()) {
						user.setUsername(username);
					} else {
						log.error("The username can't be determined. This user cannot be created");
						break;
					}
					
					// Adding the created user to the list of users
					users.add(user);
				}

				// Closing the workbook
				workbook.close();

			}

			log.info("Uploaded file has {} number of sheet(s)", numberOfSheets);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			log.error(e.getMessage(), e);
		}
		
		return users;
	}

}
