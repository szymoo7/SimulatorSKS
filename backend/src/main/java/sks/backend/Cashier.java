package sks.backend;

import sks.backend.enums.ClientStatus;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Cashier extends Thread {
    private volatile Line toPayLine;
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
                Client current;
                synchronized(toPayLine) {
                    current = toPayLine.peekClient();
                    if (current != null) {
                        synchronized(current) {
                            if (current.getStatus() == ClientStatus.IN_QUEUE_TO_PAY) {
                                try {
                                    if(toPayLine.getId() == 3) {
                                        resources.setAnimation(new AnimationDto(current.id(),
                                                coordinatesForAnimation.get(0).x,
                                                coordinatesForAnimation.get(0).y));
                                    } else if (toPayLine.getId() == 4) {
                                        resources.setAnimation(new AnimationDto(current.id(),
                                                coordinatesForAnimation.get(1).x,
                                                coordinatesForAnimation.get(1).y));
                                    }

                                    current.setStatus(ClientStatus.PAYING);
                                    toPayLine.removeClient();

                                    checkOut(current);
                                } catch (InterruptedException e) {
                                    current.setStatus(ClientStatus.IN_QUEUE_TO_PAY);
                                    Thread.currentThread().interrupt();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private Client peekClient() {
        Client current = toPayLine.peekClient();
        if(current == null) {
            return null;
        }
        synchronized(current) {
            if(current.getStatus() != ClientStatus.IN_QUEUE_TO_PAY) {
                return null;
            }

            if(toPayLine.getId() == 3) {
                resources.setAnimation(new AnimationDto(current.id(),
                        coordinatesForAnimation.get(0).x, coordinatesForAnimation.get(0).y));
            } else if (toPayLine.getId() == 4) {
                resources.setAnimation(new AnimationDto(current.id(),
                        coordinatesForAnimation.get(1).x, coordinatesForAnimation.get(1).y));
            }
            current.setStatus(ClientStatus.PAYING);
        }
        return current;
    }

    private void checkOut(Client current) throws InterruptedException {
        Thread.sleep(random.nextInt(1000, 5000));

        synchronized(current) {
            if (current.getStatus() == ClientStatus.PAYING) {
                current.setStatus(ClientStatus.LOOKING_FOR_SEAT);
            }
        }
    }
}
