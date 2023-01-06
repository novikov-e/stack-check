package ru.taskmanger.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskList implements Serializable {

    private UUID id;
    private String name;
    private int position;
    private String color;
    private List<Task> tasks = new ArrayList<>();

    public TaskList() {}

    public TaskList(String name) {
        this.name = name;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        String result = "TaskList: " + position + " " + id + " " + name + "\n";
        for (Task task : tasks) {
            result += task.toString() + "\n";
        }
        return result;
    }
}