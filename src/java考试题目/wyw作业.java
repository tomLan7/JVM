package java������Ŀ;

import java.util.Arrays;
import java.util.Scanner;

public class wyw��ҵ {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
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
		System.out.println("����"+arr.length+"\t���ֵ��"+max+"\t��Сֵ:"+min+"\t2���±�"+dex);
	}

}
