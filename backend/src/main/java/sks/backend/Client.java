package sks.backend;

import sks.backend.enums.ClientStatus;
import sks.backend.enums.Dishes;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Thread {

    private static final Random random = new Random();
    private static final AtomicInteger idCreator = new AtomicInteger(0);
    private final int id;
    private String order;
    private ClientStatus status;
    private CanteenManager resources;

    public Client(CanteenManager resources) {
        this.id = idCreator.getAndIncrement();
        this.status = ClientStatus.ENTERING;
        this.resources = resources;
    }

    @Override
    public void run() {
        joinQueue();
        System.out.println("\u001B[34m" + this + "\u001B[0m");
        try {
            this.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
        CanteenManager.lock.lock();
        try {
            Line shortestLine = findShorterLine(resources.getLines());
            System.out.println("\u001B[31mClient id: " + id + " joined " + shortestLine.getName() + "\u001B[0m");
            Thread.sleep(2000);
            shortestLine.addClient(this);
            this.status = ClientStatus.IN_QUEUE;
            selectOrder();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            CanteenManager.lock.unlock();
        }
    }

    private void goToCounter() {
        CanteenManager.lock.lock();
        try {
            Line shortestLine = findShorterLine(resources.getToPayLines());
            System.out.println("\u001B[31mClient id: " + id + " is waiting to pay in " + shortestLine.getName() + "\u001B[0m");
            Thread.sleep(2000);
            shortestLine.addClient(this);
            this.status = ClientStatus.IN_QUEUE;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            CanteenManager.lock.unlock();
        }
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    private Line findShorterLine(List<Line> lines) {
        lines.forEach(line -> System.out.println("Line: " + line.getName() + ", Size: " + line.getSize()));
        return lines.stream()
                .min(Comparator.comparingInt(Line::getSize))
                .orElseThrow();
    }

    public int id() {
        return id;
    }

    private void selectOrder() {
        this.order = Dishes.values()[random.nextInt(Dishes.values().length)].name();
    }
}
