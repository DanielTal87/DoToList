package com.hit.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Hadas Barel
 * @author Daniel Tal
 */
public class User implements Serializable
{

	private static final long	serialVersionUID	= 1L;
	private int					id;
	private String				name;
	private String				password;
	private String				email;
	private List<Item>			items;

	/**
	 * default Constructor
	 */
	public User()
	{}

	public User(String name, String password, String email)
	{
		setName(name);
		setPassword(password);
		setEmail(email);
	}

	/**
	 * temp.. //TO-DO change if needed
	 */
	@Override
	public String toString()
	{
		return "User: [userId=" + id + ", name=" + name + ", password=" + password + ", items=" + items + "]";
	}

	/* ~~~~~~~~~~~~~~~ Setter & Getters ~~~~~~~~~~~~~~~ */

	public int getId()
	{
		return id;
	}

	public void setId(int userId)
	{
		this.id = userId;
	}

	public String getName()
	{
		return name;
	}

	public boolean setName(String name)
	{
		if (name == null || name.isEmpty())
		{
			return false;
		}
		this.name = name;

		return true;
	}

	public String getPassword()
	{
		return password;
	}

	public boolean setPassword(String password)
	{
		if (password == null || password.isEmpty())
		{
			return false;
		}
		this.password = password;

		return true;
	}

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
