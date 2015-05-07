package com.nullcognition.javaconcurrencyinpractice.chapter03;// Created by ersin on 06/05/15

import java.util.concurrent.atomic.AtomicLong;

public class AtomicAndSync{

	// intrinsic locks are reentrant, allowing a thread that tries to acquire a lock it already holds to suceed, solving its deadlock issue
	// with extended classes and its superclass that has a synchronized method

	// every shared mutable variable should be guarded by exactly one lock

	AtomicLong atomicLong = new AtomicLong(1L);
	private Object expensiveObject = null;
	private int value = 0;

	// use thread safe objects to manage state, easier to maintain and verify
	public long getAtomicLong(){return atomicLong.incrementAndGet();}

	// lazy initialization with race conditions, both threads may read ex obj to be null, receiving 2 different objects
	public Object getExpensiveObject(){

		if(expensiveObject == null){ expensiveObject = new Object(); }
		return expensiveObject;
	}

	public synchronized int getNextSynched(){return value++;} // thread safe now only one thread may enter the method; updating the value

	// due to timing two threads may call method at same time and get the same value
	public int getNextUnSafeSequence(){ return value++;}

}
