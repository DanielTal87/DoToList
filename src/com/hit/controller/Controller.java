package com.hit.controller;

import com.hit.exception.ToDoListException;
import com.hit.model.HibernateToDoListDAO;
import com.hit.model.Item;
import com.hit.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ... login
 * 
 * @author Daniel Altalat
 * @author Hadas Barel
 */
@WebServlet("/Controller/*")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = -2912527655479010562L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName, password, email, assingment, category, oldAssingment, newUserName, idAssignment;
		int userId;
		User user;
		RequestDispatcher rd;
		HttpSession session = request.getSession();
		String action = findAction(request);
		HibernateToDoListDAO htdl = HibernateToDoListDAO.getHibernateToDoListDAOInstance();

		switch (action) {

		case "Admin":
			userName = request.getParameter("userNameAdmin");
			password = request.getParameter("passwordAdmin");
			if (userName.equals("admin") && password.equals("admin")) {
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/administratorPage.jsp");
				rd.forward(request, response);
			} else {
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/firstStart.jsp");
				rd.forward(request, response);
			}
			break;

		/**
		 * Sing-up a new user to the application data-base
		 */
		case "SignUp":
			userName = request.getParameter("username");
			password = request.getParameter("password");
			email = request.getParameter("email");
			user = new User(userName, password, email);
			try {
				htdl.addUser(user);
				session.setAttribute("userId", user.getId());
				response.addCookie(new Cookie("username", userName));
				session.setAttribute("username", userName);
				session.setAttribute("items", htdl.getItems(user));
				session.setAttribute("password", password);
				session.setAttribute("email", email);
				session.setAttribute("ButtonToClick", "null");
				// password=(String) session.getAttribute("password");
				// email=(String) session.getAttribute("email");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
				rd.forward(request, response);
			} catch (ToDoListException e) {
				session.setAttribute("Error", "an error has happend in SignUp"); // Daniel
				session.setAttribute("WhereToGoError", "FirstStart");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
				rd.forward(request, response);

			}
			break;

		/**
		 * Logs-in the user to the application
		 */
		case "LogIn":
			eraseCookie(request, response);
			rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
			rd.forward(request, response);
			break;

		case "Welcome":
			boolean userId1 = false;
			userName = (String) request.getParameter("username");
			password = (String) request.getParameter("password");
			try {
				userId1 = htdl.login(userName, password);
				if (userId1) {// user is already in DB
					user = htdl.getUser(userName);
					session.setAttribute("userId", user.getId());
					Cookie cookie = new Cookie("userName", userName);
					response.addCookie(cookie);
					session.setAttribute("username", userName);
					session.setAttribute("items", htdl.getItems(user));
					session.setAttribute("password", password);
					session.setAttribute("email", (String) htdl.getEmail((int) session.getAttribute("userId")));
					// password=(String) session.getAttribute("password");
					// email=(String) session.getAttribute("email");
					session.setAttribute("ButtonToClick", "null");// with out
																	// popUp
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/welcome.jsp");
					rd.forward(request, response);
				} else { // user is not found
					session.setAttribute("Error", "Your user name is not found");
					session.setAttribute("WhereToGoError", "FirstStart");
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);
				}
			} catch (ToDoListException e) {
				session.setAttribute("Error", "an error has happend in LogIn");
				session.setAttribute("WhereToGoError", "FirstStart");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
				rd.forward(request, response);
			}
			break;
		case "AddItem":
			userId = (int) session.getAttribute("userId");
			assingment = (String) request.getParameter("assingment");
			category = (String) request.getParameter("category");
			try {
				Item temp = new Item(userId, assingment, category);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate localDate = LocalDate.now();
				temp.setLastmodify(dtf.format(localDate));
				htdl.addItem(temp);
				user = htdl.getUser((String) session.getAttribute("username"));
				session.setAttribute("items", htdl.getItems(user));
				session.setAttribute("ButtonToClick", "null");// with out popUp
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
				rd.forward(request, response);
			} catch (ToDoListException e) {
				session.setAttribute("Error", "an error has happend in AddItem");
				session.setAttribute("WhereToGoError", "Items");
				session.setAttribute("ButtonToClick", "AddItem");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
				rd.forward(request, response);
			}
			break;

		case "UpdateItem":
			userId = (int) session.getAttribute("userId");
			idAssignment = request.getParameter("idAssignmentUpdate");
			assingment = request.getParameter("assingmentToUpdate");
			category = request.getParameter("categoryToUpdate");
			Item itemToUpdate = null;
			try {
				user = htdl.getUser((String) session.getAttribute("username"));
				oldAssingment = htdl.getItems(user).get(Integer.valueOf(idAssignment)).getAssignment();
				itemToUpdate = htdl.getItem(oldAssingment, userId);
				htdl.updateItem(itemToUpdate, userId, assingment, category);
				user = htdl.getUser((String) session.getAttribute("username"));
				session.setAttribute("items", htdl.getItems(user));
				session.setAttribute("ButtonToClick", "null");// with out popUp
				session.removeAttribute("oldAssingment");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
				rd.forward(request, response);
			} catch (ToDoListException e) {
				session.setAttribute("Error", "an error has happend in UpdateItem");
				session.removeAttribute("oldAssingment");
				session.setAttribute("WhereToGoError", "Items");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
				rd.forward(request, response);
			}
			break;

		case "DeleteItem":
			userId = (int) session.getAttribute("userId");
			idAssignment = (String) request.getParameter("idAssignmentDelete");
			if (idAssignment == "") {
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
				rd.forward(request, response);
			} else {
				Item item = null;
				try {
					user = htdl.getUser((String) session.getAttribute("username"));
					assingment = htdl.getItems(user).get(Integer.valueOf(idAssignment)).getAssignment();
					item = htdl.getItem(assingment, userId);
					htdl.deleteItem(item);
					session.removeAttribute("assingment");
					user = htdl.getUser((String) session.getAttribute("username"));
					session.setAttribute("items", htdl.getItems(user));
					session.setAttribute("ButtonToClick", "null");// with out
																	// popUp
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
					rd.forward(request, response);
				} catch (ToDoListException e) {
					session.setAttribute("Error", "an error has happend in DeleteItem");
					session.setAttribute("WhereToGoError", "Items");
					rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
					rd.forward(request, response);
				}
			}
			break;

		case "DeleteAllItem":
			try {
				user = htdl.getUser((String) session.getAttribute("username"));
				htdl.deleteAllItems(user);
				session.setAttribute("items", htdl.getItems(user));
				session.setAttribute("ButtonToClick", "null");// with out popUp
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
				rd.forward(request, response);
			} catch (ToDoListException e) {
				session.setAttribute("Error", "an error has happend in DeleteAllItem");
				session.setAttribute("WhereToGoError", "Items");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
				rd.forward(request, response);
			}
			break;

		case "DeleteUser":
			try {
				user = htdl.getUser((String) session.getAttribute("username"));
				htdl.deleteUser(user);
				session.setAttribute("ButtonToClick", "null");// with out popUp
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/firstStart.jsp");
				rd.forward(request, response);
			} catch (ToDoListException e) {
				session.setAttribute("Error", "an error has happend in DeleteUser");
				session.setAttribute("WhereToGoError", "Items");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
				rd.forward(request, response);
			}
			break;

		case "UpdateUser":
			newUserName = (String) request.getParameter("newUserName");
			password = (String) request.getParameter("password");
			email = (String) request.getParameter("email");
			try {
				user = htdl.getUser((String) session.getAttribute("username"));// oldUserName
				htdl.updateUser(user, newUserName, password, email);
				session.setAttribute("username", newUserName);
				session.setAttribute("password", password);
				session.setAttribute("email", email);
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
				rd.forward(request, response);
			} catch (ToDoListException e) {
				session.setAttribute("Error", "an error has happend in UpdateUser");
				session.setAttribute("WhereToGoError", "Items");
				rd = request.getServletContext().getRequestDispatcher("/JSPFiles/error.jsp");
				rd.forward(request, response);
			}
			break;
		case "FirstStart":
			rd = request.getServletContext().getRequestDispatcher("/JSPFiles/firstStart.jsp");
			rd.forward(request, response);

			break;

		case "Items":
			rd = request.getServletContext().getRequestDispatcher("/JSPFiles/items.jsp");
			rd.forward(request, response);
			break;

		case "Tip":
			rd = request.getServletContext().getRequestDispatcher("/JSPFiles/dailyTip.jsp");
			rd.forward(request, response);
			break;
		/**
		 * Logs-out the user from the application & redirect to re-login
		 */
		case "LogOut":
			session.removeAttribute("username");
			session.removeAttribute("userId");
			session.removeAttribute("items");
			// response.sendRedirect("JSPFiles/firstStart.jsp");
			eraseCookie(request, response);
			rd = request.getServletContext().getRequestDispatcher("/JSPFiles/firstStart.jsp");
			rd.forward(request, response);
			break;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public String findAction(HttpServletRequest request) {
		String action = null;
		if (request.getRequestURI().endsWith("LogIn")) {
			action = "LogIn";
		} else {
			if (request.getRequestURI().endsWith("SignUp")) {
				action = "SignUp";
			} else {
				if (request.getRequestURI().endsWith("AddItem")) {
					action = "AddItem";
				} else {
					if (request.getRequestURI().endsWith("UpdateItem")) {
						action = "UpdateItem";
					} else {
						if (request.getRequestURI().endsWith("DeleteItem")) {
							action = "DeleteItem";
						} else {
							if (request.getRequestURI().endsWith("DeleteAllItem")) {
								action = "DeleteAllItem";
							} else {
								if (request.getRequestURI().endsWith("DeleteUser")) {
									action = "DeleteUser";
								} else {
									if (request.getRequestURI().endsWith("UpdateUser")) {
										action = "UpdateUser";
									} else {
										if (request.getRequestURI().endsWith("FirstStart")) {
											action = "FirstStart";
										} else {
											if (request.getRequestURI().endsWith("items")) {
												action = "Items";
											} else {
												if (request.getRequestURI().endsWith("LogOut")) {
													action = "LogOut";
												} else {
													if (request.getRequestURI().endsWith("Admin")) {
														action = "Admin";
													} else {
														if (request.getRequestURI().endsWith("Tip")) {
															action = "Tip";
														} else {
															if (request.getRequestURI().endsWith("Welcome")) {
																action = "Welcome";
															} else {
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

	private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userName")) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
			}

	}

}
