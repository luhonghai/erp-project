package com.rp.database;

import java.sql.*;

public interface CacheableStatement
{
	void close() throws SQLException;
	void destroy() throws SQLException;
}	