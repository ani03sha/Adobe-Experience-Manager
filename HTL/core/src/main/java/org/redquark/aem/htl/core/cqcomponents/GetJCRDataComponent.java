package org.redquark.aem.htl.core.cqcomponents;

import java.util.List;

import org.redquark.aem.htl.core.services.FetchJCRData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

/**
 * @author Anirudh Sharma
 *
 */
public class GetJCRDataComponent extends WCMUsePojo {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private List<String> jcrPaths;

	// Service to fetch data from JCR
	private FetchJCRData fetchJCRData;

	// Search keyword
	private String keyword;

	@Override
	public void activate() throws Exception {

		// Getting the search term / keyword
		keyword = getProperties().get("search", "").toLowerCase();

		if (keyword.isEmpty()) {
			log.info("No search term is entered");
			keyword = "We.Retail";
		}

		// Getting OSGi service - FetchJCRData
		fetchJCRData = getSlingScriptHelper().getService(FetchJCRData.class);
	}

	/**
	 * @return the jcrPaths
	 */
	public List<String> getJcrPaths() {
		// Getting JCR data using the service
		jcrPaths = fetchJCRData.getJCRDataBasedOnSearchTerm(keyword);
		return jcrPaths;
	}

}
