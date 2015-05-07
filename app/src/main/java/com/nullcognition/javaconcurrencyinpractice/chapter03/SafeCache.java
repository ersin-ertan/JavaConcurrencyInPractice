package com.nullcognition.javaconcurrencyinpractice.chapter03;// Created by ersin on 06/05/15

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class SafeCache{
	// atomic variables are good for single operation, but we have synch methods to make atomic operations, concurrent usage is synchronizing
	// the shortest possible code paths for performance and simplicity, lock releasing/aquiring has overhead so going too deep in structure
	// is not recommended

	// watch out for compute intensive operations that hold onto the lock for too long, which could cause blocking operations risking the
	// liveness of the code block

	private BigInteger lastNum = new BigInteger("3");
	private long hits;
	private long cacheHits;

	public synchronized long getHits(){return hits;}

	public synchronized double getCacheHitRatio(){ return (double) cacheHits / (double) hits;}

	public void service(){

		BigInteger i = BigInteger.valueOf(new Random().nextLong());


		synchronized(this){
			++hits;
			if(i.equals(lastNum)){/* logic */}

		}
		boolean moreLogic = true;
		if(moreLogic){
			// work
			synchronized(this){
				lastNum = i;
			}
		}
	}
}

class UnsafeCaching{

	private final AtomicReference<BigInteger> lastNum = new AtomicReference<>();

	// extreme example, poor responsiveness as only one thread may enter the method at once
	public synchronized void service(){

		BigInteger i = BigInteger.valueOf(new Random().nextLong());

		if(lastNum.get()
		          .equals(i)){}
		else{}

	}
}
