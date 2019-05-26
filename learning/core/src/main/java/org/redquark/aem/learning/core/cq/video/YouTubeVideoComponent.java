package org.redquark.aem.learning.core.cq.video;

import javax.jcr.Node;

import org.redquark.aem.learning.core.models.video.YouTubeVideo;
import org.redquark.aem.learning.core.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.gson.Gson;

/**
 * @author Anirudh Sharma
 *
 */
public class YouTubeVideoComponent extends WCMUsePojo {

	// Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Instance of model class
	private YouTubeVideo youtubeVideo;

	@Override
	public void activate() throws Exception {

		// Getting the instance of the current node from the resource object
		Node currentNode = getResource().adaptTo(Node.class);

		// Instantiating the model class
		this.youtubeVideo = new YouTubeVideo();

		// Default ID of the video
		String videoId = "oeO6zwjxyQw";

		if (currentNode.hasProperty("videoId")) {
			videoId = currentNode.getProperty("./videoId").getString();
		}

		log.info("Video ID of the current video is {}", videoId);

		// Getting the Json response in the form of String
		String json = HttpClientUtil.getJsonResponse(videoId);

		// Convert json string to Json object using Google's GSON library
		Gson gson = new Gson();
		youtubeVideo = gson.fromJson(json, YouTubeVideo.class);

	}

	/**
	 * @return the youtubeVideo
	 */
	public YouTubeVideo getYoutubeVideo() {
		return youtubeVideo;
	}
}
