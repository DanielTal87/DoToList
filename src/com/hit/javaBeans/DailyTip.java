package com.hit.javaBeans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

public class DailyTip implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String day;

	public DailyTip() {
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public String  getTip(){
		String tip = new String();
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DATE);
		String dayOfMonthStr = String.valueOf(dayOfMonth);
		if(dayOfMonthStr.equals(day)){
			Random rand = new Random();
			int  n = rand.nextInt(5) + 1;
			switch(n){
				case 1: 
					tip="";
					break;
				case 2:
					tip="";
					break;
				case 3:
					tip="";	
					break;
				case 4:
					tip="";
					break;
				case 5:
					tip="";
					break;
			}
		}
		else{
			tip="try again..";
		}
		return tip;
	}
	
	@Override
	public String toString() {
		return "DailyTip [day=" + day + "]";
	}
}
