package com.nullcognition.javaconcurrencyinpractice.chapter03;// Created by ersin on 09/05/15

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class CachingEx{

	// Safe publication - getting the factors from the last cache value, using one value cache
	// but if the cache is empty, do the computation, and re populate a new cache data structure
	private volatile OneValueCache oneValueCache = new OneValueCache(null, null);
	public void service(int request, LinkedList<BigInteger> response){
		BigInteger i = response.getFirst();
		BigInteger[] factors = oneValueCache.getLastFactors(i);
		if(factors == null){
			factors = getFactor(i);
			oneValueCache = new OneValueCache(i, factors);
		}

	}
	private BigInteger[] getFactor(final BigInteger i){
		return new BigInteger[0];
	}

	// --------------------------------------------------
	public static int stackConfinement(LinkedList<Integer> integerCollection){
		// do work
		int i = integerCollection.remove(3);
		// since primitives do not reference an object, it is safe to pass them back
		return i;
	}

//	improperly publish objects (without sync) can cause stale values when object reference is visible to threads but not the values, sharing null values of uninitialized variables
	// to other threads, thus sharing across threads require sync, or volatile(mostly for state visibility), or immutable structures
	// because of their immutable state, final fields and proper construction

	// final fields can be safely accessed without sync, but if they refer to mutable objects, sync is required to access
	// the refering state

}

final class Immutable{ // returning primitives, variable initialization in constructor
	private final Set<String> stringSet = new HashSet<>();
	public Immutable(){
		stringSet.add("one");
		stringSet.add("two");
		stringSet.add("three");
	}
	public boolean contains(final String name){ return stringSet.contains(name);}
}

/* The basis for immutable data structures

* Race conditions in accessing or updating multiple related variables can be eliminated by using an immutable object to 
hold  all  the  variables.  With  a  mutable  holder  object,  you  would  have  to  use  locking  to  ensure  atomicity;  with  an 
immutable one, once a thread acquires a reference to it, it need never worry about another thread modifying its state. If 
the variables are to be updated, a new holder object is created, but any threads working with the previous holder still 
see it in a consistent state. 
* */

class OneValueCache{
	private final BigInteger lastNumber;
	private final BigInteger[] lastFactors;

	public OneValueCache(BigInteger i, BigInteger[] factors){
		lastNumber = i;
		lastFactors = Arrays.copyOf(factors, factors.length); // original, new length
		//creating the new array to work with data reserved to the class
	}

	public BigInteger[] getLastFactors(BigInteger i){
		if(lastNumber == null || !lastNumber.equals(i)){ return null; }
		else{ return Arrays.copyOf(lastFactors, lastFactors.length); }
	}
}
