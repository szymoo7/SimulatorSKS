package sks.backend;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Line {

    //TODO: Zrobić możliwość ustawienia kolejki o ograniczonym rozmiarze
    private volatile Queue<Client> clientsInLine;
    private final Cook cook;
    public final Lock lock;

    public Line(Cook cook) {
        this.clientsInLine = new LinkedList<>();
        this.cook = cook;
        this.lock = new ReentrantLock(true);
    }

    public int getSize() {
        lock.lock();
        try {
            return clientsInLine.size();
        } finally {
            lock.unlock();
        }
    }

    public boolean addClient(Client client) {
        lock.lock();
        try {
            return clientsInLine.add(client);
        } finally {
            lock.unlock();
        }
    }


}
