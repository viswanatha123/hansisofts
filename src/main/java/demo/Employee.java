package demo;

public class Employee {
	
	private String name;
	private int age;
	private int sal;
	
	public Employee(String name, int age, int sal)
	{
		this.name=name;
		this.age=age;
		this.sal=sal;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getSal() {
		return sal;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setSal(int sal) {
		this.sal = sal;
	}
	
	
	
}
