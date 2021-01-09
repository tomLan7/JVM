package java������Ŀ;

import java.util.ArrayList;

public class ��̬���� {
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
		list.add(new Horse("����"));
		list.add(new Horse("����"));
		list.add(new Snake("���š������"));
		list.add(new Snake("������"));
		
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
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	void breathe() {
		// TODO �Զ����ɵķ������
		System.out.println("����"+name+"����");
	}

	@Override
	void walk() {
		// TODO �Զ����ɵķ������
		System.out.println("����"+name+"��·");
		
	}
	
}
class Snake extends Animal{

	Snake(String n) {
		super(n);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	void breathe() {
		// TODO �Զ����ɵķ������
		System.out.println("����"+name+"����");
		
	}

	@Override
	void walk() {
		// TODO �Զ����ɵķ������
		System.out.println("����"+name+"��·");
		
	}
	
}

class Person{
	public String toString(){
		return "һ����";
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
		return "Сѧ��";
	}
	void GoToSchool(){
		System.out.println("ȥ��ѧ");
	}
}
class GraduateStudent extends Person{
	public String toString(){
		return "�о���";
	}
	void GoToProject(){
		System.out.println("ȥ������Ŀ");
	}
}
