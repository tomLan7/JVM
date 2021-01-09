package java学习;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

public class 遍历接口 {
	static int c=5;
	static class test implements Iterable<Integer>{
		public Iterator<Integer> iterator() {
			// TODO 自动生成的方法存根
			return new Iterator<Integer>(){

				@Override
				public boolean hasNext() {
					System.out.println("你好");
					// TODO 自动生成的方法存根
					return c-->0;
				}

				@Override
				public Integer next() {
					System.out.println("呀"+c);
					// TODO 自动生成的方法存根
					return 5;
				}};
		}

		
		
		
	}
	public static void main(String args[]) throws UnsupportedEncodingException{
		String str = new String("hhhh ty智障%shfu摸淑芬十分uif内服NSF黑");
		// 1.以GBK编码方式获取str的字节数组，再用String有参构造函数构造字符串
		System.out.println(new String(str.getBytes("GBK")));
		// 2.以UTF-8编码方式获取str的字节数组，再以默认编码构造字符串
		System.out.println(new String(str.getBytes("UTF-8")));
		test b=new test();
		for(int i:b){
			
		}
		try( FileInputStream var2 = new FileInputStream("nihao"); ){
			
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Vector<Integer> arr = new Vector<Integer>();
		arr.add(5);
		arr.add(6);
		for(Iterator<Integer> i=arr.iterator();i.hasNext();){
			System.out.println(i.next());
		}
	}
}
