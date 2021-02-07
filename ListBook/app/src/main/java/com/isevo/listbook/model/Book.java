package com.isevo.listbook.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_table")
public class Book {



    @PrimaryKey(autoGenerate = true)

    private int id;
    private String title;
    private String description;
    private int priority;
    private String uriImage;
    private String datum;
    private String category;
    //konstruktor

    public Book(String title, String description, int priority,String uriImage,String datum,String category) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.uriImage=uriImage;
        this.datum=datum;
        this.category=category;
    }

    public void setId(int id) {
        this.id = id;
    }


    //metode za dohvat podataka


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }


    public String getUriImage() {
        return uriImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatum() {
        return datum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
