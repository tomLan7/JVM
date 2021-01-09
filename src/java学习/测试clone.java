package java学习;
//浅拷贝，浅显的复制所有字面值，字段值相同，引用也引用同一对象。
//深拷贝，字段正常拷贝，把引用递归的clone()。
/*1.必须实现Cloneable接口，否则会抛CloneNotSupportedException异常
 * 2.默认浅拷贝，想深拷贝就得重写clone函数。
 * */
public class 测试clone implements Cloneable{
	class B implements Cloneable{
		int money=100;
		public B clone(){
			try {
				return (B)super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return null;
		}
	}
	int a;
	int b;
	B house;
	
	@Override
	public 测试clone clone() throws CloneNotSupportedException{
		测试clone cl=(测试clone)super.clone();
		cl.house=(B)this.house.clone();
		return cl;
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		测试clone obj=new 测试clone();
		obj.a=25;
		obj.b=77;
		obj.house=obj.new B();
		
		try {
			测试clone other=(测试clone)obj.clone();
			System.out.println("克隆的值是"+other.a+other.b+"钱"+other.house.money);
			other.a=11;
			other.b=22;
			other.house.money-=100;
			System.out.println("克隆的值是"+other.a+other.b+"钱"+other.house.money);
			System.out.println("原来的值是"+obj.a+obj.b+"钱"+obj.house.money);
			
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			System.out.println("克隆失败，不支持克隆");
			e.printStackTrace();
		}
	}

}
