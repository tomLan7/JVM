package javaÑ§Ï°;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class ¶¨Ê±Æ÷{
	public static void main(String args[]){
		Timer t=new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
			System.out.println(this.scheduledExecutionTime());
			}
		},5,500);
		t.schedule(new TimerTask(){
			@Override
			public void run() {
			System.out.println(this.scheduledExecutionTime());
			}
		},5,500);
	}
	
	
}
