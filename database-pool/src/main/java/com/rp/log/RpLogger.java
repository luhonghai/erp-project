/*
 * RpLogger.java
 *
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.log;

import com.rp.util.DateTime;
import com.rp.util.PropertiesReader;

import java.io.*;

/**
 * @author  Richardson Publications
 * @version 1.0, 05/2002
 * @see		com.rp.log.Logger
 */

public class RpLogger extends StandardLogger
{
	public final static int HOURLY = 0;
	public final static int DAILY = 1;
	public final static int WEEKLY = 2;
	public final static int MONTHLY = 3;
	public final static int YEARLY = 4;
	public final static int FOREVER = 5;
	public final static int NODATE = 99;
	
	protected String fileName = null;
	protected int frequency = DAILY;
	protected int loggingLevel = super.DEBUG;
	protected DateTime dateTime = null;
	
	public RpLogger () throws IOException
	{}
	
	public RpLogger (String fileName) throws IOException
	{
		this(fileName, DEBUG, DAILY);
	}
	
	public RpLogger (String fileName, int loggingLevel) throws IOException
	{
		this(fileName, loggingLevel, DAILY);	
	}
	
	public RpLogger (String fileName, int loggingLevel, int frequency) throws IOException
	{
		super();
		
		if (!this.verifyFrequency (frequency))
		{
			throw new IllegalArgumentException("RpLogger::Constructor:USAGE EXCEPTION::Invalid Frequency::frequency = " +frequency);
		}
		if (!super.verifyLevel (loggingLevel))
		{
			throw new IllegalArgumentException ("RpLogger::Constructor:USAGE EXCEPTION::Invalid Logging Level::level = " +loggingLevel);	
		}
		this.setFileName(fileName);	
		this.setFrequency (frequency);
		this.setLoggingLevel (loggingLevel);
		this.setDateTime (new DateTime());
		this.initWriter();
	}
	
	public RpLogger (Writer writer) throws IOException
	{
		this(writer, DEBUG);
	}
	
	public RpLogger (Writer writer, int loggingLevel) throws IOException
	{
		super();
		if (!super.verifyLevel (loggingLevel))
		{
			throw new IllegalArgumentException ("RpLogger::Constructor:USAGE EXCEPTION::Invalid Logging Level::level = " +loggingLevel);	
		}
		this.setFrequency (NODATE);
		this.setLoggingLevel (loggingLevel);
		this.setDateTime (new DateTime());
		super.setWriter(new LogWriter(writer));
	}
	
	protected  void initWriter () throws IOException
	{
		super.setWriter ( new LogWriter (this.getFileName() + this.getFileDateTime(this.getDateTime())) );
	}
	
	protected void reload () throws IOException
	{
		this.initWriter();
	}

	protected String getFileName ()
	{
		return this.fileName;	
	}
	
	protected void setFileName (String fileName)
	{
		this.fileName = fileName;	
	}
	
	protected int getFrequency ()
	{
		return this.frequency;
	}	
	
	protected void setFrequency (int frequency)
	{
		this.frequency = frequency;	
	}
	
	protected int getLoggingLevel ()
	{
		return this.loggingLevel;	
	}
	
	protected void setLoggingLevel (int level)
	{
		super.setLevel(level);
		this.loggingLevel = super.getLevel();	
	}
	
	protected void setLoggingLevel (String level)
	{
		if (level != null)
		{
			super.setLevel ( (new Integer(level)).intValue() );
		}	
		this.loggingLevel = super.getLevel();
	}
	
	public DateTime getDateTime()
	{
		return this.dateTime;	
	}
	
	protected void setDateTime (DateTime dateTime)
	{
		this.dateTime = dateTime;
	}	
	
	protected void write (String s)
	{
		this.checkDate();
		this.writer.println(s);	
	}
	
	protected void write (boolean b)
	{
		this.checkDate();
		this.writer.println(b);	
	}
	
	protected void write (char c)
	{
		this.checkDate();
		this.writer.println(c);	
	}
	
	protected void write (byte b)
	{
		this.checkDate();
		this.writer.println(b);	
	}
	
	protected void write (double d)
	{
		this.checkDate();		
		this.writer.println(d);	
	}
	
	protected void write (float f)
	{
		this.checkDate();		
		this.writer.println(f);	
	}
	
	protected void write (int i)
	{
		this.checkDate();		
		this.writer.println(i);	
	}
	
	protected void write (long l)
	{
		this.checkDate();		
		this.writer.println(l);	
	}
	
	protected void write (Object o)
	{
		this.checkDate();		
		this.writer.println(o);	
	}
	
	protected void write (char[] buf)
	{
		this.checkDate();		
		this.writer.println(buf);	
	}
	
	protected void checkDate ()
	{
		DateTime tempDate = new DateTime();
		if (this.checkFile(tempDate))
		{
			LogWriter tempWriter = null;
			try
			{
				tempWriter = new LogWriter(super.getWriter());
				this.writer = new LogWriter (this.getFileName() + this.getFileDateTime(tempDate));	
				tempWriter.destroy();
				tempWriter = null;
			}
			catch (Exception e)
			{
				this.writer = tempWriter;
			}
			this.getDateTime().refresh();
		}
	}
	
	protected boolean checkFile(DateTime tempDate)
	{
		int freq = this.getFrequency();
		DateTime dTime = this.getDateTime();
		
		if (freq == NODATE || freq == FOREVER)
		{
			return false;	
		}
		
		String oldCheck;
		String newCheck;
		
		switch (freq)
		{
			case HOURLY:
				oldCheck = dTime.getHour();
				newCheck = tempDate.getHour();
				break;
			
			case DAILY:
				oldCheck = dTime.getDate();
				newCheck = tempDate.getDate();
				break;
			
			case MONTHLY:
				oldCheck = dTime.getMonth();
				newCheck = tempDate.getMonth();
				break;
				
			case YEARLY:
				oldCheck = dTime.getYear();
				newCheck = tempDate.getYear();
				break;
			
			default:
				oldCheck = "0";
				newCheck = "0";
				break;
		}
	
		if (oldCheck.equals(newCheck))
		{
			return false;
		}
		return true;
	}

	protected String getFileDateTime (DateTime dTime)
	{
		int freq = this.getFrequency();
		String fileDateTime = "";
		
		switch (freq)
		{
			case HOURLY:
				fileDateTime = dTime.getFullDate("-") + "_" + dTime.getStandardTime("-");
				break;
			
			case DAILY:
				fileDateTime = dTime.getFullDate("-");
				break;
			
			case MONTHLY:
				fileDateTime = dTime.getFullDate("-");
				break;
				
			case YEARLY:
				fileDateTime = dTime.getFullDate("-");
				break;
			
			default:
				fileDateTime = "";
				break;
		}
		return fileDateTime;
	}
	
	protected boolean verifyFrequency (int freq)
	{
		switch (freq)
		{
			case HOURLY:
				return true;
			case DAILY:
				return true;
			case MONTHLY:
				return true;
			case YEARLY:
				return true;	
			case FOREVER:
				return true;
			case NODATE:
				return true;
		}	
		return false;
	}
}