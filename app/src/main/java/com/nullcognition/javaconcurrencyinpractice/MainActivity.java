package com.nullcognition.javaconcurrencyinpractice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.nullcognition.javaconcurrencyinpractice.chapter03.ThreadLocalEx;
import com.nullcognition.javaconcurrencyinpractice.chapter03.ThreadStates;


public class MainActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		threadLocal();
//		threadSleeps();
	}

	private void threadSleeps(){
		ThreadStates threadStates = new ThreadStates(true);
		ThreadStates threadStates1 = new ThreadStates(false);
		threadStates.start();
		threadStates1.start();
		int i = 0;
	}

	private void threadLocal(){
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
ThreadLocal<Context> context; that it  when a thread(activity)
"used to prevent sharing in designs based on mutable Singletons or global variables"
*/
	}

}
