package 实例;

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
 * 代表一个字节码对象。可以方便获取字节码的所有成员。
 * 字节码解析器，用于解析字节码文件(.class)，可以读取字节码到内存中，并能正确获得所有的字段。
 * 因为java没有无符号类型，所以u2就用int存,u4就用long存。
 * 字节码文件非常重要的部分是常量池。所有其他部分都会依赖常量池。
 * @author lan7
 * @version 1.0
 * @since JDK 1.8
 * */
//P:\learn\java\学习进度\上一个阶段学习历史\新\数据库\test3.class
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
		 * 存储所有访问修饰符的静态类.
		 * 
		 * {@link java.lang.reflect.Modifier} 只适用于反射，所以这个修饰符只能自己写。
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
			/** 判断修饰符是否包括public
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isPublic(int accflag){
				return (accflag&ACC_PUBLIC)>0;
			}
			/** 判断修饰符是否包括private
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isPrivate(int accflag){
				return (accflag&ACC_PRIVATE)>0;
			}
			/** 判断修饰符是否包括protected
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isProtected(int accflag){
				return (accflag&ACC_PROTECTED)>0;
			}
			/** 判断修饰符是否包括static
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isStatic(int accflag){
				return (accflag&ACC_STATIC)>0;
			}
			/** 判断修饰符是否包括final
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isFinal(int accflag){
				return (accflag&ACC_FINAL)>0;
			}
			/** 判断修饰符是否包括super，JDK1.0.2之后编译出来的类的这个标志默认为真
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isSuper(int accflag){
				return (accflag&ACC_SUPER)>0;
			}
			/** 判断字段的修饰符是修饰了synchronized
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isSynchronized(int accflag){
				return (accflag&ACC_SYNCHRONIZED)>0;
			}
			/** 判断字段的修饰符是修饰了volatile
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isVolatile(int accflag){
				return (accflag&ACC_VOLATILE)>0;
			}
			/** 判断字段的修饰符是修饰了bridge，泛型继承时的桥接方法。
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isBridge(int accflag){
				return (accflag&ACC_BRIDGE)>0;
			}
			/** 判断字段的修饰符是修饰了transient，在序列化时会被忽略。
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isTransient(int accflag){
				return (accflag&ACC_TRANSIENT)>0;
			}
			/** 判断字段的修饰符是修饰了varargs， 可边长参数。
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isVarargs(int accflag){
				return (accflag&ACC_VARARGS)>0;
			}
			/** 判断字段的修饰符是修饰了native，本地方法。
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isNative(int accflag){
				return (accflag&ACC_NATIVE)>0;
			}
			/** 判断字段的修饰符是修饰了interface，是一个接口。
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isInterface(int accflag){
				return (accflag&ACC_INTERFACE)>0;
			}
			/** 判断字段的修饰符是修饰了abstract，是一个抽象类或方法。
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isAbstract(int accflag){
				return (accflag&ACC_ABSTRACT)>0;
			}
			/** 判断字段的修饰符是修饰了strictfp .编译器自动生成的方法
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isStrictfp(int accflag){
				return (accflag&ACC_STRICTFP)>0;
			}
			/** 判断字段的修饰符是修饰了synthetic，
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isSynthetic(int accflag){
				return (accflag&ACC_SYNTHETIC)>0;
			}
			/** 判断字段的修饰符是修饰了annotation , 拥有注解
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isAnnotation(int accflag){
				return (accflag&ACC_ANNOTATION)>0;
			}
			/** 判断字段的修饰符是修饰了emun，是枚举
			 * @param accflag 修饰符
			 * @return 是否包含该修饰
			 * */
			public static boolean isEnum(int accflag){
				return (accflag&ACC_ENUM)>0;
			}
			/**
			 * 传入一个方法的修饰符，获得其中的修饰符。
			 * @param flags 方法的修饰符
			 * @return 一个String数组，存储了所有修饰符的字符串表示。 
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
			 * 传入一个字段的修饰符，获得其中的修饰符。
			 * @param flags 字段的修饰符
			 * @return 一个String数组，存储了所有修饰符的字符串表示。 
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
			 * 传入一个类的修饰符，获得其中的修饰符。
			 * @param flags 类的修饰符
			 * @return 一个String数组，存储了所有修饰符的字符串表示。 
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
	 * 存储魔数
	 * */
		public long magic;
		public int minor_version;
		public int major_version;
		/**
		 * 常量池计数器
		 * */
		
		public int constant_pool_count;
		/**
		 * 获得该字节码中 常量池的某个常量的主要内容。
		 * */
		String getOtherConstantMainContent(int index){
			return constant_pool.allC[index].getMainContent();
		}
		/**
		 * 常量池
		 * 每个常量池都知道自己在哪个字节码对象中，所以是内部类。
		 * */
		/**
		 * @author lan7
		 *
		 */
		
		public class cp_info{
			/**
			 * 代表一个常量，所有常量的共同父类。
			 * 每个常量都知道自己在哪个常量池对象中，所以是内部类。
			 * */
			abstract class Constant{
				short tag;//当前常量类型
				Constant(int tag){
					this.tag=(short)tag;   
				}
				/**
				 * 每个常量的读取方法不同，多态表示
				 * */
				abstract void initFromDataInputStream(DataInputStream in)throws IOException;
				/**
				 * 获得常量的字符串表示，常量类型和内容
				 * */
				public String toString(){
					return "["+getTypeName()+"@"+getContent()+"]";
				}
				/** 
				 * 获得常量类型，常量类型存于常量表中
				 * */
				abstract String getTypeName();
				/**
				 * 获得常量的所有内容，用于遍历时输出该常量
				 * */
				abstract String getContent();
				/**
				 * 获得常量的主要内容，用于本常量 在其他常量中引用时 直接输出
				 * */
				abstract String getMainContent();
				
			}
			//工厂方法，用于根据tag获得一个对象
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
			//存储所有常量，长度为初始化就传入的 
			Constant[] allC;
			cp_info(int numb){//构造函数传常量数
				allC=new Constant[numb];
			}
			public Constant getConstant(int index){
				return allC[index]; 
			}
			public String toString(){
				String tem="常量池元素个数"+allC.length;
				StringBuilder sb=new StringBuilder(tem);
				for(int i=0;i<allC.length;i++){
					sb.append(i+"\t"+allC[i]+"\n");
				}
				return sb.toString();
			}
			void initFromDataInstream(DataInputStream in) throws IOException{
				allC[0]=new CONSTANT_Null(0);//0号占位常量，是否应该用空指针设计模式？
				for(int i=1;i<Bytecode.this.constant_pool_count;i++){
					//try{
						allC[i]=readConstant(in);
					//}catch(NullPointerException n){
					//	System.out.println("第"+i+"个常量读取失败");
					//}
				}
			}
			/**
			 * 空对象模式的使用，常量池第0号元素
			 * @author lan7
			 *
			 */
			class CONSTANT_Null extends Constant{
				CONSTANT_Null(int tag) {
					super(tag);
					// TODO 自动生成的构造函数存根
				}
				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException{
				}

				@Override
				String getTypeName() {
					// TODO 自动生成的方法存根
					return "占位常量";
				}

				@Override
				String getContent() {
					return "";
				}
				@Override
				String getMainContent() {
					// TODO 自动生成的方法存根
					return "";
				}
			}
			/**
			 * 方法引用
			 * @author lan7
			 *
			 */
			class CONSTANT_Methodref_info extends Constant{
				int indexClassInfo;
				int indexNameAndType;
				CONSTANT_Methodref_info(int tag){
					super(tag);
					// TODO 自动生成的构造函数存根
					}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException{
					indexClassInfo=in.readUnsignedShort();
					indexNameAndType=in.readUnsignedShort();
				}

				@Override
				String getTypeName() {
					// TODO 自动生成的方法存根
					return "方法引用";
				}

				@Override
				String getContent() {
					// TODO 自动生成的方法存根
					return "类索引:"+indexClassInfo+"("+getOtherConstantMainContent(indexClassInfo)+"),名字特征索引:"+indexNameAndType+"("+getOtherConstantMainContent(indexNameAndType)+")";
				}

				@Override
				String getMainContent() {
					// TODO 自动生成的方法存根
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
					// TODO 自动生成的构造函数存根
				}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException {
					// TODO 自动生成的方法存根
					indexClassInfo=in.readUnsignedShort();
					indexNameAndType=in.readUnsignedShort();
				}

				@Override
				String getTypeName() {
					// TODO 自动生成的方法存根
					return "字段引用";
				}

				@Override
				String getContent() {
					// TODO 自动生成的方法存根
					return "类索引:"+indexClassInfo+"("+Bytecode.this.getOtherConstantMainContent(indexClassInfo)+"),名字特征索引:"+indexNameAndType+"("+getOtherConstantMainContent(indexNameAndType)+")";
				}

				@Override
				String getMainContent() {
					// TODO 自动生成的方法存根
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
					// TODO 自动生成的构造函数存根
				}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException {
					// TODO 自动生成的方法存根
					indexClassName=in.readUnsignedShort();
				}

				@Override
				String getTypeName() {
					// TODO 自动生成的方法存根
					return "类";
				}

				@Override
				String getContent() {
					// TODO 自动生成的方法存根
					return "类名索引:"+indexClassName+"("+getOtherConstantMainContent(indexClassName)+")";
				}

				@Override
				String getMainContent() {
					// TODO 自动生成的方法存根
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
					// TODO 自动生成的构造函数存根
				}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException {
					// TODO 自动生成的方法存根
					str=in.readUTF();
				}

				@Override
				String getTypeName() {
					// TODO 自动生成的方法存根
					return "utf-8字符串";
				}

				@Override
				String getContent() {
					// TODO 自动生成的方法存根
					return "字符串内容："+"\""+str+"\"";
				}

				@Override
				String getMainContent() {
					// TODO 自动生成的方法存根
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
					// TODO 自动生成的构造函数存根
				}

				@Override
				void initFromDataInputStream(DataInputStream in) throws IOException {
					// TODO 自动生成的方法存根
					indexName=in.readUnsignedShort();
					indexCharact=in.readUnsignedShort();
					
				}

				@Override
				String getTypeName() {
					// TODO 自动生成的方法存根
					return "名称和特征";
				}

				@Override
				String getContent() {
					// TODO 自动生成的方法存根
					return "名字索引:"+indexName+"("+getOtherConstantMainContent(indexName)+"),特征索引:"+indexCharact+"("+getOtherConstantMainContent(indexCharact)+")";
				}

				@Override
				String getMainContent() {
					// TODO 自动生成的方法存根
					return getOtherConstantMainContent(indexName)+"&"+getOtherConstantMainContent(indexCharact);
				}
			}
		} ;
		
		/**常量池
		 */
		public cp_info constant_pool;
		/**
		 * 访问标志
		 * */
		public int access_flag;
		public int this_class;
		public int super_class;
		public int interfaces_count;
		/**
		 * 存储所有的接口索引
		 * */
		public int interfaces[];
		public int fields_count;
		public field_info fields[];
		public int methods_count;
		public method_info methods[];
		public int attributes_count;
		attribute_info attributes[];
		/**
		 * 字段和方法的抽象父类，封装了两者相同的代码。
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
				// TODO 自动生成的方法存根
				return false;
			}

			@Override
			boolean isMethod() {
				// TODO 自动生成的方法存根
				return true;
			}
			
		}
		class field_info extends member_info{

			@Override
			boolean isField() {
				// TODO 自动生成的方法存根
				return true;
			}

			@Override
			boolean isMethod() {
				// TODO 自动生成的方法存根
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
		 * 只需要将文件名输入其中即可打开读取字节码。
		 *
		 * @param fileName 打开的字节码文件名的路径
		 * @throws FileNotFoundException  当文件路径不存在时，抛出该异常。
		 */
		Bytecode(String fileName) throws FileNotFoundException{
			if(!(file=new File(fileName)).exists()){
				throw new FileNotFoundException();
			}
		}
		public void run(){//可以在新线程中解析类
			try (DataInputStream di=new DataInputStream(new BufferedInputStream(new FileInputStream(file)));){
				magic=(di.readInt()&0x0000ffffffffL);
				minor_version=di.readUnsignedShort();
				major_version=di.readUnsignedShort();
				constant_pool_count=di.readUnsignedShort();
				constant_pool=new cp_info(constant_pool_count);
				constant_pool.initFromDataInstream(di);
				access_flag=di.readUnsignedShort();//访问标志
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
					System.out.println("刚好读取结束，完美的字节码读取");
				}
				
				} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				System.out.println("打开文件失败，文件找不到");
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
				System.out.println("读取class文件失败。");
			}
		}
		public String toString(){
			return "魔数:"+this.getMagicNumbHexAsString()+"\n"
					+"版本:"+major_version+"."+minor_version+"\t对应JDK版本:"+classVersionJDK.get(major_version+"."+minor_version)+'\n'
					+"总常量数:"+this.constant_pool_count+":"
					+constant_pool+"\n"
					+"类修饰符:"+Arrays.toString(Access_flags.toStringAsClass(access_flag))+"\n"
					+"当前类索引:"+this_class+"("+Bytecode.this.getOtherConstantMainContent(this_class)+")\n"
					+"父类索引:"+super_class+"("+Bytecode.this.getOtherConstantMainContent(super_class)+")\n"
					+"接口数:"+interfaces_count+","+Arrays.toString(this.interfaces)+"\n"
					+"字段数:"+fields_count+"\n"
			;
			
		}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		String filename=null;
		while(true){
			if(args.length==0){
				Scanner in=new Scanner(System.in);
				System.out.println("输入class文件名：");
				filename=in.next();
				try {
					Bytecode cb=new Bytecode(filename);
					cb.run();
					System.out.println(cb);
				} catch (FileNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					System.out.println("文件找不到");
					args=new String[0];
				}
				
			}
		}
		
	}

}


