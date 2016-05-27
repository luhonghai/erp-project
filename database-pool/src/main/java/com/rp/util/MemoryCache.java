package com.rp.util;

import java.util.*;

public abstract class MemoryCache implements Cache
{
	protected String CLASS_NAME = "MemoryCache";
	protected boolean addWhenFull; 
	protected Map cache = null;
	protected Map times = null;
	protected List keys = null;
	protected int maxSize = 0;
	protected int timeOut = 0;
	protected Loggable log = null;
	protected String logKey = null;
	
	public MemoryCache (int max, int t, boolean addWhenFull)
	{
		this(max, t, addWhenFull, null, null);	
	}
	
	public MemoryCache (int max, int t, boolean addWhenFull, Loggable inLog, String inLogKey)
	{
		this.maxSize = max;	
		this.cache = new HashMap(max);
		this.keys = new ArrayList(max);
		this.times = new HashMap(max);
		this.timeOut = t;
		//if true, remove from the beginning when cache is full, add new object to the end
		//if false, do not add objects to the cache after the cache is full
		this.addWhenFull = addWhenFull;
		this.log = inLog;
		this.logKey = inLogKey;
	}

	public abstract void destroy ();
	
	public synchronized int getSize()
	{
		return cache.size();	
	}
	
	protected synchronized Object[] getObjects ()
	{
		String method = "getObjects()";
		info (method, "entering . . .");
		int size = cache.size();
		debug (method, "cache size = " +size);
		Object[] arr = new Object[size];
		for (int i = 0; i < size; i++)
		{
			arr[i] = cache.get ( (String)keys.get(i) );	
		}
		info (method, "leaving . . .");
		return arr;
	}	
	
	public synchronized void remove (Object key)
	{
		String method = "remove(Object)";
		info(method, "entering . . .");
		times.remove(key);
		cache.remove(key);
		keys.remove(key);
		info (method, "leaving . . .");
	}
	
	//if we refresh times when an object is retrieved from the pool, 
	//how do we handle the get method?  can't loop thru the entire cache
	//looking for a timeout everytime someone tries a put after the cache is full?
	public synchronized boolean put (Object key, Object obj)
	{
		String method = "put(Object, Object)";
		info (method, "entering . . .");
		boolean addFlag = false;
		if (maxSize > 0)
		{
			//we are at capacity
			if (cache.size() >= maxSize)
			{
				debug (method, "cache is full.  cache size > maxSize");
				Object tempKey = keys.get(0);
				Object tempTimeObj = null;
				if (timeOut > 0)
				{
					tempTimeObj = times.get(tempKey);	
					debug (method, "timeout > 0, getting time for object");
				}
				if (tempTimeObj != null)
				{
					long holdTime = ((Long)tempTimeObj).longValue();
					debug (method, "time held = " +holdTime);
					//if the object has been in the cache longer than the timeout, 
					//remove it
					if (convertTime((getTime() - holdTime)) > timeOut)
					{
						debug (method, "object in cache longer than timeout, removing");
						remove(tempKey);
						addFlag = true;
					}
				}
				//the first object has not timed out
				if (!addFlag)
				{
					//remove the first object if addWhenFull is true
					//to make room for the new object
					if (addWhenFull)
					{
						debug (method, "add when full is set, removing first object");
						remove(tempKey);
						addFlag = true;	
					}
				}
			}
			//there is still room in the cache
			else
			{
				addFlag = true;	
			}	
		}
		if (addFlag)
		{
			debug (method, "adding object to cache");
			cache.put (key, obj);					
			times.put (key, (new Long(getTime())));
			keys.add(key);	
		}
		else
		{
			debug (method, "cache is full, not adding");	
		}
		return addFlag;
	}
	
	public synchronized Object get (Object key)
	{
		String method = "get(Object";
		info (method, "entering . . .");
		Object retObj = null;
		if (timeOut > 0)
		{
			debug (method, "timeout set, checking time held of object");
			retObj = cache.get(key);
			if (retObj != null)
			{
				long tValue = ( (Long)times.get(key) ).longValue();
				if ( convertTime((getTime() - tValue)) > timeOut)
				{
					debug (method, "time held greater than timeout, removing object from cache");
					remove (key);
				}
			}
		}
		else
		{
			retObj = cache.get(key);	
		}
		info (method, "leaving . . .");
		return retObj;
	}
	
	protected long getTime()
	{
		return System.currentTimeMillis();
	}	
	
	protected long convertTime (long t)
	{
		t = (t / 1000L);
		return t;	
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
}