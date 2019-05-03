package org.redquark.aem.learning.core.models.grid;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for a multi-column grid control
 * 
 * @author Anirudh Sharma
 */
@Model(adaptables = Resource.class)
public class ColumnControl {

	// Default Logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	@Optional
	private String desktopColumns;

	@Inject
	@Optional
	private String tabletColumns;

	private List<Columns> columns;

	@PostConstruct
	protected void init() {

		log.info("Column Control *** Initializing ***");
		columns = new LinkedList<>();

		if (desktopColumns != null || tabletColumns != null) {

			int desktopColumnValues = Integer.parseInt(desktopColumns);
			int tabletColumnValues = Integer.parseInt(tabletColumns);

			log.info("Column Control **** Desktop columns **** {}", desktopColumnValues);
			log.info("Column Control **** Tablet columns **** {}", tabletColumnValues);

			int mdValue = 12 / desktopColumnValues;
			int smValue = 12 / tabletColumnValues;

			for (int i = 0; i < desktopColumnValues; i++) {
				Columns item = new Columns();
				item.setCount(i);
				item.setDesktopValue(mdValue);
				item.setTabValue(smValue);
				log.info("Column Control *** " + item.toString());
				columns.add(item);
			}
		}
		
		setColumns(columns);
	}

	public List<Columns> getColumns() {
		return columns;
	}

	public void setColumns(List<Columns> columns) {
		this.columns = columns;
	}
}
