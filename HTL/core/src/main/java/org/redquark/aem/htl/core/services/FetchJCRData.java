package org.redquark.aem.htl.core.services;

import java.util.List;

/**
 * Fetches JCR data based on the passed search term
 * 
 * @author Anirudh Sharma
 */
public interface FetchJCRData {

	List<String> getJCRDataBasedOnSearchTerm(String keyword);
}
