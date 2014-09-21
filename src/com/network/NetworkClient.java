package com.network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class NetworkClient {
	static Socket p1=null;
	
	
	public static void main(String arg[])
	{
		
	
	try {
		p1=new Socket("localhost",8989);
		System.out.println("Socket created");
		BufferedReader clientFeed =new BufferedReader(new InputStreamReader(p1.getInputStream()));
		System.out.println(clientFeed.readLine());
		
		int i=0;
		while(i<20)
		{
			p1.getOutputStream().write("test from the client".getBytes());	
			i++;
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
	
	
	
	
}
