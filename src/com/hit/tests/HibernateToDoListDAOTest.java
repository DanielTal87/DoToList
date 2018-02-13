package com.hit.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.hit.model.HibernateToDoListDAO;
import com.hit.model.IToDoListDAO;
import com.hit.model.Item;
import com.hit.model.User;
import com.hit.exception.ToDoListException;

/**
 * API - Test
 * please follow the instructions
 * <b> Pre running Instruction:</b>
 * <p> 1. Make sure MAMP application is running
 * <p> 2. Make sure both Apace and MySQL servers are connected
 * <p> 3. Verify that "ToDoList" database does exist
 * <p> 4. Run
 * @author Daniel Altalat
 * @author Hadas Barel
 */
public class HibernateToDoListDAOTest
{

	/**
	 * test if getHibernateToDoListDAOInstance() method works
	 */
	@Test
	public void testGetInstance()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();
		if (model != null)
			assertTrue(true);
		else
			assertTrue(false);
	}

	/**
	 * test if addUser() method works
	 */
	@Test
	public void testAddUser()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		// clear user table
		model.emptyUserTable();

		// set values
		User user = new User();
		user.setName("Daniel");
		user.setPassword("Tal");

		try
		{
			// add user to data base
			model.addUser(user);
			System.out.println("testAddUser : Created user");
			assertTrue(true);
		} catch (ToDoListException e)
		{
			// failed
			System.out.println("testAddUser : Failed to create user");
			e.printStackTrace();
			assertTrue(false);
			return;
		}
	}

	/**
	 * test if deleteUser() method works
	 */
	@Test
	public void testDeleteUser()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		// clear user table
		model.emptyUserTable();

		// set values
		User user = new User();
		user.setName("Daniel");
		user.setPassword("Tal");

		// add user to data base
		try
		{
			model.addUser(user);
			System.out.println(user);
		} catch (ToDoListException e)
		{
			System.out.println("testDeleteUser : Failed to create user");
			e.printStackTrace();
			assertTrue(false);
			return;
		}

		try
		{
			// delete user from data base
			model.deleteUser(user);
			System.out.println("testDeleteUser : Successfully Deleted User - " + user.getName());
			assertTrue(true);
		} catch (ToDoListException e)
		{
			// failed
			System.out.println("testDeleteUser : Failed to delete user");
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * test if getUsers() method works
	 */
	@Test
	public void testGetUsers()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		// clear user table
		model.emptyUserTable();

		// set values
		User user1 = new User();
		user1.setName("Daniel");
		user1.setPassword("Tal");
		user1.setEmail("D@T");

		User user2 = new User();
		user2.setName("Hada");
		user2.setPassword("Barel");
		user1.setEmail("H@B");

		// add users to data base
		try
		{
			model.addUser(user1);
			model.addUser(user2);
			System.out.println("testGetUsers : Successfully created users");
		} catch (ToDoListException e)
		{
			// failed
			System.out.println("testGetUsers : Failed to create users");
			e.printStackTrace();
			assertTrue(false);
			return;
		}

		ArrayList<User> allUsers = new ArrayList<User>();
		try
		{
			// get list of users
			allUsers.addAll(model.getUsers());
			System.out.println("testGetUsers : Successfully Retrieved a list of users - " + allUsers);
			assertTrue(true);
		} catch (ToDoListException e)
		{
			// failed
			System.out.println("testGetUsers : Failed to retrieve a list of users.");
			e.printStackTrace();
			assertTrue(false);
			return;
		}

		// prints users to console
		try
		{
			for (User user : allUsers)
			{
				model.deleteUser(user);
				System.out.println("testGetUsers : Successfully Deleted User - " + user.getName());
			}
		} catch (ToDoListException e)
		{
			// failed
			System.out.println("testGetUsers : Unable to delete users");
			e.printStackTrace();
		}
	}

	/**
	 * test if getItems() method works
	 */
	@Test
	public void testGetItems()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		// clear user && item tables
		model.emptyUserTable();
		model.emptyItemTable();

		// set values
		User user = new User();
		user.setName("Hadas");
		user.setPassword("barel");

		// add user to data base
		try
		{
			model.addUser(user);
			// prints list of user items
			System.out.println(model.getItems(user));
			assertTrue(true);
		} catch (ToDoListException e)
		{
			// failed
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * test if addItem() method works
	 */
	@Test
	public void testAddItem()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		// clear user && item tables
		model.emptyUserTable();
		model.emptyItemTable();

		// set values
		User user = new User();
		user.setName("Hadas");
		user.setPassword("barel");

		// add user to data base
		try
		{
			model.addUser(user);
			System.out.println("testAddItem : Successfully created user - " + user.getName());
		} catch (ToDoListException e)
		{
			System.out.println("testAddItem : Failed to create user");
			e.printStackTrace();
			assertTrue(false);
			return;
		}

		// set values
		Item item = new Item();
		item.setAssignment("Buy Avocado");
		try
		{
			// set user id field in item
			for (User someone : model.getUsers())
			{
				if (someone.getName().equals("Hadas"))
				{
					item.setUserId(someone.getId());
					break;
				}
			}
			// add item to data base
			model.addItem(item);
			assertTrue(true);
		} catch (ToDoListException e)
		{
			// failed
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * test if deleteItem() method works
	 */
	@Test
	public void testDeleteItem()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		// clear user && item tables
		model.emptyUserTable();
		model.emptyItemTable();

		// set values
		User user = new User();
		user.setName("Hadas");
		user.setPassword("barel");

		// add user to data base
		try
		{
			model.addUser(user);
			System.out.println("testRemoveItem : Successfully created user - " + user.getName());
		} catch (ToDoListException e)
		{
			System.out.println("testRemoveItem : Failed to create user");
			e.printStackTrace();
			assertTrue(false);
			return;
		}

		// set values
		Item item = new Item();
		item.setAssignment("Buy Bread");
		try
		{
			// set user id field in item
			for (User someone : model.getUsers())
			{
				if (someone.getName().equals("Hadas"))
				{
					item.setUserId(someone.getId());
					break;
				}
			}// add item to data base
			model.addItem(item);
			assertTrue(true);
		} catch (ToDoListException e)
		{
			e.printStackTrace();
			assertTrue(false);
		}

		// delete item && user from data base
		try
		{
			model.deleteItem(item);
			model.deleteUser(user);
			System.out.println("testRemoveItem : Successfully Deleted User and item");
		} catch (ToDoListException e)
		{
			System.out.println("testRemoveItem : Unable to delete item");
			e.printStackTrace();
		}
	}

}
