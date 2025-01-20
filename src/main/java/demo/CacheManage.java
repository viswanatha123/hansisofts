package demo;

import java.util.HashMap;

public class CacheManage {
	
	private  int capacity;
    private  HashMap<Integer, LinkedNode> cache;
    private  DoublLinkedList dll;
    
        
    
    public CacheManage(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.dll = new DoublLinkedList();
    }
    
    
			    public int getNode(int key) {
			    	LinkedNode node = cache.get(key);
			        if (node == null) {
			            return -1; 
			        }
			     return node.value;
			    }
			    
    
		
			    public void putNode(int key, int value) {
			    	LinkedNode node = cache.get(key);
			        if (node != null) {
			            node.value = value;
			            dll.remove(node);
			            dll.addFirst(node);
			        } else {
			             if (cache.size() >= capacity) {
			            	 LinkedNode last = dll.removeLast();
			                cache.remove(last.key);
			            }
			          
			             LinkedNode newNode = new LinkedNode(key, value);
			            dll.addFirst(newNode);
			            cache.put(key, newNode);
			        }
			    }
			    
			
		        public int getSize() {
		            return cache.size();
		        }
		        
		    
		        public void removeNode(int key) {
		        	LinkedNode node = cache.get(key);
		            if (node != null) {
		                dll.remove(node);
		                cache.remove(key);
		            }
		        }
    
    
}   
	 
