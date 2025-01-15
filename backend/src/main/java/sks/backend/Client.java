package sks.backend;

import sks.backend.enums.ClientStatus;
import sks.backend.enums.Dishes;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Thread {

    private static final Random random = new Random();
    private static final AtomicInteger idCreator = new AtomicInteger(0);
    private final int id;
    private String order;
    private ClientStatus status;
    private CanteenManager resources;
    private volatile double x;
    private volatile double y;

    public Client(CanteenManager resources) {
        this.id = idCreator.getAndIncrement();
        this.status = ClientStatus.ENTERING;
        this.resources = resources;
    }

    @Override
    public void run() {
        joinQueue();
        while (status != ClientStatus.IN_QUEUE_TO_PAY) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        goToCounter();
        while (status != ClientStatus.LOOKING_FOR_SEAT) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            goEating();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            this.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", order='" + order + '\'' +
                ", status=" + status +
                '}';
    }

    private void joinQueue() {
        try {
            Line shortestLine = findShorterLine(resources.getLines());
            shortestLine.addClient(this);
            this.status = ClientStatus.IN_QUEUE;
            selectOrder();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void goToCounter() {
        Line shortestLine = findShorterLine(resources.getToPayLines());
        shortestLine.addClient(this);
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    private Line findShorterLine(List<Line> lines) {
        return lines.stream()
                .min(Comparator.comparingInt(Line::getSize))
                .orElseThrow();
    }

    public int id() {
        return id;
    }

    private void selectOrder() {
        this.order = Dishes.values()[random.nextInt(Dishes.values().length)].name();
    }

    private Seat findSeat(Table table) {
        return table.getSeats()
                .stream()
                .filter(seat -> !seat.isOccupied())
                .findFirst().orElseThrow();
    }

    private Table findTable() {
        return resources.getTables()
                .stream()
                .filter(table -> table.getSeats().stream().anyMatch(seat -> !seat.isOccupied()))
                .findFirst().orElseThrow();
    }

    private void occupySeat(Seat seat) {
        seat.setOccupied(true);
    }

    private void goEating() throws InterruptedException {
        Table table = findTable();
        Seat seat = findSeat(table);
        occupySeat(seat);
        this.status = ClientStatus.EATING;
        System.out.println("\u001B[32mClient id: " + id + " is eating at table (" + table.getNumber() + ")\u001B[0m");
        Thread.sleep(10_000);
        seat.setOccupied(false);
        this.status = ClientStatus.EXITING;
        System.out.println("\u001B[32mClient id: " + id + " is leaving" + "\u001B[0m");
    }
}
