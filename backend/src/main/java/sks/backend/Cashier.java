package sks.backend;

import sks.backend.enums.ClientStatus;

public class Cashier extends Thread {
    private final Line toPayLine;

    public Cashier(Line toPayLine) {
        this.toPayLine = toPayLine;
    }

    public void run() {
        while (true) {
            if (toPayLine.getSize() > 0) {
                Client client = toPayLine.pollClient();
                client.setStatus(ClientStatus.PAYING);
                System.out.println("Client " + client.id() + " is paying at " + toPayLine.getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
