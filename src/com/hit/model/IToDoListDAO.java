package com.hit.model;

import java.util.List;

import com.hit.exception.ToDoListException;

/**
 * The project main API
 * @author Daniel Tal
 * @author Hadas Barel
 */
public interface IToDoListDAO
{
	String getEmail(int userId) throws ToDoListException;

	/**
	 * add new user to the database, user names are unique
	 * @param user
	 *            the user to add
	 */
	void addUser(User user) throws ToDoListException;

	/**
	 * delete existing user from the database
	 * @param user
	 *            the user to delete
	 * @return true if the user deleted successfully, false otherwise
	 */
	public boolean deleteUser(User user) throws ToDoListException;

	/**
	 * update existing user from the database
	 * @param user
	 *            the user to update
	 * @return true if the user updated successfully, false otherwise
	 */
	public boolean updateUser(User user, String nameToUpdate, String passwordToUpdate, String email) throws ToDoListException;

	/**
	 * get list of users from the database
	 * @return return the list of all users from the database
	 */
	public List<User> getUsers() throws ToDoListException;

	/**
	 * get a user by its user name
	 * @param userName
	 *            the user name
	 * @return the user, null if it was not found
	 */
	public User getUser(String userName) throws ToDoListException;

	/**
	 * given user with its user name and password, find the user in the database by its name and match between
	 * the passwords
	 * @param user
	 *            the user to check
	 * @return true if the given user name and the password are in the DB, false otherwise
	 */
	public boolean login(String nameToCheck, String passwordToCheck) throws ToDoListException;

	/**
	 * get a item by its item assignment
	 * @param assignment
	 *            the assignment
	 * @return the item, null if it was not found
	 */
	public Item getItem(String assignment, int id) throws ToDoListException;

	/**
	 * get the list of all to-do items in the DB
	 * @return the list of all to-do items in the DB
	 */
	public List<Item> getItems(User user) throws ToDoListException;

	/**
	 * add a new item to the database
	 * @param item
	 *            the to-do item to add
	 */
	public void addItem(Item item) throws ToDoListException;

	/**
	 * delete an item
	 * @param item
	 *            the item to delete
	 * @return true if the item was deleted. false otherwise.
	 */
	public boolean deleteItem(Item item) throws ToDoListException;

	/**
	 * update a to-do item in the database with the changes done in the given to-do item
	 * @param item
	 *            the item to update
	 * @return true if the update was successful. false otherwise
	 */
	public boolean updateItem(Item item, int userIdToUpdate, String assignmentToUpdate, String categoryToUpdate) throws ToDoListException;

	/**
	 * delete all items from specific user
	 * @param user
	 *            the user who needs to delete all his items
	 * @return true if the delete was successful. false otherwise
	 */
	public boolean deleteAllItems(User user) throws ToDoListException;

}
