/*
 * GenericFileWriter.java
 *
 * Copyright 2002 Richardson Publications All Rights Reserved.
 * 
 * Use is subject to license terms.  See License.txt included with the distribution for details
 *
 * http://www.richardsonpublications.com
 */

package com.rp.log;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Convenience class to automatically create a buffered java.io.PrintWriter to write to files
 *
 * @author  Richardson Publications
 * @version 1.0, 05/2002
 * @see    	java.io.PrintWriter
 */

public class GenericFileWriter extends PrintWriter
{	

	public GenericFileWriter(String fileName) throws IOException
	{
		this (fileName, true, true);
	}
	
	public GenericFileWriter (String fileName, boolean append) throws IOException
	{
		this (fileName, append, true);
	}
	
	public GenericFileWriter (String fileName, boolean append, boolean flush) throws IOException
	{
		super ( new BufferedWriter( new FileWriter(fileName, append) ), flush );	
	}
	
	public GenericFileWriter (String fileName, int bufferSize) throws IOException
	{
		this (fileName, true, true, bufferSize);
	}	
	
	public GenericFileWriter (String fileName, boolean append, int bufferSize) throws IOException
	{
		this (fileName, append, true, bufferSize);	
	}

	public GenericFileWriter (String fileName, boolean append, boolean flush, int bufferSize) throws IOException
	{
		super ( new BufferedWriter( new FileWriter(fileName, append), bufferSize ), flush );	
	}
	
	public GenericFileWriter (Writer writer) throws IOException
	{
		super (writer, true);	
	}
	
	protected void finalize()
	{
		this.destroy();
	}
	
	public void destroy()
	{
		try
		{
			super.close();	
		}	
		catch (Exception e)
		{}	
	}
}