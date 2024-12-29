package sks.backend;

public class Seat {
    private int seatNumber;
    private boolean isOccupied;

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

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
