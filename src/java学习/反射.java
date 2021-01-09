package java学习;
import java.lang.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
public class 反射 {
	反射(){
		System.out.println("调用构造函数");
	}
	反射(int f,int d){
		System.out.println("调用int,int构造函数"+f+"和"+d);
	}
	反射(int a){
		System.out.println("调用构造函数");
	}
	static void tt() throws Exception{
		
		throw new IllegalArgumentException();
		
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException, NoSuchMethodException {
		//java万物皆对象，java把各种类和方法和字段和修饰符都有对应的对象
		//方法，字段，构造函数 都可以选择获得 本类定义的，还是获得公有的。
		Class<反射> c=反射.class;
		Method mttt=c.getDeclaredMethod("tt");
		try{
			mttt.invoke(null);
				
		}catch(InvocationTargetException e){
			
			System.out.println("异常错误"+e.getTargetException().getClass().getName());
			
		}catch(Exception e){
			System.out.println("标准异常错误");
			
		}
		
		
		System.out.println(反射.class);
		System.out.println(c.getName());
		Method mt[]=c.getDeclaredMethods();//获得全部的方法，只有本类的方法，公有私有都可以
		System.out.println(Arrays.toString(mt));
		System.out.println(Arrays.toString(c.getMethods()));//获得public方法，包括继承来的
		
		try {
			c.newInstance();//使用无参构造来创建对象。
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}//可以通过Class直接调用无参构造来创建对象
		Constructor cs[]=c.getDeclaredConstructors();//获得当前Class的所有构造方法
		System.out.println(Arrays.toString(cs));
		反射 f=(反射)cs[1].newInstance(5,3);
		mt[1].invoke(f);//成员方法想要执行，需要具体对象
		Field fd=c.getDeclaredField("a");//fd代表了 java学习.反射 类中的a字段
		//f.a=66;f.b=77;
		System.out.println(fd.get(f));//字段想要获得值，必须获得某一个对象的字段的值AA
		
		String str=new String("nihao");
		//char[] value;
		Field sv=String.class.getDeclaredField("value");
		sv.setAccessible(true);//正常来讲反射不能使用私有字段，可以通过setAccessible来让可以使用私有字段
		System.out.println(sv.get(str));
		( (char[])sv.get(str)  )[2]='3';//用反射获得了String内部的char数组
		System.out.println(str);
	}
	/*
	 * class A{
		private int t=123;
		}
	 * */
	void run(){
		System.out.println("调用run函数");
	}
	public void ttt(){
		
	}
}
