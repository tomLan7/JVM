package java考试题目;

import java.util.Arrays;
import java.util.Scanner;

public class wyw作业 {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Scanner in=new Scanner(System.in);
		String str=in.nextLine().trim();
		char arr[]=str.toCharArray();
		Arrays.sort(arr);
		char max=arr[arr.length-1],min=arr[0];
		int dex=Arrays.binarySearch(arr, '2');
		for(char t:arr){
			System.out.print(t+"\t");
		}
		System.out.println();
		System.out.println("长度"+arr.length+"\t最大值："+max+"\t最小值:"+min+"\t2的下标"+dex);
	}

}
