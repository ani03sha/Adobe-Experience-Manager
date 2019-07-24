package org.redquark.aem.msm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.redquark.aem.msm.core.services.LiveCopyStatusService;

/**
 * @author Anirudh Sharma
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PathModel {

	@Inject
	@Named("lcPath")
	private String liveCopyPath;

	private String liveCopyStatusDetails;

	@OSGiService
	private LiveCopyStatusService liveCopyStatusService;

	@PostConstruct
	protected void init() {

		liveCopyStatusDetails = liveCopyStatusService.getLiveCopyStatus(liveCopyPath);
	}

	/**
	 * @return the liveCopyPath
	 */
	public String getLiveCopyPath() {
		return liveCopyPath;
	}

	/**
	 * @return the liveCopyStatusDetails
	 */
	public String getLiveCopyStatusDetails() {
		return liveCopyStatusDetails;
	}

}
