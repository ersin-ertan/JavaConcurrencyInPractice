package com.nullcognition.javaconcurrencyinpractice.chapter03.practice;// Created by ersin on 09/05/15

public class SafeListener{
	// does not allow the this reference to escape, esp. when in the constructor leaving the object
	// in an incomplete state(if something goes wrong)

	// any new thread or runnable created in the constructor will have ties to the outer class
	private final BroadcastListener listener;
	private int valueFromBroadcast = 0;
	public int getValueFromBroadcast(){return valueFromBroadcast;}

	private SafeListener(){ // note the private constructor
		listener = new BroadcastListener(){
			@Override
			public void onBroadcast(final Broadcast b){
				valueFromBroadcast = b.getShoutCast();
			}
		};
	}

	public static SafeListener newInstance(Broadcaster b){
		SafeListener safe = new SafeListener();
		b.registerListener(safe.listener);
		return safe;
	}
	// to register listeners or start threads, use a factory and a private constructor

}
