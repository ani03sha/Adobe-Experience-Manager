package org.redquark.aem.htl.core.beans;

import com.day.cq.tagging.Tag;

/**
 * @author Anirudh Sharma
 *
 */
public class TagCountBean {

	private Tag tag;
	private int usageCount;

	public TagCountBean(Tag tag, int usageCount) {
		this.tag = tag;
		this.usageCount = usageCount;
	}

	/**
	 * @return the tag
	 */
	public Tag getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(Tag tag) {
		this.tag = tag;
	}

	/**
	 * @return the usageCount
	 */
	public int getUsageCount() {
		return usageCount;
	}

	/**
	 * @param usageCount the usageCount to set
	 */
	public void setUsageCount(int usageCount) {
		this.usageCount = usageCount;
	}

}
