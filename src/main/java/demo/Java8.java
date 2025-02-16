package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8 {

	public static void main(String[] args) {

		

				String name="viswanatha";
				List<Character> list=name.chars().mapToObj(n->(char)n).collect(Collectors.toList());
				
				
				
				Map<Character,Long> ee=list.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
				
						for(Map.Entry<Character,Long> xx:ee.entrySet())
						{
							
								System.out.println(xx.getKey()+"  "+xx.getValue());
							
						}
	}
	
	
}
