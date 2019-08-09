package org.redquark.aem.htl.core.cqcomponents;

import java.util.LinkedList;
import java.util.List;

import org.redquark.aem.htl.core.services.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

/**
 * @author Anirudh Sharma
 *
 */
public class MovieComponent extends WCMUsePojo {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private int numberOfMovies;
	private String path;
	private Movie movie;
	private List<String> file;

	@Override
	public void activate() throws Exception {

		this.numberOfMovies = Integer.valueOf(getProperties().get("maxMovies", ""));
		this.path = getProperties().get("moviesPath", "");

		movie = getSlingScriptHelper().getService(Movie.class);
	}

	public List<String> getFiles() {

		try {
			log.debug("The search term is: {}", this.numberOfMovies);
			this.file = new LinkedList<>();
			this.file = movie.getUpcomingMovies(numberOfMovies, path);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return this.file;
	}

}
