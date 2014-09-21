package com.cpu;

import com.disk.ChannelOps;
import java.util.Random;

public class MarkCPU {
	 
	 //static int MAX_VALUE=2000;
	 static int MAX_VALUE=Integer.MAX_VALUE;
	 static int[] bucket;
	 static int cores=0;
	 static long memory=0;
	 static long startTime=0;
	 public static void main(String arg [])
	 {
	
		 cores=Runtime.getRuntime().availableProcessors();
		 memory=Runtime.getRuntime().freeMemory();
		 startTime=System.currentTimeMillis();
		 
		 System.out.println("Available processors (cores): "
		 +cores);
		 // Total amount of free memory available to the JVM 
		 System.out.println("Free memory (bytes): " + 
		 memory);
		 System.out.println("total memory"+Runtime.getRuntime().totalMemory());
		 
		 System.out.println("Max value for the iterator"+MAX_VALUE);
		 
		 System.out.println("Begining of the loop -Start Time::"+System.currentTimeMillis());
		 Random rnd = new Random();
		//bucket[1]=rnd.nextInt();
		 
		 for(int i=0;i<MAX_VALUE;i++)
		 {  
			 float a=(float)rnd.nextInt(MAX_VALUE)+(float)rnd.nextInt(MAX_VALUE);
		 }
		 
				System.out.println("Available processors (cores): "
				 +Runtime.getRuntime().availableProcessors());
		 	
				 //Total amount of free memory available to the JVM 
				 System.out.println("Free memory (bytes) after loop: " + 
				 Runtime.getRuntime().freeMemory()); long exeTime=(long)System.currentTimeMillis()-startTime;
				 System.out.println("Time for executing this code"+ exeTime);
				 System.out.println("total instructiopn/ sec=="+(MAX_VALUE/(exeTime)));

	 	}
}
