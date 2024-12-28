package sks.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CanteenManager {

    static volatile List<Client> clients = new ArrayList<>();
    static volatile List<Table> tables = new ArrayList<>(8);
    static volatile List<Line> lines = new ArrayList<>() {
        {
            add(new Line("Kolejka 1"));
            add(new Line("Kolejka 2"));
        }
    };
    static volatile Set<Counter> counters;
    static Lock lock = new ReentrantLock(true);

    public CanteenManager(int clientsPerSec, int nSeats) {
        for(int i = 0; i < 8; i++) {
            tables.add(new Table(nSeats));
        }
    }

    public void startSimulation() throws InterruptedException {

        for (int i = 0; i < 2; i++) {
            new Cook(lines.get(i), 1).start();
        }

        for(int i = 0; i < 10; i++) {
            //Thread.sleep(2000);
            Client c = new Client(this);
            clients.add(c);
            c.start();

        }

    }

    public static void main(String[] args) throws InterruptedException {
        CanteenManager canteenManager = new CanteenManager(1, 4);
        canteenManager.startSimulation();
    }


    public List<Line> getLines() {
        return lines;
    }
}
