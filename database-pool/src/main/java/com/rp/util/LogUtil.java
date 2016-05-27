package com.rp.util;

import com.rp.log.*;
import java.util.*;
import java.io.*;

public final class LogUtil implements Loggable
{
	public final static int DEBUG = 4;
	public final static int INFO = 3;
	public final static int WARN = 2;
	public final static int ERROR = 1;
	public final static int FATAL = 0;
	
	private ObjectLogger logger = null;
	private int logLevel = DEBUG;
	private static Map keyMap = new HashMap();
	static PrintWriter writer = null;
	static RpLogger log = null;
	
	public static void load (PrintWriter inWriter)
	{
		writer = inWriter;
		try
		{
			log = new RpLogger(writer, RpLogger.INFO);
		}
		catch (Exception e)
		{
			writer.println("Error loading log in LogUtil: " +e.toString());	
		}
	}
	
	public static void log (Object obj)
	{
		/*if (writer != null)
		{
			writer.println(obj);	
		}*/
		if (log != null)
		{
			log.info(obj);	
		}	
	}
	
	public static void close()
	{
		/*if (writer != null)
		{
			try
			{
				writer.close();
			}	
			catch(Exception e)
			{}
		}*/
		if (log != null)
		{
			log.destroy();	
		}		
	}
	
	private LogUtil (ObjectLogger inLogger, int inLevel)
	{
		logger = inLogger;
		logLevel = inLevel;
	}
	
	public static LogUtil getInstance (String key)
	{
		return (LogUtil)keyMap.get(key);	
	} 
	
	public static LogUtil createInstance (ObjectLogger logger, int logLevel, String key)
	{
		LogUtil logUtil = new LogUtil (logger, logLevel);
		keyMap.put(key, logUtil);
		return logUtil;
	}
	
	public void fatal (String className, String method, String message, String key)
	{
		LogUtil logUtil = (LogUtil)keyMap.get(key);
		if (logUtil != null)
		{
			logUtil.fatal(className, method, message);	
		}
	}
	
	public void error (String className, String method, String message, String key)
	{
		LogUtil logUtil = (LogUtil)keyMap.get(key);
		if (logUtil != null)
		{
			logUtil.error(className, method, message);	
		}
	}
	
	public void warn (String className, String method, String message, String key)
	{
		LogUtil logUtil = (LogUtil)keyMap.get(key);
		if (logUtil != null)
		{
			logUtil.warn(className, method, message);	
		}
	}
	
	public void info (String className, String method, String message, String key)
	{
		LogUtil logUtil = (LogUtil)keyMap.get(key);
		if (logUtil != null)
		{
			logUtil.info(className, method, message);	
		}
	}
	
	public void debug (String className, String method, String message, String key)
	{
		LogUtil logUtil = (LogUtil)keyMap.get(key);
		if (logUtil != null)
		{
			logUtil.debug(className, method, message);	
		}
	}

	public void fatal (String className, String method, String message)
	{
		if (logger != null)
		{
			logger.fatal (className + "::" + method + "::" +message);
		}	
	}
	
	public void error (String className, String method, String message)
	{
		if (logLevel >= ERROR && logger != null)
		{
			logger.error  (className + "::" + method + "::" +message);
		}
	}
	
	public void warn (String className, String method, String message)
	{
		if (logLevel >= WARN && logger != null)
		{
			logger.warn  (className + "::" + method + "::" +message);
		}
	}
	
	public void info (String className, String method, String message)
	{
		if (logLevel >= INFO && logger != null)
		{
			logger.info  (className + "::" + method + "::" +message);
		}
	}
	
	public void debug (String className, String method, String message)
	{
		if (logLevel >= DEBUG && logger != null)
		{
			logger.debug  (className + "::" + method + "::" +message);
		}
	}
}