package com.hit.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.hit.model.HibernateToDoListDAO;
import com.hit.model.Item;
import com.hit.model.User;
import com.hit.exception.ToDoListException;

/**
 * <b> Pre running Instruction:</b>
 * <p> 1. Make sure MAMP application is running
 * <p> 2. MAske sure both Apace and MySQL servers are connected
 * <p> 3. Verify that "ToDoList" database does exist 
 * <p> 4. Run
 * 
 * @author Daniel Altalat
 * @author Hadas Barel
 */
public class HibernateToDoListDAOTest
{

	@Test
	public void testGetInstance()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();
		if (model != null)
			assertTrue(true);
		else
			assertTrue(false);
	}

	@Test
	public void testAddUser()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		model.emptyUserTable();

		User user = new User();
		user.setName("Daniel");
		user.setPassword("Tal");

		try
		{
			model.addUser(user);
			System.out.println("testAddUser : Created user");
			assertTrue(true);
		} catch (ToDoListException e)
		{
			System.out.println("testAddUser : Failed to create user");
			e.printStackTrace();
			assertTrue(false);
			return;
		}
	}

	@Test
	public void testDeleteUser()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		model.emptyUserTable();

		User user = new User();
		user.setName("Daniel");
		user.setPassword("Tal");

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
			model.deleteUser(user);
			System.out.println("testDeleteUser : Successfully Deleted User - " + user.getName());
			assertTrue(true);
		} catch (ToDoListException e)
		{
			System.out.println("testDeleteUser : Failed to delete user");
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testGetUsers()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		model.emptyUserTable();

		User user1 = new User();
		user1.setName("Daniel");
		user1.setPassword("Tal");

		User user2 = new User();
		user2.setName("Hadas");
		user2.setPassword("Barel");

		try
		{
			model.addUser(user1);
			model.addUser(user2);
			System.out.println("testGetUsers : Successfully created users");
		} catch (ToDoListException e)
		{
			System.out.println("testGetUsers : Failed to create users");
			e.printStackTrace();
			assertTrue(false);
			return;
		}

		ArrayList<User> allUsers = new ArrayList<User>();
		try
		{
			allUsers.addAll(model.getUsers());
			System.out.println("testGetUsers : Successfully Retrieved a list of users - " + allUsers);
			assertTrue(true);
		} catch (ToDoListException e)
		{
			System.out.println("testGetUsers : Failed to retrieve a list of users.");
			e.printStackTrace();
			assertTrue(false);
			return;
		}

		try
		{
			for (User user : allUsers)
			{
				model.deleteUser(user);
				System.out.println("testGetUsers : Successfully Deleted User - " + user.getName());
			}
		} catch (ToDoListException e)
		{
			System.out.println("testGetUsers : Unable to delete users");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetItems()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		model.emptyUserTable();
		model.emptyItemTable();

		User user = new User();
		user.setName("Hadas");
		user.setPassword("barel");

		try
		{
			model.addUser(user);
			System.out.println(model.getItems(user));
			assertTrue(true);
		} catch (ToDoListException e)
		{
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testAddItem()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		model.emptyUserTable();
		model.emptyItemTable();

		User user = new User();
		user.setName("Hadas");
		user.setPassword("barel");

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

		Item item = new Item();
		item.setAssignment("Buy Avocado");
		try
		{
			for (User someone : model.getUsers())
			{
				if (someone.getName().equals("Hadas"))
				{
					item.setUserId(someone.getId());
					break;
				}
			}
			model.addItem(item);
			assertTrue(true);
		} catch (ToDoListException e)
		{
			e.printStackTrace();
			assertTrue(false);
		}

		try
		{
			model.deleteItem(item);
			model.deleteUser(user);
			System.out.println("testAddItem : Successfully Deleted User and item");
		} catch (ToDoListException e)
		{
			System.out.println("testAddItem : Unable to delete item");
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteItem()
	{
		HibernateToDoListDAO model = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		model.emptyUserTable();
		model.emptyItemTable();

		User user = new User();
		user.setName("Hadas");
		user.setPassword("barel");

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

		Item item = new Item();
		item.setAssignment("Buy Bread");
		try
		{
			for (User someone : model.getUsers())
			{
				if (someone.getName().equals("Hadas"))
				{
					item.setUserId(someone.getId());
					break;
				}
			}
			model.addItem(item);
			assertTrue(true);
		} catch (ToDoListException e)
		{
			e.printStackTrace();
			assertTrue(false);
		}

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
