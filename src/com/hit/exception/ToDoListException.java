package com.hit.exception;

/**
 * @author Hadas Barel
 * @author Daniel Altalat
 */
public class ToDoListException extends Exception
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that creates an exception
	 * @param msg
	 *            message that explains the exception
	 */
	public ToDoListException(String msg)
	{
		super(msg);
	}

	/**
	 * Constructor that creates an exception with a throwable parameter
	 * @param msg
	 *            message that explains the exception
	 * @param throwable
	 *            object to pass through the exception
	 */
	public ToDoListException(String msg, Throwable throwable)
	{
		super(msg, throwable);
	}
}
