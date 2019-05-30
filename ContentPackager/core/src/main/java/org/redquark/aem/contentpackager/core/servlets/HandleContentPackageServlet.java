package org.redquark.aem.contentpackager.core.servlets;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import static org.redquark.aem.contentpackager.core.constants.AppConstants.EMPTY_STRING;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.contentpackager.core.models.ContentFilters;
import org.redquark.aem.contentpackager.core.services.FileReaderService;
import org.redquark.aem.contentpackager.core.services.PackagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Handle File Upload Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST,
		"sling.servlet.paths=" + "/bin/contentpackager/fileupload" })
public class HandleContentPackageServlet extends SlingAllMethodsServlet {

	// Generated serial version UID
	private static final long serialVersionUID = 560680167075919693L;

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Path of the temporary file
	private String tempFilePath;

	// PrintWriter instance to set response
	private PrintWriter printWriter;

	// Injecting reference of the FileReaderService
	@Reference
	private FileReaderService fileReaderService;

	// Injecting reference of PackagerService
	@Reference
	private PackagerService packagerService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		log.info("Invoking HandleContentPackageServlet...");

		try {

			// Package and Group name values
			String packageName = EMPTY_STRING;
			String groupName = EMPTY_STRING;

			// Check if the file is multi-part
			final boolean isMultipart = ServletFileUpload.isMultipartContent(request);

			// Setting the temporary file path - This path will be on the server from
			// where the AEM is running
			tempFilePath = System.getProperty("user.dir");

			// Getting the writer instance from the response object
			printWriter = response.getWriter();

			String createdFilePath = EMPTY_STRING;

			// Temporary file
			File file = null;

			if (isMultipart) {

				// Getting the request parameters from the request object
				Map<String, RequestParameter[]> parameters = request.getRequestParameterMap();

				// Getting the request parameters from the entry set
				for (final Map.Entry<String, RequestParameter[]> pairs : parameters.entrySet()) {

					// Getting the value of request parameter - first element only
					RequestParameter parameter = pairs.getValue()[0];

					// Checking if the posted value is a file or JCR path
					final boolean isFormField = parameter.isFormField();

					if (!isFormField) {
						// Getting the input stream object
						InputStream inputStream = parameter.getInputStream();

						// Creating a temporary file
						file = File.createTempFile("sample", ".xlsx", new File(tempFilePath));

						// Writing contents from input stream to the temporary file
						FileUtils.copyInputStreamToFile(inputStream, file);

						createdFilePath = file.getAbsolutePath();
					} else {
						packageName = request.getParameter("package");
						groupName = request.getParameter("group");
					}
				}
				printWriter.println("File uploaded successfully");

				// Getting the list of users from the excel file
				List<ContentFilters> filters = fileReaderService.readData(createdFilePath);

				log.info("Filters have been read from the file");
				printWriter.println("Filters have been read from the file");

				// Deleting the temporary file
				file.delete();

				// Calling the PackagerService to create package in AEM Package Manager
				boolean isSuccessful = packagerService.createPackage(packageName, groupName, filters, request);

				if (isSuccessful) {
					log.info("Package has been created successfully");
					printWriter.println("Package has been created successfully");
				} else {
					printWriter.println("Some error occurred. Check the logs.");
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
