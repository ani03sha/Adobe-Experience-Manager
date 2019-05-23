package org.redquark.aem.xmlreader.core.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Anirudh Sharma
 *
 */
@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -2525852165658067914L;

	@XmlElement(name = "Product_ID")
	private String productId;

	@XmlElement(name = "SKU")
	private String sku;

	@XmlElement(name = "Name")
	private String name;

	@XmlElement(name = "Product_URL")
	private String productUrl;

	@XmlElement(name = "Price")
	private double price;

	@XmlElement(name = "Retail_Price")
	private double retailPrice;

	@XmlElement(name = "Thumbnail_URL")
	private String thumbnailUrl;

	@XmlElement(name = "Search_Keywords")
	private String searchKeywords;

	@XmlElement(name = "Description")
	private String description;

	@XmlElement(name = "Category")
	private String category;

	@XmlElement(name = "Category_ID")
	private String categoryId;

	@XmlElement(name = "Brand")
	private String brand;

	@XmlElement(name = "Child_SKU")
	private String childSku;

	@XmlElement(name = "Child_Price")
	private String childPrice;

	@XmlElement(name = "Color")
	private String color;

	@XmlElement(name = "Color_Family")
	private String colorFamily;

	@XmlElement(name = "Color_Swatches")
	private String colorSwatches;

	@XmlElement(name = "Size")
	private String size;

	@XmlElement(name = "Shoe_Size")
	private String shoeSize;

	@XmlElement(name = "Pants_Size")
	private String pantsSize;

	@XmlElement(name = "Occassion")
	private String occassion;

	@XmlElement(name = "Season")
	private String season;

	@XmlElement(name = "Badges")
	private String badges;

	@XmlElement(name = "Rating_Avg")
	private double ratingAvg;

	@XmlElement(name = "Rating_Count")
	private int ratingCount;

	@XmlElement(name = "Inventory_Count")
	private int inventoryCount;

	@XmlElement(name = "Date_Created")
	private String dateCreated;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * @param sku
	 *            the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the productUrl
	 */
	public String getProductUrl() {
		return productUrl;
	}

	/**
	 * @param productUrl
	 *            the productUrl to set
	 */
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the retailPrice
	 */
	public double getRetailPrice() {
		return retailPrice;
	}

	/**
	 * @param retailPrice
	 *            the retailPrice to set
	 */
	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	/**
	 * @return the thumbnailUrl
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	/**
	 * @param thumbnailUrl
	 *            the thumbnailUrl to set
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 * @return the searchKeywords
	 */
	public String getSearchKeywords() {
		return searchKeywords;
	}

	/**
	 * @param searchKeywords
	 *            the searchKeywords to set
	 */
	public void setSearchKeywords(String searchKeywords) {
		this.searchKeywords = searchKeywords;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the childSku
	 */
	public String getChildSku() {
		return childSku;
	}

	/**
	 * @param childSku
	 *            the childSku to set
	 */
	public void setChildSku(String childSku) {
		this.childSku = childSku;
	}

	/**
	 * @return the childPrice
	 */
	public String getChildPrice() {
		return childPrice;
	}

	/**
	 * @param childPrice
	 *            the childPrice to set
	 */
	public void setChildPrice(String childPrice) {
		this.childPrice = childPrice;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the colorFamily
	 */
	public String getColorFamily() {
		return colorFamily;
	}

	/**
	 * @param colorFamily
	 *            the colorFamily to set
	 */
	public void setColorFamily(String colorFamily) {
		this.colorFamily = colorFamily;
	}

	/**
	 * @return the colorSwatches
	 */
	public String getColorSwatches() {
		return colorSwatches;
	}

	/**
	 * @param colorSwatches
	 *            the colorSwatches to set
	 */
	public void setColorSwatches(String colorSwatches) {
		this.colorSwatches = colorSwatches;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the shoeSize
	 */
	public String getShoeSize() {
		return shoeSize;
	}

	/**
	 * @param shoeSize
	 *            the shoeSize to set
	 */
	public void setShoeSize(String shoeSize) {
		this.shoeSize = shoeSize;
	}

	/**
	 * @return the pantsSize
	 */
	public String getPantsSize() {
		return pantsSize;
	}

	/**
	 * @param pantsSize
	 *            the pantsSize to set
	 */
	public void setPantsSize(String pantsSize) {
		this.pantsSize = pantsSize;
	}

	/**
	 * @return the occassion
	 */
	public String getOccassion() {
		return occassion;
	}

	/**
	 * @param occassion
	 *            the occassion to set
	 */
	public void setOccassion(String occassion) {
		this.occassion = occassion;
	}

	/**
	 * @return the season
	 */
	public String getSeason() {
		return season;
	}

	/**
	 * @param season
	 *            the season to set
	 */
	public void setSeason(String season) {
		this.season = season;
	}

	/**
	 * @return the badges
	 */
	public String getBadges() {
		return badges;
	}

	/**
	 * @param badges
	 *            the badges to set
	 */
	public void setBadges(String badges) {
		this.badges = badges;
	}

	/**
	 * @return the ratingAvg
	 */
	public double getRatingAvg() {
		return ratingAvg;
	}

	/**
	 * @param ratingAvg
	 *            the ratingAvg to set
	 */
	public void setRatingAvg(double ratingAvg) {
		this.ratingAvg = ratingAvg;
	}

	/**
	 * @return the ratingCount
	 */
	public int getRatingCount() {
		return ratingCount;
	}

	/**
	 * @param ratingCount
	 *            the ratingCount to set
	 */
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	/**
	 * @return the inventoryCount
	 */
	public int getInventoryCount() {
		return inventoryCount;
	}

	/**
	 * @param inventoryCount
	 *            the inventoryCount to set
	 */
	public void setInventoryCount(int inventoryCount) {
		this.inventoryCount = inventoryCount;
	}

	/**
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * Overridden toString() method
	 */
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", sku=" + sku + ", name=" + name + ", productUrl=" + productUrl
				+ ", price=" + price + ", retailPrice=" + retailPrice + ", thumbnailUrl=" + thumbnailUrl
				+ ", searchKeywords=" + searchKeywords + ", description=" + description + ", category=" + category
				+ ", categoryId=" + categoryId + ", brand=" + brand + ", childSku=" + childSku + ", childPrice="
				+ childPrice + ", color=" + color + ", colorFamily=" + colorFamily + ", colorSwatches=" + colorSwatches
				+ ", size=" + size + ", shoeSize=" + shoeSize + ", pantsSize=" + pantsSize + ", occassion=" + occassion
				+ ", season=" + season + ", badges=" + badges + ", ratingAvg=" + ratingAvg + ", ratingCount="
				+ ratingCount + ", inventoryCount=" + inventoryCount + ", dateCreated=" + dateCreated + "]";
	}

}
