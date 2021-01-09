package java学习;

public class 表达式计算顺序 {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//比c++强，确实从左到右
		int a=25;
		String str=new String();
		str=null;
		if(a>=++a){
			System.out.println("true");
		}else
		System.out.println("false");
	}

}
