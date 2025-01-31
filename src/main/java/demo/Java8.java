package demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Java8 {

	public static void main(String[] args) {

	
				int x[]= {1,2,13,4,5};
			
		List<Integer> xx=Arrays.stream(x).boxed().collect(Collectors.toList());
		List<String> ss=xx.stream().map(n->n+"").filter(n->n.startsWith("1")).collect(Collectors.toList());
		ss.forEach(n->System.out.println(n));
	}

}
