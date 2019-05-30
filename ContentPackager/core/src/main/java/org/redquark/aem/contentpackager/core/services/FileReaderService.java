package org.redquark.aem.contentpackager.core.services;

import java.util.List;

import org.redquark.aem.contentpackager.core.models.ContentFilters;

/**
 * @author Anirudh Sharma
 *
 */
public interface FileReaderService {

	List<ContentFilters> readData(String filePath);
}
