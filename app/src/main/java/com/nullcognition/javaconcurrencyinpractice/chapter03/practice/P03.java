package com.nullcognition.javaconcurrencyinpractice.chapter03.practice;// Created by ersin on 09/05/15

import android.util.Log;

import com.nullcognition.javaconcurrencyinpractice.chapter03.ThreadLocalEx;
import com.nullcognition.javaconcurrencyinpractice.chapter03.ThreadSync;

public class P03{

	public P03(){}

	// sharing objects policy: thread-confined, shared read-only, shared thread-safe, guarded
	
	/*
 To publish an object safely, both the reference to the object and the object's state must be made visible to other 
threads at the same time. A properly constructed object can be safely published by: 

x Initializing an object reference from a static initializer; think static factory methods
x Storing a reference to it into a volatile field or AtomicReference ; // not specific to the threads cache - look up atomicReference
x Storing a reference to it into a final field of a properly constructed object; or 
x Storing a reference to it into a field that is properly guarded by a lock. */

// use concurrent collections/data structures, Future/Exchhanger, see page 35


	public void safeConstruction(){
		Broadcaster broadcaster = new Broadcaster(6);
		SafeListener safeListener = SafeListener.newInstance(broadcaster);
		broadcaster.broadcast();
		Log.e("logErr", "value from broadcast " + safeListener.getValueFromBroadcast());
		// should be equal to broadcasters value
	}

	public void threadLocal(){
		ThreadLocalEx threadLocalEx = new ThreadLocalEx();

//		threadLocalEx.initOverwriteThreadsWithOneRunnable();
		threadLocalEx.initCleanThreadsWithOneRunnable();
		// resume the program from the debug point to see the full list of logcat logs
		int i = 0;
/* the reason for creating the thread local value private static is that if the
object being created has high overhead such as creating a database or network
connection does, then only one should exist. If that object is not thread safe and is
global, then multithreaded code accessing it needs the thread to have its own cached version
of the variable. Ex. An activity is multi stacked each with its own thread, the single service is
running as part of the parameters passed to each of the activities, the service has a
ThreadLocal<Context> context; that it when a thread(activity)
"used to prevent sharing in designs based on mutable Singletons or global variables"
*/
	}

	public void threadSync(){
		ThreadSync threadSync = new ThreadSync();
		class Producer implements Runnable{

			ThreadSync ts;

			public Producer(ThreadSync t){ts = t;}

			@Override
			public void run(){
				for(;;){
					try{
						ts.add();
					} catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}
		class Consumer implements Runnable{

			ThreadSync ts;

			public Consumer(ThreadSync t){ts = t;}

			@Override
			public void run(){
				for(;;){
					try{
						ts.remove();
					} catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}

		Thread t1 = new Thread(new Producer(threadSync));
		Thread t2 = new Thread(new Consumer(threadSync));
		t1.start();
		t2.start();

	}


}

