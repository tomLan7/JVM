package javaѧϰ;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ������ʽ���� {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="<a href=\"url\">title</a>";
		String p = "<a\\b[^>]+\\bhref=\"([^\"]*)\"[^>]*>([\\s\\S]*?)</a>";
		System.out.println("ƥ����");
		 Pattern r=Pattern.compile(p);//�����ַ�����������ģ��
		  Matcher  m=r.matcher(str);//������ģ���ý��
		  while (m.find()) {
				System.out.println("��λ��������"+m.groupCount());
			  for(int i=1;i<=m.groupCount();i++){
				  System.out.println(m.group(i));
			  }
		  }
	}
}
