package org.redquark.aem.htl.core.cqcomponents;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.redquark.aem.htl.core.beans.TagCountBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

/**
 * @author Anirudh Sharma
 *
 */
public class GetTagsComponent extends WCMUsePojo {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private List<TagCountBean> tagList = null;

	@Override
	public void activate() throws Exception {

		// Getting the ValueMap object
		ValueMap vm = getResource().getValueMap();

		// Check if the resource is associated with tags
		if (vm.containsKey("cq:tags")) {

			// Getting TagManager instance from the resource resolver after adapting it
			TagManager tagManager = getResourceResolver().adaptTo(TagManager.class);

			// Get all the Tag IDs
			String[] tagIds = vm.get("cq:tags", String[].class);

			if (tagIds.length > 0) {

				log.info("Total tags: {}", tagIds.length);
				this.tagList = new LinkedList<>();

				for (String tagId : tagIds) {

					// Getting the resolved tag
					Tag resolvedTag = tagManager.resolve(tagId);

					if (resolvedTag != null) {
						TagCountBean tagCountBean = new TagCountBean(resolvedTag,
								getTrueTagCount(tagId, getResourceResolver()).intValue());
						this.tagList.add(tagCountBean);
					}
				}
			}
		}
	}

	private Integer getTrueTagCount(String tagId, ResourceResolver resourceResolver) {

		int trueTagCount = 0;

		Map<String, String> map = new LinkedHashMap<>();

		map.put("tagid", tagId);
		map.put("tagid.property", "cq:tags");

		PredicateGroup predicates = PredicateGroup.create(map);

		QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);

		Query query = builder.createQuery(predicates, resourceResolver.adaptTo(Session.class));
		query.setHitsPerPage(9223372036854775807L);

		SearchResult result = query.getResult();

		if (!result.getHits().isEmpty()) {
			trueTagCount = result.getHits().size();
		}

		return Integer.valueOf(trueTagCount);
	}

	public List<TagCountBean> getTagCountBean() {
		return this.tagList;
	}

}
