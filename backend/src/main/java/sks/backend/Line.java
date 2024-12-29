package sks.backend;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Line {
    private final String name;
    private final Queue<Client> clientsInLine;

    public Line(String name) {
        this.clientsInLine = new ConcurrentLinkedQueue<>();
        this.name = name;
    }

    public int getSize() {
        return clientsInLine.size();
    }

    public void addClient(Client client) {
        System.out.println("\u001B[31mClient id: " + client.id() + " joined " + name + "\u001B[0m");
        clientsInLine.add(client);
    }

    public Client peekClient() {
        return clientsInLine.peek();
    }

    public String getName() {
        return name;
    }

    public void removeClient() {
        clientsInLine.remove();
    }
}