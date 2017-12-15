package com.justin.Demo;

import java.util.Date;

public class ToDoItem {
    private final String title;
    private String description;
    private Date dueDate;

    public ToDoItem(String title, String description, Date dueDate){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public Date getDueDate(){
        return this.dueDate;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }
}
