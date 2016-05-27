package com.rp.database;

import java.sql.*;
import java.io.*;
import java.util.Calendar;
import java.net.*;
import java.math.*;

public abstract class PreparedStatementWrapper extends StatementWrapper implements PreparedStatement
{
	private PreparedStatement ps = null;
	
	public PreparedStatementWrapper (PreparedStatement stmt)
	{
		super(stmt);
		this.ps = stmt;
	}		
	
	public void addBatch() throws SQLException
	{
		ps.addBatch();	
	}
	
	public void clearParameters() throws SQLException
	{
		ps.clearParameters();	
	}
	
	public boolean execute() throws SQLException
	{
		return ps.execute();
	}	
	
	public ResultSet executeQuery() throws SQLException
	{
		return ps.executeQuery();
	}
	
	public int executeUpdate() throws SQLException
	{
		return ps.executeUpdate();	
	}
	
	public ResultSetMetaData getMetaData() throws SQLException
	{
		return ps.getMetaData();	
	}
	
	public ParameterMetaData getParameterMetaData() throws SQLException
	{
		return ps.getParameterMetaData();	
	}
	
	public void setArray (int i, Array x) throws SQLException
	{
		ps.setArray(i, x);	
	}
	
	public void setAsciiStream (int parameterIndex, InputStream x, int length) throws SQLException
	{
		ps.setAsciiStream(parameterIndex, x, length);
	}
	
	public void setBigDecimal (int parameterIndex, BigDecimal x) throws SQLException
	{
		ps.setBigDecimal (parameterIndex, x);	
	}
	
	public void setBinaryStream (int parameterIndex, InputStream x, int length) throws SQLException
	{
		ps.setBinaryStream (parameterIndex, x, length);	
	}
	
	public void setBlob (int i, Blob x) throws SQLException
	{
		ps.setBlob (i, x);	
	}
	
	public void setBoolean (int parameterIndex, boolean x) throws SQLException
	{
		ps.setBoolean (parameterIndex, x);	
	}
	
	public void setByte (int parameterIndex, byte x) throws SQLException
	{
		ps.setByte (parameterIndex, x);	
	}
	
	public void setBytes (int parameterIndex, byte[] x) throws SQLException
	{
		ps.setBytes (parameterIndex, x);	
	}
	
	public void setCharacterStream (int parameterIndex, Reader reader, int length) throws SQLException
	{
		ps.setCharacterStream (parameterIndex, reader, length);	
	}
	
	public void setClob (int parameterIndex, Clob x) throws SQLException
	{
		ps.setClob (parameterIndex, x);	
	}
	
	public void setDate (int parameterIndex, Date x) throws SQLException
	{
		ps.setDate (parameterIndex, x);	
	}
	
	public void setDate (int parameterIndex, Date x, Calendar cal) throws SQLException
	{
		ps.setDate (parameterIndex, x, cal);	
	}
	
	public void setDouble (int parameterIndex, double x) throws SQLException
	{
		ps.setDouble (parameterIndex, x);	
	}
	
	public void setFloat (int parameterIndex, float x) throws SQLException
	{
		ps.setFloat (parameterIndex, x);	
	}
	
	public void setInt (int parameterIndex, int x) throws SQLException
	{
		ps.setInt (parameterIndex, x);	
	}
	
	public void setLong (int parameterIndex, long x) throws SQLException
	{
		ps.setLong (parameterIndex, x);	
	}
	
	public void setNull (int parameterIndex, int sqlType) throws SQLException
	{
		ps.setNull (parameterIndex, sqlType);	
	}
	
	public void setNull (int parameterIndex, int sqlType, String typeName) throws SQLException
	{
		ps.setNull (parameterIndex, sqlType, typeName);	
	}
	
	public void setObject (int parameterIndex, Object x) throws SQLException
	{
		ps.setObject (parameterIndex, x);	
	}
	
	public void setObject (int parameterIndex, Object x, int targetSqlType) throws SQLException
	{
		ps.setObject (parameterIndex, x, targetSqlType);	
	}
	
	public void setObject (int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException
	{
		ps.setObject (parameterIndex, x, targetSqlType, scale);	
	}
	
	public void setRef (int parameterIndex, Ref x) throws SQLException
	{
		ps.setRef (parameterIndex, x);	
	}
	
	public void setShort (int parameterIndex, short x) throws SQLException
	{
		ps.setShort (parameterIndex, x);	
	}
	
	public void setString (int parameterIndex, String x) throws SQLException
	{
		ps.setString (parameterIndex, x);	
	}
	
	public void setTime (int parameterIndex, Time x) throws SQLException
	{
		ps.setTime (parameterIndex, x);	
	}
	
	public void setTime (int parameterIndex, Time x, Calendar cal) throws SQLException
	{
		ps.setTime (parameterIndex, x, cal);	
	}
	
	public void setTimestamp (int parameterIndex, Timestamp x) throws SQLException
	{
		ps.setTimestamp (parameterIndex, x);	
	}
	
	public void setTimestamp (int parameterIndex, Timestamp x, Calendar cal) throws SQLException
	{
		ps.setTimestamp (parameterIndex, x, cal);	
	}
	
	public void setUnicodeStream (int parameterIndex, InputStream x, int length) throws SQLException
	{
		ps.setUnicodeStream (parameterIndex, x, length);	
	}
	
	public void setURL (int parameterIndex, URL x) throws SQLException
	{
		ps.setURL (parameterIndex, x);	
	}
}