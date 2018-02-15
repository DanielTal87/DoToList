package com.hit.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	/**
	 * Session Factory, use to get connection from data base
	 */
	private SessionFactory				factory;

	/**
	 * first instance, initialized to null
	 */
	public static HibernateToDoListDAO	HibernateToDoListDAOInstance	= null;

	/**
	 * Static method, to get the current instance of this class
	 */
	public static HibernateToDoListDAO getHibernateToDoListDAOInstance()
	{
		if (HibernateToDoListDAOInstance == null)
		{
			HibernateToDoListDAOInstance = new HibernateToDoListDAO();
		}
		return HibernateToDoListDAOInstance;
	}

	/**
	 * Private constructor
	 */
	private HibernateToDoListDAO()
	{
		factory = new AnnotationConfiguration().configure().buildSessionFactory();
	}

	@Override
	public void addUser(User user) throws ToDoListException
	{
		if (user != null)
		{
			Session session = factory.openSession();
			try
			{
				session.beginTransaction();
				// add user to data base
				session.save(user);
				session.getTransaction().commit();
			} catch (HibernateException e)
			{
				if (session.getTransaction() != null)
				{
					session.getTransaction().rollback();
				}
				throw new ToDoListException("ERROR! Unable to add the user", e);
			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! user value is null");
		}
	}

	@Override
	public boolean deleteUser(User user) throws ToDoListException
	{
		if (user != null)
		{
			Session session = factory.openSession();
			try
			{
				session.beginTransaction();
				// delete user from DB && delete all his items
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
				throw new ToDoListException("ERROR! Unable to delete the user", e);

			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! user value is null");
		}
	}

	@Override
	public boolean updateUser(User user, String nameToUpdate, String passwordToUpdate, String email) throws ToDoListException
	{
		if (user != null)
		{
			Session session = factory.openSession();
			try
			{
				session.beginTransaction();
				// update all user fields
				user.setName(nameToUpdate);
				user.setPassword(passwordToUpdate);
				user.setEmail(email);
				// update
				session.update(user);
				session.getTransaction().commit();
				return true;
			} catch (HibernateException e)
			{
				if (session.getTransaction() != null)
				{
					session.getTransaction().rollback();
				}
				throw new ToDoListException("ERROR! Unable to update the user", e);
			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! user value is null");
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
			// get list of users
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
			throw new ToDoListException("ERROR! Unable to get the users list from database", e);

		} finally
		{
			session.close();
		}
	}

	@Override
	public User getUser(String userName) throws ToDoListException
	{
		if (!userName.isEmpty() && userName != null)
		{
			Session session = factory.openSession();
			try
			{
				List<User> list = null;
				User user = null;
				session.beginTransaction();
				// get list of users by its user name
				list = session.createQuery("from User where name= '" + userName + "'").list();
				if (list != null && list.isEmpty())
				{
					return null;
				} else
					if (list.size() > 1) // Verifies that the user name is
					                     // unique
					{
						throw new ToDoListException("ERROR! Returned more than one user");
					} else
					{
						// return the only item in the list
						user = list.get(0);
					}
				session.getTransaction().commit();
				return user;
			} catch (HibernateException e)
			{
				throw new ToDoListException("ERROR! Unable to get the user from the database", e);
			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! Unable to get the user");
		}
	}

	@Override
	public boolean login(String nameToCheck, String passwordToCheck) throws ToDoListException
	{
		try
		{
			User user = getUser(nameToCheck);
			// checks if user is in DB
			if (user != null && user.getPassword().equals(passwordToCheck))
			{
				return true;
			}
		} catch (HibernateException e)
		{
			throw new ToDoListException("ERROR! the username or the password is incorrect", e);
		}
		return false;
	}

	@Override
	public Item getItem(String assignment, int userId) throws ToDoListException
	{
		if (!assignment.isEmpty() && assignment != null)
		{
			Session session = factory.openSession();
			try
			{
				List<Item> list = null;
				Item item = null;
				session.beginTransaction();
				// get list of item by assignment and user id
				list = session.createQuery("from Item where Assignment= '" + assignment + "'" + " and UserId= '" + userId + "'").list();
				if (list != null && list.isEmpty())
				{
					return null;
				} else
					if (list.size() > 1) // Verifies that the user name is
					                     // unique
					{
						throw new ToDoListException("ERROR! Returned more than one item");
					} else
					{
						// return the only item in the list
						item = list.get(0);
					}
				session.getTransaction().commit();
				return item;
			} catch (HibernateException e)
			{
				throw new ToDoListException("ERROR! Unable to get the item from the database", e);
			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! Unable to get the item from the database");
		}
	}

	@Override
	public List<Item> getItems(User user) throws ToDoListException
	{
		if (user != null)
		{
			List<Item> list = null;
			Session session = factory.openSession();
			try
			{
				session.beginTransaction();
				// get list of items by user id
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
				throw new ToDoListException("ERROR! Unable to get the items list from database", e);

			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! Unable to get the items list from database, user value is null");
		}
	}

	@Override
	public void addItem(Item item) throws ToDoListException
	{
		if (item != null)
		{
			Session session = factory.openSession();
			try
			{
				session.beginTransaction();
				// when user add item, the date of the current day will also enter
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate localDate = LocalDate.now();
				item.setLastmodify(dtf.format(localDate));
				// add item
				session.save(item);
				session.getTransaction().commit();
			} catch (HibernateException e)
			{
				try
				{
					if (session.getTransaction() != null)
					{
						session.getTransaction().rollback();
					}
				} catch (HibernateException e1)
				{
					throw new ToDoListException("ERROR! Unable to add the item", e1);
				}
				throw new ToDoListException("ERROR! Unable to add the item", e);
			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! Unable to add the item, the value is null");
		}
	}

	@Override
	public boolean deleteItem(Item item) throws ToDoListException
	{
		if (item != null)
		{
			Session session = factory.openSession();
			try
			{
				session.beginTransaction();
				// delete specific item
				session.delete(item);
				session.getTransaction().commit();
				return true;
			} catch (HibernateException e)
			{
				if (session.getTransaction() != null)
				{
					session.getTransaction().rollback();
				}
				throw new ToDoListException("ERROR! Unable to delete the item", e);
			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! Unable to delete the item, the value is null");
		}
	}

	@Override
	public boolean deleteAllItems(User user) throws ToDoListException
	{
		if (user != null)
		{
			List<Item> list = null;
			Session session = factory.openSession();
			try
			{
				session.beginTransaction();
				// get list of user items
				list = getItems(user);
				// delete all items
				for (Item item : list)
				{
					session.delete(item);
				}
				session.getTransaction().commit();
				return true;
			} catch (HibernateException e)
			{
				throw new ToDoListException("ERROR! Unable to delete all the user items", e);

			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! Unable to delete all the user items,user value is null");
		}
	}

	@Override
	public boolean updateItem(Item item, int userIdToUpdate, String assignmentToUpdate, String categoryToUpdate) throws ToDoListException
	{
		if (item != null)
		{
			Session session = factory.openSession();
			try
			{
				session.beginTransaction();
				// update all item fields
				item.setUserId(userIdToUpdate);
				item.setAssignment(assignmentToUpdate);
				item.setCategory(categoryToUpdate);
				// when user update item, the "lastmodify" will also update
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate localDate = LocalDate.now();
				item.setLastmodify(dtf.format(localDate));
				// update item
				session.update(item);
				session.getTransaction().commit();
				return true;
			} catch (HibernateException e)
			{
				if (session.getTransaction() != null)
				{
					session.getTransaction().rollback();
				}
				throw new ToDoListException("ERROR! Unable to update the item", e);
			} finally
			{
				session.close();
			}
		} else
		{
			throw new ToDoListException("ERROR! Unable to update the item, item value is null");
		}
	}

	@Override
	public String getEmail(int userId) throws ToDoListException
	{
		Session session = factory.openSession();
		List<User> user = null;
		String email = null;
		try
		{
			session.beginTransaction();
			// get list of email's by user id
			user = session.createQuery("from User where Id= " + userId + "").list();
			if (user != null && user.isEmpty())
			{
				return null;
			} else
				if (user.size() > 1) // Verifies that the user name is unique
				{
					throw new ToDoListException("ERROR! Returned more than one email");
				} else
				{
					// return the only email in the list
					email = user.get(0).getEmail();
				}
			session.getTransaction().commit();
			return email;
		} catch (HibernateException e)
		{
			if (session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}
			throw new ToDoListException("ERROR! Unable to get email", e);
		} finally
		{
			session.close();
		}
	}

	/* ~~~~~~~~~~~~~~~ Only for Tests ~~~~~~~~~~~~~~~ */

	/**
	 * Empty the item table
	 * <p>
	 * Does not drop the table, only clearing the content
	 */
	public void emptyItemTable()
	{
		Session session = factory.openSession();
		session.createSQLQuery("truncate table User").executeUpdate();
	}

	/**
	 * Empty the user table Does not drop the table, only clearing the content
	 */
	public void emptyUserTable()
	{
		Session session = factory.openSession();
		session.createSQLQuery("truncate table Item").executeUpdate();
	}

}
