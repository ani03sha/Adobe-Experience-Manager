package org.redquark.aem.xmlreader.core.models;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Anirudh Sharma
 *
 */
@XmlRootElement(name = "Products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductList implements Serializable {

	// Generated serialVersionUID
	private static final long serialVersionUID = -7423990858185520203L;

	// Instance of one product
	@XmlElement(name = "Product")
	private List<Product> product;

	public ProductList() {
		super();
	}

	/**
	 * @param product
	 */
	public ProductList(List<Product> product) {
		super();
		this.product = product;
	}

	/**
	 * @return the product
	 */
	public List<Product> getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(List<Product> product) {
		this.product = product;
	}

	/**
	 * Overridden toString() method
	 */
	@Override
	public String toString() {
		return "ProductList [product=" + product + "]";
	}
}
