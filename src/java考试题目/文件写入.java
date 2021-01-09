package java考试题目;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 文件写入 {

	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根
			
				String pathP="B:/";
				BufferedReader f=new BufferedReader(new InputStreamReader(new FileInputStream("B:/student.txt"),"UTF-8"));
				while(true){
					String S=f.readLine();
					if(S!=null){
						String arr[]=S.split("@");
						File temF=new File(pathP,arr[0]+" 学号 "+arr[1]);
						temF.mkdir();
					}else{
						break;
					}
				}
	}

}
