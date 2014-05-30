package com.company.concurrent.way;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vova on 5/29/14.
 */

public class ConcurrentMain {
    public static void main(String[] args) {
        Parking parking = new Parking(10);
        Random r = new Random();

        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Using concurrent way java...\n");
        try {
            while(true) {
                Car car = new Car(parking, r.nextLong(), r.nextInt() % 3000);
                executor.execute(car);
                Thread.currentThread().sleep(2000);
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        finally{
            executor.shutdown();
        }
    }
}
