package com.rp.database;

import java.util.*;
import java.io.*;
import java.sql.*;
import com.rp.util.pool.*;
import com.rp.util.*;

public class CSConnectionManager extends PooledConnectionManager
{	
	protected String CLASS_NAME = "CSConnectionManager::";
	protected boolean cacheable = false;
	protected Cache statementCache = null;
	
	public static ObjectManager getInstance (String url, String user, String password, String driver, 
												String checkQuery, int holdTime, 
												boolean autoCommit, String logKey, Loggable log,
												boolean cacheable, Cache statementCache)
												throws SQLException, ClassNotFoundException
	{
		ObjectManager cm = new CSConnectionManager (url, user, password, driver, checkQuery, holdTime,
										autoCommit, logKey, log, cacheable, statementCache);	
		return cm;
	}
	
	protected CSConnectionManager (String url, String user, String password, String driver, String checkQuery, 
							int holdTime, boolean autoCommit, String logKey, Loggable log,
							boolean cacheable, Cache statementCache) 
							throws SQLException, ClassNotFoundException
	{
		super (url, user, password, driver, checkQuery,
				holdTime, autoCommit, logKey, log);
		this.cacheable = cacheable;
		this.statementCache = statementCache;
		super.setClassName(CLASS_NAME);
	}
	
	public Object create () throws PoolException
	{
		String method = "create()";
		info (method, "entering  . . .");
		if (pool == null)
		{
			fatal(method, "pool cannot be null");
			throw new PoolException ("CSConnectionManager:create(): pool cannot be null");	
		}
		Connection pConn = null;
		try
		{
			debug (method, "attempting to create connection");
			Connection con = createConnection();
			debug (method, "connection created.");
			debug (method, "attempting to set up pooled connection . . .");
			pConn = new CSConnection (con, pool, logKey, log, cacheable, 
									statementCache);
			debug (method, "pooled connection set up.");
		}
		catch (SQLException e)
		{
			error(method, "SQL EXCEPTION while trying to create connections: " +e.toString());
			throw new PoolException (e.toString(), e);
		}
		info (method, "leaving  . . .");
		return pConn;
	}
}