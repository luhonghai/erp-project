package com.rp.util.pool;

import java.util.*;
import com.rp.util.*;

public class ObjectPoolImpl implements ObjectPool
{
	protected String CLASS_NAME = "ObjectPoolImpl";
	protected ObjectManager manager = null;
	protected List freeList = null;
	protected List usedList = null;
	protected int total = 0;
	protected int min = 0;
	protected long waitTime = 0L;
	protected boolean initFlag = false;
	protected Loggable log = null;
	protected String logKey = null;
	protected boolean destroyFlag = false;
		
	public static ObjectPool getInstance (ObjectManager iMan, int total, int min, long wt, boolean init) throws PoolException
	{
		return (getInstance(iMan, total, min, wt, init, null, null));
	}	
	
	public static ObjectPool getInstance (ObjectManager iMan, int total, int min, long wt, boolean init,
		Loggable log, String logKey) throws PoolException
	{
		return (new ObjectPoolImpl(iMan, total, min, wt, init, log, logKey));	
	}
		
	protected ObjectPoolImpl (ObjectManager iMan, int total, int min, long wt, boolean init, Loggable inLog, String inLogKey) throws PoolException
	{
		this.manager = iMan;
		this.total = total;
		this.min = min;
		this.waitTime = wt;
		this.log = inLog;
		this.logKey = inLogKey;
		if (init)
		{
			init();
		}
	}	
	
	public synchronized void init() throws PoolException
	{
		String method = "init()";
		info(method, "entering . . .");
		freeList = new ArrayList();
		usedList = new ArrayList();
		debug (method, "min size = " +min);
		debug (method, "creating");
		for (int i = 0; i < min; i++)
		{
			Object obj = manager.create();
			freeList.add(obj);	
		}	
		debug (method, "done creating");
		info(method, "leaving . . .");
		initFlag = true;
		destroyFlag = false;
	}
	
	public synchronized Object getObject() throws PoolException
	{
		String method = "getObject()";
		info (method, "entering . . .");
		if (!initFlag)
		{
			throw new PoolException ("ObjectPool has not been inited.  Init() needs to be called.");	
		}
		Object obj = null;
		debug (method, "destroy flag = " +destroyFlag);
		if (!destroyFlag)
		{
			int freeSize = freeList.size();
			debug (method, "free size = " +freeSize);
			if (freeSize > 0)
			{
				obj = freeList.get(0);
				if (obj != null)
				{
					debug (method, "got object from freeList, calling doGet()");
					obj = doGet(obj);
					debug (method, "done calling doGet()");
				}
				else
				{
					debug (method, "object is null from freeList, creating new one");
					obj = doCreate(true);
					debug (method, "done creating");
				}
			}
			else
			{
				debug (method, "free list is empty");
				int usedSize = usedList.size();
				debug (method, "used size = " +usedSize);
				if ( (usedSize + freeSize) < total)
				{
					debug (method, "used + free < total, creating new object");
					obj = doCreate(false);
					debug (method, "done with create");
				}
				else
				{
					debug (method, "no objects available.  checking existing objects - calling doEmpty()");
					Object status = doEmpty();
					debug (method, "done calling doEmpty()");
					if (status != null)		//we have a new object for the pool
					{
						debug (method, "object returned from doEmpty call, notifying . . .");
						//notify the next thread waiting on this lock
						notify();
						obj = getObject();	
					}
					else
					{
						debug (method, "no objects returned from doEmpty, preparing to wait");
						try
						{
							if (waitTime > 0L)
							{
								debug (method, "wait time = " +waitTime);
								debug (method, "waiting for waitTime");
								long beginTime = getTime();
								wait(waitTime);	
								long endTime = getTime();
								long timeWaited = endTime - beginTime;
								debug (method, "waited for " +timeWaited);
								
								//no objects have become available while 
								//we were waiting.  throw Exception
								if (timeWaited >= waitTime)
								{
									debug (method, "wait time has expired, no objects became available, throwing exception");
									throw new PoolException("Waited for wait time. "+
									"No Objects available during wait period.");	
								}
							}
							else
							{
								debug (method, "no wait time set, waiting until notified . . .");
								wait();	
								debug (method, "done waiting");
							}
						}
						catch (InterruptedException ie)
						{
							debug (method, "InterruptedException caught: " +ie.toString());	
						}
						debug (method, "calling getObject()");
						obj = getObject();
					}
				}
			}
		}
		else
		{
			debug (method, "destroy flag set, notifying and throwing exception");
			//need to notify the next thread waiting on this lock
			notify();
			throw new PoolException ("Pool is in destroy mode.  Objects cannot be obtained");
		}
		info (method, "leaving . . .");
		return obj;
	}
	
	public synchronized void freeObject(Object freeObj) throws PoolException
	{
		String method = "freeObject(Object)";
		info (method, "entering . . .");
		int index = usedList.indexOf(freeObj);
		debug (method, "index of object = " +index);
		if (index >= 0)
		{
			debug (method, "object found, calling performFree and removing");
			freeObj = manager.performFree(freeObj);
			usedList.remove(index);
			freeList.add(freeObj);
			debug (method, "notifying");
			notify();
		}
		info (method, "leaving . . .");
	}
	
	//tries to destroy first.  then makes call to init.  no guarantee that
	//destroy will go smoothly
	public synchronized void recycle (boolean waitFlag) throws PoolException
	{
		String method = "recycle()";
		info (method, "entering . . .");
		debug (method, "waitFlag = " +waitFlag);
		if (waitFlag)
		{
			debug (method, "calling destroy(true)");
			destroy(true);	
		}
		else
		{
			debug (method, "calling destory(false)");
			destroy(false);
		}
		debug (method, "calling init()");
		init();
		info (method, "leaving . . .");
	}
	
	//tries to destroy all objects.  if exception occurs,
	//sets storage lists to null.  
	public synchronized void destroy (boolean waitFlag) throws PoolException
	{
		String method = "destroy()";
		info (method, "entering . . .");
		try
		{
			debug (method, "waitFlag = " +waitFlag);
			if (waitFlag)
			{
				destroyFlag = true;
				debug (method, "calling doDestory");
				doDestroy(freeList);
				debug (method, "done calling doDestory");
				if (freeList.size() > 0 || usedList.size() > 0)
				{
					debug (method, "used or free size > 0");
					debug (method, "calling doEmpty()");
					Object status = doEmpty();
					debug (method, "done calling doEmpty()");
					if (status != null)
					{
						debug (method, "Object returned from doEmpty() not null");
						debug (method, "calling destroy(true)");
						destroy(true);
					}
					else
					{
						debug (method, "status returned from doEmpty() is null");
						try
						{
							debug (method, "waiting");
							wait();	
						}	
						catch (InterruptedException ie)
						{}
						debug (method, "calling destory(true)");
						destroy(true);
					}
				}
			}
			else
			{
				debug (method, "waitFlag is false.  destroying objects");
				debug (method, "calling doDestroy on used objects");
				doDestroy(usedList);
				debug (method, "calling doDestroy on free objects");
				doDestroy(freeList);
			}
		}
		catch (PoolException ex)
		{
			error(method, "PoolException caught: " +ex.toString());
			throw new PoolException (ex.toString(), ex);
		}
		finally
		{
			freeList = null;
			usedList = null;
		}
		info (method, "leaving . . .");
	}
	
	protected synchronized int getCurrentSize()
	{
		return (usedList.size() + freeList.size());
	}
	
	protected Object doGet (Object obj) throws PoolException
	{
		String method = "doGet(Object)";
		info (method, "entering . . .");
		debug (method, "calling performGet on ObjectManager");
		obj = manager.performGet(obj);
		debug (method, "done calling performGet");
		freeList.remove(0);
		usedList.add(obj);	
		info (method, "leaving . . .");
		return obj;
	}
	
	protected Object doCreate (boolean removeFlag) throws PoolException
	{
		String method = "doCreate(boolean)";
		info (method, "entering . . .");
		debug (method, "calling create() on ObjectManager");
		Object obj = manager.create();
		debug (method, "done calling create");
		if (obj == null)
		{
			debug (method, "object returned from ObjectManager is null.  Throwing exception");
			throw new PoolException ("NULL object returned from call to " 
									+manager.getClass().getName()+ " create() method");
		}
		debug (method, "calling performGet on ObjectManager");
		obj = manager.performGet(obj);
		debug (method, "done calling performGet");
		debug (method, "removeFlag = " +removeFlag);
		if (removeFlag)
		{
			debug (method, "removing from free objects");
			freeList.remove(0);
		}
		usedList.add(obj);	
		info (method, "leaving . . .");
		return obj;
	}
	
	protected Object doEmpty () throws PoolException
	{
		String method = "doEmpty()";
		info (method, "entering . . .");
		boolean found = false;
		Object obj = null;
		ListIterator i = usedList.listIterator();
		while (i.hasNext())
		{
			obj = i.next();
			if (obj != null)
			{
				debug (method, "calling performEmpty on object");
				obj = manager.performEmpty(obj);
				if (obj != null)
				{
					debug (method, "object returned from performEmpty, adding to free objects");
					i.remove();
					freeList.add(obj);	
					found = true;
					break;
				}
			}
			else
			{
				debug (method, "object is null.  calling create()");
				obj = manager.create();
				if (obj != null)
				{
					debug(method, "object not null from create, calling performGet");
					obj = manager.performGet(obj);
					i.remove();
					debug (method, "adding object to free objects");
					freeList.add(obj);
					found = true;
					break;
				}
				else
				{
					error (method, "create returned null object, throwing exception");
					throw new PoolException ("NULL object returned from call to " 
									+manager.getClass().getName()+ " create() method");
				}
			}	
		}
		if (!found)
		{
			obj = null;
		}	
		info (method, "leaving . . .");
		return obj;
	}	
	
	protected void doDestroy (List list) throws PoolException
	{
		String method = "doDestory(List)";
		info (method, "entering . . .");
		if (list != null)
		{
			ListIterator i = list.listIterator();
			while (i.hasNext())
			{
				Object temp = i.next();
				if (temp != null)
				{
					debug (method, "destroying object");
					manager.destroy(temp);	
					i.remove();
				}
			}
		}
		info(method, "leaving . . .");
	}
	
	protected void finalize() throws Throwable
	{
		destroy(false);
	}
	
	protected long getTime ()
	{
		return System.currentTimeMillis();	
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
