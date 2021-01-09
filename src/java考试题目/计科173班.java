package java考试题目;

import java.util.Collections;
import java.util.Scanner;

public class 计科173班 {
	static void q1(){
		Scanner in=new Scanner(System.in);
		StringBuilder sb=new StringBuilder();
		int cn=(int)(Math.random()*100);
		
		
		for(int i=0;i<cn;i++){
			int c=(int)(Math.random()*26);
			int big=(int)(Math.random()*2);
			sb.append((char)('a'+('A'-'a')*big+c));
		}
		System.out.println(sb);
		String userInput=in.next();
		int errorNumb=sb.length();
		for(int i=0;i<sb.length();i++){
			if(i<userInput.length()){
				if(sb.charAt(i)==userInput.charAt(i)){
					errorNumb--;
				}
			}
		}
		System.out.println("错误数:"+errorNumb+"\t错误率："+errorNumb/(double)sb.length());
	}
	static int Factorial(int l){
		int sum=1;
		for(int i=0;i<l;i++){
			sum*=i;
		}
		return sum;
	}
	static void q2(){
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		int A[]=new int[n];
		int B[]=new int[n];
		
		for(int i=0;i<A.length;i++){
			A[i]=(int)(Math.random()*10)+10;
		}
		for(int i=0;i<B.length;i++){
			B[i]=A[i];
		}
		System.out.println("随机生成的数组是");
		for(int i=0;i<A.length;i++){
			System.out.print(A[i]+"\t");
		}
		
		double result=0;
		for(int i:A){
			result+=Factorial(i);
		}
		System.out.println("\nn1!+n2! +n3!+n4!+n5!"+result);
		for(int i=0;i<B.length-1;i++){
			for(int j=0;j<B.length-i-1;j++){
				if(B[j]<B[j+1]){
					int tem=B[j];
					B[j]=B[j+1];
					B[j+1]=tem;
				}
			}
		}
		System.out.println("排序后的B数组是");
		for(int i=0;i<B.length;i++){
			System.out.print(B[i]+"\t");
		}
		System.out.println("\n最大值是:"+B[0]);
	}
	static void q4(){
		Scanner in=new Scanner(System.in);
		System.out.println("请输入题量数：");
		int n=0;
		n=in.nextInt();
		int answerNumber=0;
		int yesNumber=0;
		for(int i=0;i<n;i++){
			int n1=(int)(Math.random()*11),n2=(int)(Math.random()*11);
			System.out.println(n1+"乘"+n2+"的积是多少?");
			
			while(true){
				int userAnswer=in.nextInt();
				answerNumber++;
				if(userAnswer==n1*n2){
					System.out.println("非常棒");
					yesNumber++;
					break;
				}else{
					System.out.println("不对，请再试一次");
				}
			}
			
		}
		double Accuracy=((double)yesNumber/answerNumber)*100;
		System.out.println("正确率为:"+Accuracy+"%");
			if(Accuracy<=75){
				System.out.println("请你的老师给你辅导一下");
			}
		}
	
	
public static void main(String[] args) {
		MyPoint.main(args);
	}
}
class MyPoint {
	double x;
	double y;
	MyPoint(){
		x=0;y=0;
	}
	MyPoint(double x,double y){
	this.x=x;
	this.y=y;
	}
	double distance(MyPoint other){
		return Math.pow(Math.pow(other.x-x, 2)+Math.pow(other.y-y, 2), 0.5);
	}
public static void main(String[] args) {
		MyPoint a=new MyPoint(),b=new MyPoint(10,30.5);
	System.out.println("a和b点的距离是"+a.distance(b));
	}
}