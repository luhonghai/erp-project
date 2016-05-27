/*
 * PropertiesReader.java
*
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

/**
 * Convenience class for reading properties from a properties file
 *
 * @author  Richardson Publications
 * @version 1.0, 05/2002
 */

public class PropertiesReader
{

	private Locale locale = null;
	private String propertiesFile = null;
	private ResourceBundle resourceBundle = null;
	
	public PropertiesReader (String propertiesFile) throws Exception
	{
		this(propertiesFile, Locale.getDefault());
	}
	
	public PropertiesReader (String propertiesFile, Locale locale) throws Exception
	{
		try
		{
			this.setLocale (locale);
			this.setPropertiesFile (propertiesFile);
			this.setResourceBundle ( ResourceBundle.getBundle(this.getPropertiesFile(), this.getLocale()) );
		}
		catch(Exception e)
		{
			throw new Exception ("PropertiesReader:Constructor()::Exception--" +e.toString());
		}
	}
	
	public PropertiesReader()
	{}
	
	public void setLocale (Locale locale)
	{
		this.locale = locale;	
	}
	
	public Locale getLocale ()
	{
		return this.locale;	
	}
	
	public void setPropertiesFile (String propertiesFile)
	{
		this.propertiesFile = propertiesFile;	
	}
	
	public String getPropertiesFile ()
	{
		return this.propertiesFile;	
	}
	
	public void setResourceBundle (ResourceBundle resourceBundle)
	{
		this.resourceBundle = resourceBundle;	
	}
	
	public ResourceBundle getResourceBundle ()
	{
		return this.resourceBundle;	
	}
	
	public String getProperty (String propertyName)
	{
		try
		{
			String propertyValue = this.getResourceBundle().getString(propertyName);
			return propertyValue;
		}
		catch (Exception e)
		{
			return null;	
		}
	}
	
	public Enumeration getKeys ()
	{
		try
		{
			return this.getResourceBundle().getKeys();	
		}
		catch(Exception e)
		{
			return null;	
		}
	}
	
	public Map getPropertyMap ()
	{
		try
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
		catch (Exception e)
		{
			return null;	
		}
	}
}