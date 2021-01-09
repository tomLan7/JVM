package ʵ��;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.lang.reflect.Modifier;
/**
 * ����һ���ֽ�����󡣿��Է����ȡ�ֽ�������г�Ա��
 * �ֽ�������������ڽ����ֽ����ļ�(.class)�����Զ�ȡ�ֽ��뵽�ڴ��У�������ȷ������е��ֶΡ�
 * ��Ϊjavaû���޷������ͣ�����u2����int��,u4����long�档
 * �ֽ����ļ��ǳ���Ҫ�Ĳ����ǳ����ء������������ֶ������������ء�
 * @author lan7
 * @version 1.0
 * @since JDK 1.8
 * */
//P:\learn\java\ѧϰ����\��һ���׶�ѧϰ��ʷ\��\���ݿ�\test3.class
public class Bytecode implements Runnable{
	private File file;
	private final static Map<String,String> classVersionJDK =new HashMap<String,String>(){{
		put("53.0","Java SE 9");
		put("52.0","Java SE 8");	
		put("51.0","Java SE 7");	
		put("50.0","Java SE 6.0");	
		put("49.0","Java SE 5.0");		
		put("48.0","JDK 1.4");				
		put("47.0","JDK 1.3");				
		put("46.0","JDK 1.2");				
		put("45.0","JDK 1.1");			
	}};
		/**
		 * 
		 * �洢���з������η��ľ�̬��.
		 * 
		 * {@link java.lang.reflect.Modifier} ֻ�����ڷ��䣬����������η�ֻ���Լ�д��
		 * 
		 * @see java.lang.reflect.Modifier
		 * @author lan7
		 */
	public static class Access_flags{
			public static int ACC_PUBLIC=0x0001;
			public static int ACC_PRIVATE=0x0002;
			public static int ACC_PROTECTED=0x0004;
			public static int ACC_STATIC=0x0008;
			public static int ACC_FINAL=0x0010;
			public static int ACC_SUPER=0x0020;
			
			public static int ACC_SYNCHRONIZED=0x0020;
			public static int ACC_VOLATILE=0x0040;
			public static int ACC_BRIDGE=0x0040;
			public static int ACC_TRANSIENT=0x0080;
			public static int ACC_VARARGS=0x0080;
			public static int ACC_NATIVE=0x0100;
			public static int ACC_INTERFACE=0x0200;
			public static int ACC_ABSTRACT=0x0400;
			public static int ACC_STRICTFP=0x0800;
			public static int ACC_SYNTHETIC=0x1000;
			public static int ACC_ANNOTATION=0x2000;
			public static int ACC_ENUM=0x4000;
			/** �ж����η��Ƿ����public
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isPublic(int accflag){
				return (accflag&ACC_PUBLIC)>0;
			}
			/** �ж����η��Ƿ����private
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isPrivate(int accflag){
				return (accflag&ACC_PRIVATE)>0;
			}
			/** �ж����η��Ƿ����protected
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isProtected(int accflag){
				return (accflag&ACC_PROTECTED)>0;
			}
			/** �ж����η��Ƿ����static
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isStatic(int accflag){
				return (accflag&ACC_STATIC)>0;
			}
			/** �ж����η��Ƿ����final
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isFinal(int accflag){
				return (accflag&ACC_FINAL)>0;
			}
			/** �ж����η��Ƿ����super��JDK1.0.2֮������������������־Ĭ��Ϊ��
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isSuper(int accflag){
				return (accflag&ACC_SUPER)>0;
			}
			/** �ж��ֶε����η���������synchronized
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isSynchronized(int accflag){
				return (accflag&ACC_SYNCHRONIZED)>0;
			}
			/** �ж��ֶε����η���������volatile
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isVolatile(int accflag){
				return (accflag&ACC_VOLATILE)>0;
			}
			/** �ж��ֶε����η���������bridge�����ͼ̳�ʱ���Žӷ�����
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isBridge(int accflag){
				return (accflag&ACC_BRIDGE)>0;
			}
			/** �ж��ֶε����η���������transient�������л�ʱ�ᱻ���ԡ�
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isTransient(int accflag){
				return (accflag&ACC_TRANSIENT)>0;
			}
			/** �ж��ֶε����η���������varargs�� �ɱ߳�������
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isVarargs(int accflag){
				return (accflag&ACC_VARARGS)>0;
			}
			/** �ж��ֶε����η���������native�����ط�����
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isNative(int accflag){
				return (accflag&ACC_NATIVE)>0;
			}
			/** �ж��ֶε����η���������interface����һ���ӿڡ�
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isInterface(int accflag){
				return (accflag&ACC_INTERFACE)>0;
			}
			/** �ж��ֶε����η���������abstract����һ��������򷽷���
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isAbstract(int accflag){
				return (accflag&ACC_ABSTRACT)>0;
			}
			/** �ж��ֶε����η���������strictfp .�������Զ����ɵķ���
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isStrictfp(int accflag){
				return (accflag&ACC_STRICTFP)>0;
			}
			/** �ж��ֶε����η���������synthetic��
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isSynthetic(int accflag){
				return (accflag&ACC_SYNTHETIC)>0;
			}
			/** �ж��ֶε����η���������annotation , ӵ��ע��
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isAnnotation(int accflag){
				return (accflag&ACC_ANNOTATION)>0;
			}
			/** �ж��ֶε����η���������emun����ö��
			 * @param accflag ���η�
			 * @return �Ƿ����������
			 * */
			public static boolean isEnum(int accflag){
				return (accflag&ACC_ENUM)>0;
			}
			/**
			 * ����һ�����������η���������е����η���
			 * @param flags ���������η�
			 * @return һ��String���飬�洢���������η����ַ�����ʾ�� 
			 */
			public static String[] toStringAsMethod(int flags){
				ArrayList<String> arr=new ArrayList<String>();
				if(isPublic(flags)) arr.add("public");
				if(isPrivate(flags)) arr.add("private");
				if(isProtected(flags)) arr.add("protected");
				if(isStatic(flags)) arr.add("static");
				if(isFinal(flags)) arr.add("final");
				if(isBridge(flags)) arr.add("bridge");
				if(isVarargs(flags)) arr.add("varargs");
				
				if(isNative(flags)) arr.add("native");
				if(isAbstract(flags)) arr.add("abstract");
				if(isStrictfp(flags)) arr.add("strictfp");
				if(isSynthetic(flags)) arr.add("synchetic");
				
				String rtn[]=new String[arr.size()];
				arr.toArray(rtn);
				return rtn;
			}
			/**
			 * ����һ���ֶε����η���������е����η���
			 * @param flags �ֶε����η�
			 * @return һ��String���飬�洢���������η����ַ�����ʾ�� 
			 */
			public static String[] toStringAsField(int flags){
				ArrayList<String> arr=new ArrayList<String>();
				if(isPublic(flags)) arr.add("public");
				if(isPrivate(flags)) arr.add("private");
				if(isProtected(flags)) arr.add("protected");
				if(isStatic(flags)) arr.add("static");
				if(isFinal(flags)) arr.add("final");
				if(isVolatile(flags)) arr.add("volatile");
				if(isTransient(flags)) arr.add("transient");
				if(isSynthetic(flags)) arr.add("synchetic");
				if(isEnum(flags)) arr.add("enum");
				
				String rtn[]=new String[arr.size()];
				arr.toArray(rtn);
				return rtn;
			}
			/**
			 * ����һ��������η���������е����η���
			 * @param flags ������η�
			 * @return һ��String���飬�洢���������η����ַ�����ʾ�� 
			 */
			public static String[] toStringAsClass(int flags){
				ArrayList<String> arr=new ArrayList<String>();
				if(isPublic(flags)) arr.add("public");
				if(isFinal(flags)) arr.add("final");
				if(isSuper(flags)) arr.add("super");
				if(isInterface(flags)) arr.add("interface");
				if(isAbstract(flags)) arr.add("abstract");
				if(isSynthetic(flags)) arr.add("synchetic");
				if(isAnnotation(flags)) arr.add("annotation");
				if(isEnum(flags)) arr.add("enum");
				
				String rtn[]=new String[arr.size()];
				arr.toArray(rtn);
				return rtn;
				
			}
			private Access_flags(){}
		}
		
	/**
	 * �洢ħ��
	 * */
		public long magic;
		public int minor_version;
		public int major_version;
		/**
		 * �����ؼ�����
		 * */
		
		public int constant_pool_count;
		/**
		 * ��ø��ֽ����� �����ص�ĳ����������Ҫ���ݡ�
		 * */
		String getOtherConstantMainContent(int index){
			return constant_pool.allC[index].getMainContent();
		}
		/**
		 * ������
		 * ÿ�������ض�֪���Լ����ĸ��ֽ�������У��������ڲ��ࡣ
		 * */
		/**
		 * @author lan7
		 *
		 */
		
		public class cp_info{
			/**
			 * ����һ�����������г����Ĺ�ͬ���ࡣ
			 * ÿ��������֪���Լ����ĸ������ض����У��������ڲ��ࡣ
			 * */
			abstract class Constant{
				short tag;//��ǰ��������
				Constant(int tag){
					this.tag=(short)tag;   
				}
				/**
				 * ÿ�������Ķ�ȡ������ͬ����̬��ʾ
				 * */
				abstract void initFromDataInputStream(DataInputStream in)throws IOException;
				/**
				 * ��ó������ַ�����ʾ���������ͺ�����
				 * */
				public String toString(){
					return "["+getTypeName()+"@"+getContent()+"]";
				}
				/** 
				 * ��ó������ͣ��������ʹ��ڳ�������
				 * */
				abstract String getTypeName();
				/**
				 * ��ó������������ݣ����ڱ���ʱ����ó���
				 * */
				abstract String getContent();
				/**
				 * ��ó�������Ҫ���ݣ����ڱ����� ����������������ʱ ֱ�����
				 * */
				abstract String getMainContent();
				
			}
			//�������������ڸ���tag���һ������
			Constant createConstant(int tag){
				Constant ct=null;
				switch(tag){
					case 1:
					{
						ct=new CONSTANT_utf8_info(tag);
					}
						break;
					case 9:
					{
						ct=new CONSTANT_Fieldref_info(tag);
					}
						break;
					case 10:
					{
						ct=new CONSTANT_Methodref_info(tag);
					}
						break;
					case 7:
					{
						ct=new CONSTANT_Class_info(tag);
						break;
					}
					case 12:
					{
						ct=new CONSTANT_NameAndType_info(tag);
						break;
					}
				}
				return ct;
			}
			Constant readConstant(DataInputStream in) throws IOException{
				int tag=in.readUnsignedByte();
				Constant ct=createConstant(tag);
				ct.initFromDataInputStream(in);
				return ct;
			}
			//�洢���г���������Ϊ��ʼ���ʹ���� 
			Constant[] allC;
			cp_info(int numb){//���캯����������
				allC=new Constant[numb];
			}
			public Constant getConstant(int index){
				return allC[index]; 
			}
			public String toString(){
				String tem="������Ԫ�ظ���"+allC.length;
				StringBuilder sb=new StringBuilder(tem);
				for(int i=0;i<allC.length;i++){
					sb.append(i+"\t"+allC[i]+"\n");
				}
				return sb.toString();
			}
			void initFromDataInstream(DataInputStream in) throws IOException{
				allC[0]=new CONSTANT_Null(0);//0��ռλ�������Ƿ�Ӧ���ÿ�ָ�����ģʽ��
				for(int i=1;i<Bytecode.this.constant_pool_count;i++){
					//try{
						allC[i]=readConstant(in);
					//}catch(NullPointerException n){
					//	System.out.println("��"+i+"��������ȡʧ��");
					//}
				}
			}
			/**
			 * �ն���ģʽ��ʹ�ã������ص�0��Ԫ��
			 * @author lan7
			 *
			 */
			class CONSTANT_Null extends Constant{
				CONSTANT_Null(int tag) {
					super(tag);
					// TODO �Զ����ɵĹ��캯�����
				}
				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException{
				}

				@Override
				String getTypeName() {
					// TODO �Զ����ɵķ������
					return "ռλ����";
				}

				@Override
				String getContent() {
					return "";
				}
				@Override
				String getMainContent() {
					// TODO �Զ����ɵķ������
					return "";
				}
			}
			/**
			 * ��������
			 * @author lan7
			 *
			 */
			class CONSTANT_Methodref_info extends Constant{
				int indexClassInfo;
				int indexNameAndType;
				CONSTANT_Methodref_info(int tag){
					super(tag);
					// TODO �Զ����ɵĹ��캯�����
					}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException{
					indexClassInfo=in.readUnsignedShort();
					indexNameAndType=in.readUnsignedShort();
				}

				@Override
				String getTypeName() {
					// TODO �Զ����ɵķ������
					return "��������";
				}

				@Override
				String getContent() {
					// TODO �Զ����ɵķ������
					return "������:"+indexClassInfo+"("+getOtherConstantMainContent(indexClassInfo)+"),������������:"+indexNameAndType+"("+getOtherConstantMainContent(indexNameAndType)+")";
				}

				@Override
				String getMainContent() {
					// TODO �Զ����ɵķ������
					return getOtherConstantMainContent(indexClassInfo)+"."+getOtherConstantMainContent(indexNameAndType);
				}
			}
			/**
			 * @author lan7
			 *
			 */
			class CONSTANT_Fieldref_info extends Constant{
				int indexClassInfo;
				int indexNameAndType;
				CONSTANT_Fieldref_info(int tag) {
					super(tag);
					// TODO �Զ����ɵĹ��캯�����
				}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException {
					// TODO �Զ����ɵķ������
					indexClassInfo=in.readUnsignedShort();
					indexNameAndType=in.readUnsignedShort();
				}

				@Override
				String getTypeName() {
					// TODO �Զ����ɵķ������
					return "�ֶ�����";
				}

				@Override
				String getContent() {
					// TODO �Զ����ɵķ������
					return "������:"+indexClassInfo+"("+Bytecode.this.getOtherConstantMainContent(indexClassInfo)+"),������������:"+indexNameAndType+"("+getOtherConstantMainContent(indexNameAndType)+")";
				}

				@Override
				String getMainContent() {
					// TODO �Զ����ɵķ������
					return getOtherConstantMainContent(indexClassInfo)+"."+getOtherConstantMainContent(indexNameAndType);
				}
			}
			/**
			 * @author lan7
			 *
			 */
			class CONSTANT_Class_info extends Constant{
				int indexClassName;
				CONSTANT_Class_info(int tag) {
					super(tag);
					// TODO �Զ����ɵĹ��캯�����
				}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException {
					// TODO �Զ����ɵķ������
					indexClassName=in.readUnsignedShort();
				}

				@Override
				String getTypeName() {
					// TODO �Զ����ɵķ������
					return "��";
				}

				@Override
				String getContent() {
					// TODO �Զ����ɵķ������
					return "��������:"+indexClassName+"("+getOtherConstantMainContent(indexClassName)+")";
				}

				@Override
				String getMainContent() {
					// TODO �Զ����ɵķ������
					return getOtherConstantMainContent(indexClassName);
				}
			}
			/**
			 * @author lan7
			 *
			 */
			class CONSTANT_utf8_info extends Constant{
				String str;
				CONSTANT_utf8_info(int tag) {
					super(tag);
					// TODO �Զ����ɵĹ��캯�����
				}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException {
					// TODO �Զ����ɵķ������
					str=in.readUTF();
				}

				@Override
				String getTypeName() {
					// TODO �Զ����ɵķ������
					return "utf-8�ַ���";
				}

				@Override
				String getContent() {
					// TODO �Զ����ɵķ������
					return "�ַ������ݣ�"+"\""+str+"\"";
				}

				@Override
				String getMainContent() {
					// TODO �Զ����ɵķ������
					return str;
				}
			}
			/**
			 * @author lan7
			 *
			 */
			class CONSTANT_NameAndType_info extends Constant{
				int indexName;
				int indexCharact;
				CONSTANT_NameAndType_info(int tag) {
					super(tag);
					// TODO �Զ����ɵĹ��캯�����
				}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException {
					// TODO �Զ����ɵķ������
					indexName=in.readUnsignedShort();
					indexCharact=in.readUnsignedShort();
					
				}

				@Override
				String getTypeName() {
					// TODO �Զ����ɵķ������
					return "���ƺ�����";
				}

				@Override
				String getContent() {
					// TODO �Զ����ɵķ������
					return "��������:"+indexName+"("+getOtherConstantMainContent(indexName)+"),��������:"+indexCharact+"("+getOtherConstantMainContent(indexCharact)+")";
				}

				@Override
				String getMainContent() {
					// TODO �Զ����ɵķ������
					return getOtherConstantMainContent(indexName)+"&"+getOtherConstantMainContent(indexCharact);
				}
			}
		} ;
		
		/**������
		 */
		public cp_info constant_pool;
		/**
		 * ���ʱ�־
		 * */
		public int access_flag;
		public int this_class;
		public int super_class;
		public int interfaces_count;
		/**
		 * �洢���еĽӿ�����
		 * */
		public int interfaces[];
		public int fields_count;
		public field_info fields[];
		public int methods_count;
		public method_info methods[];
		public int attributes_count;
		attribute_info attributes[];
		/**
		 * �ֶκͷ����ĳ����࣬��װ��������ͬ�Ĵ��롣
		 * */
		abstract class member_info{
			int access_flags;
			int name_index;
			int descriptor_index;
			int attributes_count;
			attribute_info attributes[];
			
			void initFromDataInputStream(DataInputStream is) throws IOException{
				access_flags=is.readUnsignedShort();
				name_index=is.readUnsignedShort();
				descriptor_index=is.readUnsignedShort();
				attributes_count=is.readUnsignedShort();
				attributes=new attribute_info[attributes_count];
				for(int i=0;i<attributes_count;i++){
					attributes[i]=createAttribute_info(is);
					attributes[i].initFromDataInputStream(is);
				}
			}
			
			abstract boolean isField();
			abstract boolean isMethod();
		}
		class method_info extends member_info{

			@Override
			boolean isField() {
				// TODO �Զ����ɵķ������
				return false;
			}

			@Override
			boolean isMethod() {
				// TODO �Զ����ɵķ������
				return true;
			}
			
		}
		class field_info extends member_info{

			@Override
			boolean isField() {
				// TODO �Զ����ɵķ������
				return true;
			}

			@Override
			boolean isMethod() {
				// TODO �Զ����ɵķ������
				return false;
			}
		}
		attribute_info createAttribute_info(DataInputStream di) throws IOException{
			int attribute_name_index=di.readUnsignedShort();
			String arrtibute_name=Bytecode.this.getOtherConstantMainContent(attribute_name_index);
			return new attribute_info(attribute_name_index);
		}
		class attribute_info{
			int attribute_name_index;
			long attribute_length;
			attribute_info(int ani){
				attribute_name_index=ani;
			}
			
			final void initFromDataInputStream(DataInputStream di) throws IOException{
				attribute_length=(di.readInt()&0x0ffffffff);
				initInfoFromDataInputStream(di);
			};
			protected void initInfoFromDataInputStream(DataInputStream di) throws IOException{
				for(int i=0;i<attribute_length;i++){di.read();}
			}
		}
		
		
		public String getMagicNumbHexAsString(){
			return String.format("%8x",magic);
		}
		/** 
		 * ֻ��Ҫ���ļ����������м��ɴ򿪶�ȡ�ֽ��롣
		 *
		 * @param fileName �򿪵��ֽ����ļ�����·��
		 * @throws FileNotFoundException  ���ļ�·��������ʱ���׳����쳣��
		 */
		Bytecode(String fileName) throws FileNotFoundException{
			if(!(file=new File(fileName)).exists()){
				throw new FileNotFoundException();
			}
		}
		public void run(){//���������߳��н�����
			try (DataInputStream di=new DataInputStream(new BufferedInputStream(new FileInputStream(file)));){
				magic=(di.readInt()&0x0000ffffffffL);
				minor_version=di.readUnsignedShort();
				major_version=di.readUnsignedShort();
				constant_pool_count=di.readUnsignedShort();
				constant_pool=new cp_info(constant_pool_count);
				constant_pool.initFromDataInstream(di);
				access_flag=di.readUnsignedShort();//���ʱ�־
				this_class=di.readUnsignedShort();
				super_class=di.readUnsignedShort();
				interfaces_count=di.readUnsignedShort();
				interfaces=new int[interfaces_count];
				for(int i=0;i<interfaces_count;i++){
					interfaces[i]=di.readUnsignedShort();
				}
				fields_count=di.readUnsignedShort();
				fields=new field_info[fields_count];
				for(int i=0;i<fields_count;i++){
					fields[i]=new field_info();
					fields[i].initFromDataInputStream(di);
				}
				methods_count=di.readUnsignedShort();
				methods=new method_info[methods_count];
				for(int i=0;i<methods_count;i++){
					methods[i]=new method_info();
					methods[i].initFromDataInputStream(di);
				}
				attributes_count=di.readUnsignedShort();
				attributes=new attribute_info[attributes_count];
				for(int i=0;i<attributes_count;i++){
					attributes[i]=createAttribute_info(di);
					attributes[i].initFromDataInputStream(di);
				}
				if(di.read()==-1){
					System.out.println("�պö�ȡ�������������ֽ����ȡ");
				}
				
				} catch (FileNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				System.out.println("���ļ�ʧ�ܣ��ļ��Ҳ���");
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
				System.out.println("��ȡclass�ļ�ʧ�ܡ�");
			}
		}
		public String toString(){
			return "ħ��:"+this.getMagicNumbHexAsString()+"\n"
					+"�汾:"+major_version+"."+minor_version+"\t��ӦJDK�汾:"+classVersionJDK.get(major_version+"."+minor_version)+'\n'
					+"�ܳ�����:"+this.constant_pool_count+":"
					+constant_pool+"\n"
					+"�����η�:"+Arrays.toString(Access_flags.toStringAsClass(access_flag))+"\n"
					+"��ǰ������:"+this_class+"("+Bytecode.this.getOtherConstantMainContent(this_class)+")\n"
					+"��������:"+super_class+"("+Bytecode.this.getOtherConstantMainContent(super_class)+")\n"
					+"�ӿ���:"+interfaces_count+","+Arrays.toString(this.interfaces)+"\n"
					+"�ֶ���:"+fields_count+"\n"
			;
			
		}

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		String filename=null;
		while(true){
			if(args.length==0){
				Scanner in=new Scanner(System.in);
				System.out.println("����class�ļ�����");
				filename=in.next();
				try {
					Bytecode cb=new Bytecode(filename);
					cb.run();
					System.out.println(cb);
				} catch (FileNotFoundException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
					System.out.println("�ļ��Ҳ���");
					args=new String[0];
				}
				
			}
		}
		
	}

}


