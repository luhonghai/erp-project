/*
 * LogWriter.java
 *
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.log;

import com.rp.util.DateTime;

import java.io.Writer;
import java.io.IOException;
import java.io.InterruptedIOException;

/**
 * Writes output to the specified file, inserting a date and time stamp prior to writing output.
 *
 * @author  Richardson Publications
 * @version 1.0, 05/2002
 */

public class LogWriter extends GenericFileWriter
{	
	private DateTime dateTime = null;
	private boolean error = true;

	public LogWriter(String fileName) throws IOException
	{
		super(fileName);
		dateTime = new DateTime();
	}
	
	public LogWriter (Writer writer) throws IOException
	{
		super(writer);
		dateTime = new DateTime();	
	}
	
	public void write (String s, int off, int len)
	{
		try
		{
			synchronized (super.lock) 
			{
			this.checkOpen();
			dateTime.refresh();
			String dt = dateTime.getFullDate("/") + " " + dateTime.getFullTime(":");
			super.out.write(dt + " " + s, off, dt.length() + 1 + len);
			}
		}
		catch (InterruptedIOException x) 
		{
	    	Thread.currentThread().interrupt();
		}
		catch (IOException x) 
		{
	    	this.error = true;
		}
	}
	
	public void write (String s)
	{
		if (s != null)
		{
			write (s, 0, s.length());	
		}
		else
		{
			write ("null", 0, 4);	
		}
	}
	
	public void write (int c)
	{
		write (String.valueOf(c), 0, 1);	
	}
	
	public void write (char[] buf)
	{
		if (buf != null)
		{
			write (buf, 0, buf.length);
		}
		else
		{
			write ("null", 0, 4);	
		}
	}
	
	public void write (char[] buf, int off, int len)
	{
		if (buf != null)
		{	
			StringBuffer sb = new StringBuffer(len);
			for (int i = off; i < len; i++)
			{
				sb.append (buf[i]);
			}
			write (sb.toString());
		}
		else
		{
			write ("null", 0, 4);	
		}
	}
	
	public boolean checkError ()
	{
		return error;	
	}
	
	private void checkOpen () throws IOException
	{
		if (super.out == null)
		{
			throw new IOException ("Stream closed");	
		}	
	}
	
	protected void finalize()
	{
		super.finalize();	
	}
}