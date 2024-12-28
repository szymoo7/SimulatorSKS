package sks.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CanteenManager {

    private volatile List<Client> clients = new ArrayList<>();
    private volatile List<Table> tables = new ArrayList<>(8);
    private volatile Set<Line> lines;
    private volatile Set<Counter> counters;
    public Lock lock;

    public CanteenManager(int clientsPerSec, int nSeats) {
        for(int i = 0; i < 8; i++) {
            tables.add(new Table(nSeats));
        }
        this.lock = new ReentrantLock(true);
    }

    public void start() {

        for(int i = 0; i < 100; i++) {
            clients.add(new Client(this));
        }

        for(Client client : clients) {
            try {
                Thread.sleep(1000);
                client.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CanteenManager canteenManager = new CanteenManager(1, 4);
        canteenManager.start();
    }


    public Set<Line> getLines() {
        return lines;
    }
}
