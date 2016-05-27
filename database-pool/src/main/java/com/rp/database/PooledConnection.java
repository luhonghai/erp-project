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
import java.util.concurrent.Executor;

/**
 * Implementation of a database connection that cannot be physically closed.  All calls to
 * close() will return this connection to the pool registered with this connection.
 *
 * @author  Richardson Publications
 * @version 2.0, 05/2003
 *
 * Revision History
 * 1.0 - 05/2002
 * 2.0 - 05/2003 - Added finalize method
 */
 

public class PooledConnection extends ConnectionWrapper
{
	protected Connection con = null;
	protected ObjectPool pool = null;
	protected List statements = null;
	protected Loggable log = null;
	protected String logKey = null;
	protected String CLASS_NAME = "PooledConnection";
	private long time = 0L;

	/*
	6. STATEMENT SHOULD BE CACHED WHEN CALLING THE CLOSE METHOD INSTEAD OF THE PREPARE CALL METHOD?
		test with storing it at creation time and see what happens.
	*/
	
	public PooledConnection (Connection con, ObjectPool pool, String logKey, Loggable log)
	{
		super(con);
		this.log = log;
		this.logKey = logKey;
		String method = "Constructor";
		info (method, "in constructor");
		this.con = con;
		this.pool = pool;
		this.statements = new ArrayList();
		this.time = getCurrentTime();
		info (method, "leaving constructor");
	}
	
	public PooledConnection (Connection con, ObjectPool pool)
	{
		this(con, pool, null, null);
		this.time = getCurrentTime();
	}
	
	public long getTime ()
	{
		return this.time;	
	}
	
	protected void setClassName(String name)
	{
		CLASS_NAME = name;	
	}
	
	protected long getCurrentTime()
	{
		return System.currentTimeMillis();	
	}

	public Statement createStatement() throws SQLException
	{
		String method = "createStatement()";
		info (method, "entering . . .");
		debug (method, "creating statement . . .");
		Statement s = con.createStatement();
		statements.add(s);
		info (method, "leaving . . . ");
		return s;
	}
	
	public PreparedStatement prepareStatement (String sql) throws SQLException
	{
		String method = "prepareStatement(String)";
		info (method, "entering . . .");
		debug (method, "preparing statement with sql: "+sql);
		PreparedStatement p = con.prepareStatement (sql);
		debug (method, "statement prepared");
   		statements.add(p);
   		info (method, "leaving . . .");
   		return p;	
	}
	
	public CallableStatement prepareCall (String sql) throws SQLException
	{
		String method = "prepareCall(String)";
		info (method, "entering . . .");
		debug (method, "preparing call with sql: " +sql);
		CallableStatement c = con.prepareCall (sql);
		debug (method, "statement prepared");
		statements.add(c);
		info (method, "leaving . . .");
		return c;
	}
	
	public Statement createStatement(int resultSetType, int resultSetConcurrency) 
      throws SQLException
    {
    	String method = "createStatement(int, int)";
    	info (method, "entering . . .");
    	debug (method, "creating statement: "+resultSetType+ ", " +resultSetConcurrency);
    	Statement s = con.createStatement (resultSetType, resultSetConcurrency);
    	debug (method, "statement created");
    	statements.add(s);
    	info (method, "leaving . . .");
    	return s;	
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, 
					int resultSetConcurrency)
       throws SQLException
   	{
   		String method = "prepareStatement(String, int, int)";
   		info (method, "entering . . .");
   		debug (method, "preparing statement "+sql+ ", " +resultSetType+ ", " +resultSetConcurrency);
   		PreparedStatement p = con.prepareStatement (sql, resultSetType, resultSetConcurrency);
   		debug (method, "statement prepared");
   		statements.add(p);
   		info (method, "leaving . . .");
   		return p;	
   	}

    public CallableStatement prepareCall(String sql, int resultSetType, 
				 int resultSetConcurrency) throws SQLException
	{
		String method = "prepareCall(String, int, int)";
		info (method, "entering . . .");
		debug (method, "preparing call "+sql+ ", " +resultSetType+ ", " +resultSetConcurrency);
		CallableStatement c = con.prepareCall (sql, resultSetType, resultSetConcurrency);
		debug (method, "call prepared");
		statements.add(c);
		info (method, "leaving . . .");
		return c;
	}			 
	
	 public Statement createStatement(int resultSetType, int resultSetConcurrency, 
			      int resultSetHoldability) throws SQLException
	{
		String method = "createStatement(int, int, int)";
		info (method, "entering . . .");
		debug (method, "creating statement "+resultSetType+ ", " +resultSetConcurrency+ ", " +resultSetHoldability);
		Statement s = con.createStatement (resultSetType, resultSetConcurrency, resultSetHoldability);
		debug (method, "statement created");
		statements.add(s);
		info (method, "leaving . . .");
		return s;
	}
	 
    public PreparedStatement prepareStatement(String sql, int resultSetType, 
				       int resultSetConcurrency, int resultSetHoldability)
		throws SQLException
	{
		String method = "prepareStatement (String, int, int, int)";
		info (method, "entering . . .");
		debug (method, "preparing statement "+sql+ ", " +resultSetType+ ", " +resultSetConcurrency+ ", " +resultSetHoldability);
		PreparedStatement p = con.prepareStatement (sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		debug (method, "statement prepared");
		statements.add(p);
		info (method, "leaving . . .");
		return p;
	}
  	 
    public CallableStatement prepareCall(String sql, int resultSetType, 
				  int resultSetConcurrency, 
				  int resultSetHoldability) throws SQLException
	 {
	 	String method = "prepareCall (String, int, int, int)";
	 	info (method, "entering . . .");
	 	debug (method, "preparing call " +sql+ ", " +resultSetType+ ", " +resultSetConcurrency+ 
	 		", " +resultSetHoldability);
	 	CallableStatement c = con.prepareCall (sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	 	debug (method, "call prepared");
	 	statements.add(c);
	 	info(method, "leaving . . .");
		return c;
	 }
	 
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
		throws SQLException
	{
		String method = "prepareStatement(String, int)";
		info (method, "entering . . .");
		debug (method, "preparing statement "+sql+", " +autoGeneratedKeys);
		PreparedStatement p = con.prepareStatement (sql, autoGeneratedKeys);
		debug (method, "statement prepared");
		statements.add(p);
		info (method, "leaving . . .");
	 	return p;
	}
	 
    public PreparedStatement prepareStatement(String sql, int columnIndexes[])
		throws SQLException
	{
		String method = "prepareStatement (String, int[]";
		info (method, "entering . . .");
		debug (method, "preparing statement "+sql);
		PreparedStatement p = con.prepareStatement (sql, columnIndexes);
		debug (method, "statement prepared");
		statements.add(p);
		info (method, "leaving . . .");
		return p;
	}
	 
    public PreparedStatement prepareStatement(String sql, String columnNames[])
		throws SQLException
	{
		String method = "prepareStatement(String, String[])";
		info (method, "entering . . .");
		debug (method, "preparing statement " +sql);
		PreparedStatement p = con.prepareStatement (sql, columnNames);	
		debug (method, "statement prepared");
		statements.add(p);
		info (method, "leaving . . .");
    	return p;
	}

	public Clob createClob() throws SQLException {
		return null;
	}

	public Blob createBlob() throws SQLException {
		return null;
	}

	public NClob createNClob() throws SQLException {
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		return null;
	}

	public boolean isValid(int timeout) throws SQLException {
		return false;
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {

	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {

	}

	public String getClientInfo(String name) throws SQLException {
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		return null;
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return null;
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return null;
	}

	public void setSchema(String schema) throws SQLException {

	}

	public String getSchema() throws SQLException {
		return null;
	}

	public void abort(Executor executor) throws SQLException {

	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

	}

	public int getNetworkTimeout() throws SQLException {
		return 0;
	}

	public void close() throws SQLException
    {
    	String method = "close()";
    	info (method, "entering . . .");
    	try
    	{
	    	debug (method, "closing connection resources . . .");
	    	this.closeResources();
	    	debug (method, "done closing connection resources .");
	    	debug (method, "freeing connection back to pool . . .");
			this.pool.freeObject(this);
			debug (method, "done freeing connection.");
		}
		catch (PoolException e)
		{
			error (method, "Exception trying to free object: " +e.toString());
			throw new SQLException(e.toString());	
		}
		info (method, "leaving . . .");
	}

    protected void closeConnection() throws SQLException
    {
    	String method = "closeConnection()";
    	info (method, "entering . . .");
    	if (con != null)
    	{
    		debug (method, "connection not null, attempting to close");
    		con.close();	
    		debug (method, "connection is closed");
    		con = null;
    	}
    	info (method, "leaving . . .");
    }
    
    protected void finalize() throws Throwable
    {
    	destroy();
    }
    
    protected void destroy() throws SQLException
    {
    	String method = "destroy()";
    	info (method, "entering . . .");
    	SQLException sqle = null;
    	try
    	{
	    	try
	  		{
	  			debug (method, "attempting to close resources");
	    		closeResources();
	    		debug (method, "resources closed");
	    	}
	    	catch (SQLException e)
	    	{
	    		error(method, e.toString());
	    		sqle = e;	
	    	}
	    	
	    	try
	    	{
	    		debug (method, "attempting to close connection");
	    		closeConnection();
	    		debug (method, "connection closed");
	    	}
	    	catch (SQLException e)
	    	{
	    		error(method, e.toString());
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
    
    protected void closeResources() throws SQLException
    {
    	String method = "closeResources()";
    	info (method, "entering . . .");
    	SQLException eThrow = null;
    	if (statements != null)
    	{
			int size = statements.size();
			debug (method, "statements size = " +size);
			debug (method, "closing statements");
			
			ListIterator i = statements.listIterator();
			while (i.hasNext())
			{
				try
				{
					Statement s = (Statement)i.next();
					if (s != null)
					{
						s.close();
					}	
					i.remove();
				}
				catch (SQLException e)
				{
					error (method, "error closing statement.  index = " +i+ " total indices = " +size);
					eThrow = e;
				}
			}
			debug (method, "done closing statements");
		}
		if (eThrow != null)
		{	
			throw eThrow;
		}
		info (method, "leaving . . .");
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

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
}
