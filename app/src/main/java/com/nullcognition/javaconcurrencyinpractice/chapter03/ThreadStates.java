package com.nullcognition.javaconcurrencyinpractice.chapter03;// Created by ersin on 07/05/15

import android.util.Log;

public class ThreadStates extends Thread{

	volatile boolean isReady = false;

	public ThreadStates(boolean is){ isReady = is;}

	@Override
	public void run(){
		super.run();
		while(true){
			waity();
			wakey();
		}
	}

	private void waity(){
		synchronized(this){
			while(!isReady){
				try{
					Log.e("logErr", Thread.currentThread().getName() + " Sleep");
					wait();
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}

	}

	private void wakey(){

		synchronized(this){
			while(isReady){
				try{
					Log.e("logErr", Thread.currentThread().getName() + " Wake");
				} catch(Exception e){} finally{
					isReady = false;
					notifyAll();
				}
			}
		}

	}

}
