package org.redquark.aem.learning.core.msm.liveaction;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.msm.api.ActionConfig;
import com.day.cq.wcm.msm.api.LiveAction;
import com.day.cq.wcm.msm.api.LiveRelationship;

/**
 * @author Anirudh Sharma
 *
 */
@SuppressWarnings("deprecation")
public class CustomLiveAction implements LiveAction {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private String name;
	private ValueMap configs;

	/**
	 * @param name
	 * @param configs
	 */
	public CustomLiveAction(String name, ValueMap configs) {
		this.name = name;
		this.configs = configs;
	}

	@Override
	public void execute(ResourceResolver resourceResolver, LiveRelationship liveRelationship, ActionConfig actionConfig,
			boolean autoSave) throws WCMException {
	}

	@Override
	public void execute(Resource source, Resource target, LiveRelationship liveRelationship, boolean autoSave,
			boolean isResetRollout) throws WCMException {

		log.info("***** Executing custom live action *****");
		String lastModified = null;
		/*
		 * Determine if the LiveAction is configured to copy the cq:lastModifiedBy
		 * property
		 */
		if ((boolean) configs.get("repLastModBy")) {

			// Get the source's cq:lastModifiedBy property
			if (source != null && source.adaptTo(Node.class) != null) {
				ValueMap sourceVM = source.adaptTo(ValueMap.class);
				lastModified = sourceVM.get(NameConstants.PN_PAGE_LAST_MOD_BY, String.class);
			}

			// Set the target node's la-lastModifiedBy property
			Session session = null;
			if (target != null && target.adaptTo(Node.class) != null) {
				ResourceResolver resourceResolver = target.getResourceResolver();
				session = resourceResolver.adaptTo(Session.class);

				Node targetNode;
				try {
					targetNode = target.adaptTo(Node.class);
					targetNode.setProperty("la-lastModifiedBy", lastModified);
					log.info("***** Target node lastModifiedBy property updated: {} *****", lastModified);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

			if (autoSave) {
				try {
					session.save();
				} catch (Exception e) {
					try {
						session.refresh(true);
					} catch (RepositoryException re) {
						log.error(re.getMessage(), re);
					}
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void execute(ResourceResolver resourceResolver, LiveRelationship liveRelationship, ActionConfig actionConfig,
			boolean autoSave, boolean isResetRollout) throws WCMException {
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getParameterName() {
		return null;
	}

	@Override
	public String[] getPropertiesNames() {
		return null;
	}

	@Override
	public int getRank() {
		return 0;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public void write(JSONWriter jsonWriter) throws JSONException {
	}

}
