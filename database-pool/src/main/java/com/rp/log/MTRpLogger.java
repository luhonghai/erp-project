/*
 * MTRpLogger.java
 *
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.log;

import com.rp.util.DateTime;

import java.io.*;
import java.util.*;

/**
 * Implementation of the com.rp.log.RpLogger class with additional support multi-threading.  All io writes take
 * place in a separate thread from the main thread.
 *
 * @author  Richardson Publications
 * @version 1.0, 09/2002
 * @see		com.rp.log.Logger
 */

public class MTRpLogger extends RpLogger implements Runnable
{
	private int BUFFER_LIMIT = 1000;
	private List buffer = Collections.synchronizedList (new ArrayList(10));
	private Thread logThread = null;
	private boolean check = true;
	private final String newLine = System.getProperty("line.separator");
	private int bufferIndex = 0;
	
	public MTRpLogger (String fileName) throws IOException
	{
		this(fileName, DEBUG, DAILY);
	}
	
	public MTRpLogger (String fileName, int loggingLevel) throws IOException
	{
		this(fileName, loggingLevel, DAILY);	
	}
	
	public MTRpLogger (String fileName, int loggingLevel, int frequency) throws IOException
	{
		super(fileName, loggingLevel, frequency);
		this.setLogThread(new Thread(this, "logThread"));
		this.getLogThread().start();
	}
	
	public MTRpLogger (Writer writer) throws IOException
	{
		this(writer, DEBUG);
	}
	
	public MTRpLogger (Writer writer, int loggingLevel) throws IOException
	{
		super(writer, loggingLevel);
		this.setLogThread(new Thread(this, "logThread"));
		this.getLogThread().start();
	}

	protected Thread getLogThread()
	{
		return this.logThread;	
	}
	
	protected void setLogThread(Thread t)
	{
		this.logThread = t;	
	}
	
	protected void stop () throws Exception
	{
		this.check = false;
	}
	
	public void destroy ()
	{
		try
		{
			this.stop();
		}
		catch (Exception e)
		{}	
		try
		{
			dumpBuffer();
		}
		catch (Exception e)
		{}
		finally
		{
			super.destroy();	
		}
	}
	
	public void run()
	{
		List logBuffer = new ArrayList (10);
		int lbSize = 0;
		int bufferSize = 0;
		
		while (check)
		{
			try
			{
				while (buffer.size() == 0)
				{
					wait();	
				}
				
				int size = buffer.size();
				for (int i = 0; i < size; i++)
				{
					print (buffer.get(i));
					bufferIndex++;	
				}
				
				if (bufferIndex > BUFFER_LIMIT)
				{
					synchronized(buffer)
					{
						remove(buffer, bufferIndex);
						bufferIndex = 0;
					}	
				}
			}
			catch (Exception e)
			{}	
		}
	}
	
	protected void removeAll (List b)
	{
		b.clear();
	}
	
	protected void remove(List b, int size)
	{
		for (int i = 0; i < size; i++)
		{
			b.remove(i);	
		}	
	}
	
	protected void dumpBuffer ()
	{
		Iterator iter = buffer.iterator();
		while (iter.hasNext())
		{
			print(iter.next());	
		}	
	}

	protected void print (Object o)
	{
		super.write(o);	
	}
	
	protected void update (Object o)
	{
		try
		{
			this.buffer.add(o);
			notify();
		}
		catch (Exception e)
		{}
	}

	protected void write (String s)
	{
		this.update(s);
	}
	
	protected void write (boolean b)
	{
		this.update(String.valueOf(b));
	}
	
	protected void write (char c)
	{
		this.update(String.valueOf(c));
	}
	
	protected void write (double d)
	{
		this.update(String.valueOf(d));
	}
	
	protected void write (float f)
	{
		this.update(String.valueOf(f));
	}
	
	protected void write (int i)
	{
		this.update(String.valueOf(i));
	}
	
	protected void write (byte b)
	{
		this.update(String.valueOf(b));	
	}
	
	protected void write (short s)
	{
		this.update(String.valueOf(s));	
	}
	
	protected void write (long l)
	{
		this.update(String.valueOf(l));
	}
	
	protected void write (Object o)
	{
		this.update(o);
	}
	
	protected void write (char[] buf)
	{
		int len = buf.length;
		StringBuffer charBuffer = new StringBuffer(len);
		for (int i = 0; i < len; i++)
		{
			charBuffer.append(buf[i]);	
		}
		this.update(charBuffer);
	}
	
}