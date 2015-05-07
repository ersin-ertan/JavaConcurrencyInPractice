package com.nullcognition.javaconcurrencyinpractice.chapter03;
// Created by ersin on 06/05/15

// associate a per-thread value with a value-holding object being the thread local

import android.util.Log;

import java.util.Date;

public class ThreadLocalEx{

	public void initOverwriteThreadsWithOneRunnable(){

		Thread[] threads = new Thread[10];
		Runnable sharedRunnable = new SharedOverwriteRunnable();

		for(int i = 0; i < 10; i++){
			threads[i] = new Thread(sharedRunnable);
			threads[i].start();
			try{
				Thread.sleep(1_000); // every thread is created/started 1 second apart
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public void initCleanThreadsWithOneRunnable(){

		Thread[] threads = new Thread[10];
		Runnable sharedRunnable = new SharedCleanRunnable();

		for(int i = 0; i < 10; i++){
			threads[i] = new Thread(sharedRunnable);
			threads[i].start();
			try{
				Thread.sleep(1_000); // every thread is created/started 1 second apart
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}

class SharedOverwriteRunnable implements Runnable{

	private Date date;

	public SharedOverwriteRunnable(){ }

	@Override
	public void run(){

		date = new Date();
		Log.e("logErr", Thread.currentThread().getName() + " start date:" + date);
		try{
			Thread.sleep(10_000); // each thread posts their start time which should be
			// 1 second apart as per the initialization
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		// after the 10 second wait that each runnable goes through each of the 10 threads should be
		// done being created because 10 threads with a 1 second interval = 10*1 = 10 seconds

		// posting the end date should repeat the value not of the start date, but that of the start
		// of only the last thread, since all other threads do post their start date but are overwitten
		// by the last threads value
		Log.e("logErr", Thread.currentThread().getName() + " end date:" + date);


		// for(;;){}; // infinite spider
	}
}


class SharedCleanRunnable implements Runnable{

	// second type param is required when creating an anon inner class
	private ThreadLocal<Date> date = new ThreadLocal<Date>(){
		@Override
		protected Date initialValue(){
			return new Date();
		}
	};

	public SharedCleanRunnable(){ }

	@Override
	public void run(){

		Log.e("logErr", Thread.currentThread().getName() + " start date:" + date.get());
		try{
			Thread.sleep(10_000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		Log.e("logErr", Thread.currentThread().getName() + " end date:" + date.get());

		// what the date.get() method allows for is keeping the value returned consistent to the
		// thread that initialized/set the variable

		// each thread has its own instance of the variables value, but there is only one variable


	}
}
