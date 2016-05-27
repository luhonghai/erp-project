
/*Copyright 2002 Richardson Publications*/

package com.rp.log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class GenericFileReader
{
    private String fileName = null;
    private int bufferSize = 1000;
    
    public GenericFileReader ()
    {}
    
    public GenericFileReader (String fileName, int bufferSize)
    {
        this.setFileName (fileName);
        this.setBufferSize (bufferSize);    
    }
    
    public GenericFileReader (String fileName)
    {
        this(fileName, 1000);
    }
    
    public void setFileName (String fileName)
    {
        this.fileName = fileName;    
    }
    
    public String getFileName ()
    {
        return this.fileName;    
    }
    
    public void setBufferSize (int bufferSize)
    {
        this.bufferSize = bufferSize;
    }
    
    public int getBufferSize ()
    {
        return this.bufferSize;    
    }
    
    public ArrayList read () throws java.io.FileNotFoundException, java.io.IOException
    {
        FileReader fr = new FileReader (this.getFileName());
        BufferedReader br = new BufferedReader (fr);
        ArrayList aList = new ArrayList (this.getBufferSize());
        
        String line = null;
        while ((line = br.readLine()) != null)
        {
            aList.add(line);
        }
        
        br.close();
        fr.close();
        
        return aList;
    }
    
    public String readLine () throws java.io.FileNotFoundException, java.io.IOException
    {
        FileReader fr = new FileReader (this.getFileName());
        BufferedReader br = new BufferedReader (fr);
        
        String line = br.readLine();
        br.close();
        fr.close();
        
        return line;
    }
}
