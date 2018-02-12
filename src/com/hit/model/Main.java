package com.hit.model;

import java.util.List;

import com.hit.exception.ToDoListException;

public class Main
{

	public static void main(String[] args)
	{

		HibernateToDoListDAO htdl = HibernateToDoListDAO.getHibernateToDoListDAOInstance();
		htdl.emptyUserTable();
		htdl.emptyItemTable();
		
		User user = new User();

		try
		{
			User user3 = new User("daniel", "1234","da");
			htdl.addUser(user3);
			htdl.addUser(new User("ddd", "124","ddd"));
			htdl.addUser(new User("hsd", "q34","hah"));
			System.out.println(user3.getId());
			List<User> list = htdl.getUsers();
			for (User usr : list)
			{
				System.out.println(usr);
			}

			// htdl.deleteUser(user3);
			List<User> list1 = htdl.getUsers();
			User user4 = htdl.getUser("ddd");
			System.out.println(user4);

			System.out.println(htdl.login("ddd", "124"));

			htdl.updateUser(user3, "aaaa", "123456", "hadas@achalt.ta-rosh");
			System.out.println(user3);

			Item item = new Item(user3.getId(), "go home", "importent");
			htdl.addItem(item);
			Item item1 = new Item(user3.getId(), "and again go home!!!", "importent");
			htdl.addItem(item1);
			Item item2 = new Item(user4.getId(), "and again go home!!!", "importent");
			htdl.addItem(item2);

			// htdl.deleteItem(item1);
			htdl.deleteAllItems(user3);
			User user5 = new User("aaaba", "111111","aba");

			htdl.addUser(user5);
			htdl.addItem(item2);
			htdl.addItem(new Item(user3.getId(), "home sweet home", " very importent!"));
			htdl.addUser(user5); // Spouse to throw an error!!!

		} catch (ToDoListException e)
		{
			e.printStackTrace();
		}
	}

}
