package com.nullcognition.javaconcurrencyinpractice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


public class MainActivity extends ActionBarActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState){

	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
   }

   // intrinsic locks are reentrant, allowing a thread that tries to acquire a lock it already holds to suceed, solving its deadlock issue
   // with extended classes and its superclass that has a synchronized method

   // every shared mutable variable should be guarded by exactly one lock

   class SafeCache{
      // atomic variables are good for single operation, but we have synch methods to make atomic operations, concurrent usage is synchronizing
      // the shortest possible code paths for performance and simplicity, lock releasing/aquiring has overhead so going too deep in structure
      // is not recommended

      // watch out for compute intensive operations that hold onto the lock for too long, which could cause blocking operations risking the
      // liveness of the code block

      private BigInteger lastNum = new BigInteger("3");
      private long hits;
      private long cacheHits;

      public synchronized long getHits(){return hits;}

      public synchronized double getCacheHitRatio(){ return (double)cacheHits / (double)hits;}

      public void service(){

         BigInteger i = BigInteger.valueOf(new Random().nextLong());


         synchronized(this){
            ++hits;
            if(i.equals(lastNum)){/* logic */};
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

   class UnsafeCaching {

      private final AtomicReference<BigInteger> lastNum = new AtomicReference<>();

      // extreme example, poor responsiveness as only one thread may enter the method at once
      public synchronized void service(){

         BigInteger i = BigInteger.valueOf(new Random().nextLong());

         if(lastNum.get()
                   .equals(i)){}
         else{}

      }
   }

   AtomicLong atomicLong = new AtomicLong(1L);

   // use thread safe objects to manage state, easier to maintain and verify
   public long getAtomicLong(){return atomicLong.incrementAndGet();}

   private Object expensiveObject = null;

   // lazy initialization with race conditions, both threads may read ex obj to be null, receiving 2 different objects
   public Object getExpensiveObject(){

	  if(expensiveObject == null){ expensiveObject = new Object(); }
	  return expensiveObject;
   }

   private int value = 0;

   public synchronized int getNextSynched(){return value++;} // thread safe now only one thread may enter the method; updating the value

   // due to timing two threads may call method at same time and get the same value
   public int getNextUnSafeSequence(){ return value++;}


   @Override
   public boolean onCreateOptionsMenu(Menu menu){

	  getMenuInflater().inflate(R.menu.menu_main, menu);
	  return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item){

	  int id = item.getItemId();
	  if(id == R.id.action_settings){
		 return true;
	  }
	  return super.onOptionsItemSelected(item);
   }
}
