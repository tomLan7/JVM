package java学习;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
public class URL访问{
	Queue<URI> q=new LinkedList<URI>();
	Set<URI> visibled=new HashSet<URI>();
	class t  implements Runnable{
		@Override
		public void run() {//每个线程都会取
			URI u=null;
			while(true){
				synchronized(q){
					
					u= q.poll();
					if(u==null){
						try {
							q.wait();
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				}
				try {
					Document co=Jsoup.connect(u.toASCIIString()).get();
					Element page=co.getElementById("pages");
					Elements es=page.getElementsByTag("a");
					for(Element e:es){
	
							URI uri=URI.create(e.attr("href"));
							synchronized(visibled){
								if(!visibled.contains(uri)){
									visibled.add(uri);
								}
							}
							synchronized(q){
								q.add(uri);
								q.notify();
							}
					}
					Elements imgs=co.getElementsByClass("tupian_img");
					for(Element e2:imgs){
						URI uri=URI.create(e2.attr("src"));
						boolean isEd=false;//是否下载过该图片
						synchronized(visibled){
							if(!visibled.contains(uri)){
								visibled.add(uri);
								isEd=true;
							}
						}
						if(isEd){
							transIMG(uri.toASCIIString());
						}
						
					}
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			
			
		}
	}
	static void transIMG(String uri) throws IOException{
		
		File f=new File("readIMG/"+uri.substring(uri.lastIndexOf('/')+1));
		
		URL url=new URL(uri);
		URLConnection con=url.openConnection();
		System.out.println("读取到图片"+uri);
		try(
		BufferedOutputStream fo=new BufferedOutputStream(new FileOutputStream(f));
		 BufferedInputStream bi=new BufferedInputStream(con.getInputStream());){
			 int c=0;
			 while((c=bi.read())!=-1){
				 fo.write(c);
			 }
			 System.out.println("下载图片"+f.getAbsolutePath()+"成功");
		}catch(IOException e){	
			 System.out.println("下载图片"+f.getAbsolutePath()+"失败");
		}
	}
	
	public static void main(String args[]) throws IOException{
		File f=new File("readIMG");
		f.mkdir();
	
		URL访问 u=new URL访问();
		try {
			u.q.add(new URI("https://www.lanvshen.com/a/33548/10.html"));
		} catch (URISyntaxException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		new Thread(u.new t()).start();
		new Thread(u.new t()).start();
		synchronized(u.q){
			u.q.notify();
		}
		
	}

	
}
