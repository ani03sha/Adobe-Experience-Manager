package org.redquark.aem.contentpackager.core.services.impl;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.osgi.service.component.annotations.Component;
import org.redquark.aem.contentpackager.core.models.ContentFilters;
import org.redquark.aem.contentpackager.core.services.FileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = FileReaderService.class, immediate = true)
public class FileReaderServiceImpl implements FileReaderService {

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private List<ContentFilters> filters;

	@Override
	public List<ContentFilters> readData(String filePath) {

		try {

			// Instantiating the ContentFilters' list
			filters = new LinkedList<>();

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

				ContentFilters filter = null;

				// Looping over all the rows
				while (rowIterator.hasNext()) {

					// Getting the current row
					Row row = rowIterator.next();

					// Creating an object of the UserDetails model class
					filter = new ContentFilters();

					log.info("Reading user details from the Row object");

					// Reading the filter path and setting it
					String contentFilter = row.getCell(0).toString();
					if (contentFilter != null && !contentFilter.isEmpty()) {
						filter.setContentFilterPath(contentFilter);
					} else {
						log.error("Invalid content filter path");
						return Collections.emptyList();
					}

					filters.add(filter);
				}

				workbook.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return filters;
	}

}
