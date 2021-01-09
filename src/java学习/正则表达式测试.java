package java学习;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class 正则表达式测试 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="<a href=\"url\">title</a>";
		String p = "<a\\b[^>]+\\bhref=\"([^\"]*)\"[^>]*>([\\s\\S]*?)</a>";
		System.out.println("匹配中");
		 Pattern r=Pattern.compile(p);//根据字符串生成正则模板
		  Matcher  m=r.matcher(str);//用正则模板获得结果
		  while (m.find()) {
				System.out.println("定位的组数："+m.groupCount());
			  for(int i=1;i<=m.groupCount();i++){
				  System.out.println(m.group(i));
			  }
		  }
	}
}
