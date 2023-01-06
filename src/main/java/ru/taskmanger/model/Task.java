package ru.taskmanger.model;

import java.io.Serializable;
import java.util.UUID;

public class Task implements Serializable {

    private UUID id;
    private String name;
    private boolean state = false;
    private int position;

    public Task() {}

    public Task(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Task: " + position + " " + id + " " + name + " " + state;
    }
}