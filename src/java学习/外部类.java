package javaѧϰ;

public class  �ⲿ�� {
	int a;
	int b;
	static int c;
	class ��ͨ�ڲ���{
		void run(){
			a=2;
			System.out.println("�ⲿ����b��ֵ��"+�ⲿ��.this.b);//�ڲ�����ӵ���ⲿ���һ������
			;
			
		}
	}
	static class ��̬�ڲ���{
		void run(){
			c=2;

			//�ⲿ��.this.b;��̬�ڲ��౾����ⲿ��û��ϵ
			//a=2;
		}
	}
	public static void main(String args[]){
		�ⲿ��.��̬�ڲ��� s=new �ⲿ��.��̬�ڲ���();
		�ⲿ�� o=new �ⲿ��(){//����һ�����࣬���������д����ķ���
			
		};
		o.b=77;
		�ⲿ��.��ͨ�ڲ��� s2=o.new ��ͨ�ڲ���();//��ͨ�ڲ�����ϵͳ�����һ���ⲿ�������
		s2.run();
	}
	void ����(int c){
		final int d=c;
		class �����ڲ���{
			void run(){
				a=2;
				a=d;//ֻ��ʹ��final������������lamada���ʽʵ��ԭ��һ����ԭ��
				�ⲿ��.this.a=2;
			}
		}
		new �����ڲ���(){
			void run(){
				System.out.println("��ð�");// ��ð�
			}
		};
	}
}
