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
import org.redquark.aem.htl.core.services.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

/**
 * @author Anirudh Sharma
 */
@Component(service = Movie.class)
public class MovieImpl implements Movie {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Reference
	private QueryBuilder queryBuilder;

	private Map<String, Object> serviceUserMap;

	@Activate
	protected void activate() {
		// Creating service user map
		serviceUserMap = new HashMap<>();
		serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, "htlServlce");
	}

	@Override
	public List<String> getUpcomingMovies(int numberOfMovies, String path) {

		List<String> hyperLinks = new LinkedList<>();
		Session session = null;
		try {
			// Getting resource resolver
			ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);

			// Getting session
			session = resourceResolver.adaptTo(Session.class);

			// Creating predicate map
			Map<String, String> predicates = new HashMap<>();
			predicates.put("path", path);
			predicates.put("type", "dam:Asset");
			predicates.put("p.limit", String.valueOf(numberOfMovies));

			Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
			query.setStart(0);

			SearchResult result = query.getResult();

			log.debug("Result: {}", result.getHits().size());

			for (Hit hit : result.getHits()) {
				log.debug(hit.getPath());
				hyperLinks.add(hit.getPath());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (session != null) {
				session.logout();
			}
		}
		return hyperLinks;
	}

}
