package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8 {

	public static void main(String[] args) {

		
		List<Employee> list=new ArrayList<>();
		list.add(new Employee("aaa",10,1000));
		list.add(new Employee("bbb",10,1000));
		list.add(new Employee("bbb",10,1000));
		list.add(new Employee("ccc",10,1000));
		list.add(new Employee("ddd",10,1000));
		
		Map<String, Long> xx=list.stream().collect(Collectors.groupingBy(Employee::getName,Collectors.counting()));
		
		
		Map<String, Integer> bb=list.stream().collect(Collectors.groupingBy(Employee::getName, Collectors.summingInt(Employee::getAge)));
		
		
		for(Map.Entry<String, Integer> cc: bb.entrySet())
		{
			System.out.println(cc.getKey()+"  "+cc.getValue());
		}
		
		
		
	}
	
	
}
