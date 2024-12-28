package sks.backend.enums;

public enum Dishes {
    PIZZA("Pizza", 10.5),
    PASTA("Pasta", 8.75),
    SALAD("Sałatka", 5.5),
    SOUP("Zupa", 5),
    DESSERT("Deser", 3.75),
    DRINK("Napój", 2);

    private final String name;
    private final double price;

    Dishes(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
