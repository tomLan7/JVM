package java������Ŀ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Scanner;
public class ָ����ϰ {
	public static void main(String[] args) throws IOException{
		String arr="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		File f=new File("test");
		Scanner in=new Scanner(System.in);
		int n=in.nextInt(),m=in.nextInt();
		StringBuilder sb=null;
		
		if(f.createNewFile()){
			FileWriter fw=new FileWriter(f);
			
			for(int j=0;j<n;j++){
				sb=new StringBuilder();
				for(int i=0;i<m;i++){
					sb.append(arr.charAt((int)(arr.length()*Math.random())));
				}
				sb.append('\n');
				fw.write(sb.toString());
			}
			fw.close();
		}else{
			
		}
		BufferedReader fr=new BufferedReader(new FileReader(f));
		StringBuilder errorChar=new StringBuilder();
		errorChar.append("subject");
		errorChar.append('\t');
		errorChar.append("user");
		errorChar.append("\r\n");
		int CharSum=0;
		int yesSum=0;
		in.nextLine();//�Ѷ���Ļس����ߣ���������Ҫ���س�
		while(true){
			String str=fr.readLine();
			if(str!=null){
				System.out.println("please input:\t"+str);
				
				String userInput=in.nextLine();
				
				for(int i=0;i<str.length()&&i<userInput.length();i++){
						CharSum++;
					if(str.charAt(i)==userInput.charAt(i)){
						yesSum++;
					}else{
						errorChar.append(str.charAt(i));
						errorChar.append('\t');
						errorChar.append(userInput.charAt(i));
						errorChar.append("\r\n");
					}
				}
			}else{
				break;
			}
		}
		FileWriter fw2=new FileWriter("errorInput");
		fw2.write(errorChar.toString());
		fw2.close();
		System.out.println("׼ȷ����:"+(double)yesSum/CharSum*100+"%");
	}	
}
