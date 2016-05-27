package com.rp.database;

import java.sql.*;

public class CacheableCallableStatement extends CallableStatementWrapper implements CacheableStatement
{
	private CallableStatement cs = null;
	
	public CacheableCallableStatement (CallableStatement stmt)
	{
		super(stmt);
		this.cs = stmt;
	}		
	
	public void close() throws SQLException
	{}
	
	public void destroy() throws SQLException
	{
		cs.close();
	}
}	