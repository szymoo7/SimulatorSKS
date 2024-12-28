package sks.backend;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Line {
    private String name;
    private final Queue<Client> clientsInLine;

    public Line(String name) {
        this.clientsInLine = new ConcurrentLinkedQueue<>();
        this.name = name;
    }

    public int getSize() {
        return clientsInLine.size();
    }

    public boolean addClient(Client client) {
        return clientsInLine.add(client);
    }

    public Client pollClient() {
        return clientsInLine.poll();
    }

    public String getName() {
        return name;
    }
}