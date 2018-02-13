package com.hit.controller;

import com.hit.exception.ToDoListException;
import com.hit.model.HibernateToDoListDAO;
import com.hit.model.IToDoListDAO;
import com.hit.model.Item;
import com.hit.model.User;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The Controller in the MVC architecture
 * @author Daniel Tal
 * @author Hadas Barel
 */
@WebServlet("/Controller/*")
public class Controller extends HttpServlet
{
	private static final long serialVersionUID = -2912527655479010562L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String userName, password, email, assingment, category, oldAssingment, newUserName, idAssignment;
		int userId;
		User user;
		RequestDispatcher rd;
		HttpSession session = request.getSession();
		String action = findAction(request);
		IToDoListDAO htdl = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		// According to the action, the controller will give an answer
		switch (action)
		{

			/**
			 * Logs-in the administrator to the 'Administrator page'
			 */
			case "Admin":

				// get parameters from jsp files
				userName = request.getParameter("userNameAdmin");
				password = request.getParameter("passwordAdmin");

				// if user name and password are valid we will refer to administrator page,
				// otherwise we will refer to firstStart page
				if (userName.equals("admin") && password.equals("admin"))
				{
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/administratorPage.jsp");
					rd.forward(request, response);
				} else
				{
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/firstStart.jsp");
					rd.forward(request, response);
				}
				break;

			/**
			 * Sing-up a new user to the application data-base
			 */
			case "SignUp":

				// get parameters from jsp files
				userName = request.getParameter("username");
				password = request.getParameter("password");
				email = request.getParameter("email");
				user = new User(userName, password, email);
				try
				{

					// all the commands here will be executed if the addUser() method succeeded
					htdl.addUser(user);
					session.setAttribute("userId", user.getId());
					response.addCookie(new Cookie("username", userName));
					session.setAttribute("username", userName);
					session.setAttribute("items", htdl.getItems(user));
					session.setAttribute("password", password);
					session.setAttribute("email", email);
					session.setAttribute("ButtonToClick", "null");// with out popUp in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
					rd.forward(request, response);
				} catch (ToDoListException e)
				{

					// an exception occurred
					// turn to error page
					session.setAttribute("Error", "an error has happend in SignUp");
					session.setAttribute("WhereToGoError", "FirstStart");
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);

				}
				break;

			/**
			 * Logs-in the user to the application
			 */
			case "LogIn":
				// we will turn to items page (the main page for each user)
				eraseCookie(request, response);
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
				rd.forward(request, response);
				break;

			/**
			 * Opens a welcome pop-up
			 */
			case "Welcome":
				boolean userIdForCheck = false;

				// get parameters from jsp files
				userName = (String)request.getParameter("username");
				password = (String)request.getParameter("password");
				try
				{
					userIdForCheck = htdl.login(userName, password);
					if (userIdForCheck)
					{
						// user is already in data base
						user = htdl.getUser(userName);
						session.setAttribute("userId", user.getId());
						Cookie cookie = new Cookie("username", userName);
						response.addCookie(cookie);
						session.setAttribute("username", userName);
						session.setAttribute("items", htdl.getItems(user));
						session.setAttribute("password", password);
						session.setAttribute("email", (String)htdl.getEmail((int)session.getAttribute("userId")));
						session.setAttribute("ButtonToClick", "null");// with out popUp in items page

						// turn to welcome page - pop up page that shows user how many tasks remained
						// for him
						rd = request.getServletContext().getRequestDispatcher("/JSPFiles/welcome.jsp");
						rd.forward(request, response);
					} else
					{
						// user is not found, turn to error page
						session.setAttribute("Error", "Your user name is not found");
						session.setAttribute("WhereToGoError", "FirstStart");
						rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
						rd.forward(request, response);
					}
				} catch (ToDoListException e)
				{
					// an exception occurred
					// turn to error page
					session.setAttribute("Error", "an error has happend in LogIn");
					session.setAttribute("WhereToGoError", "FirstStart");
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);
				}
				break;

			/**
			 * Add item to the list
			 */
			case "AddItem":

				userId = (int)session.getAttribute("userId");
				// get parameters from jsp files
				assingment = (String)request.getParameter("assingment");
				category = (String)request.getParameter("category");
				try
				{
					// all the commands here will be executed if the addItem() method succeeded
					Item temp = new Item(userId, assingment, category);
					htdl.addItem(temp);
					user = htdl.getUser((String)session.getAttribute("username"));
					session.setAttribute("items", htdl.getItems(user));
					session.setAttribute("ButtonToClick", "null");// with out popUp in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
					rd.forward(request, response);
				} catch (ToDoListException e)
				{
					// an exception occurred
					// turn to error page
					session.setAttribute("Error", "an error has happend in AddItem");
					session.setAttribute("WhereToGoError", "Items");
					session.setAttribute("ButtonToClick", "AddItem");// with popUp in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);
				}
				break;

			/**
			 * Update item from the list
			 */
			case "UpdateItem":
				userId = (int)session.getAttribute("userId");
				// get parameters from jsp files
				idAssignment = request.getParameter("idAssignmentUpdate");
				assingment = request.getParameter("assingmentToUpdate");
				category = request.getParameter("categoryToUpdate");
				Item itemToUpdate = null;
				try
				{
					// if updateItem() method succeeded
					user = htdl.getUser((String)session.getAttribute("username"));
					oldAssingment = htdl.getItems(user).get(Integer.valueOf(idAssignment)).getAssignment();
					itemToUpdate = htdl.getItem(oldAssingment, userId);
					htdl.updateItem(itemToUpdate, userId, assingment, category);
					user = htdl.getUser((String)session.getAttribute("username"));
					session.setAttribute("items", htdl.getItems(user));
					session.setAttribute("ButtonToClick", "null");// with out popUp in items page
					session.removeAttribute("oldAssingment");
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
					rd.forward(request, response);
				} catch (ToDoListException e)
				{
					// an exception occurred, turn to error page
					session.setAttribute("Error", "an error has happend in UpdateItem");
					session.removeAttribute("oldAssingment");
					session.setAttribute("WhereToGoError", "Items"); // with pop-up in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);
				}
				break;

			/**
			 * Delete item from the list
			 */
			case "DeleteItem":
				userId = (int)session.getAttribute("userId");
				// get parameters from jsp files
				idAssignment = (String)request.getParameter("idAssignmentDelete");
				if (idAssignment == "")
				{
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
					rd.forward(request, response);
				} else
				{
					Item item = null;
					try
					{
						// all the commands here will be executed if the deleteItem() method succeeded
						user = htdl.getUser((String)session.getAttribute("username"));
						assingment = htdl.getItems(user).get(Integer.valueOf(idAssignment)).getAssignment();
						item = htdl.getItem(assingment, userId);
						htdl.deleteItem(item);
						session.removeAttribute("assingment");
						user = htdl.getUser((String)session.getAttribute("username"));
						session.setAttribute("items", htdl.getItems(user));
						session.setAttribute("ButtonToClick", "null");// with out popUp in items page
						rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
						rd.forward(request, response);
					} catch (ToDoListException e)
					{
						// an exception occurred
						// turn to error page
						session.setAttribute("Error", "an error has happend in DeleteItem");
						session.setAttribute("WhereToGoError", "Items");// with popUp in items page
						rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
						rd.forward(request, response);
					}
				}
				break;

			/**
			 * Delete all items from the list
			 */
			case "DeleteAllItem":
				try
				{
					user = htdl.getUser((String)session.getAttribute("username"));
					// all the commands here will be executed if the deleteAllItem() method
					// succeeded
					htdl.deleteAllItems(user);
					session.setAttribute("items", htdl.getItems(user));
					session.setAttribute("ButtonToClick", "null");// with out popUp in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
					rd.forward(request, response);
				} catch (ToDoListException e)
				{
					// an exception occurred
					// turn to error page
					session.setAttribute("Error", "an error has happend in DeleteAllItem");
					session.setAttribute("WhereToGoError", "Items");// with popUp in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);
				}
				break;

			/**
			 * Delete user account
			 */
			case "DeleteUser":
				try
				{
					user = htdl.getUser((String)session.getAttribute("username"));
					// all the commands here will be executed if the deleteUser() method succeeded
					htdl.deleteUser(user);
					session.setAttribute("ButtonToClick", "null");// with out popUp in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/firstStart.jsp");
					rd.forward(request, response);
				} catch (ToDoListException e)
				{
					// an exception occurred
					// turn to error page
					session.setAttribute("Error", "an error has happend in DeleteUser");
					session.setAttribute("WhereToGoError", "Items");// with popUp in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);
				}
				break;

			/**
			 * Update user account
			 */
			case "UpdateUser":

				// get parameters from jsp files
				newUserName = (String)request.getParameter("newUserName");
				password = (String)request.getParameter("password");
				email = (String)request.getParameter("email");
				try
				{
					// all the commands here will be executed if the updateUser() method succeeded
					user = htdl.getUser((String)session.getAttribute("username"));// oldUserName
					htdl.updateUser(user, newUserName, password, email);
					session.setAttribute("username", newUserName);
					session.setAttribute("password", password);
					session.setAttribute("email", email);
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
					rd.forward(request, response);
				} catch (ToDoListException e)
				{
					// an exception occurred
					// turn to error page
					session.setAttribute("Error", "an error has happend in UpdateUser");
					session.setAttribute("WhereToGoError", "Items");// with popUp in items page
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);
				}
				break;

			/**
			 * When an error occurred in the user log-in or sign-up
			 */
			case "FirstStart":
				// turn to first start page
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/firstStart.jsp");
				rd.forward(request, response);
				break;

			/**
			 * When an error occurred in the main page (items)
			 */
			case "Items":
				// turn to items page
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
				rd.forward(request, response);
				break;

			/**
			 * Opens the tip page
			 */
			case "Tip":
				// turn to daily tip page
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/dailyTip.jsp");
				rd.forward(request, response);
				break;

			/**
			 * Logs-out the user from the application & redirect to re-login
			 */
			case "LogOut":
				// remove attributes from session, turn to first start page
				session.removeAttribute("username");
				session.removeAttribute("userId");
				session.removeAttribute("items");
				eraseCookie(request, response);
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/firstStart.jsp");
				rd.forward(request, response);
				break;

			/**
			 * When action is null
			 */
			default:
				session.setAttribute("Error", "an error has happend");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
				rd.forward(request, response);
				break;
		} // END-of switch

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	/**
	 * Checks the request source, by the request end
	 * @return action the action accordingly to the request end
	 */
	public String findAction(HttpServletRequest request)
	{
		String action = null;
		if (request.getRequestURI().endsWith("LogIn"))
		{
			action = "LogIn";
		} else
		{
			if (request.getRequestURI().endsWith("SignUp"))
			{
				action = "SignUp";
			} else
			{
				if (request.getRequestURI().endsWith("AddItem"))
				{
					action = "AddItem";
				} else
				{
					if (request.getRequestURI().endsWith("UpdateItem"))
					{
						action = "UpdateItem";
					} else
					{
						if (request.getRequestURI().endsWith("DeleteItem"))
						{
							action = "DeleteItem";
						} else
						{
							if (request.getRequestURI().endsWith("DeleteAllItem"))
							{
								action = "DeleteAllItem";
							} else
							{
								if (request.getRequestURI().endsWith("DeleteUser"))
								{
									action = "DeleteUser";
								} else
								{
									if (request.getRequestURI().endsWith("UpdateUser"))
									{
										action = "UpdateUser";
									} else
									{
										if (request.getRequestURI().endsWith("FirstStart"))
										{
											action = "FirstStart";
										} else
										{
											if (request.getRequestURI().endsWith("items"))
											{
												action = "Items";
											} else
											{
												if (request.getRequestURI().endsWith("LogOut"))
												{
													action = "LogOut";
												} else
												{
													if (request.getRequestURI().endsWith("Admin"))
													{
														action = "Admin";
													} else
													{
														if (request.getRequestURI().endsWith("Tip"))
														{
															action = "Tip";
														} else
														{
															if (request.getRequestURI().endsWith("Welcome"))
															{
																action = "Welcome";
															} else
															{
																action = "Error";
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return action;
	}

	/**
	 * Erase the cookie
	 */
	private void eraseCookie(HttpServletRequest req, HttpServletResponse resp)
	{
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals("username"))
				{
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					resp.setContentType("text/html");
					resp.addCookie(cookie);
				}
			}
	}
}
