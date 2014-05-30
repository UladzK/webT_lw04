package com.company.concurrent.way;

import java.util.Random;

/**
 * Created by vova on 5/29/14.
 */
public class Car extends Thread{

    Parking parking;
    long maxWaitTime;
    int ID;

    public Car(Parking parking, long maxWaitTime, int ID){
        this.parking = parking;
        this.maxWaitTime = maxWaitTime;
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }

    public long getMaxWaitTime(){
        return maxWaitTime;
    }

    @Override
    public void run(){

        try {
            ParkingPlace pp = parking.getFreePlace(this);
            if (pp == null) {
                System.out.println("Time is up. Car " + ID + " is going to the other parking");
                return ;
            }
            Random r = new Random();

            Thread.currentThread().sleep(r.nextLong() % 3000 + 3000);
            System.out.println("Car " + ID + " left parking");
            parking.freePlace(pp);

        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
