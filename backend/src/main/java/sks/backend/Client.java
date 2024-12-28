package sks.backend;

import sks.backend.enums.ClientStatus;

import java.util.Comparator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Thread {

    private static Random random = new Random();
    private static AtomicInteger idCreator = new AtomicInteger(0);
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
        System.out.println(this);
        joinQueue();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", order='" + order + '\'' +
                ", status=" + status +
                '}';
    }

    private boolean joinQueue() {
        resources.lock.lock();
        try {
            Line shortestLine = findShorterLine(resources.getLines());
            return shortestLine.addClient(this);
        } finally {
            resources.lock.unlock();
        }
    }

    private Line findShorterLine(Set<Line> lines) {
        return lines.stream().min(Comparator.comparingInt(Line::getSize)).orElseThrow();
    }
}
