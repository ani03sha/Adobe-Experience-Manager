package org.redquark.aem.learning.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static org.redquark.aem.learning.core.constants.AppConstants.YOUTUBE_VIDEO_URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anirudh Sharma
 *
 */
public final class HttpClientUtil {

	private final static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * This method returns the JSON response of the video details as per the id
	 * 
	 * @param videoId
	 * @return {@link String}
	 */
	public static String getJsonResponse(String videoId) {

		StringBuilder builder = new StringBuilder();

		try {

			// Creating an instance of URL by passing the actual URL string
			URL url = new URL(YOUTUBE_VIDEO_URL + videoId);

			// Creating an HTTPS connection by opening a connection using the URL object
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			// Setting the request method
			connection.setRequestMethod("GET");

			// Setting the request property - User-Agent
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			// Getting the response code
			int responseCode = connection.getResponseCode();

			log.info("Response code: {}", responseCode);

			// If the connection is successful
			if (responseCode == HttpsURLConnection.HTTP_OK) {

				// Getting the instance of the BufferedReader from the connection's input stream
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				// Variable to reach each line in the BufferendReader
				String line;

				// Loop until each line is read and append it to the StringBuilder object
				while ((line = br.readLine()) != null) {
					builder.append(line);
				}

				// Closing the connection to avoid memory leaks
				br.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return builder.toString();
	}
}
