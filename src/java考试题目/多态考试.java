package java考试题目;

import java.util.ArrayList;

public class 多态考试 {
	public static void main(String args[]){
		Animal.main(args);
	}
}
abstract class Animal{
	protected String name;
	abstract void breathe();
	abstract void walk();
	public static void main( String args[]){
		
		ArrayList<Animal> list = new ArrayList<Animal>();
		list.add(new Horse("宝莉"));
		list.add(new Horse("碧琪"));
		list.add(new Snake("波雅·汉库克"));
		list.add(new Snake("大蛇丸"));
		
		for(Animal a:list){
			a.breathe();
			a.walk();
		}
		
	}
	Animal(String n){
		name=n;
	}
}
class Horse extends Animal{

	Horse(String n) {
		super(n);
		// TODO 自动生成的构造函数存根
	}

	@Override
	void breathe() {
		// TODO 自动生成的方法存根
		System.out.println("老马"+name+"呼吸");
	}

	@Override
	void walk() {
		// TODO 自动生成的方法存根
		System.out.println("老马"+name+"走路");
		
	}
	
}
class Snake extends Animal{

	Snake(String n) {
		super(n);
		// TODO 自动生成的构造函数存根
	}

	@Override
	void breathe() {
		// TODO 自动生成的方法存根
		System.out.println("蛇男"+name+"呼吸");
		
	}

	@Override
	void walk() {
		// TODO 自动生成的方法存根
		System.out.println("蛇男"+name+"走路");
		
	}
	
}

class Person{
	public String toString(){
		return "一个人";
	}
	public static void main(String args[]){
		ArrayList<Person> list = new ArrayList<Person>();
		list.add(new PupilStudent());
		list.add(new GraduateStudent());
	
		
		for(Person a:list){
			System.out.println(a);
			if(a instanceof PupilStudent){
				((PupilStudent)a).GoToSchool();
			}
			if(a instanceof GraduateStudent){
				((GraduateStudent)a).GoToProject();
			}
		}
		
	}
}
class PupilStudent extends Person{
	public String toString(){
		return "小学生";
	}
	void GoToSchool(){
		System.out.println("去上学");
	}
}
class GraduateStudent extends Person{
	public String toString(){
		return "研究生";
	}
	void GoToProject(){
		System.out.println("去从事项目");
	}
}
