package sks.backend;

import java.awt.*;

public class Seat {
    private volatile int seatNumber;
    private volatile boolean isOccupied;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isOccupied = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public synchronized boolean setOccupied(boolean occupied) {
        if(isOccupied && occupied == true) {
            return false;
        }
        if(isOccupied && occupied == false) {
            isOccupied = occupied;
            return true;
        }
        if(!isOccupied && occupied) {
            isOccupied = occupied;
            return true;
        }
        return false;
    }
}
