package sks.backend;

import java.util.ArrayList;
import java.util.List;

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
    static volatile List<Thread> threads = new ArrayList<>();

    private volatile boolean isRunning = false;
    private double simulationSpeed = 1;
    private int nSeats = 1;
    private long clientEveryNSeconds = 1;


    public CanteenManager(int clientEveryNSeconds, int nSeats) {
        this.clientEveryNSeconds = clientEveryNSeconds;
        this.nSeats = nSeats;

    }

    public CanteenManager() {
    }


    public void startSimulation() {
        clients.clear();
        tables.clear();
        threads.clear();
        for(int i = 0; i < 8; i++) {
            tables.add(new Table(nSeats, i));
        }
        for (int i = 0; i < 2; i++) {
            Cook c = new Cook(lines.get(i), 1);
            threads.add(c);
            c.start();
        }

        for(Counter c: counters) {
            c.start();
        }
    }

    public void generateClient() {
        Client client = new Client(this);
        clients.add(client);
        client.start();
    }

    public void stopSimulation() {
        for(Thread t: threads) {
            t.interrupt();
        }
        for(Counter c: counters) {
            c.interrupt();
        }
    }


//    public static void main(String[] args) throws InterruptedException {
//        CanteenManager canteenManager = new CanteenManager(1, 1);
//        canteenManager.startSimulation();
//    }


    public List<Line> getLines() {
        return lines;
    }

    public List<Line> getToPayLines() {
        return counters.stream().filter(Counter::isOpen).map(Counter::getToPayLine).toList();
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setSimulationSpeed(int i) {
        this.simulationSpeed = i;
    }

    public void setNSeats(int i) {
        this.nSeats = i;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setStatus(boolean status) {
        this.isRunning = status;
    }


}
