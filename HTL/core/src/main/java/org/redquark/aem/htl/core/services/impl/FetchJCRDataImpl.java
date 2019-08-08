package org.redquark.aem.htl.core.services.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.htl.core.services.FetchJCRData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

/**
 * @author Anirudh Sharma
 *
 */
@Component(service = FetchJCRData.class)
public class FetchJCRDataImpl implements FetchJCRData {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting ResourceResolverFactory instance
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	// Injecting the instance of the QueryBuilder to fetch data
	@Reference
	private QueryBuilder queryBuilder;

	private Map<String, Object> serviceUserMap;

	@Activate
	protected void activate() {
		log.debug("Mapping service user to the service");
		// Creating service user map for accessing the JCR
		serviceUserMap = new HashMap<>();
		serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "htlService");
	}

	@Override
	public List<String> getJCRDataBasedOnSearchTerm(String keyword) {

		// List to store JCR paths
		List<String> paths = new LinkedList<>();

		// JCR session
		Session session = null;

		try {

			log.debug("***** Initialization stuff starting *****");

			// Getting resource resolver from the factory
			ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);

			// Getting session from adapting the ResourceResolver
			session = resourceResolver.adaptTo(Session.class);

			// Create predicates of query using Map
			Map<String, String> predicates = new HashMap<>();
			predicates.put("path", "/content");
			predicates.put("type", "cq:Page");
			predicates.put("group.p.or", "true"); // Combine this group with OR
			predicates.put("group.1_fulltext", keyword);
			predicates.put("group.1_fulltext.relPath", "jcr:content");
			predicates.put("group.2_fulltext", keyword);
			predicates.put("group.2_fulltext.relPath", "jcr:content/@cq:tags");
			predicates.put("p.offset", "0");
			predicates.put("p.limit", "20");

			// Creating query
			Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);

			// Getting search results
			SearchResult result = query.getResult();

			// Paging metadata
			int hitsPerPage = result.getHits().size();
			long totalMatches = result.getTotalMatches();
			long offset = result.getStartIndex();
			long numberOfPages = totalMatches / hitsPerPage;

			log.debug("Hits per page: {}", hitsPerPage);
			log.debug("Total matches: {}", totalMatches);
			log.debug("Offset: {}", offset);
			log.debug("Number of pages: {}", numberOfPages);

			// Iterating over the results
			for (Hit hit : result.getHits()) {
				String path = hit.getPath();
				// Added path to the list
				paths.add(path);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (session != null) {
				session.logout();
			}
		}

		return paths;
	}

}
