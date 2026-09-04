package com.myapp.teambuilder.builder;
public class Player {
    
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
}