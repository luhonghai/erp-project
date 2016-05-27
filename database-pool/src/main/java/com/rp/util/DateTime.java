/*
 * DateTime.java
 *
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.util;

import java.util.Calendar;

/**
 * Convenience class for getting date and/or time values in java.lang.String format
 *
 * @author  Richardson Publications
 * @version 1.0, 05/2002
 */

public class DateTime implements java.io.Serializable
{
	private Calendar calendar = null;
	
	private String year;
	private String month;
	private String date;
	private String hour;
	private String minute;
	private String second;
	private String millisecond;
	
	private final static String SEPARATOR = "_";
	
	public DateTime()
	{
		this.init();
	}	
	
	public String getFullDateTime()
	{
		return this.getFullDateTime (SEPARATOR);
	}

	public String getFullDateTime (String sep)
	{
		StringBuffer sb = new StringBuffer(25);
		sb.append (this.getFullDate());
		sb.append (sep);
		sb.append (this.getFullTime());
		return sb.toString();
	}
	
	public String getFullDate ()
	{
		return this.getFullDate (SEPARATOR);
	}

	public String getFullDate (String sep)
	{
		StringBuffer sb = new StringBuffer(10);
		sb.append (this.getMonth());
		sb.append (sep);
		sb.append (this.getDate());
		sb.append (sep);
		sb.append (this.getYear());
		return sb.toString();
	}
	
	public String getFullTime ()
	{
		return this.getFullTime(SEPARATOR);
	}

	public String getFullTime (String sep)
	{
		StringBuffer sb = new StringBuffer(11);
		sb.append (this.getHour());
		sb.append (sep);
		sb.append (this.getMinute());
		sb.append (sep);
		sb.append (this.getSecond());
		sb.append (sep);
		sb.append (this.getMillisecond());
		return sb.toString();	
	}
	
	public String getStandardTime (String sep)
	{
		StringBuffer sb = new StringBuffer(8);
		sb.append (this.getHour());
		sb.append (sep);
		sb.append (this.getMinute());
		sb.append (sep);
		sb.append (this.getSecond());
		return sb.toString();
	}
	
	protected void init ()
	{
		this.calendar = Calendar.getInstance();
		this.setYear ( String.valueOf(calendar.get(Calendar.YEAR)) );
		this.setMonth ( String.valueOf(calendar.get(Calendar.MONTH)) );
		this.setDate ( String.valueOf(calendar.get(Calendar.DATE)) );
		this.setHour ( String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) );
		this.setMinute ( String.valueOf(calendar.get(Calendar.MINUTE)) );
		this.setSecond ( String.valueOf(calendar.get(Calendar.SECOND)) );
		this.setMillisecond ( String.valueOf(calendar.get(Calendar.MILLISECOND)) );
	}	
	
	public void refresh()
	{
		this.init();	
	}
	
	public String getYear()
	{
		return this.year;	
	}
	
	public void setYear (String year)
	{
		this.year = padd(year,2);	
	}
	
	public String getMonth()
	{
		return this.month;	
	}
	
	public void setMonth (String month)
	{
		int x = (new Integer(month)).intValue();
		x++;
		this.month = padd(String.valueOf(x), 2);
	}
	
	public String getDate()
	{
		return this.date;	
	}
	
	public void setDate (String date)
	{
		this.date = padd(date, 2);	
	}
	
	public String getHour()
	{
		return this.hour;	
	}
	
	public void setHour (String hour)
	{
		this.hour = padd(hour,2);	
	}
	
	public String getMinute()
	{
		return this.minute;	
	}
	
	public void setMinute (String minute)
	{
		this.minute = padd(minute,2);	
	}
	
	public String getSecond()
	{
		return this.second;	
	}
	
	public void setSecond (String second)
	{
		this.second = padd(second,2);	
	}
	
	public String getMillisecond()
	{
		return this.millisecond;	
	}
	
	public void setMillisecond (String millisecond)
	{
		this.millisecond = padd(millisecond,3);	
	}
	
	protected String padd (String s, int len)
	{
		int count = len - s.length();
		for (int i = 0; i < count; i++)
		{
			s = "0" + s;	
		}
		return s;
	}
}