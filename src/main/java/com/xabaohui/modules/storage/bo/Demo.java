package com.xabaohui.modules.storage.bo;

import java.sql.Timestamp;
import java.util.Date;


public class Demo {
	protected static Timestamp nowTime=new Timestamp(System.currentTimeMillis());
	
	protected static Date date= new Date();
	public void print(){
		System.out.println(date);
	}
	public static void main(String[] args) {
		new Demo().print();
	}
}
