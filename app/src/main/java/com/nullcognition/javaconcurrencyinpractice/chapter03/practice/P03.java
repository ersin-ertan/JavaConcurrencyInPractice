package com.nullcognition.javaconcurrencyinpractice.chapter03.practice;// Created by ersin on 09/05/15

import android.util.Log;

public class P03{

	public P03(){}

	public void safeConstruction(){
		Broadcaster broadcaster = new Broadcaster(6);
		SafeListener safeListener = SafeListener.newInstance(broadcaster);
		broadcaster.broadcast();
		Log.e("logErr", "value from broadcast " + safeListener.getValueFromBroadcast());
		// should be equal to broadcasters value
	}


}

