package demo;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8 {

	public static void main(String[] args) {

		int ch[]= {1,2,3,4,5,6,7};
		
		int n=ch.length;
		
		int po=2;
		po=po % n;
		
		
		reverse(ch,0,n-1);
		reverse(ch,0,po-1);
		//reverse(ch, po, n - 1);
		
		for(int i=0;i<ch.length;i++)
		{
			System.out.println(ch[i]);
		}
		
	}
	
	
	private static void reverse(int[] arr, int start, int end)
	{
		while (start < end)
		{
			int temp=arr[start];
			arr[start]=arr[end];
			arr[end]=temp;
			
			start++;
			end--;
		}
	}
	/*
	private static void reverse(int[] arr, int start, int end) {
        while (start < end) 
        {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
   */
		
	
		
}
