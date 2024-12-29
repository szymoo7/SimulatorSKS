package sks.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CanteenManager {

    static volatile List<Client> clients = new ArrayList<>();
    static volatile List<Table> tables = new ArrayList<>();
    static volatile List<Line> lines = new ArrayList<>() {
        {
            add(new Line("Kolejka 1"));
            add(new Line("Kolejka 2"));
        }
    };
    static volatile List<Counter> counters = new ArrayList<>() {
        {
            add(new Counter(true, new Line("Kolejka do kasy 1")));
            add(new Counter(false, new Line("Kolejka do kasy 2")));
        }
    };


    public CanteenManager(int clientsPerSec, int nSeats) {
        for(int i = 0; i < 8; i++) {
            tables.add(new Table(nSeats, i));
        }
    }

    public void startSimulation() throws InterruptedException {

        for (int i = 0; i < 2; i++) {
            new Cook(lines.get(i), 1).start();
        }

        counters.get(0).start();

        for(int i = 0; i < 250; i++) {
            Client c = new Client(this);
            clients.add(c);
            c.start();
            Thread.sleep(2500);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        CanteenManager canteenManager = new CanteenManager(1, 1);
        canteenManager.startSimulation();
    }


    public List<Line> getLines() {
        return lines;
    }

    public List<Line> getToPayLines() {
        return counters.stream().filter(Counter::isOpen).map(Counter::getToPayLine).toList();
    }

    public List<Table> getTables() {
        return tables;
    }
}
