package com.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class NetworkServer {
		public static void main(String args[]) throws Exception
		{String flag="true";
		Socket peerNode=null;
		String inputLine=null;
		String outputLine=null;
			ServerSocket peerListner=null;
			 try {
				 while(true)
				 {
				peerListner=new ServerSocket(8989);
				 System.out.println("Listner created");
				 peerNode=peerListner.accept();
				 System.out.println("accept connection");
				 int optionType=1;
				 PrintWriter out =
					        new PrintWriter(peerNode.getOutputStream(), true);
					    BufferedReader in = new BufferedReader(
					        new InputStreamReader(peerNode.getInputStream()));
					    while ((inputLine = in.readLine()) != null) {
					    	
					    	if(peerNode.getLocalPort()==8989)
					    	{long res=System.nanoTime();
					    	//System.out.println("reading...");
					    		
					    	long latency=(res-new Long(inputLine));
					        out.println(latency/1000);
					        if (inputLine.equals("Bye."))
					            break;
					    	}
					    	else{
					    		out.println("ok");
					    		if (inputLine.equals("\n"))
						            break;
					    	}
					    }
					    
				 System.out.println("listening...");
				 }
//				 while(false)
//				 {
//					 
//					 try {
//						
//						System.out.println("Listner Active");
//						BufferedReader clientFeed =new BufferedReader(new InputStreamReader(peerNode.getInputStream()));
//						String pingData=null;
//						DataOutputStream feedout= new DataOutputStream(peerNode.getOutputStream());
//						
//						feedout.writeBytes(pingData+System.currentTimeMillis()+" server feed".getBytes());
//						//
//						
//						while(clientFeed.readLine()!=null)
//						{System.out.println("....");
//							pingData=clientFeed.readLine();
//							System.out.println(pingData);
//							System.out.println("got ping(bytes)::"+pingData.length());	
//						}
						
//						while(!clientFeed.readLine().equals('\n'))
//						{
//							System.out.println("....");
//								
//						}
						
							
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 finally{
						 peerNode.close();
					 }
					 
				 }

}
