package java������Ŀ;

import java.lang.reflect.Array;
import java.util.ArrayList;

class student{
	public student(String no, String name, int age, char sex, String dept) {
		super();
		this.no = no;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.dept = dept;
	}
	String no;
	String name;
	int age;
	char sex;
	String dept;
	dormitory home=null;
}
//����
class dormitory{
	String BuildingNumber;
	public dormitory(String buildingNumber, String doorplate) {
		super();
		BuildingNumber = buildingNumber;
		Doorplate = doorplate;
	}
	String Doorplate;
	ArrayList<student> stu;
	void distribution(student s){
		stu.add(s);
	}
}
//ѧ������ϵͳ��ҵ
public class Test {
	public static void main(String args[]){
		int a=1,b=2,c=2;
		int hd=a+b<<c;
		System.out.println(hd);
		student s[]=new student[4];
		s[0]=new student("123","����",15,'��',"��Ϣ�����ѧ");
		s[1]=new student("124","����",16,'��',"��Ϣ�����ѧ");
		s[2]=new student("125","����",17,'Ů',"��Ϣ�����ѧ");
		s[3]=new student("126","����",18,'Ů',"��Ϣ�����ѧ");
		dormitory d[]=new dormitory[2];
		d[0]=new dormitory("12��§","2-204");
		d[1]=new dormitory("13��§","4-506");
		d[0].distribution(s[0]);
		d[0].distribution(s[1]);
		d[1].distribution(s[2]);
		d[1].distribution(s[3]);
	}
	
	
}
class StudentDAO{
	//��ɾ�Ĳ�
	student createStudent(String no, String name, int age, char sex, String dept){
		//д�����ݿ�
		return new student(no,name,age,sex,dept);
	}
	boolean deleteStudent(student stu){
		//�����ݿ���ɾ��,ɾ���ɹ�����true
		return true;
	}
	
	boolean updateStudent(student stu){
	//�����ݿ��и���ѧ����Ϣ���ҵ�ѧ������true
		return true;
	}
	student selectStudent(String no){
		//����ָ��ѧ�ŵ�ѧ��
		return null;
	}
}