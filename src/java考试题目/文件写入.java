package java������Ŀ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class �ļ�д�� {

	public static void main(String[] args) throws IOException {
		// TODO �Զ����ɵķ������
			
				String pathP="B:/";
				BufferedReader f=new BufferedReader(new InputStreamReader(new FileInputStream("B:/student.txt"),"UTF-8"));
				while(true){
					String S=f.readLine();
					if(S!=null){
						String arr[]=S.split("@");
						File temF=new File(pathP,arr[0]+" ѧ�� "+arr[1]);
						temF.mkdir();
					}else{
						break;
					}
				}
	}

}
