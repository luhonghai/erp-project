package com.rp.util.pool;

public interface ObjectPool
{
	void init() throws PoolException;
	Object getObject() throws PoolException;
	void freeObject(Object obj) throws PoolException;
	void recycle(boolean waitFlag) throws PoolException;
	void destroy(boolean waitFlag) throws PoolException;
}