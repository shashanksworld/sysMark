package com.network;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NetworkClient {
	static Socket p1=null;
	
	
	public static void main(String arg[])
	{
		long t1,t2;
	
	try {
		
		p1=new Socket("localhost",8989);
		System.out.println("Socket created\n ");
		NetworkClient nc= new NetworkClient();
		nc.showMenu();
		
		try {
		
			String userInput;
			int a[]=new int[1000];
			Scanner stdin = new Scanner(System.in);
			PrintWriter out= new PrintWriter(p1.getOutputStream(),true);
			BufferedReader in =
			        new BufferedReader(
			            new InputStreamReader(p1.getInputStream()));
			
			while ((userInput = stdin.nextLine()) != null) {
				switch (userInput)
				{
					case "1":	
								for(int i=0;i<1000;i++)
								{
									out.println(System.nanoTime());
									a[i]=new Integer(in.readLine());
								}
								System.out.println("Avg latency in microsec " + nc.computeAverage(a));
								break;
					case "2":   System.out.println("option 2 selected");
								int buffer=1,n=10000;
								t1=System.currentTimeMillis();
								for(int i=0;i<10000;i++)
								{
									out.println(((Long)nc.getRandomNo(100)).byteValue());
									a[i]=new Integer(in.readLine());
								}
								t2=System.currentTimeMillis()-t1;
								System.out.println("Throughput Mbits/sec :"+(n*buffer*8)/(1024*1024*t2));
								break;
						
					default:	System.out.println("invalid input");nc.showMenu();
								break;
					
				}
				//System.out.println("Press any key..\n");
			    
			    
			    
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	public void pushToStream(String s,Socket p)
	{
		PrintStream output=null;
			try {
				output=new PrintStream(p.getOutputStream());
				output.write("this is test to write to output stream".getBytes());
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally 
			{
				output.close();
			}

		
	}
	public void showMenu()
	{
		System.out.println("1. Calculate network latency");
		System.out.println("2. Calculate bandwidth for buffer 1B");
		System.out.println("3. Calculate bandwidth for buffer 1kB");
		System.out.println("4. Calculate bandwidth for buffer 1mB");
		
	}
	
	public float computeAverage(int []a)
	{
	float sum=0;
		if(a!=null && a.length>0)
		{
			for(int j=0;j<a.length;j++)
			{
				sum+=a[j];
			}
		}
		return sum/a.length;
	}
	 public long getRandomNo(long l)
	 {
		 return (long) (Math.random()*l);
		 	
	 }	
	
}
