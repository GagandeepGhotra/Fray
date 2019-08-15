package Libraries.ObjectClasses;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class w {
	public static void main(String[] args) throws Exception {

	     FutureTask<String> future = 
	             new FutureTask<String>((Callable<String>) new Callable<String>() {
	         public String call() {
	             try {
	                 Thread.sleep(1000);
	             } catch (InterruptedException e) {
	                 e.printStackTrace();
	             }
	             return "Hello World!";
	         }
	     });

	     ExecutorService executor = Executors.newFixedThreadPool(10);
	     executor.execute(future);

	     System.out.println(future.get());
	}
}
