package com.nullcognition.javaconcurrencyinpractice.chapter03;// Created by ersin on 07/05/15

import android.util.Log;

public class ThreadSync extends Thread{

	// do example of synchronized and synchronized(this)
	// and final private object is it static? sync(object);

	int i;

	public synchronized int remove() throws InterruptedException{
		while(i == 0){ // when notifyall is called,
			Log.e("logErr", "i == 0 should wait");
			wait(); // instead of busy waiting, while(true) sync() if(1) do
			// waiting state from running
		}
		--i;
		Log.e("logErr", "notifyAll from rem");
		notifyAll(); // must be called from sync block
		return i;
	}

	public synchronized int add() throws InterruptedException{
		while(i == 5){
			Log.e("logErr", "i == 5 should wait");
			wait();
		}
		i++;
		Log.e("logErr", "notifyAll from add");
		notifyAll(); // ex. sync(this) is the lock, notifyall gives up the lock and
		// wakes up all threads, only one can grab the lock, and check their condition
		return i;
	}
}
