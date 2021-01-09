package java考试题目;

import java.util.concurrent.atomic.AtomicInteger;

public class Maipiao {
	 public static void main(String args[]) {
		 man zhang=new man("张某",3,0,0,1);
		 man zhao=new man("赵某",1,0,1,0);
		 man li=new man("李某",3,0,1,0);
		 man sun=new man("孙某",2,0,0,1);
		 sale s1=new sale(); 
		 sale s2=new sale();
		 zhang.setTarget(s1);
		 zhao.setTarget(s1);
		 li.setTarget(s2);
		 sun.setTarget(s2);
		new Thread(zhang).start();
		new Thread(zhao).start();
		new Thread(li).start();
		new Thread(sun).start();
	}
}
	class man implements Runnable {
		String name;
		int number10=0;
		int number20=0;
		int number50=0;
		
		int needTickets;
		public man(String name,int needTickets, int number10, int number20, int number50) {
			this.name = name;
			this.number10 = number10;
			this.number20 = number20;
			this.number50 = number50;
			this.needTickets=needTickets;
		}
		sale target;
		public void setTarget(sale target) {
			this.target = target;
		}
		public void run() {
				while(needTickets!=0){
					for(int t=needTickets;t>0;t--){
						boolean selled=false;
						for(int i=0;i<number10;i++){
							for(int j=0;j<number20;j++){
								for(int k=0;k<number50;k++){
									if(target.sellTickets(i*10+j*20+k*50,i)){
										needTickets-=t;
										number10-=i;
										number20-=j;
										number50-=k;
										System.out.println(name+"购买"+t+"张票成功,还需要购买"+needTickets+"张。");
										selled=true;
										break;
									}
								}
								if(selled){
									break;
								}
							}
							if(selled){
								break;
							}
						}
						if(selled){
							break;
						}
						
					}
				}
				System.out.println(name+"购买所有需求的票成功。");
		}
		
	}
	class sale{
		static AtomicInteger tickets=new AtomicInteger(6);
		String name="售票窗口"+i++;
		static int i=1;
		 int number10 = 0, number20 = 1,number50=0;
		synchronized boolean sellTickets(int money,int ticketNumber){//花费指定的钱，尝试指定张票
			int expectMoney=money-ticketNumber*10;
			for(int i=0;i<number10;i++){
				for(int j=0;j<number20;j++){
					for(int k=0;k<number50;k++){
						if(i*10+j*20+k*50==expectMoney){
							number10-=i;
							number20-=j;
							number50-=k;
							tickets.decrementAndGet();
							System.out.println(name+"找了"+i+"张10元,"+j+"张20元"+k+"张50元，现在情况是："+toString());
							return true;
						}
						if(i*10+j*20+k*50>expectMoney){
							break;
						}
					}
					if(i*10+j*20>expectMoney){
						break;
					}
				}
				if(i*10>expectMoney){
					break;
				}
			}
			return false;
		}
		@Override
		public String toString(){
			return name+"有"+number10+"张十元，"+number20+"张20元，"+number50+"张50元，系统还有"+tickets+"张票。";
		}
	}
	