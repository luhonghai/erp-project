package com.rp.database;

import java.util.*;
import java.io.*;
import java.sql.*;
import com.rp.util.*;
import com.rp.util.pool.*;

public abstract class AbstractConnectionManager implements ObjectManager
{	
	protected String url = null;
	protected String user = null;
	protected String password = null;
	protected String driver = null;
	protected String checkQuery = null;
	protected boolean autoCommit = false;
	protected String logKey = null;
	protected Loggable log = null;
	protected String CLASS_NAME = "AbstractConnectionManager";

	//must override destroy, create, and performEmpty

	protected AbstractConnectionManager (String url, String user, String password, String driver, String checkQuery, 
							boolean autoCommit, String logKey, Loggable log) 
							throws SQLException, ClassNotFoundException
	{
		this.logKey = logKey;
		this.log = log;
		String method = "constructor";
		info (method, "entering constructor()");
		info (method, "setting member variables");
		this.url = url;
		this.user = user;
		this.password = password;
		this.driver = driver;
		this.checkQuery = checkQuery;
		this.autoCommit = autoCommit;
		info (method, "leaving . . .");
	}
	
	protected final void init() throws SQLException
	{
		String method = "init()";
		info (method, "entering  . . .");
		try
		{
			debug (method, "attempting to load driver");
			Class.forName(driver);
			debug (method, "done loading driver");
		}
		catch(ClassNotFoundException c)
		{
			error(method, "COULD NOT LOAD DRIVER: Class Not Found: " +driver);
			error(method, "EXCEPTION MESSAGE: " +c.toString());
        	throw new SQLException (CLASS_NAME + ":" +method+ "::ClassNotFoundException--"+c.toString());
		}
		info (method, "leaving  . . .");	
	}
	
	protected void setClassName(String name)
	{
		CLASS_NAME = name;	
	}
	
	public abstract Object create () throws PoolException;
	public abstract void destroy (Object obj) throws PoolException;
	public abstract Object performEmpty (Object obj) throws PoolException;
		
	public Object performGet (Object obj) throws PoolException
	{
		String method = "performGet()";
		info (method, "entering  . . .");
		Connection con = null;
		try
		{	
			debug (method, "casting object to Connection . . .");
			con = (Connection)obj;
			debug (method, "done with cast.");
			debug (method, "checking if connection is closed . . .");
			if (checkClosed(con))
			{
				debug (method, "connection is closed or not available");
				try
				{
					debug (method, "attempting to destroy closed connection . . .");
					destroy(con);
					debug (method, "closed connection destroyed.");
				}
				catch (PoolException e)
				{
					error (method, "exception while trying to destroy closed connection: " +e.toString());	
				}
				debug (method, "connection is closed.  attempting to create new connection . . .");
				con = (Connection)create();
				debug (method, "new connection created.");
			} 
			else
			{
				debug (method, "connection is not closed.  checking connection. . .");
				con = checkConnection(con);	
				debug (method, "done checking connection.");
			}
		}
		catch (PoolException p)
		{
			error (method, "PoolException:: " +p.toString());
			throw p;	
		}
		catch (SQLException e)
		{
			error (method, "SQLException:: " +e.toString());
			throw new PoolException (e.toString(), e);
		}
		info (method, "leaving  . . .");
		return con;
	}

	public Object performFree (Object obj) throws PoolException
	{
		String method = "performFree()";
		Connection con = null;
		info (method, "entering  . . .");
		try
		{
			debug (method, "casting object to Connection . . .");
			con = (Connection)obj;
			debug (method, "cast complete");
			debug (method, "returning connection to previous state . . .");
			resetConnection(con);
			debug (method, "done resetting connection.");
		}
		catch (SQLException e)
		{
			error (method, "SQLException:: " +e.toString());
			throw new PoolException (e.toString(), e);
		}
		debug (method, "leaving  . . .");
		return con;
	}
	
	protected Connection createConnection() throws SQLException
	{
		String method = "createConnection()";
		info (method, "entering  . . .");
		Connection con = null;
		try
		{
			debug (method, "attempting to create connection: user = " +user+ " url = " +url);
			con = DriverManager.getConnection(url, user, password);
			debug (method, "connection created");
			debug (method, "setting autocommit . . .");
			con.setAutoCommit(autoCommit);
			debug (method, "autocommit set to " +autoCommit);
		}	
		catch (SQLException e)
		{
			error(method, "SQL Exception occurred while creating connection . . . MESSAGE: " +e.toString());
			throw e;
		}
		info (method, "leaving "+method+" . . .");
		return con;
	}
	
	protected void resetConnection(Connection con) throws SQLException
	{
		String method = "resetConnection";
		info (method, "entering . . .");
		if (con != null)
		{
			try
			{	
				debug (method, "setting autocommit . . .");
				con.setAutoCommit(autoCommit);
				debug (method, "autocommit set to " +autoCommit);
			}
			catch (SQLException e)
			{
				error (method, "Exception thrown on set of autoCommit");
				throw e;	
			}
			try
			{
				debug (method, "clearing warnings . . .");
				con.clearWarnings();
				debug (method, "warnings cleared.");
			}
			catch (SQLException e)
			{
				error (method, "Exception thrown on clearWarnings");
				throw e;	
			}
		}
		info (method, "leaving "+method+" . . .");
	}	
	
	protected Connection checkConnection (Connection con) throws SQLException
	{
		String method = "checkConnection()";
		info (method, "entering . . .");
		debug (method, "checking checkQuery value . . .");
		if ( !isNull(checkQuery) )
		{
			debug (method, "checkQuery has a value.  preparing to execute checkQuery.");
			PreparedStatement st = null;
			try
			{
				debug (method, "preparing statement with query = "+checkQuery+ ". . .");
	 			st = con.prepareStatement(checkQuery);
	 			debug (method, "statement prepared.");
	 			debug (method, "executing preparedStatement . . .");
	 			boolean val = st.execute();
	 			debug (method, "preparedStatement executed");
			}
			catch (SQLException sqle)
			{
				error (method, "An exception occurred while attempting to run checkQuery: " +sqle.toString());
				error (method, "STACK TRACE: " +ExceptionUtil.getStackTrace(sqle));
				try
				{
					debug (method, "attempting to destroy bad connection . . .");
					destroy(con);
					debug (method, "bad connection destroyed");
				}
				catch (PoolException e)
				{
					error (method, "Exception while trying to close bad connection:: "+e.toString());	
				}
				try
				{
					debug (method, "replacing connection with new connection . . .");
					con = (Connection)create();	
					debug (method, "connection replaced.");
				}
				catch (PoolException e)
				{
					error (method, "An exception occurred while creating a new Connection.  MESSAGE: " +e.toString());
					error (method, "STACK TRACE: " +ExceptionUtil.getStackTrace(e));
					throw new SQLException(e.toString());
				}
			}	
			finally
			{
				debug (method, "closing up checkQuery resources . . .");
				try
				{
					if (st != null)
					{
	 					st.close();	
	 				}
	 			}
	 			catch (Exception ex)
	 			{}
			}
		}
		info (method, "leaving "+method+" . . .");
		return con;
	}
	
	protected boolean checkClosed(Connection con)
	{
		boolean closed = false;
		String method = "checkClosed(Connection)";
		info (method, "entering . . .");
		try
		{
			if (con == null || con.isClosed())
			{
				closed = true;
			}	
		}
		catch (Exception e)
		{
			warn(method, "Exception checking if connection is closed: " +e.toString());
			closed = true;
		}	
		info (method, "leaving . . .");
		return closed;
	}
	
	protected void fatal (String method, String s)
	{
		if (log != null)
		{
			log.fatal(CLASS_NAME, method, s, logKey);	
		}
	}
	
	protected void error (String method, String s)
	{
		if (log != null)
		{
			log.error(CLASS_NAME, method, s, logKey);	
		}
	}
	
	protected void warn (String method, String s)
	{		
		if (log != null)
		{
			log.warn(CLASS_NAME, method, s, logKey);	
		}
	}
	
	protected void info (String method, String s)
	{
		if (log != null)
		{
			log.info(CLASS_NAME, method, s, logKey);	
		}
	}
	
	protected void debug (String method, String s)
	{
		if (log != null)
		{
			log.debug(CLASS_NAME, method, s, logKey);	
		}
	}
	
	protected boolean isNull (String s)
	{
		if (s == null || s.trim().length() == 0)
		{
			return true;	
		}
		return false;
	}
}