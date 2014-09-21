package com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {
		public static void main(String args[]) throws Exception
		{String flag="true";
			ServerSocket peerListner=null;
			 try {
				 peerListner=new ServerSocket(8989);
				 System.out.println("Listner created");
				 int i=0;
				 while(flag.equalsIgnoreCase("true"))
				 {System.out.println("listening...");
					 Socket peerNode=null;
					 try {
						peerNode=peerListner.accept();
						System.out.println("Listner Active");
						BufferedReader clientFeed =new BufferedReader(new InputStreamReader(peerNode.getInputStream()));
						System.out.println(clientFeed.readLine());
						 PrintWriter feed= new PrintWriter(peerNode.getOutputStream());
						 feed.println("this is test @"+System.currentTimeMillis());
						 
							
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 finally{
						 peerNode.close();
					 }
					 if(i>=10)
					 {
						 flag="false";
					 }
					 i++;
				 }
				 
				 System.out.println("Socket Created");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{peerListner.close();
				System.out.println("end of listner program");
			}
			 
			 
			 
		}
}
