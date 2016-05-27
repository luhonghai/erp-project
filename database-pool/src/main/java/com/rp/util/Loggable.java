package com.rp.util;

public interface Loggable
{	
	void fatal (String className, String method, String message);
	void error (String className, String method, String message);
	void warn (String className, String method, String message);
	void info (String className, String method, String message);
	void debug (String className, String method, String message);
	void fatal (String className, String method, String message, String key);
	void error (String className, String method, String message, String key);
	void warn (String className, String method, String message, String key);
	void info (String className, String method, String message, String key);
	void debug (String className, String method, String message, String key);
}