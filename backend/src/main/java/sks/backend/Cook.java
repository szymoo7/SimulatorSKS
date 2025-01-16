package sks.backend;

import sks.backend.enums.ClientStatus;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Cook extends Thread {

    private double speed;
    private static final Random random = new Random();
    private final Line line;
    private final String name;
    private CanteenManager resources;

    public Cook(CanteenManager resources, Line line, double speed) {
        this.line = line;
        this.speed = speed;
        this.name = line.getName();
        this.resources = resources;
    }
    private java.util.List<Point> coordinatesForAnimation =  List.of(
            new Point(800, 20),
            new Point(406,52)
    );

    @Override
    public void run() {
        while(true) {
            Client current = peekClient();
            if(current != null) {
                if(line.getId() == 1) {
                    resources.setClientToUpdate(new ClientDto(current.id(),
                            coordinatesForAnimation.get(0).x, coordinatesForAnimation.get(0).y));

                }
                else {
                    resources.setClientToUpdate(new ClientDto(current.id(),
                            coordinatesForAnimation.get(1).x, coordinatesForAnimation.get(1).y));

                }
                simulateAction(1100);
                serveClient(current);
                removeClient();
                current.setStatus(ClientStatus.IN_QUEUE_TO_PAY);
                System.out.println("\u001B[32mClient id: " + current.id() + " has been served from " + name + "\u001B[0m");
            }
        }
    }

    private Client peekClient() {
        Client current = line.peekClient();
        if(current == null || current.getStatus() != ClientStatus.IN_QUEUE) {
            return null;
        }
        current.setStatus(ClientStatus.ORDERING);
        return current;
    }

    private void removeClient() {
        if(line.getSize() > 0) {
            line.removeClient();
        }
    }

    private void serveClient(Client current) {
        System.out.println("\u001B[32mClient id: " + current.id() + " is being served from " + name + "\u001B[0m");
        try {
            Thread.sleep(random.nextInt(3000, 8000));
        } catch (InterruptedException e) {
            System.out.println("Cook was interrupted");
        }
    }
    private void simulateAction(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
