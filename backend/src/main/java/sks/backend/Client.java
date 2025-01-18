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
    private volatile String order;
    private volatile ClientStatus status;
    private CanteenManager resources;
    private volatile Seat currentSeat;

    private List<Point> coordinatesForAnimation =  List.of(
            new Point(1365,559),
            new Point(1288-37, 188-37),
            new Point(1050, 100),
            new Point(648,227),
            new Point(236, 90),
            new Point(825, 200),
            new Point(509, 222),
            new Point(800, 20)
    );

    public Client(CanteenManager resources) {
        this.id = idCreator.getAndIncrement();
        this.status = ClientStatus.ENTERING;
        this.resources = resources;
    }

    @Override
    public void run() {
        while(true) {
            resources.setAnimation(new AnimationDto(this.id,
                    coordinatesForAnimation.get(0).x, coordinatesForAnimation.get(0).y));
            resources.setAnimation(new AnimationDto(this.id,
                    coordinatesForAnimation.get(1).x, coordinatesForAnimation.get(1).y));
            simulateAction(1000);
            joinQueue();
            goToCounter();
            goEating();
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
                resources.setAnimation(new AnimationDto(this.id,
                        coordinatesForAnimation.get(2).x, coordinatesForAnimation.get(2).y));
                simulateAction(1000);
            }
            else {
                resources.setAnimation(new AnimationDto(this.id,
                        coordinatesForAnimation.get(3).x, coordinatesForAnimation.get(3).y));
                simulateAction(1000);
                resources.setAnimation(new AnimationDto(this.id,
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
        while(status != ClientStatus.ORDERED) {
            simulateAction(1);
        }
        synchronized (resources) {
            resources.notifyAll();
            Line shortestLine = findShorterLine(resources.getToPayLines());
            while(shortestLine ==  null) {
                simulateAction(10);
                shortestLine = findShorterLine(resources.getToPayLines());
            }
            shortestLine.addClient(this);
            if(shortestLine.getId() == 3) {
                resources.setAnimation(new AnimationDto(this.id,
                        coordinatesForAnimation.get(5).x, coordinatesForAnimation.get(5).y));
            }
            else if(shortestLine.getId() == 4) {
                resources.setAnimation(new AnimationDto(this.id,
                        coordinatesForAnimation.get(6).x, coordinatesForAnimation.get(6).y));
            }
            this.status = ClientStatus.IN_QUEUE_TO_PAY;
        }
        simulateAction(1000);


        while(true) {
            if(status == ClientStatus.LOOKING_FOR_SEAT) {
                break;
            }
            simulateAction(10);
        }
    }

    public void setStatus(ClientStatus status) {
        System.out.println("Client " + id + " status changing from " + this.status + " to " + status);
        this.status = status;
    }

    private Line findShorterLine(List<Line> lines) {
        return lines.stream()
        .min(Comparator.comparingInt(Line::getSize))
        .orElse(null);
    }

    public int id() {
        return id;
    }

    private void selectOrder() {
        this.order = Dishes.values()[random.nextInt(Dishes.values().length)].name();
    }

    //TODO: Reentrance lock?
    private void findAndTakeSeat() {
        while (currentSeat == null) {
            synchronized(resources) {
                resources.notifyAll();
                Optional<Table> tableOpt = resources.getTables().stream()
                        .filter(t -> t.getSeats().stream().anyMatch(s -> !s.isOccupied()))
                        .findFirst();

                if (tableOpt.isPresent()) {
                    Table table = tableOpt.get();
                    Optional<Seat> seatOpt = table.getSeats().stream()
                            .filter(s -> !s.isOccupied())
                            .findFirst();

                    if (seatOpt.isPresent()) {
                        Seat seat = seatOpt.get();
                        if(seat.setOccupied(true)) {
                            currentSeat = seat;
                            resources.setAnimation(new AnimationDto(this.id,
                                    table.getNumber(), seat.getSeatNumber(), true));
                            break;
                        }
                    }
                }
            }
            simulateAction(10);
        }
        simulateAction(1000);
    }

    private synchronized void leaveSeat() {
        while(currentSeat != null) {
            if(currentSeat.setOccupied(false)) {
                resources.setAnimation(new AnimationDto(this.id,
                        coordinatesForAnimation.get(0).x, coordinatesForAnimation.get(0).y));
                simulateAction(1000);
                resources.setAnimation(new AnimationDto(this.id,
                        null, null));
                currentSeat = null;
            }
        }
    }

    private void goEating() {
        while (status != ClientStatus.LOOKING_FOR_SEAT) {
            simulateAction(10);
        }
        findAndTakeSeat();
        setStatus(ClientStatus.EATING);
        simulateAction(10000);
        setStatus(ClientStatus.EXITING);
        leaveSeat();
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
