package com.company.synch.way;

import java.util.LinkedList;
import java.util.Random;
/**
 * Created by vova on 5/29/14.
 */

public class Parking {

    int placesCount;
    LinkedList<ParkingPlace> freePlaces;

    public Parking(int placesCount){
        this.placesCount = placesCount;
        freePlaces = new LinkedList<ParkingPlace>();

        for (int i = 0; i < placesCount; i++){
            freePlaces.add(new ParkingPlace(i));
        }
    }

    private synchronized ParkingPlace operatePlaces(){

        Random r = new Random();

        ParkingPlace pp = freePlaces.get( r.nextInt(freePlaces.size() - 1) + 1 );
        freePlaces.remove(pp);

        System.out.println("Parking place " + pp.getParkingID() + " are busy now!");
        return pp;
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
