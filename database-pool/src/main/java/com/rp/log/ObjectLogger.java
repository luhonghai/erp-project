package com.rp.log;

public interface ObjectLogger
{
	boolean checkError ();
	void destroy ();
	void debug (Object o);
	void info (Object o);
	void warn (Object o);
	void error (Object o);
	void fatal (Object o);
}