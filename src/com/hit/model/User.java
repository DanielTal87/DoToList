package com.hit.model;

import java.io.Serializable;
import java.util.List;

/**
 * User class
 * @author Hadas Barel
 * @author Daniel Tal
 */
public class User implements Serializable
{

	private static final long	serialVersionUID	= 1L;
	/**
	 * User id
	 */
	private int					id;

	/**
	 * User name
	 */
	private String				name;

	/**
	 * User password
	 */
	private String				password;

	/**
	 * User email
	 */
	private String				email;

	/**
	 * User items list
	 */
	private List<Item>			items;

	/**
	 * default Constructor
	 */
	public User()
	{}

	/**
	 * Constructor
	 */
	public User(String name, String password, String email)
	{
		setName(name);
		setPassword(password);
		setEmail(email);
	}

	@Override
	public String toString()
	{
		return "User: [userId=" + id + ", name=" + name + ", password=" + password + ", items=" + items + "]";
	}

	/* ~~~~~~~~~~~~~~~ Setter & Getters ~~~~~~~~~~~~~~~ */

	/**
	 * @return id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Set the user id
	 * @param userId
	 *            the user id to set
	 */
	public void setId(int userId)
	{
		this.id = userId;
	}

	/**
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set the user name
	 * @param name
	 *            the name to set
	 * @return true if the name is valid (not empty or null), false otherwise
	 */
	public boolean setName(String name)
	{
		if (name == null || name.isEmpty())
		{
			return false;
		}
		this.name = name;

		return true;
	}

	/**
	 * @return password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Set the user password
	 * @param password
	 *            the password to set
	 * @return true if the password is valid (not empty or null), false otherwise
	 */
	public boolean setPassword(String password)
	{
		if (password == null || password.isEmpty())
		{
			return false;
		}
		this.password = password;

		return true;
	}

	/**
	 * @return items
	 *         The user items list
	 */
	public List<Item> getItems()
	{
		return items;
	}

	/**
	 * Set the user list of items
	 * @param items
	 *            The user items list
	 */
	public void setItems(List<Item> items)
	{
		this.items = items;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            get the user Email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

}
