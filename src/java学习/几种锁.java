package javaѧϰ;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.atomic.*;
public class ������ {
	public static void main(String args[]) {
		
		
	}
	static Lock getLock(String lockName){
		Lock rtn=null;
		switch(lockName){
		case "������":
			rtn=new SpinLock();
			break;
		case "��������":
			rtn=new Mutex();
			break;
		}
		return rtn;
	}
}
/**
 * �����ר��������Ϊ��������ĸ��࣬�ṩlock������unlock������try_lock����
 * */
interface Lock{
	void lock();
	void unlock();
	boolean try_lock();
	boolean isLock();
	int getWaitThreadNumber();//��õȴ����߳���
}
class SpinLock implements Lock{
	AtomicBoolean obj=new AtomicBoolean(false);
	static AtomicInteger waitThreadNumber=new AtomicInteger();
	@Override
	public void lock() {
		// TODO �Զ����ɵķ������
		waitThreadNumber.incrementAndGet();
		while(!obj.compareAndSet(false, true));
		waitThreadNumber.decrementAndGet();
	}
	@Override
	public void unlock() {
		// TODO �Զ����ɵķ������
		obj.set(false);
	}

	@Override
	public boolean try_lock() {
		// TODO �Զ����ɵķ������
		return obj.compareAndSet(false, true);
	}

	@Override
	public boolean isLock() {
		// TODO �Զ����ɵķ������
		return obj.get();
	}
	@Override
	public int getWaitThreadNumber() {
		// TODO �Զ����ɵķ������
		return waitThreadNumber.get();
	}	
}
/**
 * ���̻߳���ʵ�ֵ���������
 * */
class Mutex2 implements Lock{
	Queue<Thread> tlist;
	Thread current=null;//Ϊ��ʵ��ƫ����(�ݹ���)
	int count=0;//�ݹ�ļ���
	
	@Override
	public void lock() {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void unlock() {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public boolean try_lock() {
		// TODO �Զ����ɵķ������
		return false;
	}

	@Override
	public boolean isLock() {
		// TODO �Զ����ɵķ������
		return false;
	}

	@Override
	public int getWaitThreadNumber() {
		// TODO �Զ����ɵķ������
		return 0;
	}
	
}
class Mutex implements Lock{
	AtomicBoolean obj=new AtomicBoolean(false);
	static AtomicInteger waitThreadNumber=new AtomicInteger();
	@Override
	public void lock() {
		// TODO �Զ����ɵķ������
		synchronized (this){
				try {
					waitThreadNumber.incrementAndGet();
			while(!obj.compareAndSet(false, true)){
				this.wait();
			}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		waitThreadNumber.decrementAndGet();
	}

	@Override
	public void unlock() {
		obj.set(false);
			try{
				synchronized (this){
					this.notify();
				}
				
			}catch(IllegalMonitorStateException im){	
			}
	}
	@Override
	public boolean try_lock() {
		// TODO �Զ����ɵķ������
		return obj.compareAndSet(false, true);
		
	}

	@Override
	public boolean isLock() {
		// TODO �Զ����ɵķ������
		return obj.get();
	}

	@Override
	public int getWaitThreadNumber() {
		// TODO �Զ����ɵķ������
		return waitThreadNumber.get();
	}	
	
}