package sks.backend;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int size;
    private final List<Client> row1 = new ArrayList<>(size);
    private final List<Client> row2 = new ArrayList<>(size);

    public Table(int size) {
        this.size = size;
    }
}
