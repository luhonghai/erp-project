package com.rp.util.pool;

public abstract class ObjectManagerImpl implements ObjectManager
{
	public abstract Object create() throws PoolException;
	
	public Object performGet(Object obj) throws PoolException
	{
		return obj;
	}
	
	public Object performFree(Object obj) throws PoolException
	{
		return obj;
	}
	
	public Object peformEmpty(Object obj) throws PoolException
	{
		return null;
	}
	
	public void destroy(Object obj) throws PoolException
	{
		obj = null;	
	}
}