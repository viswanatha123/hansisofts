package demo;

public class DoublLinkedList {
	
	LinkedNode head, tail;
	
	 DoublLinkedList() {
         head = new LinkedNode(-1, -1); 
         tail = new LinkedNode(-1, -1); 
         
         head.next = tail;
         tail.prev = head;
     }
	 
	 
	 void addFirst(LinkedNode node) {
         node.next = head.next;
         node.prev = head;
         head.next.prev = node;
         head.next = node;
     }
	 
	 
	
     void remove(LinkedNode node) {
         node.prev.next = node.next;
         node.next.prev = node.prev;
     }
     

     LinkedNode removeLast() {
         if (head.next == tail) return null; 
         LinkedNode last = tail.prev;
         remove(last);
         return last;
     }

}
