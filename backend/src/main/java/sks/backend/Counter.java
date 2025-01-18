package sks.backend;

public class Counter extends Thread {
    private boolean isOpen;
    private final Line toPayLine;
    private Cashier cashier;
    private CanteenManager resources;

    public Counter(CanteenManager resources, boolean isOpen, Line toPayLine) {
        this.isOpen = isOpen;
        this.toPayLine = toPayLine;
        this.cashier = new Cashier(toPayLine, resources);
        this.resources = resources;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (isOpen) {
                if (!cashier.isAlive()) {
                    cashier = new Cashier(toPayLine, resources);
                    cashier.start();
                }
            } else {
                if (cashier.isAlive()) {
                    cashier.interrupt();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        if (cashier.isAlive()) {
            cashier.interrupt();
        }
    }


    public boolean isOpen() {
        return isOpen;
    }

    public Line getToPayLine() {
        return toPayLine;
    }
}
