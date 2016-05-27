package com.rp.database;

import java.sql.*;
import java.io.*;
import java.util.Calendar;
import java.util.Map;
import java.net.*;
import java.math.*;

public class CallableStatementWrapper extends PreparedStatementWrapper implements CallableStatement
{
	
	private CallableStatement cs = null;
	
	public CallableStatementWrapper (CallableStatement stmt)
	{
		super(stmt);
		this.cs = stmt;
	}
	
	public Array getArray (int i) throws SQLException
	{
		return cs.getArray(i);	
	}
	
	public Array getArray (String parameterName) throws SQLException
	{
		return cs.getArray(parameterName);	
	}
	
	public BigDecimal getBigDecimal (int parameterIndex) throws SQLException
	{
		return cs.getBigDecimal(parameterIndex);	
	}
	
	public BigDecimal getBigDecimal (int parameterIndex, int scale) throws SQLException
	{
		return cs.getBigDecimal(parameterIndex, scale);	
	}
	
	public BigDecimal getBigDecimal (String parameterName) throws SQLException
	{
		return cs.getBigDecimal(parameterName);	
	}
	
	public Blob getBlob (int i) throws SQLException
	{
		return cs.getBlob(i);	
	}
	
	public Blob getBlob (String parameterName) throws SQLException
	{
		return cs.getBlob(parameterName);	
	}
	
	public boolean getBoolean (int parameterIndex) throws SQLException
	{
		return cs.getBoolean(parameterIndex);	
	}
	
	public boolean getBoolean (String parameterName) throws SQLException
	{
		return cs.getBoolean(parameterName);	
	}
	
	public byte getByte (int parameterIndex) throws SQLException
	{
		return cs.getByte (parameterIndex);	
	}
	
	public byte getByte (String parameterName) throws SQLException
	{
		return cs.getByte (parameterName);	
	}
	
	public byte[] getBytes (int parameterIndex) throws SQLException
	{
		return cs.getBytes (parameterIndex);	
	}
	
	public byte[] getBytes (String parameterName) throws SQLException
	{
		return cs.getBytes (parameterName);	
	}
	
	public Clob getClob (int i) throws SQLException
	{
		return cs.getClob (i);	
	}
	
	public Clob getClob (String parameterName) throws SQLException
	{
		return cs.getClob (parameterName);	
	}
	
	public Date getDate (int i) throws SQLException
	{
		return cs.getDate (i);	
	}
	
	public Date getDate (int i, Calendar cal) throws SQLException
	{
		return cs.getDate (i, cal);	
	}
	
	public Date getDate (String parameterName) throws SQLException
	{
		return cs.getDate (parameterName);	
	}
	
	public Date getDate (String parameterName, Calendar cal) throws SQLException
	{
		return cs.getDate (parameterName, cal);	
	}
	
	public double getDouble (String parameterName) throws SQLException
	{
		return cs.getDouble(parameterName);	
	}
	
	public double getDouble (int i) throws SQLException
	{
		return cs.getDouble (i);	
	}
	
	public float getFloat (String parameterName) throws SQLException
	{
		return cs.getFloat (parameterName);	
	}
	
	public float getFloat (int i) throws SQLException
	{
		return cs.getFloat (i);	
	}
	
	public int getInt (int i) throws SQLException
	{
		return cs.getInt (i);	
	}
	
	public int getInt (String parameterName) throws SQLException
	{
		return cs.getInt (parameterName);	
	}
	
	public long getLong (int i) throws SQLException
	{
		return cs.getLong (i);	
	}
	
	public long getLong (String parameterName) throws SQLException
	{
		return cs.getLong (parameterName);	
	}
	
	public Object getObject (int i) throws SQLException
	{
		return cs.getObject (i);	
	}
	
	public Object getObject (int i, Map map) throws SQLException
	{
		return cs.getObject (i, map);	
	}
	
	public Object getObject (String parameterName) throws SQLException
	{
		return cs.getObject (parameterName);	
	}
	
	public Object getObject (String parameterName, Map map) throws SQLException
	{
		return cs.getObject (parameterName, map);	
	}
	
	public Ref getRef (int i) throws SQLException
	{
		return cs.getRef (i);	
	}
	
	public Ref getRef (String parameterName) throws SQLException
	{
		return cs.getRef (parameterName);	
	}
	
	public short getShort (int i) throws SQLException
	{
		return cs.getShort (i);	
	}
	
	public short getShort (String parameterName) throws SQLException
	{
		return cs.getShort (parameterName);	
	}
	
	public String getString (int i) throws SQLException
	{
		return cs.getString (i);	
	}
	
	public String getString (String parameterName) throws SQLException
	{
		return cs.getString (parameterName);	
	}
	
	public Time getTime (int i) throws SQLException
	{
		return cs.getTime (i);	
	}
	
	public Time getTime (int i, Calendar cal) throws SQLException
	{
		return cs.getTime (i, cal);	
	}
	
	public Time getTime (String parameterName) throws SQLException
	{
		return cs.getTime (parameterName);	
	}
	
	public Time getTime (String parameterName, Calendar cal) throws SQLException
	{
		return cs.getTime (parameterName, cal);	
	}
	
	public Timestamp getTimestamp (int i) throws SQLException
	{
		return cs.getTimestamp (i);	
	}
	
	public Timestamp getTimestamp (int i, Calendar cal) throws SQLException
	{
		return cs.getTimestamp (i, cal);	
	}
	
	public Timestamp getTimestamp (String parameterName) throws SQLException
	{
		return cs.getTimestamp (parameterName);	
	}
	
	public Timestamp getTimestamp (String parameterName, Calendar cal) throws SQLException
	{
		return cs.getTimestamp (parameterName, cal);	
	}
	
	public URL getURL (int i) throws SQLException
	{
		return cs.getURL (i);	
	}
	
	public URL getURL (String parameterName) throws SQLException
	{
		return cs.getURL (parameterName);	
	}

	public RowId getRowId(int parameterIndex) throws SQLException {
		return null;
	}

	public RowId getRowId(String parameterName) throws SQLException {
		return null;
	}

	public void setRowId(String parameterName, RowId x) throws SQLException {

	}

	public void setNString(String parameterName, String value) throws SQLException {

	}

	public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {

	}

	public void setNClob(String parameterName, NClob value) throws SQLException {

	}

	public void setClob(String parameterName, Reader reader, long length) throws SQLException {

	}

	public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {

	}

	public void setNClob(String parameterName, Reader reader, long length) throws SQLException {

	}

	public NClob getNClob(int parameterIndex) throws SQLException {
		return null;
	}

	public NClob getNClob(String parameterName) throws SQLException {
		return null;
	}

	public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {

	}

	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		return null;
	}

	public SQLXML getSQLXML(String parameterName) throws SQLException {
		return null;
	}

	public String getNString(int parameterIndex) throws SQLException {
		return null;
	}

	public String getNString(String parameterName) throws SQLException {
		return null;
	}

	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		return null;
	}

	public Reader getNCharacterStream(String parameterName) throws SQLException {
		return null;
	}

	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		return null;
	}

	public Reader getCharacterStream(String parameterName) throws SQLException {
		return null;
	}

	public void setBlob(String parameterName, Blob x) throws SQLException {

	}

	public void setClob(String parameterName, Clob x) throws SQLException {

	}

	public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {

	}

	public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {

	}

	public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {

	}

	public void setAsciiStream(String parameterName, InputStream x) throws SQLException {

	}

	public void setBinaryStream(String parameterName, InputStream x) throws SQLException {

	}

	public void setCharacterStream(String parameterName, Reader reader) throws SQLException {

	}

	public void setNCharacterStream(String parameterName, Reader value) throws SQLException {

	}

	public void setClob(String parameterName, Reader reader) throws SQLException {

	}

	public void setBlob(String parameterName, InputStream inputStream) throws SQLException {

	}

	public void setNClob(String parameterName, Reader reader) throws SQLException {

	}

	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		return null;
	}

	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		return null;
	}

	public void registerOutParameter (int parameterIndex, int sqlType) throws SQLException
	{
		cs.registerOutParameter (parameterIndex, sqlType);
	}
	
	public void registerOutParameter (int parameterIndex, int sqlType, int scale) throws SQLException
	{
		cs.registerOutParameter (parameterIndex, sqlType, scale);
	}
	
	public void registerOutParameter (int parameterIndex, int sqlType, String typeName) throws SQLException
	{
		cs.registerOutParameter (parameterIndex, sqlType, typeName);
	}
	
	public void registerOutParameter (String parameterName, int sqlType) throws SQLException
	{
		cs.registerOutParameter (parameterName, sqlType);
	}
	
	public void registerOutParameter (String parameterName, int sqlType, int scale) throws SQLException
	{
		cs.registerOutParameter (parameterName, sqlType, scale);
	}
	
	public void registerOutParameter (String parameterName, int sqlType, String typeName) throws SQLException
	{
		cs.registerOutParameter (parameterName, sqlType, typeName);
	}
	
	public void setAsciiStream (int parameterIndex, InputStream x, int length) throws SQLException
	{
		cs.setAsciiStream(parameterIndex, x, length);
	}
	
	public void setAsciiStream (String parameterName, InputStream x, int length) throws SQLException
	{
		cs.setAsciiStream(parameterName, x, length);
	}
	
	public void setBigDecimal (int parameterIndex, BigDecimal x) throws SQLException
	{
		cs.setBigDecimal (parameterIndex, x);	
	}
	
	public void setBigDecimal (String parameterName, BigDecimal x) throws SQLException
	{
		cs.setBigDecimal (parameterName, x);	
	}
	
	public void setBinaryStream (int parameterIndex, InputStream x, int length) throws SQLException
	{
		cs.setBinaryStream (parameterIndex, x, length);	
	}
	
	public void setBinaryStream (String parameterName, InputStream x, int length) throws SQLException
	{
		cs.setBinaryStream (parameterName, x, length);	
	}
	
	public void setBoolean (int parameterIndex, boolean x) throws SQLException
	{
		cs.setBoolean (parameterIndex, x);	
	}
	
	public void setBoolean (String parameterName, boolean x) throws SQLException
	{
		cs.setBoolean (parameterName, x);	
	}
	
	public void setByte (int parameterIndex, byte x) throws SQLException
	{
		cs.setByte (parameterIndex, x);	
	}
	
	public void setByte (String parameterName, byte x) throws SQLException
	{
		cs.setByte (parameterName, x);	
	}
	
	public void setBytes (int parameterIndex, byte[] x) throws SQLException
	{
		cs.setBytes (parameterIndex, x);	
	}
	
	public void setBytes (String parameterName, byte[] x) throws SQLException
	{
		cs.setBytes (parameterName, x);	
	}
	
	public void setCharacterStream (int parameterIndex, Reader reader, int length) throws SQLException
	{
		cs.setCharacterStream (parameterIndex, reader, length);	
	}
	
	public void setCharacterStream (String parameterName, Reader reader, int length) throws SQLException
	{
		cs.setCharacterStream (parameterName, reader, length);	
	}
	
	public void setDate (int parameterIndex, Date x) throws SQLException
	{
		cs.setDate (parameterIndex, x);	
	}
	
	public void setDate (int parameterIndex, Date x, Calendar cal) throws SQLException
	{
		cs.setDate (parameterIndex, x, cal);	
	}
	
	public void setDate (String parameterName, Date x) throws SQLException
	{
		cs.setDate (parameterName, x);	
	}
	
	public void setDate (String parameterName, Date x, Calendar cal) throws SQLException
	{
		cs.setDate (parameterName, x, cal);	
	}
	
	public void setDouble (int parameterIndex, double x) throws SQLException
	{
		cs.setDouble (parameterIndex, x);	
	}
	
	public void setDouble (String parameterName, double x) throws SQLException
	{
		cs.setDouble (parameterName, x);	
	}
	
	public void setFloat (int parameterIndex, float x) throws SQLException
	{
		cs.setFloat (parameterIndex, x);	
	}
	
	public void setFloat (String parameterName, float x) throws SQLException
	{
		cs.setFloat (parameterName, x);	
	}
	
	public void setInt (int parameterIndex, int x) throws SQLException
	{
		cs.setInt (parameterIndex, x);	
	}
	
	public void setInt (String parameterName, int x) throws SQLException
	{
		cs.setInt (parameterName, x);	
	}
	
	public void setLong (int parameterIndex, long x) throws SQLException
	{
		cs.setLong (parameterIndex, x);	
	}
	
	public void setLong (String parameterName, long x) throws SQLException
	{
		cs.setLong (parameterName, x);	
	}
	
	public void setNull (int parameterIndex, int sqlType) throws SQLException
	{
		cs.setNull (parameterIndex, sqlType);	
	}
	
	public void setNull (int parameterIndex, int sqlType, String typeName) throws SQLException
	{
		cs.setNull (parameterIndex, sqlType, typeName);	
	}
	
	public void setNull (String parameterName, int sqlType) throws SQLException
	{
		cs.setNull (parameterName, sqlType);	
	}
	
	public void setNull (String parameterName, int sqlType, String typeName) throws SQLException
	{
		cs.setNull (parameterName, sqlType, typeName);	
	}
	
	public void setObject (int parameterIndex, Object x) throws SQLException
	{
		cs.setObject (parameterIndex, x);	
	}
	
	public void setObject (int parameterIndex, Object x, int targetSqlType) throws SQLException
	{
		cs.setObject (parameterIndex, x, targetSqlType);	
	}
	
	public void setObject (int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException
	{
		cs.setObject (parameterIndex, x, targetSqlType, scale);	
	}

	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

	}

	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

	}

	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

	}

	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

	}

	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

	}

	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

	}

	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

	}

	public void setClob(int parameterIndex, Reader reader) throws SQLException {

	}

	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

	}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException {

	}

	public void setObject (String parameterName, Object x) throws SQLException
	{
		cs.setObject (parameterName, x);	
	}
	
	public void setObject (String parameterName, Object x, int targetSqlType) throws SQLException
	{
		cs.setObject (parameterName, x, targetSqlType);	
	}
	
	public void setObject (String parameterName, Object x, int targetSqlType, int scale) throws SQLException
	{
		cs.setObject (parameterName, x, targetSqlType, scale);	
	}
	
	public void setShort (int parameterIndex, short x) throws SQLException
	{
		cs.setShort (parameterIndex, x);	
	}
	
	public void setShort (String parameterName, short x) throws SQLException
	{
		cs.setShort (parameterName, x);	
	}
	
	public void setString (int parameterIndex, String x) throws SQLException
	{
		cs.setString (parameterIndex, x);	
	}
	
	public void setString(String parameterName, String x) throws SQLException
	{
		cs.setString(parameterName, x);	
	}
	
	public void setTime (int parameterIndex, Time x) throws SQLException
	{
		cs.setTime (parameterIndex, x);	
	}
	
	public void setTime (int parameterIndex, Time x, Calendar cal) throws SQLException
	{
		cs.setTime (parameterIndex, x, cal);	
	}
	
	public void setTime (String parameterName, Time x) throws SQLException
	{
		cs.setTime (parameterName, x);	
	}
	
	public void setTime (String parameterName, Time x, Calendar cal) throws SQLException
	{
		cs.setTime (parameterName, x, cal);	
	}
	
	public void setTimestamp (int parameterIndex, Timestamp x) throws SQLException
	{
		cs.setTimestamp (parameterIndex, x);	
	}
	
	public void setTimestamp (int parameterIndex, Timestamp x, Calendar cal) throws SQLException
	{
		cs.setTimestamp (parameterIndex, x, cal);	
	}
	
	public void setTimestamp (String parameterName, Timestamp x) throws SQLException
	{
		cs.setTimestamp (parameterName, x);	
	}
	
	public void setTimestamp (String parameterName, Timestamp x, Calendar cal) throws SQLException
	{
		cs.setTimestamp (parameterName, x, cal);	
	}
	
	public void setURL (int parameterIndex, URL x) throws SQLException
	{
		cs.setURL (parameterIndex, x);	
	}

	public void setRowId(int parameterIndex, RowId x) throws SQLException {

	}

	public void setNString(int parameterIndex, String value) throws SQLException {

	}

	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {

	}

	public void setNClob(int parameterIndex, NClob value) throws SQLException {

	}

	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

	}

	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

	}

	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

	}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

	}

	public void setURL (String parameterName, URL x) throws SQLException
	{
		cs.setURL (parameterName, x);	
	}
	
	public boolean wasNull () throws SQLException
	{
		return cs.wasNull();	
	}

	public boolean isClosed() throws SQLException {
		return false;
	}

	public void setPoolable(boolean poolable) throws SQLException {

	}

	public boolean isPoolable() throws SQLException {
		return false;
	}

	public void closeOnCompletion() throws SQLException {

	}

	public boolean isCloseOnCompletion() throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
}