package ru.taskmanger.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project implements Serializable {

    private UUID id;
    private String name;
    private int position;
    private List<Column> columns = new ArrayList<>();

    public Project() {}

    public Project(String name) {
        this.name = name;
    }

    public Project(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Project(UUID id, String name, List<Column> columns) {
        this.id = id;
        this.name = name;
        this.columns = columns;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        String result =  "Project: " + position + " " + id + " " + name + "\n";
        for (Column column : columns) {
            result += column.toString() + "\n";
        }
        return result;
    }
}