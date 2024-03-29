package com.rp.database;

import java.sql.*;
import java.io.*;

public abstract class StatementWrapper implements java.sql.Statement
{
	private Statement statement = null;
	
	public StatementWrapper (Statement stmt)
	{
		this.statement = stmt;
	}	
	
	public void addBatch (String sql) throws SQLException
	{
		statement.addBatch(sql);
	}
	
	public void cancel() throws SQLException
	{
		statement.cancel();	
	}
	
	public void clearBatch() throws SQLException
	{
		statement.clearBatch();
	}
	
	public void clearWarnings() throws SQLException
	{
		statement.clearWarnings();	
	}
	
	public void close() throws SQLException
	{
		statement.close();
	}
	
	public boolean execute (String sql) throws SQLException
	{
		return statement.execute(sql);	
	}
	
	public boolean execute (String sql, int autoGeneratedKeys) throws SQLException
	{
		return statement.execute(sql, autoGeneratedKeys);	
	}
	
	public boolean execute (String sql, int[] columnIndexes) throws SQLException
	{
		return statement.execute(sql, columnIndexes);	
	}
	
	public boolean execute (String sql, String[] columnNames) throws SQLException
	{
		return statement.execute(sql, columnNames);	
	}
	
	public int[] executeBatch() throws SQLException
	{
		return statement.executeBatch();	
	}
	
	public ResultSet executeQuery (String sql) throws SQLException
	{
		return statement.executeQuery(sql);	
	}
	
	public int executeUpdate (String sql) throws SQLException
	{
		return statement.executeUpdate(sql);
	}	
	
	public int executeUpdate (String sql, int autoGeneratedKeys) throws SQLException
	{
		return statement.executeUpdate(sql, autoGeneratedKeys);	
	}
	
	public int executeUpdate (String sql, int[] columnIndexes) throws SQLException
	{
		return statement.executeUpdate(sql, columnIndexes);	
	}
	
	public int executeUpdate (String sql, String[] columnNames) throws SQLException
	{
		return statement.executeUpdate(sql, columnNames);	
	}
	
	public Connection getConnection() throws SQLException
	{
		return statement.getConnection();	
	}
	
	public int getFetchDirection() throws SQLException
	{
		return statement.getFetchDirection();	
	}
	
	public int getFetchSize() throws SQLException
	{
		return statement.getFetchSize();	
	}
	
	public ResultSet getGeneratedKeys () throws SQLException
	{
		return statement.getGeneratedKeys();	
	}
	
	public int getMaxFieldSize() throws SQLException
	{
		return statement.getMaxFieldSize();	
	}
	
	public int getMaxRows() throws SQLException
	{
		return statement.getMaxRows();	
	}
	
	public boolean getMoreResults () throws SQLException
	{
		return statement.getMoreResults();	
	}
	
	public boolean getMoreResults (int current) throws SQLException
	{
		return statement.getMoreResults(current);	
	}
	
	public int getQueryTimeout () throws SQLException
	{
		return statement.getQueryTimeout();
	}
	
	public ResultSet getResultSet() throws SQLException
	{
		return statement.getResultSet();	
	}
	
	public int getResultSetConcurrency() throws SQLException
	{
		return statement.getResultSetConcurrency();	
	}
	
	public int getResultSetHoldability() throws SQLException
	{
		return statement.getResultSetHoldability();	
	}
	
	public int getResultSetType() throws SQLException
	{
		return statement.getResultSetType();	
	}
	
	public int getUpdateCount() throws SQLException
	{
		return statement.getUpdateCount();	
	}
	
	public SQLWarning getWarnings() throws SQLException
	{
		return statement.getWarnings();	
	}
	
	public void setCursorName (String name) throws SQLException
	{
		statement.setCursorName(name);
	}
	
	public void setEscapeProcessing (boolean enable) throws SQLException
	{
		statement.setEscapeProcessing(enable);
	}
	
	public void setFetchDirection(int direction) throws SQLException
	{
		statement.setFetchDirection(direction);	
	}
	
	public void setFetchSize (int rows) throws SQLException
	{
		statement.setFetchSize(rows);
	}
	
	public void setMaxFieldSize (int max) throws SQLException
	{
		statement.setMaxFieldSize(max);	
	}
	
	public void setMaxRows (int max) throws SQLException
	{
		statement.setMaxRows (max);	
	}
	
	public void setQueryTimeout (int seconds) throws SQLException
	{
		statement.setQueryTimeout(seconds);	
	}
}