package java学习;

public abstract class 测试final {
	final int a=23;
	int cc=23;
	synchronized void run(){
		
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		final int b=23;//final修饰基本类型时，
		final String str="nihao";
		final 测试final obj=new 测试final(){//匿名内部类
			@Override
			void run(){
				System.out.println("nihao");
			}
		
		};
		//finally 来释放java无法释放的内存。
		//数据库连接，打开的文件， 套接字，等
		
		obj.cc=233;
		//obj=null;
		//abstract 抽象的，抽象类可以有抽象方法。
	}

}
