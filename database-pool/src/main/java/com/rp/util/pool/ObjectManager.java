package com.rp.util.pool;

public interface ObjectManager
{
	Object create() throws PoolException;
	Object performGet(Object obj) throws PoolException;
	Object performFree(Object obj) throws PoolException;
	Object performEmpty(Object obj) throws PoolException;
	void destroy(Object obj) throws PoolException;
}