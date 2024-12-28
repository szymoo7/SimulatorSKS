package sks.backend;

import sks.backend.enums.ClientStatus;

import java.util.Random;

public class Cook extends Thread {

    private double speed;
    private static final Random random = new Random();
    private final Line line;

    public Cook(Line line, double speed) {
        this.line = line;
        this.speed = speed;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Client current = poolClient();
                if(current != null) {
                    serveClient(current);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Client poolClient() {
        Client current = line.pollClient();
        if(current == null) {
            return null;
        }
        current.setStatus(ClientStatus.ORDERING);
        return current;
    }

    private void serveClient(Client current) throws InterruptedException {
        System.out.println("\u001B[32mClient id: " + current.id() + " is being served\u001B[0m");
        Thread.sleep(10000);
    }

}
