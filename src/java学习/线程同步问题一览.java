package javaѧϰ;

public class �߳�ͬ������һ�� {
	public static void main(String[] args) throws InterruptedException {
		// TODO �Զ����ɵķ������
		Thread t[]=new Thread[10];
		for(int i=0;i<t.length;i++){
			t[i]=new Thread(new �ٽ�������());
		}
		for(Thread i:t){
			i.start();
		}
		int aliveThreadNumber=t.length;
		while(aliveThreadNumber>0){
			for(int i=0;i<t.length;i++){
				if(t[i]!=null){
					t[i].join(1);	
					if(!t[i].isAlive()){
						System.out.println("�߳�"+i+"���˳���");
						t[i]=null;
						aliveThreadNumber--;
					}
				}
				
			}
		}    
		
		System.out.println(�ٽ�������.timer+"��"+�ٽ�������.timer2);
	}
}
class �ٽ������� implements Runnable{
	static volatile long timer=0;
	static long timer2=0;
	static Lock l=������.getLock("��������");
	public void run(){
		for(int i=0;i<1000;i++){
			l.lock();
			timer++;
			timer2++;
			l.unlock();
			//System.out.println(timer);
		}
		System.out.println("һ���߳����������׼���˳�");
	}
}