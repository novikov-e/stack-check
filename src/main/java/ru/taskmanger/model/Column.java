package ru.taskmanger.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Column implements Serializable {

    private UUID id;
    private String name;
    private int position;
    private List<TaskList> taskLists = new ArrayList<>();

    public Column() {}

    public Column(String name) {
        this.name = name;
    }

    public Column(UUID id, String name, int position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Column(UUID id, String name, int position, List<TaskList> taskLists) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.taskLists = taskLists;
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

    public List<TaskList> getTaskLists() {
        return taskLists;
    }

    public void setTaskLists(List<TaskList> taskLists) {
        this.taskLists = taskLists;
    }

    @Override
    public String toString() {
        String result =  "Column: " + position + " " + id + " " + name + "\n";
        for (TaskList taskList : taskLists) {
            result += taskList.toString() + "\n";
        }
        return result;
    }
}