package sks.backend;

import sks.backend.enums.ClientStatus;

import java.util.Random;

public class Cook extends Thread {

    private double speed;
    private static final Random random = new Random();
    private final Line line;
    private final String name;

    public Cook(Line line, double speed) {
        this.line = line;
        this.speed = speed;
        this.name = line.getName();
    }

    @Override
    public void run() {
        while(true) {
            Client current = peekClient();
            if(current != null) {
                serveClient(current);
                removeClient();
                current.setStatus(ClientStatus.IN_QUEUE_TO_PAY);
                System.out.println("\u001B[32mClient id: " + current.id() + " has been served from " + name + "\u001B[0m");
            }
        }
    }

    private Client peekClient() {
        Client current = line.peekClient();
        if(current == null) {
            return null;
        }
        current.setStatus(ClientStatus.ORDERING);
        return current;
    }

    private void removeClient() {
        line.removeClient();
    }

    private void serveClient(Client current) {
        System.out.println("\u001B[32mClient id: " + current.id() + " is being served from " + name + "\u001B[0m");
        try {
            Thread.sleep(random.nextInt(3000, 8000));
        } catch (InterruptedException e) {
            System.out.println("Cook was interrupted");
        }
    }

}
