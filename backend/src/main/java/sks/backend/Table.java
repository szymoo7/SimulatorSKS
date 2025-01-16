package sks.backend;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Table {
    //nSeats to liczba miejsc z jednej strony stołu czyli jeden stół to 2*nSeats miejsc a stołów jest 8
    private int number;
    private volatile List<Seat> seats;
    private static final List<Point> coordsTable1 = List.of(
            new Point(-4, 12),
            new Point(152, 12),
            new Point(-4, 4),
            new Point(152, 4),
            new Point(-4, 12),
            new Point(151, 13),
            new Point(-4, 12),
            new Point(149, 12)
    );
    private static final List<Point> coordsTable2 = List.of(
            new Point(152, 12),
            new Point(308, 12),
            new Point(152, 4),
            new Point(308, 4),
            new Point(152, 12),
            new Point(307, 13),
            new Point(152, 12),
            new Point(306, 12)
    );

    public Table(int nSeats, int number) {
        this.number = number;
        this.seats = Collections.synchronizedList(
                IntStream.range(1, 2 * nSeats + 1).collect(
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
