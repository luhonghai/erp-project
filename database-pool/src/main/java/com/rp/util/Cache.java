package com.rp.util;


public interface Cache
{
	boolean put (Object key, Object obj);
	Object get (Object key);
	void remove (Object key);
	void destroy ();
	int getSize();
}