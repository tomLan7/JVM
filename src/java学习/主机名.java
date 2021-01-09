package java学习;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class 主机名 {
	public static void ru() throws Exception{
		
	}
	public static void main(String[] args) throws UnknownHostException {
		// TODO 自动生成的方法存根
		InetAddress addr=InetAddress.getByName("www.baidu.com");
		System.out.println(addr);
		InetAddress host=InetAddress.getLocalHost();
		System.out.println(host);
		InetAddress host2=InetAddress.getLoopbackAddress();
		System.out.println(host2);
		SecurityManager s;
		
	}

}
