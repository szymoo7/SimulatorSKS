package sks.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Table {
    //nSeats to liczba miejsc z jednej strony stołu czyli jeden stół to 2*nSeats miejsc a stołów jest 8
    private int number;
    private final List<Seat> seats;

    public Table(int nSeats, int number) {
        this.number = number;
        this.seats = Collections.synchronizedList(
                IntStream.range(0, 2 * nSeats).collect(
                        ArrayList::new,
                        (list, i) -> list.add(new Seat(i)),
                        ArrayList::addAll
                ));
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getNumber() {
        return number;
    }


}
