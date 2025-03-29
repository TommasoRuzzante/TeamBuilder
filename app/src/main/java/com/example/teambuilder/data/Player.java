package com.example.teambuilder.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "players")
public class Player {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int rating;
    private String name;
    
    public Player(String name, int rating) {
        this.rating = rating;
        this.name = name;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
}