package com.myapp.teambuilder.builder;
import java.io.Serializable;

public class Player implements Serializable {
    
    // Variabili d'istanza e costruttore
    private String name;
    private int rating;
    
    public Player(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    // Metodi base
    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    // Metodi base
    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) { this.rating = rating; }
}