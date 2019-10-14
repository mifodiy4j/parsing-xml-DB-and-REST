package ru.mifodiy67.project.model;

public class Item implements Comparable<Item> {

    private int id;
    private Integer containedIn;
    private String cololr;

    public Item(int id, Integer containedIn) {
        this.id = id;
        this.containedIn = containedIn;
    }

    public int getId() {
        return id;
    }

    public Integer getContainedIn() {
        return containedIn;
    }

    public String getCololr() {
        return cololr;
    }

    public void setCololr(String cololr) {
        this.cololr = cololr;
    }

    @Override
    public int compareTo(Item item) {
        return Integer.compare(id, item.getId());
    }
}
