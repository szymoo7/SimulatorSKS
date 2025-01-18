package sks.backend;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CanteenManager {

    static volatile List<Client> clients = Collections.synchronizedList(new ArrayList<>());
    static volatile List<Table> tables = new ArrayList<>();
    static volatile List<Line> lines = new ArrayList<>() {
        {
            add(new Line("Kolejka 1", 1));
            add(new Line("Kolejka 2", 2));
        }
    };
    static volatile List<Counter> counters = new ArrayList<>();
    static volatile List<Thread> threads = new ArrayList<>();
    static volatile Queue<ClientDto> toUpdate = new ConcurrentLinkedQueue<>();
    private volatile Queue<TableSeatDto> tableSeatToUpdate = new ConcurrentLinkedQueue<>();
    private volatile Queue<AnimationDto> animations = new ConcurrentLinkedQueue<>();

    private static volatile boolean isRunning = false;
    private static double simulationSpeed = 1;
    private static int nSeats = 3;
    private static long clientEveryNSeconds = 1;


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
        counters.clear();
        for(int i = 1; i < 9; i++) {
            tables.add(new Table(nSeats, i));
        }
        for (int i = 0; i < 2; i++) {
            Cook c = new Cook(this, lines.get(i), 1);
            threads.add(c);
            c.start();
        }

        counters.add(new Counter(this, true, new Line("Kolejka do kasy 1", 3)));
        counters.add(new Counter(this, true, new Line("Kolejka do kasy 2", 4)));
        for(Counter c : counters) {
            c.start();
        }
    }

    public Client generateClient() {
        Client client = new Client(this);
        clients.add(client);
        client.start();
        return client;
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

    public long getClientEveryNSeconds() {
        return clientEveryNSeconds;
    }

    public List<Client> getClients() {
        return clients;
    }

    public synchronized void setClientToUpdate(ClientDto client) {
        toUpdate.add(client);
    }

    public synchronized ClientDto getClientToUpdate() {
        return toUpdate.poll();
    }

    public synchronized TableSeatDto getTableSeatToUpdate() {
        return tableSeatToUpdate.poll();
    }

    public synchronized void setTableSeatToUpdate(TableSeatDto tableSeat) {
        tableSeatToUpdate.add(tableSeat);
    }

    public void setAnimation(AnimationDto animationDto) {
        animations.add(animationDto);
    }

    public AnimationDto getAnimation() {
        return animations.poll();
    }
}
