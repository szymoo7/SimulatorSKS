package sks.backend;

public class Counter extends Thread {
    private boolean isOpen;
    private final Line toPayLine;
    private Cashier cashier;

    public Counter(boolean isOpen, Line toPayLine) {
        this.isOpen = isOpen;
        this.toPayLine = toPayLine;
        this.cashier = new Cashier(toPayLine);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (isOpen) {
                if (!cashier.isAlive()) {
                    cashier = new Cashier(toPayLine);
                    cashier.start();
                }
            } else {
                if (cashier.isAlive()) {
                    cashier.interrupt();
                }
            }
            try {
                Thread.sleep(100); // Check the flag every 100ms
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
