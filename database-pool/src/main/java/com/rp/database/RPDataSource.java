package com.rp.database;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.io.*;

import com.rp.util.*;
import com.rp.util.pool.*;
import com.rp.log.*;

public abstract class RPDataSource implements DataSource, Serializable
{
	protected String CLASS_NAME = "RPDataSource";
	protected ObjectPool pool = null;
	protected CSConnectionManager connectionManager = null;
	protected DateTime dateTime = null;
	protected int loginTimeout = 0;
	protected int holdTimeout = 0;
	protected long waitTimeout = 0;
	protected String checkQuery = null;
	protected boolean autoCommit = true;
	protected String description = null;
	protected String driverClass = null;
	protected int maxCount;
	protected int minCount;
	protected String password = null;
	protected String user = null;
	protected String url = null;
	protected int statementCacheSize = 0;
	protected int logLevel = LogUtil.INFO;
	protected ObjectLogger logger = null;
	protected LogUtil logUtil = null;
	protected PrintWriter logWriter = null;
	protected final static String LOG_KEY = "RpDataSource";
	
	/**
	Properties values: logLevel, autoCommit, description, driverClass, maxCount, minCount, password, user,
					url, loginTimeout, holdTimeout, waitTimeout, checkQuery, statementCacheSize
	*/

	/*
		MAKE SURE TO CLEAN UP THE CONNECTION WHEN FREEING IT.  CALL COMMIT ON FREE?
	*/

	public RPDataSource (Properties props) throws SQLException
	{	
		this(null, props, null);
	}
	
	public RPDataSource (Properties props, PrintWriter writer) throws SQLException
	{
		this (null, props, writer);
	}
	
	public RPDataSource (Properties props, ObjectLogger logger) throws SQLException
	{
		this (logger, props, null);
	}
	
	protected RPDataSource (ObjectLogger logger, Properties props, PrintWriter writer) throws SQLException
	{
		String method = "constructor";
		try
		{
			this.dateTime = new DateTime();
			String tempDebugLevel = props.getProperty("logLevel");
			boolean gotLogLevel = true;
			if (!isNull(tempDebugLevel))
			{
				try
				{
					this.logLevel = (new Integer(tempDebugLevel)).intValue();
				}
				catch (Exception e)
				{
					gotLogLevel = false;
					logLevel = LogUtil.INFO;
				}
			}
			if (writer != null)
			{
				setLogWriter(writer, logLevel);	
			}
			else if (logger != null)
			{
				setLogger (logger, logLevel);
			}
			
			info(method, "entering " +method+ " . . .");
			debug(method, "attempting to get values from Properties object");
			if (!gotLogLevel)
			{
				debug(method, "could not get log level.  using default of INFO");
			}
			debug(method, "debugLevel = " +logLevel);
			
			String tempAuto = props.getProperty("autoCommit");
			if (!isNull(tempAuto))
			{
				debug( method, "autoCommit ("+tempAuto+")");
				if (!tempAuto.equalsIgnoreCase("true") && !tempAuto.equalsIgnoreCase("false"))
				{
					fatal (method, "invalid value for autoCommit:"+tempAuto+":");
					throw new SQLException(CLASS_NAME + ":" +method+ "::invalid value for autoCommit");
				}
			}
			else
			{
				debug( method, "autoCommit is null.  using default of true");
				tempAuto = "true";	
			}
			
			String tempDescription = props.getProperty("description");
			debug( method, "description ("+tempDescription+")");
			
			String tempDriverClass = props.getProperty("driverClass");
			if (!isNull(tempDriverClass))
			{
				debug( method, "driverClass ("+tempDriverClass+")");
			}
			else
			{
				fatal( method, "NULL value for driverClass");
				throw new SQLException(CLASS_NAME + ":" +method+ "::" + "NULL value for driverClass");	
			}
			
			String tempMax = props.getProperty("maxCount");
			if (!isNull(tempMax))
			{
				debug( method, "maxCount ("+tempMax+")");
			}
			else
			{
				fatal( method, "NULL value for maxCount");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "NULL value for maxCount");	
			}
			
			String tempMin = props.getProperty("minCount");
			if (!isNull(tempMin))
			{
				debug( method, "minCount ("+tempMin+")");
			}
			else
			{
				fatal( method, "NULL value for minCount");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "NULL value for minCount");	
			}
			
			String tempPassword = props.getProperty("password");
			String tempUser = props.getProperty("user");
			debug(method, "user ("+tempUser+")");
			
			String tempUrl = props.getProperty("url");	
			if (!isNull(tempUrl))
			{
				debug( method, "url ("+tempUrl+")");
			}
			else
			{
				fatal( method, "NULL value for user");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "NULL value for user");	
			}
			
			String tempTimeout = props.getProperty("loginTimeout");
			if (isNull(tempTimeout))
			{
				debug( method, "loginTimeout not set");
				tempTimeout = "0";	
			}
			else
			{
				debug( method, "logintimeout ("+tempTimeout+")");
			}
			
			String tempHoldTimeout = props.getProperty("holdTimeout");
			if (isNull(tempHoldTimeout))
			{
				debug( method, "holdTimeOut not set");
				tempHoldTimeout = "0";	
			}
			else
			{
				debug( method, "holdTimeOut ("+tempHoldTimeout+")");
			}
			
			String tempWaitTimeout = props.getProperty("waitTimeout");
			if (isNull(tempWaitTimeout))
			{
				debug( method, "waitTimeOut not set");
				tempWaitTimeout = "0";	
			}
			else
			{
				debug( method, "waitTimeOut ("+tempWaitTimeout+")");
			}
			
			String tempCheckQuery = props.getProperty("checkQuery");
			if (!isNull(tempCheckQuery))
			{
				debug( method, "checkQuery ("+tempCheckQuery+")");
			}
			else
			{
				debug( method, "checkQuery not set");
				tempCheckQuery = null;	
			}
			
			String tempCacheSize = props.getProperty("statementCacheSize");
			if (isNull(tempCacheSize))
			{
				debug(method, "statementCacheSize not set");
				tempCacheSize = "0";	
			}
			else
			{
				debug (method, "statementCacheSize ("+tempCacheSize+")");
			}	
			
			debug( method, "converting autocommit to boolean");
			try
			{
				tempAuto = tempAuto.toLowerCase();
				debug( method, "autocommit toLowerCase ("+tempAuto+")");
				this.autoCommit = (new Boolean(tempAuto)).booleanValue();
				debug( method, "autocommit boolean value ("+autoCommit+")");
			}
			catch (Exception e)
			{
				fatal( method, "could not convert autocommit to boolean");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "Could not convert autocommit to boolean: " +e.toString());	
			}
			
			debug( method, "setting description = "+tempDescription);
			this.description = tempDescription;
			
			debug( method, "setting driverClass = "+tempDriverClass);
			this.driverClass = tempDriverClass;
			
			try
			{
				debug( method, "converting maxCount to int");
				this.maxCount = (new Integer(tempMax)).intValue();
				debug( method, "maxCount ("+maxCount+")");
			}
			catch (Exception e)
			{
				fatal( method, "could not convert maxCount to int");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "Could not convert maxCount to int: " +e.toString());
			}
			
			try
			{
				debug( method, "converting minCount to int");
				this.minCount = (new Integer(tempMin)).intValue();
				debug( method, "minCount ("+minCount+")");
			}
			catch (Exception e)
			{
				fatal( method, "could not convert minCount to int");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "Could not convert minCount to int: " +e.toString());
			}
			
			debug( method, "setting password = ");
			this.password = tempPassword;
			
			debug( method, "setting user = " +tempUser);
			this.user = tempUser;
			
			debug( method, "setting url = " +tempUrl);
			this.url = tempUrl;
			
			try
			{
				debug( method, "converting login timeout to int");
				this.setLoginTimeout( (new Integer(tempTimeout)).intValue() );
				debug( method, "loginTimeout ("+getLoginTimeout()+")");
				if (getLoginTimeout() > 0)
				{
					DriverManager.setLoginTimeout(getLoginTimeout());	
				}
			}
			catch (Exception e)
			{
				fatal( method, "could not convert login timeout to int");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "Could not convert login timeout to int: " +e.toString());
			}
			
			try
			{
				debug( method, "converting hold timeout to long");
				this.holdTimeout = (new Integer(tempHoldTimeout)).intValue();
				debug( method, "holdTimeout ("+holdTimeout+")");
			}
			catch (Exception e)
			{
				fatal( method, "could not convert hold timeout to long");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "Could not convert hold timeout to long: " +e.toString());
			}
			
			try
			{
				debug( method, "converting wait timeout to int");
				this.waitTimeout = (new Long(tempWaitTimeout)).longValue();
				debug( method, "waitTimeout ("+waitTimeout+")");
			}
			catch (Exception e)
			{
				fatal( method, "could not convert wait timeout to int");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "Could not convert wait timeout to int: " +e.toString());
			}
			
			debug( method, "setting checkQuery = " +tempCheckQuery);
			this.checkQuery = tempCheckQuery;
			
			try
			{
				debug(method, "converting statementCacheSize to int");
				this.statementCacheSize = (new Integer(tempCacheSize)).intValue();
				debug(method, "statementCacheSize ("+statementCacheSize+")");
			}
			catch (Exception e)
			{
				fatal( method, "could not convert statementCacheSize to int");
				throw new SQLException (CLASS_NAME + ":" +method+ "::" + "Could not convert statementCacheSize to int: " +e.toString());
			}
			
			info (method, "attempting to create ConnectionPool with the following parameters:");
			info (method, "url: "+url);
			info (method, "user: "+user);
			debug (method, "password: " +password);
			info (method, "driverClass: " +driverClass);
			info (method, "maxCount: "+maxCount);
			info (method, "minCount: " +minCount);
			info (method, "checkQuery: " +checkQuery);
			info (method, "loginTimeout: "+getLoginTimeout());
			info (method, "holdTimeout: "+holdTimeout);
			info (method, "waitTimeout: "+waitTimeout);
			info (method, "autoCommit: "+autoCommit);
			info (method, "logWriter: "+getLogWriter());
			info (method, "logLevel: "+logLevel);
			info (method, "statementCacheSize: " +statementCacheSize);
		}
		catch (Exception e)
		{
			error(method, "EXCEPTION: " +e.toString());
			error(method, "STACK TRACE: " +ExceptionUtil.getStackTrace(e));
			SQLException s = new SQLException(CLASS_NAME + ":" +method+ "::" + e.toString());
			throw s;
		}	
		info( method, "leaving constructor . . .");
	}

	public Connection getConnection () throws SQLException
	{
		String method = "getConnection()";
		info( method, "entering "+method+". . .");
		Connection c = null;
		try
		{
			if (pool == null)
			{
				debug (method, "ConnectionPool is null, creating new pool");
				boolean cacheable = false;
				if (statementCacheSize > 0)
				{
					cacheable = true;
				}
				Cache sCache = new StatementCache(statementCacheSize);
				connectionManager = (CSConnectionManager)CSConnectionManager.getInstance(url, user, password, driverClass, checkQuery,
					holdTimeout, autoCommit, LOG_KEY, logUtil, cacheable, sCache);
				connectionManager.init();
				pool = ObjectPoolImpl.getInstance(connectionManager, maxCount, minCount, waitTimeout, false,
					logUtil, LOG_KEY);
				connectionManager.init(pool);
				pool.init();
				debug (method, "ConnectionPool created");
			}
			debug( method, "attempting to get connection");
			c = (Connection)pool.getObject();
			debug( method, "connection obtained.  returning. . .");
		}
		catch (Exception se)
		{
			error(method, "EXCEPTION: " +se.toString());
			error(method, "STACK TRACE: " +ExceptionUtil.getStackTrace(se));
			throw new SQLException(se.toString());	
		}
		info( method, "leaving getConnection(). . .");
		return c;
	}

	public Connection getConnection (String userName, String password) throws SQLException
	{
		String method = "getConnection(userName, password)";
		info( method, "entering "+method+". . .");
		Connection con = null;
		try
		{
			debug (method, "attempting to load driver");
			Class.forName(driverClass);
			debug (method, "done loading driver");
		}
		catch(ClassNotFoundException c)
		{
			error(method, "COULD NOT LOAD DRIVER: Class Not Found: " +driverClass);
			error(method, "EXCEPTION MESSAGE: " +c.toString());
			error(method, "STACK TRACE: " +ExceptionUtil.getStackTrace(c));
        	throw new SQLException (CLASS_NAME + ":" +method+ "::ClassNotFoundException--"+c.toString());
		}
		try
		{
			debug (method, "checking loginTime . . .");
			if (loginTimeout > 0)
			{
				debug (method, "loginTime > 0.  attempting to set loginTimeout . . .");
				DriverManager.setLoginTimeout(loginTimeout);	
				debug (method, "loginTime set to " +loginTimeout);
			}
			debug (method, "attempting to create connection: user = " +user+ " url = " +url);
			con = DriverManager.getConnection(url, user, password);
			debug (method, "connection created");
			debug(method, "setting autocommit . . .");
			con.setAutoCommit(autoCommit);
			debug (method, "autocommit set to " +autoCommit);
		}	
		catch (SQLException e)
		{
			error(method, "SQL Exception occurred while creating connection . . . MESSAGE: " +e.toString());
			error(method, "STACK TRACE: " +ExceptionUtil.getStackTrace(e));
			throw e;
		}
		return con;
	}
	
	public int getLoginTimeout() throws SQLException
	{
		return loginTimeout;	
	}
	
	public void setLoginTimeout(int seconds) throws SQLException
	{
		if (seconds > 0)
		{
			DriverManager.setLoginTimeout(loginTimeout);	
		}
		loginTimeout = seconds;	
	}
	
	public void setLogWriter(PrintWriter out) throws SQLException
	{
		setLogWriter(out, LogUtil.INFO);	
	}
	
	public void setLogWriter(PrintWriter out, int logLevel) throws SQLException
	{
		logWriter = out;
		Logger temp = null;
		int level = RpLogger.INFO;
		try
		{
			switch(logLevel)
			{
				case RpLogger.DEBUG:
					level = RpLogger.DEBUG;
					break;
				case RpLogger.INFO:
					level = RpLogger.INFO;
					break;
				case RpLogger.WARN:
					level = RpLogger.WARN;
					break;
				case RpLogger.ERROR:
					level = RpLogger.ERROR;
					break;
				case RpLogger.FATAL:
					level = RpLogger.FATAL;
					break;
			}
			
			temp = new RpLogger(out, level);
		}
		catch (Exception e)
		{
			e.printStackTrace();	
		}
		setLogger(temp, logLevel);
	}
	
	public PrintWriter getLogWriter () throws SQLException
	{
		return logWriter;	
	}

	public void setLogger (ObjectLogger inLogger, int inLogLevel) throws SQLException
	{
		logger = inLogger;
		logLevel = inLogLevel;
		logUtil = LogUtil.createInstance(logger, logLevel, LOG_KEY);		
	}	
	
	public void setLogger (ObjectLogger inLogger) throws SQLException
	{
		setLogger (inLogger, logLevel);
	}
	
	public ObjectLogger getLogger () throws SQLException
	{
		return logger;	
	}
	
	protected void fatal (String method, String s)
	{
		if (logUtil != null)
		{
			logUtil.fatal (CLASS_NAME, method, s);
		}
	}
	
	protected void error (String method, String s)
	{
		if (logUtil != null)
		{
			logUtil.error (CLASS_NAME, method, s);
		}
	}
	
	protected void warn (String method, String s)
	{
		if (logUtil != null)
		{
			logUtil.warn (CLASS_NAME, method, s);
		}
	}
	
	protected void info (String method, String s)
	{
		if (logUtil != null)
		{
			logUtil.info (CLASS_NAME, method, s);
		}
	}
	
	protected void debug (String method, String s)
	{
		if (logUtil != null)
		{
			logUtil.debug (CLASS_NAME, method, s);
		}
	}
	
	protected boolean isNull (String s)
	{
		if (s != null && s.trim().length() > 0)
		{
			return false;
		}	
		return true;
	}
	
	protected void finalize() throws Throwable
    {
    	destroy();
    }
    
    public void destroy()
    {
    	String method = "destroy";
    	debug (method, "trying to destroy pool");
    	if (pool != null)
    	{
    		debug (method, "pool is not null");
    		try
    		{
    			pool.destroy(false);	
    			debug (method, "pool destroyed");
    		}
    		catch (Exception e)
    		{
    			debug (method, "problem destroying pool: " +e.toString());
    		}
    	}	
    }
	
	private void writeObject(ObjectOutputStream out) throws IOException
	{
		out.writeObject(dateTime);
		out.writeInt(loginTimeout);
		out.writeInt(holdTimeout);
		out.writeLong(waitTimeout);
		out.writeObject(checkQuery);
		out.writeBoolean(autoCommit);
		out.writeObject(description);
		out.writeObject(driverClass);
		out.writeInt(maxCount);
		out.writeInt(minCount);
		out.writeObject(password);
		out.writeObject(user);
		out.writeObject(url);
		out.writeInt(statementCacheSize);
		out.writeInt(logLevel);
	}
	
	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		dateTime = (DateTime)in.readObject();
		loginTimeout = in.readInt();
		holdTimeout = in.readInt();
		waitTimeout = in.readLong();
		checkQuery = (String)in.readObject();
		autoCommit = in.readBoolean();
		description = (String)in.readObject();
		driverClass = (String)in.readObject();
		maxCount = in.readInt();
		minCount = in.readInt();
		password = (String)in.readObject();
		user = (String)in.readObject();
		url = (String)in.readObject();
		statementCacheSize = in.readInt();
		logLevel = in.readInt();
	}
}
