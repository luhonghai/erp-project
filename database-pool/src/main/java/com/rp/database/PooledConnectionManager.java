package com.rp.database;

import com.rp.util.Loggable;
import com.rp.util.pool.ObjectManager;
import com.rp.util.pool.ObjectPool;
import com.rp.util.pool.PoolException;

import java.sql.Connection;
import java.sql.SQLException;


public class PooledConnectionManager extends StandardConnectionManager
{	
	protected String CLASS_NAME = "PooledConnectionManager::";
	protected int holdTime = 0;
	protected ObjectPool pool = null;
	
	public static ObjectManager getInstance (String url, String user, String password, String driver, 
												String checkQuery, int holdTime, 
												boolean autoCommit, String logKey, Loggable log)
												throws SQLException, ClassNotFoundException
	{
		ObjectManager cm = new PooledConnectionManager (url, user, password, driver, checkQuery, holdTime,
										autoCommit, logKey, log);	
		return cm;
	}
	
	protected PooledConnectionManager (String url, String user, String password, String driver, String checkQuery, 
							int holdTime, boolean autoCommit, String logKey, Loggable log) 
							throws SQLException, ClassNotFoundException
	{
		super (url, user, password, driver, checkQuery,
				autoCommit, logKey, log);
		this.holdTime = holdTime;
		super.setClassName(CLASS_NAME);
	}
	
	protected void setClassName(String name)
	{
		CLASS_NAME = name;	
		super.setClassName(name);
	}
	
	public void init (ObjectPool pool)
	{
		this.pool = pool;	
	}

	public Object create () throws PoolException
	{
		String method = "create()";
		info (method, "entering  . . .");
		if (pool == null)
		{
			throw new PoolException ("ObjectPool cannot be null.  Before calling create(), an instantiated pool " +
					"must be passed into this object.");	
		}
		PooledConnection pConn = null;
		try
		{
			debug (method, "attempting to create connection");
			Connection con = createConnection();
			debug (method, "connection created.");
			debug (method, "attempting to set up pooled connection . . .");
			pConn = new PooledConnection (con, pool, logKey, log);
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
	
	public Object performEmpty (Object obj) throws PoolException
	{
		String method = "peformEmpty";
		Connection temp = null;
		info (method, "entering . . .");
		try
		{
			debug (method, "checking if hold timeout check is set");
			if (holdTime > 0)
			{
				debug (method, "hold timeout is set.  preparing to check used connections for timeout . . .");
				debug (method, "casting object to PooledConnection . . .");
				PooledConnection con = (PooledConnection)obj;
				debug (method, "cast complete");
				debug (method, "checking used connections for hold timeout . . .");
				temp = checkConnectionTime(con);
				debug (method, "used connections checked.");
			}
			else
			{
				debug (method, "hold timeout is not set.");
			}
		}
		catch (SQLException e)
		{
			error (method, "SQLException:: " +e.toString());
			throw new PoolException (e.toString(), e);	
		}
		info (method, "leaving . . .");
		return temp;
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
				debug (method, "casting object to PooledConnection . . .");
				PooledConnection pc = (PooledConnection)obj;
				debug (method, "cast complete");
				debug (method, "calling destroy on PooledConnection . . .");
				pc.destroy();	
				debug (method, "destroy call complete");
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

	//if this returns a non-null object, that is the new object for the pool
	//if it returns null, there is no new object for the pool
	protected Connection checkConnectionTime (Connection con) throws SQLException
	{
		String method = "checkConnectionTime()";
		PooledConnection pConn = null;
		Connection temp = null;
		info (method, "entering . . .");
		try
		{
			debug (method, "checking connection ");
			debug (method, "casting to PooledConnection");
			pConn = (PooledConnection)con;
			long longDuration = getTime() - pConn.getTime();
			int duration = (int)(longDuration / 1000.0);
			debug (method, "connection in use for " +duration+ " seconds");
			if (duration > holdTime)
			{
				debug (method, "duration greater than holdTimeout");
				debug (method, "attempting to destroy connection . . ..");
				try
				{
					pConn.destroy();
					debug (method, "connection destroyed");
				}
				catch (SQLException se)
				{
					debug (method, "Exception caught while destorying connection: " +se.toString());	
				}
				debug (method, "creating new connection . . .");
				temp = (Connection)create();
				debug (method, "new connection created . . .");
			}	
		}
		catch (PoolException ex)
		{
			error (method, "An error occurred while checking used connections: " +ex.toString());
			throw new SQLException(ex.toString());	
		}
		info (method, "leaving "+method+" . . .");
		return temp;
	}
	
	protected long getTime()
	{
		return System.currentTimeMillis();	
	}
}