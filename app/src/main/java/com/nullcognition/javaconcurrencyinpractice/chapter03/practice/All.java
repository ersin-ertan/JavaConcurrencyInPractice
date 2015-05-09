package com.nullcognition.javaconcurrencyinpractice.chapter03.practice;// Created by ersin on 09/05/15

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class All{


	public static class SafeListenerWrapper{

		private final Listener safeL;

		private SafeListenerWrapper(){
			safeL = new Listener(){
				@Override
				public void onEvent(final SomeEvent someEvent){
					// if we just new Thread(someEvent).start(); then the reference has escaped
					// can we even do someEven.notify, after it is in a thread?
				}
			};

		}
		public SafeListenerWrapper newInstance(BroadCstr broadCstr){
			SafeListenerWrapper safeL = new SafeListenerWrapper();
			broadCstr.register(safeL.safeL);
			return safeL;
		}
	}

}

class BroadCstr{
	public BroadCstr(){}

	List<Listener> list = new LinkedList<>();
	public void register(Listener safeListener){list.add(safeListener);}

	public void broadcast(){
		for(Listener s : list){
			SomeEvent someEvent = new SomeEvent(3);
			someEvent.isWorking = getRandom();
			s.onEvent(new SomeEvent(3));
		}
	}

	public boolean getRandom(){ return new Random().nextInt() % 2 == 0;}
}


class SomeEvent implements Runnable{
	public SomeEvent(int modifier){this.modifier.set(modifier);}

	public volatile boolean isWorking = true;
	ReentrantLock reentrantLock = new ReentrantLock(true);
	public static AtomicInteger atomicInteger = new AtomicInteger(0);
	ThreadLocal<Integer> modifier = new ThreadLocal<>();

	@Override
	public void run(){
		while(true){
			while(!isWorking){
				reentrantLock.lock();
				try{wait();} catch(InterruptedException e){e.printStackTrace();}
				finally{reentrantLock.unlock();}
			}
			atomicInteger.incrementAndGet();

		}
	}
}

interface Listener{
	void onEvent(SomeEvent someEvent);
}
