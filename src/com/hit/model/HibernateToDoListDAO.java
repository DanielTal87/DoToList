package com.hit.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.hit.exception.ToDoListException;

/**
 * @author Daniel Tal
 * @author Hadas Barel
 */
public class HibernateToDoListDAO implements IToDoListDAO
{
	private SessionFactory				factory;
	public static HibernateToDoListDAO	HibernateToDoListDAOInstance	= null;

	public static HibernateToDoListDAO getHibernateToDoListDAOInstance()
	{
		if (HibernateToDoListDAOInstance == null)
		{
			HibernateToDoListDAOInstance = new HibernateToDoListDAO();
		}
		return HibernateToDoListDAOInstance;
	}

	private HibernateToDoListDAO()
	{
		factory = new AnnotationConfiguration().configure().buildSessionFactory();
	}

	@Override
	public void addUser(User user) throws ToDoListException
	{
		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			session.save(user);
			System.out.println(user);
			session.getTransaction().commit();
		} catch (HibernateException e)
		{
			if (session.getTransaction() != null){
				session.getTransaction().rollback();
			}
			throw new ToDoListException("ERROR! Unable to add the user");
		} finally
		{
			session.close();
		}
	}

	@Override
	public boolean deleteUser(User user) throws ToDoListException
	{
		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			session.delete(user);
			deleteAllItems(user);
			
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e)
		{
			if (session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}
			throw new ToDoListException("ERROR! Unable to delete the user");

		} finally
		{
			session.close();
		}
	}

	/** Update*/

	@Override
	public boolean updateUser(User user, String nameToUpdate, String passwordToUpdate,String email) throws ToDoListException
	{
		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			user.setName(nameToUpdate);
			user.setPassword(passwordToUpdate);
			user.setEmail(email);
			session.update(user);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e)
		{
			if (session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}
			throw new ToDoListException("ERROR! Unable to update the user");
		} finally
		{
			session.close();
		}

	}

	@Override
	public List<User> getUsers() throws ToDoListException
	{
		List<User> list = null;
		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			list = session.createQuery("from User").list();
			session.getTransaction().commit();
			if (list != null && list.isEmpty())
			{
				return null;
			} else
			{
				return list;
			}
		} catch (HibernateException e)
		{
			throw new ToDoListException("ERROR! Unable to get the users list from database");

		} finally
		{
			session.close();
		}
	}

	@Override
	public User getUser(String userName) throws ToDoListException
	{
		Session session = factory.openSession();
		try
		{
			List<User> list = null;
			User user = null;
			session.beginTransaction();
			list = session.createQuery("from User where name= '" + userName + "'").list();
			if (list != null && list.isEmpty())
			{
				return null;
			} else
				if (list.size() > 1) // Verifies that the user name is unique
				{
					throw new ToDoListException("ERROR! Returned more than one user");
				} else
				{
					user = list.get(0);
				}
			session.getTransaction().commit();
			return user;
		} catch (HibernateException e)
		{
			throw new ToDoListException("ERROR! Unable to get the user from the database");
		} finally
		{
			session.close();
		}
	}

	@Override
	public boolean login(String nameToCheck, String passwordToCheck) throws ToDoListException
	{
		try
		{
			User user = getUser(nameToCheck);
			if (user != null && user.getPassword().equals(passwordToCheck))
			{
				return true;
			}
		} catch (HibernateException e)
		{
			throw new ToDoListException("ERROR! the username or the password is incorrect");
		}
		return false;
	}

	@Override
	public Item getItem(String assignment, int userId) throws ToDoListException
	{

		Session session = factory.openSession();
		try
		{
			List<Item> list = null;
			Item item = null;
			session.beginTransaction();
			String query ="from Item where Assignment= '" + assignment + "'"+" and UserId= '" + userId + "'";
			list = session.createQuery(query).list();
			item = list.get(0);
			session.getTransaction().commit();
			return item;
		} catch (HibernateException e)
		{
			throw new ToDoListException("\"ERROR! Unable to get the item from the database");
		} finally
		{
			session.close();
		}
	}

	@Override
	public List<Item> getItems(User user) throws ToDoListException
	{
		List<Item> list = null;
		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			list = session.createQuery("from Item where userId= '" + user.getId() + "'").list();
			session.getTransaction().commit();
			if (list != null && list.isEmpty())
			{
				return new ArrayList<Item>();
			} else
			{
				return list;
			}
		} catch (HibernateException e)
		{
			throw new ToDoListException("ERROR! Unable to get the items list from database");

		} finally
		{
			session.close();
		}
	}

	@Override
	public void addItem(Item item) throws ToDoListException
	{
		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			session.save(item);
			System.out.println(item);
			session.getTransaction().commit();
		} catch (HibernateException e)
		{
			try{
				if (session.getTransaction() != null)
				{
					session.getTransaction().rollback();
				}
			}catch(HibernateException e1){
				throw new ToDoListException("ERROR! Unable to add the item");
			}
			throw new ToDoListException("ERROR! Unable to add the item");
		} finally
		{
			session.close();
		}
	}

	@Override
	public boolean deleteItem(Item item) throws ToDoListException
	{
		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			session.delete(item);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e)
		{
			if (session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}
			throw new ToDoListException("ERROR! Unable to delete the item");
		} finally
		{
			session.close();
		}
	}

	@Override
	public boolean deleteAllItems(User user) throws ToDoListException
	{
		List<Item> list = null;

		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			list = getItems(user);
			for (Item item : list)
			{
				session.delete(item);
			}
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e)
		{
			throw new ToDoListException("ERROR! Unable to delete all the user items");

		} finally
		{
			session.close();
		}
	}

	@Override
	public boolean updateItem(Item item, int userIdToUpdate, String assignmentToUpdate, String categoryToUpdate) throws ToDoListException
	{
		Session session = factory.openSession();
		try
		{
			session.beginTransaction();
			item.setUserId(userIdToUpdate);
			item.setAssignment(assignmentToUpdate);
			item.setCategory(categoryToUpdate);
			item.setLastmodify("");
			session.update(item);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e)
		{
			if (session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}
			throw new ToDoListException("ERROR! Unable to update the item");
		} finally
		{
			session.close();
		}
	}

	/**
	 * Empty the user table
	 * Does not drop the table, only clearing the content
	 */
	public void emptyUserTable()
	{
		Session session = factory.openSession();
		session.createSQLQuery("truncate table User").executeUpdate();
	}

	/**
	 * Empty the item table
	 * <p> Does not drop the table, only clearing the content
	 */
	public void emptyItemTable()
	{
		Session session = factory.openSession();
		session.createSQLQuery("truncate table Item").executeUpdate();
	}

	@Override
	public String getEmail(int i) throws ToDoListException {
		Session session = factory.openSession();
		List<User> user=null;
		try
		{
			session.beginTransaction();
			user = session.createQuery("from User where Id= " +i+"").list();
			session.getTransaction().commit();
			return user.get(0).getEmail();
		} catch (HibernateException e)
		{
			if (session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}
			throw new ToDoListException("ERROR! Unable to get email");
		} finally
		{
			session.close();
		}
	}

}
