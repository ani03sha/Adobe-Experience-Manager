package org.redquark.aem.excelapp.core.servlets.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=File Upload Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/excelApp/fileUpload" })
public class FileUploadServlet extends SlingAllMethodsServlet {

	// Generated serial version uid
	private static final long serialVersionUID = 4108063929475196474L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private String tempFilePath;

	private PrintWriter out;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		log.info("Invoking File Upload Servlet");

		try {

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			tempFilePath = System.getProperty("user.dir") + "\\crx-quickstart\\sample.xlsx";
			out = response.getWriter();

			if (isMultipart) {

				Map<String, RequestParameter[]> params = request.getRequestParameterMap();

				for (final Map.Entry<String, RequestParameter[]> pairs : params.entrySet()) {
					RequestParameter parameter = pairs.getValue()[0];
					final InputStream inputStream = parameter.getInputStream();
					File file = new File(tempFilePath);
					FileUtils.copyInputStreamToFile(inputStream, file);
					break;
				}
				out.println("File uploaded sucessfully");
				readContents();
			}

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			out.println("Some unexpected error occured");
		}
	}

	private void readContents() {

		try {
			// Creating a workbook from the excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(new File(tempFilePath));

			// Retrieving number of sheets in workbook
			out.println("Workbooks has sheets: " + workbook.getNumberOfSheets());

			workbook.forEach(sheet -> {
				out.println("Sheet name: " + sheet.getSheetName());
			});

			// Getting the sheet at index 0
			Sheet sheet = workbook.getSheetAt(0);

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();

			log.info("Iterating over rows and columns");

			sheet.forEach(row -> {
				row.forEach(cell -> {
					String cellValue = dataFormatter.formatCellValue(cell);
					out.println("Cell value: " + cellValue + " \t");
				});
				out.println("\n");
			});

			// Closing the workbook
			workbook.close();

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
