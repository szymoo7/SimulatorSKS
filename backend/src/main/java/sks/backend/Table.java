package sks.backend;

import java.util.ArrayList;
import java.util.List;

public class Table {
    //nSeats to liczba miejsc z jednej strony stołu czyli jeden stół to 2*nSeats miejsc a stołów jest 8
    private int nSeats;
    private final List<Client> row1 = new ArrayList<>(nSeats);
    private final List<Client> row2 = new ArrayList<>(nSeats);

    public Table(int nSeats) {
        this.nSeats = nSeats;
    }
}
