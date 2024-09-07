package demo;

public class Demo1 {
	
	public static void main(String args[])
	{
Runtime runtime = Runtime.getRuntime();
        
        long maxMemory = runtime.maxMemory(); // Maximum amount of memory the JVM will attempt to use
        long totalMemory = runtime.totalMemory(); // Total amount of memory currently available to the JVM
        long freeMemory = runtime.freeMemory(); // Free memory within the total memory
        
        System.out.println("Max Memory: " + maxMemory / (1024 * 1024) + " MB");
        System.out.println("Total Memory: " + totalMemory / (1024 * 1024) + " MB");
        System.out.println("Free Memory: " + freeMemory / (1024 * 1024) + " MB");
	}

}
