package javaѧϰ;
//ǳ������ǳ�Եĸ�����������ֵ���ֶ�ֵ��ͬ������Ҳ����ͬһ����
//������ֶ����������������õݹ��clone()��
/*1.����ʵ��Cloneable�ӿڣ��������CloneNotSupportedException�쳣
 * 2.Ĭ��ǳ������������͵���дclone������
 * */
public class ����clone implements Cloneable{
	class B implements Cloneable{
		int money=100;
		public B clone(){
			try {
				return (B)super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			return null;
		}
	}
	int a;
	int b;
	B house;
	
	@Override
	public ����clone clone() throws CloneNotSupportedException{
		����clone cl=(����clone)super.clone();
		cl.house=(B)this.house.clone();
		return cl;
	}
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		����clone obj=new ����clone();
		obj.a=25;
		obj.b=77;
		obj.house=obj.new B();
		
		try {
			����clone other=(����clone)obj.clone();
			System.out.println("��¡��ֵ��"+other.a+other.b+"Ǯ"+other.house.money);
			other.a=11;
			other.b=22;
			other.house.money-=100;
			System.out.println("��¡��ֵ��"+other.a+other.b+"Ǯ"+other.house.money);
			System.out.println("ԭ����ֵ��"+obj.a+obj.b+"Ǯ"+obj.house.money);
			
		} catch (CloneNotSupportedException e) {
			// TODO �Զ����ɵ� catch ��
			System.out.println("��¡ʧ�ܣ���֧�ֿ�¡");
			e.printStackTrace();
		}
	}

}
