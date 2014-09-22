package com.disk;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;


public class ChannelOps implements Runnable {
	
	public  String path=null;
	public  String operation=null;
	public  int buffersize=0;
	public ChannelOps(String path,int buffer,String op)
	{
		this.path=path;this.buffersize=buffer;this.operation=op;
	}
	public ChannelOps() {
		// TODO Auto-generated constructor stub
	}
	public void run()
	{
		float benchmark[]= new float[3];
		System.out.println("##Thread started for operation "+this.operation);
		if(this.operation.equals("seqRead"))
		{
			System.out.println("Performing disk read operation for....buffer size=1 B");
			benchmark[0]=this.readdata(1, this.path);
			System.out.println("Performing disk read operation for....buffer size=1 KB");
			benchmark[1]=this.readdata(1024, this.path);
			System.out.println("Performing disk read operation for....buffer size=1 MB");
			benchmark[2]=this.readdata(1024*1024, this.path);
		}
		if(this.operation.equals("ranRead"))
		{
			System.out.println("Performing disk randomread operation for....buffer size=1 B");
			benchmark[0]=this.readRandom(1, this.path);
			System.out.println("Performing disk randomread operation for....buffer size=1 KB");
			benchmark[1]=this.readRandom(1024, this.path);
			System.out.println("Performing disk randomread operation for....buffer size=1 MB");
			benchmark[2]=this.readRandom(1024*1024, this.path);
		}
		
		
		System.out.println("###Thread"+Thread.currentThread().getName()+"  complete for operation "+this.operation);
		this.printMarks(benchmark,Thread.currentThread().getName());
	}
	
	synchronized public int  readdata(long buffer, String filepath)
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
			long count=0;
			if(buffer<100)
			{
			count=1024*1024;
			}
			else
			{	count=aFile.length();
			}
			System.out.println("count is +"+count);
			
			System.out.println("startTime::"+startTime+" file size:"+aFile.length()+"read limit:"+count);
			//File Operation
			    while (bytesRead != -1 && inChannel.position()<count) {
			      //buf.flip();
			      while(buf.hasRemaining()){
			    	  buf.get();
			      }
			      buf.clear();
			      bytesRead = inChannel.read(buf);
			    }
			    System.out.println("Total time in milli sec::" +(System.currentTimeMillis()-startTime));
			    int totaltime=(int) (System.currentTimeMillis()-startTime);
			    long throughput= (count)/(totaltime);
			    System.out.println("Total bytes read (in Bytes)::" +count);
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
				
				
				long c= (aFile.length()/buffer);
				long posRange=0;
				if(buffer<100 )
				{
					c=c/(1024);
					posRange=c;	
				}
				else{
					posRange=c;
				}
				System.out.println("startTime::"+startTime+"::length of file "+aFile.length()+"pos range:"+posRange);
				
				System.out.println("counter is:"+c);
				int j=0;
				    while (j<c) {
				      buf.flip();
				      while(buf.hasRemaining()){
				    	  buf.get();
				      }
				      buf.clear();
				      //inChannel.position(newPosition)
				      bytesRead = inChannel.read(buf);
				      inChannel.position(getRandomPosition(posRange));
				     //System.out.println("@::"+inChannel.position());
				     j++;
				    }
				    System.out.println("Total time in milli sec::" +(System.currentTimeMillis()-startTime));
				    int totaltime=(int) (System.currentTimeMillis()-startTime);
				    long throughput= (c*buffer)/(totaltime);
				    
				    System.out.println("Total bytes read (in Bytes)::" +c*buffer);
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
	 public float[] squentialOps(String filepath, int tCount)
	 
	 {
		 
		 float benchMark[]= new float[3];
		 	try {
				
			 //ChannelOps cOps= new ChannelOps();
			 System.out.println("\n #### Disk BenchMark  ####");
			 for(int i=0;i<3;i++)
			 {int j=0;
				 
				 	if(i==0)
					 {	System.out.println("Performing disk read operation for....buffer size=1 B");
						 //_byte[j]=cOps.readdata((int)Math.pow(1024,i));
					 //_byte[j]=readdata(1,filepath);
					 ChannelOps c1=new ChannelOps(filepath,1,"seqRead");
						 for(int k=0;k<tCount;k++)
						 {new Thread(c1,"Thread00"+k+1).start();
						 }
					 }
//				 if(i==1)
//				 {	  System.out.println("Performing disk read operation for....buffer size=1 KB");
//					 _kbyte[j]=readdata(1024,filepath);	 
//				 }
//				 if(i==2)
//				 { System.out.println("Performing disk read operation for....buffer size=1 MB");
//					 _mbyte[j]=readdata(1024*1024,filepath);	 
//				 	
//				 	}
			 }
//			 benchMark[0]=computeAvg(_byte);
//			 benchMark[1]=computeAvg(_kbyte);
//			 benchMark[2]=computeAvg(_mbyte);
			 
	 }
	  catch (Exception e) {
			e.printStackTrace();
		}
		 	return benchMark;
	}
	 public int  randomOps(String filePath,int tCount)
	 {
		 try {
			 System.out.println("\n #### Disk BenchMark (randomized) ####");
			 ChannelOps c1=new ChannelOps(filePath,1,"ranRead");
			 for(int k=0;k<tCount;k++)
			 {
				 new Thread(c1,"Thread00"+k+1).start();
			 }
		 
//			 for(int i=0;i<3;i++)
//			 {int j=0;
//				 
//			 System.out.println("for i ="+i+" buffer size is::"+(int)Math.pow(1024,i));
//			  	if(i==0)
//					 {	System.out.println("Performing disk read operation for....buffer size=1 B");
//						// _byte[j]=cOps.readdata((int)Math.pow(1024,i));
//					 _byte[j]=readRandom(1,filePath);
//					 	
//					 }
//				 
//				 if(i==1)
//				 {	  
//					 System.out.println("Performing random disk read operation for....buffer size=1 KB");
//					 _kbyte[j]=readRandom(1024,filePath);	 
//					 }
//		
//				 if(i==2)
//				 { System.out.println("Performing random disk read operation for....buffer size=1 MB");
//					 _mbyte[j]=readRandom(1024*1024,filePath);	 
//					
//				 	}
//			 
//			 }
		 
	 }
	  catch (Exception e) {
			e.printStackTrace();
		}
		 
		 return 0;
	 }
	 
	 public int writeData(boolean random, int buffer) throws FileNotFoundException
	 {
		 long startTime=0;
		 long endTime=0;
		 int totalIteration=100000;
		 try {
			RandomAccessFile aFile = new RandomAccessFile("outfile.txt", "rw");
			String s = "###shashank sahrmaw api="+System.currentTimeMillis();
			 	if(random==false)
			 	{
			// BufferedWriter bw=Files.newBufferedWriter("benchmarktest.txt", Charset.forName("US-ASCII"),OpenOption.Create);
			 		
			 		byte data[] = s.getBytes();
			 		
			 		try 
			 		{
//		 			Path path = FileSystems.getDefault().getPath("test001.txt");
//		 			System.out.println("path::"+path.toString());
//		 			OutputStream out = new BufferedOutputStream(
//		 		                 Files.newOutputStream(path,(OpenOption)StandardOpenOption.CREATE,StandardOpenOption.WRITE));
//		 		System.out.println("writing to the file");
//		 		    out.write(s.getBytes(), 10, s.length());
//		 		    out.close();
			 			
			 			aFile.write(s.getBytes(), 10, 10);
			 			aFile.close();
			 		} catch (IOException x) {
			 		    System.err.println(x);
			 		}

				    finally
				    {
				    	System.out.println("written to files");
				    }
			 	}
			 	if(random==true)
			 	{
			 		Path path = FileSystems.getDefault().getPath("outfile1.txt");
			 		
		 			System.out.println("path::"+path.toString());
		 			OutputStream out = new BufferedOutputStream(
		 		                 Files.newOutputStream(path,(OpenOption)StandardOpenOption.CREATE,StandardOpenOption.WRITE));
		 			System.out.println("writing to the file");
		 		    
		 		   byte data[][]=constructString(buffer);
		 		   startTime=System.currentTimeMillis();
		 		   for(int i=0;i<(totalIteration/buffer);i++)
		 		   {
		 			  out.write(getDataPacket(buffer), 0, buffer);
		 			 out.flush();
		 		   }
		 		    out.close();
		 		    endTime=System.currentTimeMillis();
		 		    System.out.println("size of data written::"+(buffer*totalIteration));
		 		    float t1= ( (totalIteration*buffer)/(endTime-startTime));
		 		    float throughput=(t1*1000)/(1024*1024);
		 		    System.out.println("Throughput:: MB/sec"+throughput);
		 		    return (int)throughput;
			 	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally
		    {
		    	System.out.println("written to files");
		    }
		 	
		 		
		 return 0;
		 
	 }
	 public float[] writeOps()
	 {
		 
		 float benchMark[]= new float[3];
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
					 //_byte[j]=readdata(100,filepath);
					 _byte[j]= writeData(true, 1);
					 }
				 if(i==1)
				 {	  System.out.println("Performing disk read operation for....buffer size=1 KB");
					 //_kbyte[j]=readdata(1024,filepath);	 
				 _kbyte[j]=writeData(true, 1024);
				 }
		
				 if(i==2)
				 { System.out.println("Performing disk read operation for....buffer size=1 MB");
					 _mbyte[j]=writeData(true, 1024*1024);	 
				 	
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
	 
	 public byte[][] constructString(int size)
	 {
		 String[] a=null;
		 byte data[][]=new byte[size][];
		 for(int i=0;i<size;i++)
		 { 
			//data[i]=(""+System.currentTimeMillis()+"").getBytes();
			data[i]=getDataPacket(size);
		 }
		 
		 return data;
	 }
	 public byte[] getDataPacket(int size)
	 {
		 byte a[]=new byte[size];
		 for(int i=0;i<size;i++)
		 {
			 a[i]=(byte) getRandomPosition(9);
		 }
		 
		 return a;
	 }
	 public long checkFileSize(String path,long buffer) 
	 {
		 try {
			RandomAccessFile aFile  = new RandomAccessFile(path, "rw");
			 long temp=aFile.length();
			 long i= (temp/buffer);
			 if(i<100)
			 {
				 System.out.print("fileSize not enough benchmark with a buffer "+buffer);
				 
			 }if(i>(1024*1024))
			 {
				 System.out.print("fileSize is too big for  a buffer "+buffer);
				 
			 }
			 aFile.close();
			 return i;
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return 0;
	 }
	 synchronized public void printMarks(float[] benchmark,String name)
	 {   System.out.println("###Benchmark For Thread"+name);
		 System.out.println("\t 1000byte   \t 1kbyte \t 1mbyte");
		 for(int i=0;i<benchmark.length;i++)
		 {
			 System.out.print("\t\t "+benchmark[i]);
			 
		 }
	 }
}