package javaѧϰ;

public abstract class ����final {
	final int a=23;
	int cc=23;
	synchronized void run(){
		
	}
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		final int b=23;//final���λ�������ʱ��
		final String str="nihao";
		final ����final obj=new ����final(){//�����ڲ���
			@Override
			void run(){
				System.out.println("nihao");
			}
		
		};
		//finally ���ͷ�java�޷��ͷŵ��ڴ档
		//���ݿ����ӣ��򿪵��ļ��� �׽��֣���
		
		obj.cc=233;
		//obj=null;
		//abstract ����ģ�����������г��󷽷���
	}

}
