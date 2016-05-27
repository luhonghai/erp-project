package com.rp.database;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.rp.util.pool.*;
import com.rp.util.*;


public class StandardConnectionManager extends AbstractConnectionManager
{	
	
	/*
		Default ConnectionManager implementation.  Each connection must be explicitly returned to the pool
	
	*/
	
	protected String CLASS_NAME = "StandardConnectionManager::";
	
	public static ObjectManager getInstance (String url, String user, String password, String driver, 
												String checkQuery, boolean autoCommit, String logKey, Loggable log)
												throws SQLException, ClassNotFoundException
	{
		StandardConnectionManager cm = new StandardConnectionManager (url, user, password, driver, checkQuery,
										autoCommit, logKey, log);	
		return cm;
	}
	
	protected StandardConnectionManager (String url, String user, String password, String driver, String checkQuery, 
							boolean autoCommit, String logKey, Loggable log) 
							throws SQLException, ClassNotFoundException
	{
		super (url, user, password, driver, checkQuery,
				autoCommit, logKey, log);
		super.setClassName(CLASS_NAME);
	}
	
	protected void setClassName(String name)
	{
		CLASS_NAME = name;	
		super.setClassName(name);
	}

	public Object create () throws PoolException
	{
		String method = "create()";
		info (method, "entering  . . .");
		Connection con = null;
		try
		{
			debug (method, "attempting to create connection");
			con = createConnection();
			debug (method, "connection created.");
		}
		catch (SQLException e)
		{
			error(method, "SQL EXCEPTION while trying to create connections: " +e.toString());
			throw new PoolException (e.toString(), e);
		}
		info (method, "leaving  . . .");
		return con;
	}
	
	public Object performEmpty (Object obj) throws PoolException
	{
		String method = "performEmpty()";
		info (method, "entering . . .");
		info (method, "leaving . . .");
		return null;
	}

	public void destroy (Object obj) throws PoolException
	{
		String method = "destroy()";
		info (method, "entering . . .");
		try
		{
			debug (method, "checking to see if object is null . . .");
			if (obj != null)
			{
				debug (method, "object is not null");
				debug (method, "casting object to Connection . . .");
				Connection con = (Connection)obj;
				debug (method, "cast complete");
				debug (method, "calling close on connection . . .");
				con.close();
				debug (method, "close call complete");
			}
			else
			{
				debug (method, "object is null");	
			}
		}
		catch (SQLException e)
		{
			error (method, "Problem destroying PooledConnection.");
			error (method, "SQLException:: " +e.toString());	
			throw new PoolException (e.toString(), e);
		}
		info (method, "leaving . . .");
	}
}