package javaѧϰ;

import java.util.Scanner;

public class ScannerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="asdas asd <asdasdf asf gas> fsf";
		Scanner in=new Scanner(str);
		String name=in.findWithinHorizon("(?<=<).+?(?=>)",0);
		System.out.println(name);
	}

}
