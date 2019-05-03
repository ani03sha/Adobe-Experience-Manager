package org.redquark.aem.learning.core.models.grid;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for a two column grid component
 * 
 * @author Anirudh Sharma
 */
@Model(adaptables = Resource.class)
public class TwoColumnVariant {

	// Default logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	@Optional
	private String desktopColumns;

	@Inject
	@Optional
	private String tabletColumns;

	private List<Columns> columns;

	/**
	 * This is the method that is invoked when an author clicks the checkmark in the
	 * component dialog.
	 */
	@PostConstruct
	protected void init() {

		log.info("Column Control *** Initializing ***");
		columns = new LinkedList<>();

		if (desktopColumns != null || tabletColumns != null) {

			String[] desktopColumnsVals = desktopColumns.split(",");
			String[] tabletColumnsVals = tabletColumns.split(",");

			int[] mdVals = setValues(desktopColumnsVals);
			int[] smVals = setValues(tabletColumnsVals);

			Columns items = new Columns();

			Map<String, String> classAttributeOne = new HashMap<>();
			Map<String, String> classAttributeTwo = new HashMap<>();

			if (desktopColumns.equals(tabletColumns)) {
				classAttributeOne.put("class", "contentdiv col-sm-" + smVals[0]);
				classAttributeTwo.put("class", "asidediv col-sm-" + smVals[1]);
			} else {
				classAttributeOne.put("class", "contentdiv col-sm-" + smVals[0] + " col-md-" + mdVals[0]);
				classAttributeOne.put("class", "asidediv col-sm-" + smVals[1] + " col-md-" + mdVals[1]);
			}
			items.setClassAttributeOne(classAttributeOne);
			items.setClassAttributeTwo(classAttributeTwo);
			columns.add(items);
		}

		setColumns(columns);
	}

	private int[] setValues(String[] columnValues) {

		int[] desktopCols = new int[2];
		for (int i = 0; i < desktopCols.length; i++) {
			desktopCols[i] = Integer.parseInt(columnValues[i]);
		}
		return desktopCols;
	}

	public List<Columns> getColumns() {
		return columns;
	}

	public void setColumns(List<Columns> columns) {
		this.columns = columns;
	}
}
