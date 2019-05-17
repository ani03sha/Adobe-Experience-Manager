package org.redquark.aem.learning.core.servlets.fileupload;

import static org.redquark.aem.bulkusercreation.core.constants.AppConstants.EMPTY_STRING;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

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
import org.redquark.aem.learning.core.models.user.UserDetails;
import org.redquark.aem.learning.core.services.FileReaderService;
import org.redquark.aem.learning.core.services.UserCreationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Handle File Upload Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST,
		"sling.servlet.paths=" + "/bin/bulkusercreation/fileupload" })
public class HandleFileUploadServlet extends SlingAllMethodsServlet {

	// Generated serial version UID
	private static final long serialVersionUID = -6272122496444152824L;

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Path of the temporary file
	private String tempFilePath;

	// PrintWriter instance to set response
	private PrintWriter printWriter;

	// Injecting reference of the FileReaderService
	@Reference
	private FileReaderService fileReaderService;

	// Injecting reference of the UserCreationService
	@Reference
	private UserCreationService userCreationService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		log.info("Invoking HandleFileUploadServlet...");

		try {

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
					}
				}
				printWriter.println("File uploaded successfully");

				// Getting the list of users from the excel file
				List<UserDetails> users = fileReaderService.readExcel(createdFilePath);

				log.info("Users have been read from the file");
				printWriter.println("Users have been read from the file");

				// Deleting the temporary file
				file.delete();

				// Creating users in the AEM instance
				userCreationService.createUsers(users);

				log.info("Users have been created successfully");
				printWriter.println("Users have been created successfully");

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			printWriter.println(e.getMessage());
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}
	}

}
