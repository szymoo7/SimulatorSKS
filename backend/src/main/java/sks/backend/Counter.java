package sks.backend;

public class Counter {
    private boolean isOpen;
    private Line toPayLine = new Line("Kasa 1");

    public Counter(boolean isOpen) {
        this.isOpen = isOpen;
    }


}
