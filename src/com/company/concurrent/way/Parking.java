package com.company.concurrent.way;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * Created by vova on 5/29/14.
 */
public class Parking {

    int placesCount;
    LinkedBlockingQueue<ParkingPlace> freePlaces;

    public Parking(int placesCount){
        this.placesCount = placesCount;
        freePlaces = new LinkedBlockingQueue<ParkingPlace>(placesCount);

        for (int i = 0; i < placesCount; i++){
            freePlaces.add(new ParkingPlace(i));
        }
    }

    private synchronized ParkingPlace operatePlaces(){

        Random r = new Random();
        ParkingPlace pp = null;

        try {
            pp = freePlaces.take();
            System.out.println("Parking place " + pp.getParkingID() + " are busy now!");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        finally {
            return pp;
        }
    }

    public synchronized ParkingPlace getFreePlace(Car car) throws InterruptedException{

        long waitStartTime = System.currentTimeMillis();
        long maxWaitTime = car.getMaxWaitTime();
        System.out.println("Car " + car.getID() + " are on parking now");

        if (freePlaces.size() != 0) {
            return operatePlaces();
        }
        else{

            while( (freePlaces.size() != 0) && (System.currentTimeMillis() - waitStartTime < maxWaitTime)  ){
                wait();
            }
            if (freePlaces.size() != 0){
                return operatePlaces();
            }
            else{
                return null;
            }
        }
    }

    public synchronized void freePlace(ParkingPlace pp){

        freePlaces.add(pp);
        System.out.println("Parking place " + pp.getParkingID() + " are free now!");
        notifyAll();
    }
}
