package com.epita.veliba.service;

public class Station {
    private final int id;
    private final StationItem item;

    public Station(int id, StationItem item) {
        this.id = id;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public StationItem getItem() {
        return item;
    }
}