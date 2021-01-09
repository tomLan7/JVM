package javaѧϰ;

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

//��ʽר��Ϊ��ȡ http://comic.kkkkdm.com/ ��վ�����������
public class ���� {
	class imageDown{
		//������ҳ����������������ͼƬURI�����ص���Ӧ����·��.����Ѱ����һҳ
		URI uri;	// uri��һ��Ҫȷ���Ǿ���URL��imageDown���߱�ʶ�����URL������
		String path;
		imageDown(URI uri,String path){
			this.uri=uri;
			this.path=path;
		}
		//��������������д���ļ�
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
			// TODO �Զ����ɵķ������
			//System.out.println("��ǰ�߳���"+((ThreadPoolExecutor)TODO).getPoolSize());
			System.out.println("��ʼ��ȡĳ��������������ݣ�"+uri+"������·����"+path);
			int timer=0;//��nҳ���ӵ�һҳ��ʼ
			URI nextURI=uri;//��һҳ��URL
			while(true){
				System.out.println("�̳߳���������"+((ThreadPoolExecutor)TODO).getTaskCount());
				try {
					WebClient webClient= new WebClient(BrowserVersion.CHROME);
					HtmlPage page1 = (HtmlPage)
							webClient.getPage(nextURI.toASCIIString());
					webClient.close();
					Document doc = Jsoup.parse(page1.asXml());
					//System.out.println(page1.asXml());
					String imgName=doc.getElementsByTag("img").first().attr("src");
					URL absUrl=nextURI.resolve(imgName).toURL();
					//�����ļ���������ļ���չ��ɶ��
					int dot=imgName.lastIndexOf('.'); 
					
					File f=new File(path,imgName.substring(imgName.lastIndexOf('/',imgName.lastIndexOf('/')-1),imgName.lastIndexOf('/')+1)+timer++ +imgName.substring(dot));
					
					System.out.println("��ʼ��ȡ����ͼƬ"+absUrl+"��д���ļ�"+f);
					f.getParentFile().mkdirs();
					f.createNewFile();
					toFile(absUrl.openStream(),f);
					String nextPage=doc.getElementsByAttributeValue("src", "/images/d.gif").parents().first().attr("href");
					if(nextPage.equals("/exit/exit.htm")){
						System.out.println("��ȡ������"+path+"���һҳ��");
						break;
					}
					nextURI=uri.resolve(nextPage);
					
				} catch (FailingHttpStatusCodeException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
					break;
				} catch (MalformedURLException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
					break;
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
					System.out.println("��ȡĳһҳ����"+path+"\t��ַ"+uri+"ʧ��");
					break;
				}
			}
			
		}
	}
	//Ϊ����ÿ�¶����������أ�������Ȳ�ѯ�����Ƿ������ݣ���������ݣ���������������ģ�����һ���������һ������
	ConcurrentLinkedQueue<imageDown> contentList=new  ConcurrentLinkedQueue<imageDown>();
	 class cartoon implements Runnable{//����һ�����������ط�����Ѱ�����л���Ȼ��������л����������˳������
		 String Name;//���������֣�һ��������URL��
		 URI uri;//��������URL��ָ���˸�������ҳ�����㷨ȥ�ֱ�����
		 cartoon(URI uri,String name){
			 this.uri=uri;this.Name=name;
		 }
		 //��ʼ��ȡͼƬ��ַ�����̳߳ض�ȡ
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+"��cartoon׼������");
				System.out.println("�̳߳���������"+((ThreadPoolExecutor)TODO).getCompletedTaskCount()+"/"+((ThreadPoolExecutor)TODO).getTaskCount());
				// TODO �Զ����ɵķ������
				System.out.println("��ʼ��ȡ����"+Name+"��ַ"+uri);
				File dir=new File("������/"+Name);
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
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
					System.out.println("��ȡ����"+uri+"�����쳣��������url��Ч");
				}
				System.out.println(Thread.currentThread().getName()+"cartoon�������");
			}
			
	 }
	
	Executor TODO=Executors.newFixedThreadPool(4);//�̳߳أ����������
	Set<URI> visibled=Collections.synchronizedSet(new HashSet<URI>());
	//����ȡ��URL���������߳�,�����������URI�����ҵ��������������ҵ�����Ŀ�������������
	class finder implements Runnable{
		URI uri;//����ҳ��URL,���Զ�����
		finder(URI uri){
			this.uri=uri;
		}
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			imageDown id=null;
			System.out.println(Thread.currentThread().getName()+"��finder׼�����ȿ����д������½� û�ж���");
			System.out.println("�̳߳���������"+((ThreadPoolExecutor)TODO).getTaskCount());
			id=contentList.poll();
				while((id)!=null){
					System.out.println("�ҵ� �������½� ���������ض����е�һ����������������,����"+contentList.size());
					id.run();
					System.out.println("�ҵ� �������½� ���������ض����е�һ���������������");
					id=contentList.poll();
				}
			
			System.out.println("��ʼ��ȡҳ��"+uri);
			try {
				Document doc=Jsoup.connect(uri.toASCIIString()).get();
				boolean isManga=false;//�����ҳ����һ����������ҳ���Ͳ����ټ������µݹ���
				Elements comicList=doc.getElementsByAttributeValue("id", "comicmain").last().children();
				for(Element e:comicList){
					isManga=true;
					//System.out.println("��ȡ��"+e.html());
					Element a=e.getElementsByTag("a").get(1);
					String suri=a.attr("href");
					URI newuri=null;
					
						newuri=uri.resolve(suri);
						//System.out.println("��"+uri+"��"+suri+"������"+newuri);
						if(!visibled.contains(newuri)){
							visibled.add(newuri);
						
							TODO.execute(new cartoon(newuri,a.text()));
						}
					
				}
				//isManga=false;
				if(isManga){//��һ������ҳ��ֵ�õݹ���ȥ
					System.out.println("�ݹ���ȥ");
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				System.out.println("URI�����ã�finderʧ�ܡ�");
			}	

			System.out.println(Thread.currentThread().getName()+"finder����ִ�����");
		}
		
	}
	public static void main(String args[]) throws URISyntaxException{
		���� a=new ����();
		a.TODO.execute(a.new finder(new URI("http://comic.kkkkdm.com/comictype/3_1.htm")));
	}
}
