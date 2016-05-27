package com.rp.util;

import java.util.*;
import java.sql.*;
import com.rp.util.*;

public class DefaultMemoryCache extends MemoryCache
{
	
	public DefaultMemoryCache(int max)
	{
		super (max, 0, false);
	}	
	
	public DefaultMemoryCache(int max, int timeout)
	{
		super (max, timeout, false);	
	}
	
	public void destroy ()
	{}
}