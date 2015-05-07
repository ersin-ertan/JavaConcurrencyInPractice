package com.nullcognition.javaconcurrencyinpractice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.nullcognition.javaconcurrencyinpractice.chapter03.ThreadLocalEx;


public class MainActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		threadLocalStaticAndInitialValue();
	}

	private void threadLocalStaticAndInitialValue(){
		ThreadLocalEx threadLocalEx = new ThreadLocalEx();

		Thread t1 = new Thread(threadLocalEx);
		t1.start();
		Thread t2 = new Thread(threadLocalEx);
		t2.start();

		int i = 0;

		Log.e("logErr", "Static public threadlocal init" + ThreadLocalEx.integerThreadLocalInitStaticPublic.get());
		Log.e("logErr", "Static private threadlocal init from get" + ThreadLocalEx.getITLISP());

		ThreadLocalEx threadLocalEx2 = new ThreadLocalEx();

		Log.e("logErr", "Now Using new instance of ThreadLocalEx ext Runnable-----");
		int ii = 0;

		Thread t3 = new Thread(threadLocalEx2);
		t3.start();
		Thread t4 = new Thread(threadLocalEx2);
		t4.start();

		Log.e("logErr", "Static public threadlocal init" + ThreadLocalEx.integerThreadLocalInitStaticPublic.get());
		Log.e("logErr", "Static private threadlocal init from get" + ThreadLocalEx.getITLISP());

		int iii = 0;

		// when debugged in order, the only changing variables are the inc atomic int
		// all other variables behave like normal variables


	}

}
