package org.redquark.aem.htl.core.services;

import java.util.List;

/**
 * @author Anirudh Sharma
 *
 */
public interface Movie {

	List<String> getUpcomingMovies(int numberOfMovies, String path);
}
