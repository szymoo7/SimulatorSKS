package sks.backend;

import sks.backend.enums.ClientStatus;
import sks.backend.enums.Dishes;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Thread {

    private static final Random random = new Random();
    private static final AtomicInteger idCreator = new AtomicInteger(0);
    private final int id;
    private String order;
    private ClientStatus status;
    private CanteenManager resources;

    private List<Point> coordinatesForAnimation =  List.of(
            new Point(1365,559),
            new Point(1288-37, 188-37),
            new Point(1050, 100),
            new Point(648,227),
            new Point(236, 90),
            new Point(825, 200),//l
            new Point(509, 222),// p
            new Point(800, 20)
    );

    public Client(CanteenManager resources) {
        this.id = idCreator.getAndIncrement();
        this.status = ClientStatus.ENTERING;
        this.resources = resources;
    }

    @Override
    public void run() {
        resources.setClientToUpdate(new ClientDto(this.id,
                coordinatesForAnimation.get(0).x, coordinatesForAnimation.get(0).y));
        resources.setClientToUpdate(new ClientDto(this.id,
                coordinatesForAnimation.get(1).x, coordinatesForAnimation.get(1).y));
        simulateAction(1000);
        joinQueue();
        while (status != ClientStatus.IN_QUEUE_TO_PAY) {
            simulateAction(1);
        }
        goToCounter();
        while (status != ClientStatus.LOOKING_FOR_SEAT) {
            simulateAction(1);
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
            if(shortestLine.getId() == 1) {
                resources.setClientToUpdate(new ClientDto(this.id,
                        coordinatesForAnimation.get(2).x, coordinatesForAnimation.get(2).y));
                simulateAction(1000);
            }
            else {
                resources.setClientToUpdate(new ClientDto(this.id,
                        coordinatesForAnimation.get(3).x, coordinatesForAnimation.get(3).y));
                simulateAction(1000);
                resources.setClientToUpdate(new ClientDto(this.id,
                        coordinatesForAnimation.get(4).x, coordinatesForAnimation.get(4).y));
                simulateAction(1000);
            }
            this.status = ClientStatus.IN_QUEUE;
            selectOrder();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void goToCounter() {
        Line shortestLine = findShorterLine(resources.getToPayLines());
        shortestLine.addClient(this);
        if(shortestLine.getId() == 3) {
            resources.setClientToUpdate(new ClientDto(this.id,
                    coordinatesForAnimation.get(5).x, coordinatesForAnimation.get(5).y));
        }
        else if(shortestLine.getId() == 4) {
            resources.setClientToUpdate(new ClientDto(this.id,
                    coordinatesForAnimation.get(6).x, coordinatesForAnimation.get(6).y));
        }
        simulateAction(1000);
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
        Seat seat = table.getSeats()
                .stream()
                .filter(s -> !s.isOccupied())
                .findFirst().orElseThrow();
        seat.setOccupied(true);
        return seat;
    }

    private Table findTable() {
        return resources.getTables()
                .stream()
                .filter(table -> table.getSeats().stream().anyMatch(seat -> !seat.isOccupied()))
                .findFirst().orElseThrow();
    }

    private void goEating() throws InterruptedException {
        Seat currentSeat;
        synchronized (resources) {
            Table table = findTable();
            currentSeat = findSeat(table);
            resources.setTableSeatToUpdate(new TableSeatDto(this.id,
                    table.getNumber(), currentSeat.getSeatNumber()));
            System.out.println("\u001B[32mClient id: " + id + " is eating at table (" + table.getNumber() + ")\u001B[0m");
        }

        simulateAction(1000);
        this.status = ClientStatus.EATING;
        simulateAction(10_000);
        this.status = ClientStatus.EXITING;
        resources.setClientToUpdate(new ClientDto(this.id,
                coordinatesForAnimation.get(0).x, coordinatesForAnimation.get(0).y));
        simulateAction(1000);
        resources.setClientToUpdate(new ClientDto(this.id,
                null, null));
        currentSeat.setOccupied(false);
        System.out.println("\u001B[32mClient id: " + id + " is leaving" + "\u001B[0m");
    }

    private void simulateAction(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientStatus getStatus() {
        return status;
    }
}
