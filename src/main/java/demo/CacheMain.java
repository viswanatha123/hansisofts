package demo;

import java.util.Scanner;

public class CacheMain {

	public static void main(String[] args) {
		
		System.out.println("************* Start Cache Managemnt **********");
		

		
		CacheManage cache = new CacheManage(3); // Cache with capacity 3
		cache.putNode(1, 1);
		cache.putNode(2, 2);
		cache.putNode(2, 10);
		
	
		
		
			
		//cache.removeNode(2);
	
		
		System.out.println("get 2  :"+cache.getNode(2));
		
					
		System.out.println("Cache Size :"+cache.getSize()); // Returns 3

	}

}
