package java学习;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

//格式专门为爬取 http://comic.kkkkdm.com/ 网站的漫画而设计
public class 爬虫 {
	class imageDown{
		//单张网页漫画，分析出其中图片URI并下载到对应本地路径.还会寻找下一页
		URI uri;	// uri，一定要确保是绝对URL，imageDown不具备识别相对URL的能力
		String path;
		imageDown(URI uri,String path){
			this.uri=uri;
			this.path=path;
		}
		//将输入流的内容写入文件
		void toFile(InputStream input,File f) throws FileNotFoundException, IOException{
			try(	
					
					InputStream in=new BufferedInputStream(input);
					OutputStream out=new BufferedOutputStream(new FileOutputStream(f));
				){
				int c;
				while((c=in.read())!=-1){
					out.write(c);
				}
			}
		}
		public void run(){
			// TODO 自动生成的方法存根
			//System.out.println("当前线程数"+((ThreadPoolExecutor)TODO).getPoolSize());
			System.out.println("开始读取某话漫画具体的内容："+uri+"到本地路径："+path);
			int timer=0;//第n页，从第一页开始
			URI nextURI=uri;//下一页的URL
			while(true){
				System.out.println("线程池里任务数"+((ThreadPoolExecutor)TODO).getTaskCount());
				try {
					WebClient webClient= new WebClient(BrowserVersion.CHROME);
					HtmlPage page1 = (HtmlPage)
							webClient.getPage(nextURI.toASCIIString());
					webClient.close();
					Document doc = Jsoup.parse(page1.asXml());
					//System.out.println(page1.asXml());
					String imgName=doc.getElementsByTag("img").first().attr("src");
					URL absUrl=nextURI.resolve(imgName).toURL();
					//计算文件名，获得文件扩展名啥的
					int dot=imgName.lastIndexOf('.'); 
					
					File f=new File(path,imgName.substring(imgName.lastIndexOf('/',imgName.lastIndexOf('/')-1),imgName.lastIndexOf('/')+1)+timer++ +imgName.substring(dot));
					
					System.out.println("开始读取漫画图片"+absUrl+"并写入文件"+f);
					f.getParentFile().mkdirs();
					f.createNewFile();
					toFile(absUrl.openStream(),f);
					String nextPage=doc.getElementsByAttributeValue("src", "/images/d.gif").parents().first().attr("href");
					if(nextPage.equals("/exit/exit.htm")){
						System.out.println("读取完漫画"+path+"最后一页。");
						break;
					}
					nextURI=uri.resolve(nextPage);
					
				} catch (FailingHttpStatusCodeException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					break;
				} catch (MalformedURLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					break;
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					System.out.println("读取某一页漫画"+path+"\t地址"+uri+"失败");
					break;
				}
			}
			
		}
	}
	//为了让每章动画优先下载，爬虫会先查询这里是否有内容，如果有内容，会有限下载这里的，这里一个对象代表一章内容
	ConcurrentLinkedQueue<imageDown> contentList=new  ConcurrentLinkedQueue<imageDown>();
	 class cartoon implements Runnable{//代表一部漫画的下载方法，寻找所有话，然后添加所有话到数组里，按顺序下载
		 String Name;//漫画的名字，一般是漫画URL的
		 URI uri;//该漫画的URL，指向了该漫画首页，有算法去分别下载
		 cartoon(URI uri,String name){
			 this.uri=uri;this.Name=name;
		 }
		 //开始读取图片网址，由线程池读取
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+"的cartoon准备运行");
				System.out.println("线程池里任务数"+((ThreadPoolExecutor)TODO).getCompletedTaskCount()+"/"+((ThreadPoolExecutor)TODO).getTaskCount());
				// TODO 自动生成的方法存根
				System.out.println("开始读取漫画"+Name+"地址"+uri);
				File dir=new File("爬漫画/"+Name);
				dir.mkdirs();
				//comiclistn
				try {
					Document doc=Jsoup.connect(uri.toASCIIString()).get();
					Elements comicList=doc.getElementById("comiclistn").children();
					for(Element e:comicList){
						Element a=e.getElementsByTag("a").get(0);
						String suri=a.attr("href");
						URI newuri=uri.resolve(suri);
							contentList.add(new imageDown(newuri,dir.toString()));
					}
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					System.out.println("读取漫画"+uri+"出现异常，可能是url无效");
				}
				System.out.println(Thread.currentThread().getName()+"cartoon任务结束");
			}
			
	 }
	
	Executor TODO=Executors.newFixedThreadPool(4);//线程池，会完成任务
	Set<URI> visibled=Collections.synchronizedSet(new HashSet<URI>());
	//负责取出URL并遍历的线程,并尝试添加新URI，会找到漫画，并尝试找到新项目。添加漫画任务。
	class finder implements Runnable{
		URI uri;//该网页的URL,会自动遍历
		finder(URI uri){
			this.uri=uri;
		}
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			imageDown id=null;
			System.out.println(Thread.currentThread().getName()+"的finder准备，先看看有待下载章节 没有东西");
			System.out.println("线程池里任务数"+((ThreadPoolExecutor)TODO).getTaskCount());
			id=contentList.poll();
				while((id)!=null){
					System.out.println("找到 待下载章节 漫画话下载队列中的一个漫画，立刻下载,还有"+contentList.size());
					id.run();
					System.out.println("找到 待下载章节 漫画话下载队列中的一个漫画，下载完成");
					id=contentList.poll();
				}
			
			System.out.println("开始读取页面"+uri);
			try {
				Document doc=Jsoup.connect(uri.toASCIIString()).get();
				boolean isManga=false;//如果本页不是一个漫画索引页，就不用再继续往下递归了
				Elements comicList=doc.getElementsByAttributeValue("id", "comicmain").last().children();
				for(Element e:comicList){
					isManga=true;
					//System.out.println("读取到"+e.html());
					Element a=e.getElementsByTag("a").get(1);
					String suri=a.attr("href");
					URI newuri=null;
					
						newuri=uri.resolve(suri);
						//System.out.println("用"+uri+"和"+suri+"解析出"+newuri);
						if(!visibled.contains(newuri)){
							visibled.add(newuri);
						
							TODO.execute(new cartoon(newuri,a.text()));
						}
					
				}
				//isManga=false;
				if(isManga){//是一个漫画页，值得递归下去
					System.out.println("递归下去");
					Elements a=doc.getElementsByTag("a");
					for(Element href:a){
						URI newuri=null;
						
						newuri=uri.resolve(href.attr("href"));
						
						if(!visibled.contains(newuri)){
							visibled.add(newuri);
							TODO.execute(new finder(newuri));
						} 
						
					}
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				System.out.println("URI不可用，finder失败。");
			}	

			System.out.println(Thread.currentThread().getName()+"finder任务执行完毕");
		}
		
	}
	public static void main(String args[]) throws URISyntaxException{
		爬虫 a=new 爬虫();
		a.TODO.execute(a.new finder(new URI("http://comic.kkkkdm.com/comictype/3_1.htm")));
	}
}
