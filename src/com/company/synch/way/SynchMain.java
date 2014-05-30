package com.company.synch.way;

import java.util.Random;

public class SynchMain {

    public static void main(String[] args) {
	    Parking parking = new Parking(10);
        Random r = new Random();

        System.out.println("Using synchronized way java...\n");
        try {
            while(true) {
                Car car = new Car(parking, r.nextLong(), r.nextInt() % 3000);
                car.start();
                Thread.currentThread().sleep(2000);
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
