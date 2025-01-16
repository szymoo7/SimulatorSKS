package sks.backend;

import sks.backend.enums.ClientStatus;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Cashier extends Thread {
    private final Line toPayLine;
    private static final Random random = new Random();
    private CanteenManager resources;

    private java.util.List<Point> coordinatesForAnimation =  List.of(
            new Point(760, 270),
            new Point(546, 257)
    );

    public Cashier(Line toPayLine, CanteenManager resources) {
        this.toPayLine = toPayLine;
        this.resources = resources;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (toPayLine.getSize() > 0) {
                Client current = peekClient();
                if (current != null) {
                    try {
                        checkOut(current);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Cashier was interrupted");
                        break;
                    }
                }
            }
        }
    }

    private Client peekClient() {
        Client current = toPayLine.peekClient();
        if(current == null) {
            return null;
        }
        if(toPayLine.getId() == 3) {
            resources.setClientToUpdate(new ClientDto(current.id(),
                    coordinatesForAnimation.get(0).x, coordinatesForAnimation.get(0).y));
        } else if (toPayLine.getId() == 4) {
            resources.setClientToUpdate(new ClientDto(current.id(),
                    coordinatesForAnimation.get(1).x, coordinatesForAnimation.get(1).y));
        }
        current.setStatus(ClientStatus.PAYING);
        return current;
    }

    private void checkOut(Client current) throws InterruptedException {
        System.out.println("\u001B[32mClient " + current.id() + " is paying at " + toPayLine.getName() + "\u001B[0m");
        Thread.sleep(random.nextInt(5_000, 15_000));
        System.out.println("\u001B[34mClient " + current.id() + " has paid at " + toPayLine.getName() + "\u001B[0m");
        current.setStatus(ClientStatus.LOOKING_FOR_SEAT);
        toPayLine.removeClient();
    }

}
