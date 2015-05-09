package com.nullcognition.javaconcurrencyinpractice.chapter03.practice;// Created by ersin on 09/05/15

import java.util.HashSet;
import java.util.Set;

public class EscapedEx{

	// Excaped object - the set is visible, therefore one initialized and added too, visibility is
	// public so all code may get the set and look for/modify entries
	public static Set<Integer> visibleToAllThreads;
	public void initializeHash(){ visibleToAllThreads = new HashSet<>();}

	// private is better, but getStrings leaks internal state allowing any thread/code to modify contents
	private String[] strings = new String[]{"s1", "s2", "s3"};
	public String[] getStrings(){return strings;}

	EscapedEx(Broadcaster broadcaster){
		broadcaster.registerListener(
				new BroadcastListener(){ // when publishing the BroadcastListener, the inner class
					// contains an implicit hidden reference to the enclosing instance, allowing it to access its member
// private/public/protected variables and methods
					@Override
					public void onBroadcast(final Broadcast b){
						int shout = b.getShoutCast();
						// strings[0] = "overwrite"; leaked reference
					}
				});
	}
}

class Broadcaster{

	public Broadcaster(int broadcastingValue){this.broadcastingValue = broadcastingValue;}
	private int broadcastingValue;
	private Set<BroadcastListener> broadcastListeners = new HashSet<>();
	public void registerListener(BroadcastListener bl){broadcastListeners.add(bl);}


	public void broadcast(){
		for(BroadcastListener b : broadcastListeners){
			b.onBroadcast(new Broadcast(broadcastingValue));
		}
	}
}

class Broadcast{
	public Broadcast(int ii){shoutCast = ii;}

	int shoutCast = 0;
	public int getShoutCast(){return shoutCast;}
}

interface BroadcastListener{
	void onBroadcast(Broadcast b);
}


