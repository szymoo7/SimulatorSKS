package sks.backend;

import sks.backend.enums.ClientStatus;

import java.util.Random;

public class Cashier extends Thread {
    private final Line toPayLine;
    private static final Random random = new Random();

    public Cashier(Line toPayLine) {
        this.toPayLine = toPayLine;
    }

    public void run() {
        while (true) {
            if (toPayLine.getSize() > 0) {
                Client current = peekClient();
                if(current != null) {
                    try {
                        checkOut(current);
                        toPayLine.removeClient();
                        current.setStatus(ClientStatus.LOOKING_FOR_SEAT);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
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
        current.setStatus(ClientStatus.PAYING);
        return current;
    }

    private void checkOut(Client current) throws InterruptedException {
        System.out.println("\u001B[32mClient " + current.id() + " is paying at " + toPayLine.getName() + "\u001B[0m");
        Thread.sleep(random.nextInt(5_000, 15_000));
        System.out.println("\u001B[34mClient " + current.id() + " has paid at " + toPayLine.getName() + "\u001B[0m");
    }

}
