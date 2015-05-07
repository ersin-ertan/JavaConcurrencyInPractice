package com.nullcognition.javaconcurrencyinpractice.chapter03;// Created by ersin on 06/05/15

// associate a per-thread value with a value-holding object being the thread local

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalEx implements Runnable{

	// hidden to only inside this class, but each instance increments it
	static private AtomicInteger atomicInteger = new AtomicInteger(0);

	// this runnable does not give the thread access to the public atomic int, but can be via runnable
	static public AtomicInteger atomicIntegerPublic = new AtomicInteger(0);

	ThreadLocal<Integer> integerThreadLocalUnInit = new ThreadLocal<>();

	ThreadLocal<Integer> integerThreadLocalInit = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue(){
			return 1;
		}
	};

	static public ThreadLocal<Integer> integerThreadLocalInitStaticPublic = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue(){
			return 10;
		}
	};

	static private ThreadLocal<Integer> integerThreadLocalInitStaticPrivate = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue(){
			return 100;
		}
	};
	// requires get method
	public static Integer getITLISP(){ return integerThreadLocalInitStaticPrivate.get();}



	@Override
	public void run(){
		// static variables values persist when in the runnable in multiple threads, each thread gets a +1 value
		Log.e("logErr", "atomic int " + atomicInteger.getAndIncrement() + " from thread " + Thread.currentThread().getName());

		Log.e("logErr", "atomic int public " + atomicIntegerPublic.getAndIncrement() + " from thread " + Thread.currentThread().getName());

		Log.e("logErr", "integer thread local UnInitialized " + integerThreadLocalUnInit.get());

		Log.e("logErr", "integer thread local Initialized " + integerThreadLocalInit.get());

		Log.e("logErr", "integer thread local Initialized static " + getITLISP());

		Log.e("logErr", "It is known that only the atomic int incr due ot the get and Inc" +
				"\nNow to test the same runnable but in another thread");

		Log.e("logErr", "----------");


	}
}
