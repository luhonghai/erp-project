package com.rp.util;

import java.io.*;

public final class ExceptionUtil
{
	public static String getStackTrace (Exception e)
	{
		String ret = "";
		PrintStream printStream = null;
		try
		{
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			printStream = new PrintStream(byteStream);
			e.printStackTrace (printStream);
			ret = byteStream.toString();
		}
		catch (Exception ex)
		{}
		
		try
		{
			if (printStream != null)
			{
				printStream.close();	
			}
		}
		catch (Exception ex)
		{}
		
		return ret;
	}
}