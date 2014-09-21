package com.disk;

import java.io.Console;
import java.util.Scanner;



public class MarkDisk{
	
	/**
	 * @param args
	 */
	public static void main(String args[])
	{
	try {
		System.out.println("\n #### Disk BenchMark  ####\n\n");
		float benchmark[]= new float[3];
		//Environment details
		System.out.println("Free memory (bytes): " + 
		 Runtime.getRuntime().freeMemory());
		 System.out.println("total memory"+Runtime.getRuntime().totalMemory());
		
		 //User Input
		 MarkDisk md=new MarkDisk();
		 String filePath= md.askUser("Enter FilePath \n");
		 System.out.println("reading system path "+filePath);
		 
		 ChannelOps cOps= new ChannelOps();
		 
		 String readPattern=md.askUser("Enter ReadPattern 1)Sequential 2)Random \n");
		 
		 switch(readPattern)
		 {
		 case "1":benchmark=cOps.squentialOps(filePath); 
		 			break;
		 case "2":cOps.randomOps(filePath);
		 			break;
		 default : System.out.println("\t NO option selected \n\n");
		 		System.exit(0);break;
		 }
		 
		 System.out.println("\t 1000byte   \t 1kbyte \t 1mbyte");
		 for(int i=0;i<benchmark.length;i++)
		 {
			 System.out.print("\t\t "+benchmark[i]);
			 
		 }
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public String askUser(String q)
	{
		 System.out.println(q);
		 Scanner reader = new Scanner(System.in);
		 String resp=reader.nextLine();
		return resp.trim();
	}
	
	
}	
