package demo;

public class Heap {

	public static void main(String[] args) {
		long heapSize = Runtime.getRuntime().totalMemory();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();

		System.out.println("Current Heap Size: " + heapSize);
		System.out.println("Maximum Heap Size: " + heapMaxSize);
		System.out.println("Free Heap Size: " + heapFreeSize);

	}

}
