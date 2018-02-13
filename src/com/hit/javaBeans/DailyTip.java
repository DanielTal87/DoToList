package com.hit.javaBeans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

/**
 * The Daily tip is a utility for java-beans
 * 
 * @author Daniel Tal
 * @author Hadas Barel
 */

public class DailyTip implements Serializable {

	private static final long serialVersionUID = 1L;

	private String day;

	/**
	 * Default constructor
	 */
	public DailyTip() {
	}

	/**
	 * @return day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * Set the day
	 * @param day
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * Checks if the variable "day" contains a value that equal to the current day,
	 * and return randomly tip
	 */
	public String getTip() {
		String tip = new String();
		// get the current day
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DATE);
		String dayOfMonthStr = String.valueOf(dayOfMonth);
		// if both equal, return random tip
		if (dayOfMonthStr.equals(day)) {
			Random rand = new Random();
			int number = rand.nextInt(5) + 1;
			switch (number) {
			case 1:
				tip = "A friend asks only for your time not your money";
				break;
			case 2:
				tip = "If you refuse to accept anything but the best, you very often get it";
				break;
			case 3:
				tip = "Hard work pays off in the future, laziness pays off now";
				break;
			case 4:
				tip = "Your high-minded principles spell success";
				break;
			case 5:
				tip = "A smile is your passport into the hearts of others";
				break;
			}
		} else {
			// day is not equal
			tip = "try again..";
		}
		return tip;
	}
}
