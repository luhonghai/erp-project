/*
 * Logger.java
 *
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.log;

import java.io.PrintWriter;

/**
 * Implementation of various methods to write to a java.io.PrintWriter that signify varying levels of logging.
 *
 * @author Richardson Publications
 * @version 1.0, 05/2002
 */

public class StandardLogger implements Logger
{
	public final static int DEBUG = 4;
	public final static int INFO = 3;
	public final static int WARN = 2;
	public final static int ERROR = 1;
	public final static int FATAL = 0;

	protected PrintWriter writer = null;	
	protected boolean loggerError = false;
	protected int level = DEBUG;
	
	public StandardLogger (PrintWriter writer)
	{
		this(writer, DEBUG);
	}	
	
	public StandardLogger (PrintWriter writer, int level)
	{
		this.setWriter(writer);
		this.setLevel(level);
	}
	
	public StandardLogger ()
	{}
	
	public boolean checkError ()
	{
		if (writer != null)
		{
			if (writer.checkError() || loggerError)
			{
				return true;	
			}
		}	
		return false;
	}
	
	protected PrintWriter getWriter()
	{
		return this.writer;	
	}
	
	protected void setWriter (PrintWriter writer)
	{
		this.writer = writer;	
	}
	
	protected int getLevel ()
	{
		return this.level;	
	}
	
	protected void setLevel (int level)
	{
		this.level = level;
	}	
	
	protected boolean checkLevel (int i)
	{
		if (i <= this.level)
		{
			return true;	
		}	
		return false;
	}
	
	protected void write (String s)
	{
		this.writer.println(s);	
	}
	
	protected void write (boolean b)
	{
		this.writer.println(b);	
	}
	
	protected void write (char c)
	{
		this.writer.println(c);	
	}
	
	protected void write (byte b)
	{
		this.writer.println(b);	
	}
	
	protected void write (double d)
	{
		this.writer.println(d);	
	}
	
 	protected void write (float f)
	{
		this.writer.println(f);	
	}
	
	protected void write (int i)
	{
		this.writer.println(i);	
	}
	
	protected void write (long l)
	{
		this.writer.println(l);	
	}
	
	protected void write (Object o)
	{
		this.writer.println(o);	
	}
	
	protected void write (char[] buf)
	{
		this.writer.println(buf);	
	}
	
	public void fatal (String s)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(s);
		}
	}	
	
	public void fatal (boolean b)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(b);
		}
	}	
	
	public void fatal (char c)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(c);
		}
	}	
	
	public void fatal (double d)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(d);
		}
	}	
	
	public void fatal (short s)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(s);
		}
	}	
	
	public void fatal (byte b)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(b);
		}
	}	
	
	public void fatal (float f)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(f);
		}
	}	
	
	public void fatal (int i)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(i);
		}
	}	
	
	public void fatal (long l)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(l);
		}
	}	
	
	public void fatal (Object o)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(o);
		}
	}	
	
	public void fatal (char[] buf)
	{
		if (this.checkLevel(this.FATAL))
		{
			this.write(buf);
		}
	}	
	
	public void error (String s)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(s);
		}
	}	
	
	public void error (boolean b)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(b);
		}
	}	
	
	public void error (char c)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(c);
		}
	}	
	
	public void error (double d)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(d);
		}
	}	
	
	public void error (byte b)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(b);
		}
	}	
	
	public void error (short s)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(s);
		}
	}	
	
	public void error (float f)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(f);
		}
	}	
	
	public void error (int i)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(i);
		}
	}	
	
	public void error (long l)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(l);
		}
	}	
	
	public void error (Object o)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(o);
		}
	}	
	
	public void error (char[] buf)
	{
		if (this.checkLevel(this.ERROR))
		{
			this.write(buf);
		}
	}	
	
	public void warn (String s)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(s);
		}
	}	
	
	public void warn (boolean b)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(b);
		}
	}	
	
	public void warn (char c)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(c);
		}
	}	
	
	public void warn (double d)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(d);
		}
	}	
	
	public void warn (byte b)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(b);
		}
	}	
	
	public void warn (short s)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(s);
		}
	}	
	
	public void warn (float f)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(f);
		}
	}	
	
	public void warn (int i)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(i);
		}
	}	
	
	public void warn (long l)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(l);
		}
	}	
	
	public void warn (Object o)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(o);
		}
	}	
	
	public void warn (char[] buf)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(buf);
		}
	}	
	
	public void info (String s)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(s);
		}
	}	
	
	public void info (boolean b)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(b);
		}
	}	
	
	public void info (char c)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(c);
		}
	}	
	
	public void info (double d)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(d);
		}
	}
	
	public void info (byte b)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(b);
		}
	}
	
	public void info (short s)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(s);
		}
	}	
	
	public void info (float f)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(f);
		}
	}	
	
	public void info (int i)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(i);
		}
	}	
	
	public void info (long l)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(l);
		}
	}	
	
	public void info (Object o)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(o);
		}
	}	
	
	public void info (char[] buf)
	{
		if (this.checkLevel(this.INFO))
		{
			this.write(buf);
		}
	}	
	
	public void debug (String s)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(s);
		}
	}	
	
	public void debug (boolean b)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(b);
		}
	}	
	
	public void debug (char c)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(c);
		}
	}	
	
	public void debug (double d)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(d);
		}
	}	
	
	public void debug (byte b)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(b);
		}
	}	
	
	public void debug (short s)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(s);
		}
	}	
	
	public void debug (float f)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(f);
		}
	}	
	
	public void debug (int i)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(i);
		}
	}	
	
	public void debug (long l)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(l);
		}
	}	
	
	public void debug (Object o)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(o);
		}
	}	
	
	public void debug (char[] buf)
	{
		if (this.checkLevel(this.DEBUG))
		{
			this.write(buf);
		}
	}	
	
	protected boolean verifyLevel (int level)
	{
		if (level < FATAL || level > DEBUG)
		{
			return false;
		}	
		return true;
	}	
	
	protected void finalize ()
	{
		this.destroy();
	}
	
	protected void setError (boolean val)
	{
		this.loggerError = val;
	}
	
	public void destroy ()
	{
		try
		{
			if (this.writer != null)
			{
				this.writer.close();	
			}
		}	
		catch (Exception e)
		{
			this.setError(true);	
		}
	}
}