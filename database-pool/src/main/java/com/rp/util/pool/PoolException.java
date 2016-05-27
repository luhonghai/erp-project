package com.rp.util.pool;

import java.io.*;
import com.rp.util.ExceptionUtil;
 
public class PoolException extends Exception 
{
	public Exception exception = null;

    public PoolException (String str) 
    {
        super(str);
    }
    
    public PoolException () 
    {
        super();
    }
    
    public PoolException (String str, Exception e)
    {
    	super(str);
    	this.exception = e;	
    }
    
    public void setException (Exception e)
    {
    	this.exception = e;	
    }
    
    public Exception getException ()
    {
    	if (this.exception != null)
    	{
    		return this.exception;	
    	}
    	return this;
    }	
  
    public void printStackTrace ()
    {
    	if (this.exception != null)
    	{
    		exception.printStackTrace();
    	}	
    	else
    	{
    		super.printStackTrace();	
    	}
    }
    
    public void printStackTrace (PrintStream s)
    {
    	if (this.exception != null)
    	{
    		exception.printStackTrace(s);
    	}	
    	else
    	{
    		super.printStackTrace(s);	
    	}
    }
    
    public void printStackTrace (PrintWriter s)
    {
    	if (this.exception != null)
    	{
    		exception.printStackTrace(s);
    	}	
    	else
    	{
    		super.printStackTrace(s);	
    	}
    }
}
