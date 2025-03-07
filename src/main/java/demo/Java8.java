package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8 {

	public static void main(String[] args) {
		
		
		List<Employee> list=new ArrayList<>();
		list.add(new Employee("aaa",10,1000));
		list.add(new Employee("ddd",40,2000));
		list.add(new Employee("ccc",30,3000));
		list.add(new Employee("aaa",20,4000));
		list.add(new Employee("eee",50,5000));
		
		

		int num=5;
		
		for(int i=num;i>=1;i--)
		{
			for(int j=0;j<=num -i+ 1;j++)
			{
				System.out.print(i);
			}
		}


	
		
	}
	
	
}
