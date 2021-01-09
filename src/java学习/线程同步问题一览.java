package java学习;

public class 线程同步问题一览 {
	public static void main(String[] args) throws InterruptedException {
		// TODO 自动生成的方法存根
		Thread t[]=new Thread[10];
		for(int i=0;i<t.length;i++){
			t[i]=new Thread(new 临界区问题());
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
						System.out.println("线程"+i+"已退出。");
						t[i]=null;
						aliveThreadNumber--;
					}
				}
				
			}
		}    
		
		System.out.println(临界区问题.timer+"和"+临界区问题.timer2);
	}
}
class 临界区问题 implements Runnable{
	static volatile long timer=0;
	static long timer2=0;
	static Lock l=几种锁.getLock("重量级锁");
	public void run(){
		for(int i=0;i<1000;i++){
			l.lock();
			timer++;
			timer2++;
			l.unlock();
			//System.out.println(timer);
		}
		System.out.println("一个线程完成了任务，准备退出");
	}
}