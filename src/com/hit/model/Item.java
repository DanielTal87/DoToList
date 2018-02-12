package com.hit.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Hadas Barel
 * @author Daniel Altalat
 */
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private String assignment;
	private String category;
	private String lastmodify = "";

	/**
	 * default Constructor
	 */
	public Item() {
	}

	public Item(int userId, String assignment, String category) {
		setUserId(userId);
		setAssignment(assignment);
		setCategory(category);
	//	setLastmodify("aaa");
	}

	/**
	 * temp.. //TO-DO change if needed
	 */
	@Override
	public String toString() {
		return "Item: [itemId=" + id + " userID=" + getUserId() + "]";
	}

	/* ~~~~~~~~~~~~~~~ Setter & Getters ~~~~~~~~~~~~~~~ */

	/**
	 * @return itemId
	 */
	public int getId() {
		return id;
	}

	public void setId(int itemId) {
		this.id = itemId;
	}

	/**
	 * @return the assignment
	 */
	public String getAssignment() {
		return assignment;
	}

	/**
	 * @param assignment
	 *            the assignment to set (can be empty assignment)
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set (can be empty assignment)
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the lastModify
	 */
	public String getLastmodify() {
		return lastmodify;
	}

	/**
	 * @param lastModify
	 *            set the last modify date to the current date from the system
	 *            clock in the default time-zone
	 * @see LocalDate.now()
	 */
	public void setLastmodify(String value) {
		/*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.now();
		this.lastmodify = dtf.format(localDate);*/
		this.lastmodify=value;
	}
}
