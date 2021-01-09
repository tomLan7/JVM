package java学习;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.atomic.*;
public class 几种锁 {
	public static void main(String args[]) {
		
		
	}
	static Lock getLock(String lockName){
		Lock rtn=null;
		switch(lockName){
		case "自旋锁":
			rtn=new SpinLock();
			break;
		case "重量级锁":
			rtn=new Mutex();
			break;
		}
		return rtn;
	}
}
/**
 * 这个类专门用于作为所有锁类的父类，提供lock方法和unlock方法和try_lock方法
 * */
interface Lock{
	void lock();
	void unlock();
	boolean try_lock();
	boolean isLock();
	int getWaitThreadNumber();//获得等待的线程数
}
class SpinLock implements Lock{
	AtomicBoolean obj=new AtomicBoolean(false);
	static AtomicInteger waitThreadNumber=new AtomicInteger();
	@Override
	public void lock() {
		// TODO 自动生成的方法存根
		waitThreadNumber.incrementAndGet();
		while(!obj.compareAndSet(false, true));
		waitThreadNumber.decrementAndGet();
	}
	@Override
	public void unlock() {
		// TODO 自动生成的方法存根
		obj.set(false);
	}

	@Override
	public boolean try_lock() {
		// TODO 自动生成的方法存根
		return obj.compareAndSet(false, true);
	}

	@Override
	public boolean isLock() {
		// TODO 自动生成的方法存根
		return obj.get();
	}
	@Override
	public int getWaitThreadNumber() {
		// TODO 自动生成的方法存根
		return waitThreadNumber.get();
	}	
}
/**
 * 用线程机制实现的重量级锁
 * */
class Mutex2 implements Lock{
	Queue<Thread> tlist;
	Thread current=null;//为了实现偏向锁(递归锁)
	int count=0;//递归的计数
	
	@Override
	public void lock() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void unlock() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public boolean try_lock() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean isLock() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public int getWaitThreadNumber() {
		// TODO 自动生成的方法存根
		return 0;
	}
	
}
class Mutex implements Lock{
	AtomicBoolean obj=new AtomicBoolean(false);
	static AtomicInteger waitThreadNumber=new AtomicInteger();
	@Override
	public void lock() {
		// TODO 自动生成的方法存根
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
		// TODO 自动生成的方法存根
		return obj.compareAndSet(false, true);
		
	}

	@Override
	public boolean isLock() {
		// TODO 自动生成的方法存根
		return obj.get();
	}

	@Override
	public int getWaitThreadNumber() {
		// TODO 自动生成的方法存根
		return waitThreadNumber.get();
	}	
	
}