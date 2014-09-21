package com.disk;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;


public class ChannelOps {
	
	
	public int  readdata(long buffer, String filepath)
	{		long startTime=0;
			ByteBuffer buf=null;
			RandomAccessFile aFile=null;
		try {
			
			 aFile  = new RandomAccessFile(filepath, "rw");
			//RandomAccessFile aFile     = new RandomAccessFile("test001.txt", "rw");
			System.out.println("buffer length::"+buffer);

			FileChannel    inChannel = aFile.getChannel();
			buf = ByteBuffer.allocate((int) (buffer));
			int bytesRead = inChannel.read(buf);
			startTime=System.currentTimeMillis();
			
			System.out.println("startTime::"+startTime+" file size:"+aFile.length());
			//File Operation
			    while (bytesRead != -1) {
			      //buf.flip();
			      while(buf.hasRemaining()){
			    	  buf.get();
			      }
			      buf.clear();
			      bytesRead = inChannel.read(buf);
			    }
			    System.out.println("Total time in milli sec::" +(System.currentTimeMillis()-startTime));
			    int totaltime=(int) (System.currentTimeMillis()-startTime);
			    long throughput= (aFile.length())/(totaltime);
			    System.out.println("Total bytes read (in Bytes)::" +aFile.length());
			    System.out.println("throughput++ bytes/millisec"+throughput);
			    throughput=((throughput*(1000))/(1024*1024));
			    System.out.println("Throughput MB/sec : "+ throughput);
			    aFile.close();
			    buf.clear();
			    inChannel.close();
			    System.out.println("###########CHANNEL CLOSED##########");
			    //return ((throughput*(1000))/(1024*1024));
			    System.gc();
			    return (int) throughput;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
		buf.clear();
		
		}return 0;
	}
	 public float computeAvg(int a[])
	 {
		 float sum=0.0f;
		 for(int i=0;i<a.length;i++)
		 {
			 	sum=sum+a[i];
			 	
		 }
	//System.out.println("no of experiments"+a.length);	 
		 //return sum/a.length;
		 return sum;
	 }
	 
	 
	 public int  readRandom(long buffer,String filePath)
		{		long startTime=0;
				ByteBuffer buf=null;
				RandomAccessFile aFile=null;
			try {
				 aFile     = new RandomAccessFile(filePath, "rw");
					//aFile = new RandomAccessFile("test001.txt", "rw");
				//RandomAccessFile aFile     = new RandomAccessFile("test001.txt", "rw");
				System.out.println("buffer length::"+buffer);
				FileChannel    inChannel = aFile.getChannel();
				buf = ByteBuffer.allocate((int) (buffer));
				int bytesRead = inChannel.read(buf);

				startTime=System.currentTimeMillis();
				System.out.println("startTime::"+startTime+"::length of file "+aFile.length());
				
				int c=(int) (aFile.length()/buffer);
				int j=0;
				    while (j<=c) {
				      buf.flip();
				      while(buf.hasRemaining()){
				    	  buf.get();
				      }
				      buf.clear();
				      //inChannel.position(newPosition)
				      bytesRead = inChannel.read(buf);
				      inChannel.position(getRandomPosition(aFile.length()));
				     //System.out.println("@::"+inChannel.position());
				     j++;
				    }
				    System.out.println("Total time in milli sec::" +(System.currentTimeMillis()-startTime));
				    int totaltime=(int) (System.currentTimeMillis()-startTime);
				    long throughput= (aFile.length())/(totaltime);
				    System.out.println("Total bytes read (in Bytes)::" +aFile.length());
				    System.out.println("throughput++ bytes/millisec"+throughput);
				    throughput=((throughput*(1000))/(1024*1024));
				    System.out.println("Throughput MB/sec : "+ throughput);
				    aFile.close();
				    buf.clear();
				    inChannel.close();
				    System.out.println("###########CHANNEL CLOSED##########");
				    //return ((throughput*(1000))/(1024*1024));
				    return (int) throughput;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
			buf.clear();
			
			}return 0;
		}

	 public long getRandomPosition(long l)
	 {
		 return (long) (Math.random()*l);
		 	
	 }
	 public float[] squentialOps(String filepath)
	 {float benchMark[]= new float[3];
		 	try {
				int _byte[]=new int[3];
				int _kbyte[]=new int[3];
				int _mbyte[]=new int[3];
				
			 //ChannelOps cOps= new ChannelOps();
			 System.out.println("\n #### Disk BenchMark  ####");
			 for(int i=0;i<3;i++)
			 {int j=0;
				 
			 
				 	if(i==0)
					 {	System.out.println("Performing disk read operation for....buffer size=1 B");
						 //_byte[j]=cOps.readdata((int)Math.pow(1024,i));
					 _byte[j]=readdata(100,filepath);
					 }
				 if(i==1)
				 {	  System.out.println("Performing disk read operation for....buffer size=1 KB");
					 _kbyte[j]=readdata(1024,filepath);	 
				 
				 }
		
				 if(i==2)
				 { System.out.println("Performing disk read operation for....buffer size=1 MB");
					 _mbyte[j]=readdata(1024*1024,filepath);	 
				 	
				 	}
			 }
			 benchMark[0]=computeAvg(_byte);
			 benchMark[1]=computeAvg(_kbyte);
			 benchMark[2]=computeAvg(_mbyte);
			 
	 }
	  catch (Exception e) {
			e.printStackTrace();
		}
		 	return benchMark;
	}
	 public int  randomOps(String filePath)
	 {
		 try {
				int _byte[]=new int[3];
				int _kbyte[]=new int[3];
				int _mbyte[]=new int[3];
			 
			 System.out.println("\n #### Disk BenchMark (randomized) ####");
			 for(int i=0;i<3;i++)
			 {int j=0;
				 
			 System.out.println("for i ="+i+" buffer size is::"+(int)Math.pow(1024,i));
			  	if(i==0)
					 {	System.out.println("Performing disk read operation for....buffer size=1 B");
						// _byte[j]=cOps.readdata((int)Math.pow(1024,i));
					_byte[j]=readRandom(1000,filePath);
					 	
					 }
				 
				 if(i==1)
				 {	  
					 System.out.println("Performing random disk read operation for....buffer size=1 KB");
					 _kbyte[j]=readRandom(1024,filePath);	 
					 }
		
				 if(i==2)
				 { System.out.println("Performing random disk read operation for....buffer size=1 MB");
					 _mbyte[j]=readRandom(1024*1024,filePath);	 
					
				 	}
			 
			 }
		 
	 }
	  catch (Exception e) {
			e.printStackTrace();
		}
		 
		 return 0;
	 }
}