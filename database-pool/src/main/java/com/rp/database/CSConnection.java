/*
 * PooledConnection.java
 *
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.database;

import com.rp.util.*;
import com.rp.util.pool.*;

import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * PooledConnection that implements a PreparedStatement and CallableStatement cache
 *
 * @author  Richardson Publications
 * @version 1.0, 05/2003
 *
 * Revision History
 * 1.0 - 05/2003
 */

public class CSConnection extends PooledConnection
{
	protected String CLASS_NAME = "CSConnection";
	protected Cache statementCache = null;
	protected boolean cacheable = false;
	protected String keyCode;
	protected static Random random = new Random();
	protected List keyList = null;
	
	static
	{
		random = new Random();	
	}

	public CSConnection (Connection con, ObjectPool pool, String logKey, Loggable log,
		boolean cacheable, Cache statementCache)
	{
		super(con, pool, logKey, log);
		super.setClassName(CLASS_NAME);
		String method = "Constructor";
		info (method, "in constructor");
		this.cacheable = cacheable;
		this.statementCache = statementCache;
		keyList = new ArrayList();
		keyCode = String.valueOf(random.nextInt(10000)) + String.valueOf(System.currentTimeMillis());	
		info (method, "leaving constructor");
	}
	
	protected void setClassName(String name)
	{
		CLASS_NAME = name;	
		super.setClassName(name);
	}
	
	protected PreparedStatement checkCachePrepared (String sql)
	{
		String method = "checkCachePrepared";
		info(method, "entering . . .");
		PreparedStatement ps = null;
		debug (method, "cacheable = " +cacheable);
		if (cacheable)
		{
			debug (method, "sql = " +sql);
			Object obj = statementCache.get(sql);
			if (obj != null)
			{
				debug (method, "object is not null");
				if (obj instanceof PreparedStatement)
				{
					debug (method, "object instance of PreparedStatement");
					try
					{
						debug (method, "clearing statement");
						ps = (PreparedStatement)obj;
						ps.clearParameters();
						ps.clearWarnings();
						ps.clearBatch();	
						debug (method, "done clearing statement");
					}
					catch (Exception e)
					{
						error(method, e.toString());
						statementCache.remove(sql);
					}
				}		
			}	
		}
		info (method, "leaving . . .");
		return ps;
	}
	
	protected CallableStatement checkCacheCallable (String sql)
	{
		String method = "checkCacheCallable";
		info (method, "entering . . .");
		CallableStatement cs = null;
		debug (method, "cacheable = " +cacheable);
		if (cacheable)
		{
			debug (method, "sql = " +sql);
			Object obj = statementCache.get(sql);
			if (obj != null)
			{
				debug (method, "object is not null");
				if (obj instanceof CallableStatement)
				{
					debug (method, "object instance of CallableStatement");
					try
					{
						debug (method, "clearing statement");
						cs = (CallableStatement)obj;
						cs.clearParameters();
						cs.clearWarnings();
						cs.clearBatch();	
						debug (method, "done clearing statement");
					}
					catch (Exception e)
					{
						error(method, e.toString());
						statementCache.remove(sql);
					}
				}		
			}	
		}	
		info (method, "leaving . . .");
		return cs;
	}
		
	public PreparedStatement prepareStatement (String sql) throws SQLException
	{
		String method = "prepareStatement(String)";
		info (method, "entering . . .");
		debug (method, "sql = " +sql);
		//can't add to the statements object.  that gets cleaned up on close
		//needs to know whether the statement was added to the cache or not
		//if not, cannot create a cachable prepared statement
		PreparedStatement p = null;
		String key = keyCode + sql;
		debug (method, "key = " +key);
		debug (method, "calling checkCachePrepared()");
		PreparedStatement temp = checkCachePrepared(key);
		debug (method, "call complete");
		if (temp != null)
		{
			debug (method, "statement retrieved from cache");
			p = temp;
		}	
		else
		{
			debug (method, "statement not found in cache");
			debug (method, "preparing statement");
			temp = con.prepareStatement(sql);
			debug (method, "statement prepared");
			debug (method, "cacheable = " +cacheable);
			if (cacheable)
			{
				debug (method, "creating CacheablePreparedStatement");
				p = new CacheablePreparedStatement (temp);
				debug (method, "done creating CacheablePreparedStatement");
				debug (method, "trying to add statement to cache");
				boolean b = statementCache.put (key, p);	
				if (!b)
				{
					debug (method, "cache is full, could not add statement");
					//cache is full, etc.  statement did not get added
					//need to convert to a regular PreparedStatement
					debug (method, "converting to regular PreparedStatement");
					p = temp;
					statements.add(p);
				}
				else
				{
					debug (method, "statement added to cache");
					keyList.add(key);	
				}
			}
			else
			{
				p = temp;
				statements.add(p);
			}
		}
		info (method, "leaving . . .");
		return p;
	}
	
	public CallableStatement prepareCall (String sql) throws SQLException
	{
		String method = "prepareCall(String)";
		info (method, "entering . . .");
		debug (method, "sql = " +sql);
		CallableStatement c = null;
		String key = keyCode + sql;
		debug (method, "key = " +key);
		debug (method, "calling checkCacheCallable()");
		CallableStatement temp = checkCacheCallable(key);
		debug (method, "call complete");
		if (temp != null)
		{
			debug (method, "statement retrieved from cache");
			c = temp;
		}	
		else
		{
			debug (method, "statement not found in cache");
			debug (method, "preparing call");
			temp = con.prepareCall(sql);
			debug (method, "call prepared");
			debug (method, "cacheable = "+cacheable);
			if (cacheable)
			{
				debug (method, "creating CacheableCallableStatement");
				c = new CacheableCallableStatement (temp);
				debug (method, "statement created");
				debug (method, "trying to add statement to cache");
				boolean b = statementCache.put (key, c);
				if (!b)
				{
					debug (method, "cache is full. could not add statement");
					//cache is full, etc.  statement did not get added
					//need to convert to a regular CallableStatement
					debug (method, "converting to regular CallableStatement");
					c = temp;
					statements.add(c);
				}
				else
				{
					debug (method, "statement added to cache");
					keyList.add(key);	
				}
			}
			else
			{
				c = temp;
				statements.add(c);
			}
		}
		info (method, "leaving . . .");
		return c;
	}
	
    protected void emptyCache()
    {
    	String method = "emptyCache()";
    	info (method, "entering . . .");
    	//remove the keys containing statements
    	//created from this 
    	//connection from the cache	
    	//close those statements
    	if (keyList != null)
    	{
	    	int size = keyList.size();
	    	debug (method, "number of keys = " +size);
	    	String key = null;
	    	debug (method, "attempting to remove objects from cache");
	    	for (int i = 0; i < size; i++)
	    	{
	    		key = (String)keyList.get(i);
	    		Object obj = statementCache.get(key);
	    		if (obj != null && (obj instanceof CacheableStatement))
	    		{
	    			debug (method, "removing "+key+" from cache");
	    			statementCache.remove(key);
	    			CacheableStatement temp = (CacheableStatement)obj;
	    			debug (method, "destroying statement");
	    			try
	    			{
	    				temp.destroy();
	    			}
	    			catch (Exception e){}
	    		}	
	    	}
	    }
    	info (method, "leaving . . .");
    }
    
    protected void finalize() throws Throwable
    {
    	destroy();
    }
    
    protected void destroy() throws SQLException
    {
    	String method = "destroy";
    	info (method, "entering . . .");
    	SQLException sqle = null;
    	try
    	{
	    	try
	    	{
	    		debug (method, "calling closeResources()");
	    		closeResources();
	    		debug (method, "done calling closeResources()");
	    	}
	    	catch (SQLException e)
	    	{
	    		sqle = e;	
	    	}
	    	debug (method, "emptying cache");
	    	emptyCache();
	    	debug (method, "done emptying cache");
	    	try
	    	{
	    		debug (method, "closing connection");
	    		closeConnection();
	    		debug (method, "done closing connection");
	    	}
	    	catch (SQLException e)
	    	{
	    		sqle = e;	
	    	}
	    }
    	finally
    	{
			statements = null;
    		pool = null;
    	}
    	if (sqle != null)
    	{
    		throw sqle;	
    	}
    	info (method, "leaving . . .");
    } 
}
