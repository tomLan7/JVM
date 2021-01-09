package 实例;

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
		String name=null;//标签名
		Map<String,String> attr=null;//属性
		StringBuilder inner=null;//内部的文本
		List<element> childs=null;
		element parent=null;
		element(element parent){//每一个元素都一定有父元素，根节点除外
			this.parent=parent;
			if(parent!=null){
				parent.addChild(this);
			}
		}
		void load(label l){//装载一个label
			if(!l.isEndLabel){
				System.out.println("装载属性和名字");
				attr=l.attr;
				name=l.labelName;
			}else{
				System.out.println("是结束标签"+l);
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
	static class label{//一个标签，可以提取出信息
		String labelName=null;
		boolean isEndLabel=false;//是否是结束标签，单标签结束，就是结束标签
		boolean isXMLdeclare=false;
		Map<String,String> attr=null;//属性信息
		label(String str){//接收一个不包括<和>的内容，转化为对象
			str=str.trim();//删除前导空格
			if(str.startsWith("?xml")){
				isXMLdeclare=true;
			}
			if(str.endsWith("?")||str.startsWith("/")){
				System.out.println(str+"是结束标签");
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
					System.out.println("为什么会遍历到一条属性呢？"+str);
				}
			}
		}
		void setName(String name){
			//System.out.println("设置为的名字是"+name);
			this.labelName=name;
		}
		void addAttr(String str,String value){//添加一个属性
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
	element root=null;//根节点
	XMLChecker(String xml){
		in=new Scanner(xml);
	}
	XMLChecker(Reader rin){
		in=new Scanner(rin);
	}
	
	label readLabel(){//读取一个标签，自动命名
		//[a-zA-Z0-9]+=\"[a-zA-Z0-9]+\"
		String name=in.findWithinHorizon("(?<=<).+?(?=>)",0);
		System.out.println("读到的label为"+name);
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
	//设计BUG: 1.每个标签读取时不可以换行，
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
