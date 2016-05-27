package com.rp.database;

import java.util.*;
import java.sql.*;
import com.rp.util.*;

public class StatementCache extends MemoryCache
{
	protected String CLASS_NAME = "StatementCache";
	
	public StatementCache (int max)
	{
		super (max, 0, false);
	}	
	
	public StatementCache(int max, Loggable log, String logKey)
	{
		super(max, 0, false, log, logKey);	
	}
	
	public void destroy ()
	{
		String method = "destroy()";
		info (method, "entering . . .");
		Object[] arr = super.getObjects();
		if (arr != null)
		{
			int len = arr.length;
			debug (method, "cache len = " +len);
			Statement st = null;
			debug (method, "closing all statements in cache");
			for (int i = 0; i < arr.length; i++)
			{
				try
				{
					st = (Statement)arr[i];
				}
				catch (Exception e)
				{}		
				try
				{
					st.close();
				}
				catch (Exception e)
				{}
			}
		}
		info (method, "leaving . . .");
	}
}