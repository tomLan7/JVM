package java学习;

public class  外部类 {
	int a;
	int b;
	static int c;
	class 普通内部类{
		void run(){
			a=2;
			System.out.println("外部类中b的值是"+外部类.this.b);//内部类中拥有外部类的一个引用
			;
			
		}
	}
	static class 静态内部类{
		void run(){
			c=2;

			//外部类.this.b;静态内部类本身和外部类没关系
			//a=2;
		}
	}
	public static void main(String args[]){
		外部类.静态内部类 s=new 外部类.静态内部类();
		外部类 o=new 外部类(){//创建一个子类，子类可以重写父类的方法
			
		};
		o.b=77;
		外部类.普通内部类 s2=o.new 普通内部类();//普通内部类中系统会添加一个外部类的引用
		s2.run();
	}
	void 方法(int c){
		final int d=c;
		class 方法内部类{
			void run(){
				a=2;
				a=d;//只能使用final，可能是类似lamada表达式实现原理一样的原因。
				外部类.this.a=2;
			}
		}
		new 方法内部类(){
			void run(){
				System.out.println("你好啊");// 你好啊
			}
		};
	}
}
