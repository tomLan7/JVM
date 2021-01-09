package java考试题目;

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
//宿舍
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
//学生管理系统作业
public class Test {
	public static void main(String args[]){
		int a=1,b=2,c=2;
		int hd=a+b<<c;
		System.out.println(hd);
		student s[]=new student[4];
		s[0]=new student("123","张三",15,'男',"信息计算科学");
		s[1]=new student("124","李四",16,'男',"信息计算科学");
		s[2]=new student("125","王五",17,'女',"信息计算科学");
		s[3]=new student("126","赵六",18,'女',"信息计算科学");
		dormitory d[]=new dormitory[2];
		d[0]=new dormitory("12号搂","2-204");
		d[1]=new dormitory("13号搂","4-506");
		d[0].distribution(s[0]);
		d[0].distribution(s[1]);
		d[1].distribution(s[2]);
		d[1].distribution(s[3]);
	}
	
	
}
class StudentDAO{
	//增删改查
	student createStudent(String no, String name, int age, char sex, String dept){
		//写入数据库
		return new student(no,name,age,sex,dept);
	}
	boolean deleteStudent(student stu){
		//从数据库中删除,删除成功返回true
		return true;
	}
	
	boolean updateStudent(student stu){
	//在数据库中更新学生信息，找到学生返回true
		return true;
	}
	student selectStudent(String no){
		//查找指定学号的学生
		return null;
	}
}