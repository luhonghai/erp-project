/*
 * PropertiesFileReader.java
*
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.util;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Convenience class for reading properties from a file
 *
 * @author Richardson Publications
 * @version 1.0, 05/2002
 */
 
public class PropertiesFileReader
{
	
	private Properties props = null;
		
	public PropertiesFileReader (String propertiesFile) throws Exception
	{
		try
		{
			this.buildFromProperties (propertiesFile);
		}
		catch(Exception e)
		{
			try
			{
				this.buildFromFile (propertiesFile);	
			}
			catch (Exception ee)
			{
				throw new Exception ("PropertiesFileReader:Constructor()::Exception--Trying to load stream" +e.toString() + ee.toString());
			}
		}
	}

	public PropertiesFileReader (InputStream stream) throws Exception
	{
		try
		{
			this.props = new Properties();
			this.props.load(new BufferedInputStream(stream));
		}
		catch (IOException io)
		{
			throw new Exception ("PropertiesFileReader:Constructor()::Exception--Trying to load stream" +io.toString());
		}
	}
	
	protected void buildFromProperties (String file) throws Exception
	{
		String fName = null;
		try
		{
			fName = file.replace('.', '/') + ".properties";
			InputStream stream = ClassLoader.getSystemResourceAsStream(fName);
			BufferedInputStream bStream = new BufferedInputStream(stream);
			this.props = new Properties();
			this.props.load(bStream);
		}
		catch(Exception e)
		{
			throw new Exception ("PropertiesFileReader:buildFromProperties()::Exception--Trying to load file " +fName + e.toString());
		}	
	}
	
	protected void buildFromFile (String file) throws Exception
	{
		try
		{
			FileInputStream fStream = new FileInputStream (file);
			this.props = new Properties();
			this.props.load(new BufferedInputStream(fStream));
		}
		catch (Exception e)
		{
			throw new Exception ("PropertiesFileReader:buildFromFile()::Exception--Trying to load file " +file + e.toString());
		}	
	}
	
	public String getProperty (String propertyName)
	{
		return this.props.getProperty(propertyName);
	}
	
	public Enumeration getKeys ()
	{
		return this.props.propertyNames();
	}
	
	public Map getPropertyMap ()
	{
		Enumeration enume = this.getKeys();
		Map hm = new HashMap();
		
		while (enume.hasMoreElements())
		{
			String next = (String) enume.nextElement();
			hm.put (next, this.getProperty(next));	
		}	
		return hm;
	}
}