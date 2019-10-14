package ru.mifodiy67.project.model;

public class Box implements Comparable<Box> {

    private int id;
    private Integer containedIn;

    public Box(int id, Integer containedIn) {
        this.id = id;
        this.containedIn = containedIn;
    }

    public int getId() {
        return id;
    }

    public Integer getContainedIn() {
        return containedIn;
    }

    @Override
    public int compareTo(Box box) {
        return Integer.compare(id, box.getId());
    }
}
