package javaѧϰ;
import java.lang.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
public class ���� {
	����(){
		System.out.println("���ù��캯��");
	}
	����(int f,int d){
		System.out.println("����int,int���캯��"+f+"��"+d);
	}
	����(int a){
		System.out.println("���ù��캯��");
	}
	static void tt() throws Exception{
		
		throw new IllegalArgumentException();
		
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException, NoSuchMethodException {
		//java����Զ���java�Ѹ�����ͷ������ֶκ����η����ж�Ӧ�Ķ���
		//�������ֶΣ����캯�� ������ѡ���� ���ඨ��ģ����ǻ�ù��еġ�
		Class<����> c=����.class;
		Method mttt=c.getDeclaredMethod("tt");
		try{
			mttt.invoke(null);
				
		}catch(InvocationTargetException e){
			
			System.out.println("�쳣����"+e.getTargetException().getClass().getName());
			
		}catch(Exception e){
			System.out.println("��׼�쳣����");
			
		}
		
		
		System.out.println(����.class);
		System.out.println(c.getName());
		Method mt[]=c.getDeclaredMethods();//���ȫ���ķ�����ֻ�б���ķ���������˽�ж�����
		System.out.println(Arrays.toString(mt));
		System.out.println(Arrays.toString(c.getMethods()));//���public�����������̳�����
		
		try {
			c.newInstance();//ʹ���޲ι�������������
		} catch (InstantiationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}//����ͨ��Classֱ�ӵ����޲ι�������������
		Constructor cs[]=c.getDeclaredConstructors();//��õ�ǰClass�����й��췽��
		System.out.println(Arrays.toString(cs));
		���� f=(����)cs[1].newInstance(5,3);
		mt[1].invoke(f);//��Ա������Ҫִ�У���Ҫ�������
		Field fd=c.getDeclaredField("a");//fd������ javaѧϰ.���� ���е�a�ֶ�
		//f.a=66;f.b=77;
		System.out.println(fd.get(f));//�ֶ���Ҫ���ֵ��������ĳһ��������ֶε�ֵAA
		
		String str=new String("nihao");
		//char[] value;
		Field sv=String.class.getDeclaredField("value");
		sv.setAccessible(true);//�����������䲻��ʹ��˽���ֶΣ�����ͨ��setAccessible���ÿ���ʹ��˽���ֶ�
		System.out.println(sv.get(str));
		( (char[])sv.get(str)  )[2]='3';//�÷�������String�ڲ���char����
		System.out.println(str);
	}
	/*
	 * class A{
		private int t=123;
		}
	 * */
	void run(){
		System.out.println("����run����");
	}
	public void ttt(){
		
	}
}
