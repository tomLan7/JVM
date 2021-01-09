package ʵ��;

import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLChecker implements Runnable{
	static class element{
		String name=null;//��ǩ��
		Map<String,String> attr=null;//����
		StringBuilder inner=null;//�ڲ����ı�
		List<element> childs=null;
		element parent=null;
		element(element parent){//ÿһ��Ԫ�ض�һ���и�Ԫ�أ����ڵ����
			this.parent=parent;
			if(parent!=null){
				parent.addChild(this);
			}
		}
		void load(label l){//װ��һ��label
			if(!l.isEndLabel){
				System.out.println("װ�����Ժ�����");
				attr=l.attr;
				name=l.labelName;
			}else{
				System.out.println("�ǽ�����ǩ"+l);
			}
		}
		void addInner(String str){
			if(inner==null){
				inner=new StringBuilder();
			}
			inner.append(str);
		}
		private void addChild(element e){
			if(childs==null){
				childs=new ArrayList<element>();
			}
			childs.add(e);
		}
		public String toString(){
			StringBuilder sb=new StringBuilder();
			sb.append(name);
			sb.append(":");
			sb.append("[");
			if(attr!=null){
			Iterator<Entry<String, String>> entries = attr.entrySet().iterator();
				while(entries.hasNext()){
				    Entry<String, String> entry = entries.next();
				    String key = entry.getKey();
				    String value = entry.getValue();
				   sb.append(key+":"+value);
				   if(entries.hasNext()){
					   sb.append(",");
				   }
				}
			}
			sb.append("]");
			if(childs!=null){
				for(int i=0;i<childs.size();i++){
					sb.append('{');
					sb.append(childs.get(i).toString());
					sb.append('}');
				}
			}
			
			return sb.toString();
		}
	}
	static class label{//һ����ǩ��������ȡ����Ϣ
		String labelName=null;
		boolean isEndLabel=false;//�Ƿ��ǽ�����ǩ������ǩ���������ǽ�����ǩ
		boolean isXMLdeclare=false;
		Map<String,String> attr=null;//������Ϣ
		label(String str){//����һ��������<��>�����ݣ�ת��Ϊ����
			str=str.trim();//ɾ��ǰ���ո�
			if(str.startsWith("?xml")){
				isXMLdeclare=true;
			}
			if(str.endsWith("?")||str.startsWith("/")){
				System.out.println(str+"�ǽ�����ǩ");
				isEndLabel=true;
			}
			System.out.println(str);
			Pattern rname=Pattern.compile("^(\\S*)(\\b|$)");
			Matcher mname=rname.matcher(str);
			mname.find();
			setName(mname.group(1));
			
			Pattern r=Pattern.compile("(\\b\\S+?)=\"(\\S*?)\"(\\b|\\>)");
			Matcher m=r.matcher(str);
			while(m.find()){
				if(m.groupCount()==2){
					addAttr(m.group(1),m.group(2));
				}else{
					System.out.println("Ϊʲô�������һ�������أ�"+str);
				}
			}
		}
		void setName(String name){
			//System.out.println("����Ϊ��������"+name);
			this.labelName=name;
		}
		void addAttr(String str,String value){//���һ������
			if(attr==null){
				attr=new TreeMap<String,String>();
			}
			attr.put(str,value);
		}
		public String toString(){
			StringBuilder sb=new StringBuilder();
			sb.append(labelName+":{");
			if(attr!=null){
				Iterator<Entry<String, String>> entries = attr.entrySet().iterator();
				while(entries.hasNext()){
				    Entry<String, String> entry = entries.next();
				    String key = entry.getKey();
				    String value = entry.getValue();
				   sb.append(key+":"+value);
				   if(entries.hasNext()){
					   sb.append(",");
				   }
				}
			}
			
			sb.append("}");
			return sb.toString();
		}
	}
	Scanner in=null;
	element root=null;//���ڵ�
	XMLChecker(String xml){
		in=new Scanner(xml);
	}
	XMLChecker(Reader rin){
		in=new Scanner(rin);
	}
	
	label readLabel(){//��ȡһ����ǩ���Զ�����
		//[a-zA-Z0-9]+=\"[a-zA-Z0-9]+\"
		String name=in.findWithinHorizon("(?<=<).+?(?=>)",0);
		System.out.println("������labelΪ"+name);
		if(name==null){
			return null;
		}
		return new label(name);
	}
	public void run(){
		label XMLdeclare=readLabel();
		Stack<element> stk=new Stack<element>();
		label rootL=readLabel();
		root=new element(null);
		root.load(rootL);
		stk.add(root);
		while(!stk.empty()){
			label l=readLabel();
			if(l==null){
				System.out.println(stk.size());
				break;
			}
			if(!l.isEndLabel){
				element ele=new element(stk.peek());
				stk.add(ele);
				ele.load(l);
			}else{
				stk.pop().load(l);;
			}
		}
	}
	public String toString(){
		return root!=null?root.toString():null;
	}
	//���BUG: 1.ÿ����ǩ��ȡʱ�����Ի��У�
	public static void main(String[] args) {
		String str="<?xml version=\"1.0\" ?> "+
"<note>"+
"<to>George</to> "+
"<from>John</from> "+
"<heading>Reminder</heading> "+
"<body>Don't forget the meeting!</body> "+
"</note>";
	
		XMLChecker x=new XMLChecker(str);
		x.run();
		//System.out.println(x.readLabel());
		//System.out.println(x.readLabel());
		//System.out.println(x.readLabel());
		//System.out.println(x.readLabel());
		System.out.println(x);
	}

}
