package com.nullcognition.javaconcurrencyinpractice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.nullcognition.javaconcurrencyinpractice.chapter03.practice.P03;


public class MainActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		p03();

	}
	private void p03(){
		P03 p = new P03();
//		p.threadSync();
//		p.threadLocal();
//		p.safeConstruction();
	}


}
