package javaѧϰ;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

public class �����ӿ� {
	static int c=5;
	static class test implements Iterable<Integer>{
		public Iterator<Integer> iterator() {
			// TODO �Զ����ɵķ������
			return new Iterator<Integer>(){

				@Override
				public boolean hasNext() {
					System.out.println("���");
					// TODO �Զ����ɵķ������
					return c-->0;
				}

				@Override
				public Integer next() {
					System.out.println("ѽ"+c);
					// TODO �Զ����ɵķ������
					return 5;
				}};
		}

		
		
		
	}
	public static void main(String args[]) throws UnsupportedEncodingException{
		String str = new String("hhhh ty����%shfu�����ʮ��uif�ڷ�NSF��");
		// 1.��GBK���뷽ʽ��ȡstr���ֽ����飬����String�вι��캯�������ַ���
		System.out.println(new String(str.getBytes("GBK")));
		// 2.��UTF-8���뷽ʽ��ȡstr���ֽ����飬����Ĭ�ϱ��빹���ַ���
		System.out.println(new String(str.getBytes("UTF-8")));
		test b=new test();
		for(int i:b){
			
		}
		try( FileInputStream var2 = new FileInputStream("nihao"); ){
			
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
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
