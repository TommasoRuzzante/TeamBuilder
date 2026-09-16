package com.myapp.teambuilder.builder;
import java.io.Serializable;

/**
 * Represents a player with a name and a skill rating.
 * Implements Serializable to allow passing Player objects
 * between Android activities via Intent extras.
 */
public class Player implements Serializable {
    
    // Instance variables
    // The player's display name
    private String name;
    // The player's skill rating (higher = better player)
    private int rating;
    
    /**
     * Constructs a new Player with the given name and rating.
     * @param name   The player's name
     * @param rating The player's skill rating
     */
    public Player(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    // Getter methods

    /** @return The player's name */
    public String getName() {
        return name;
    }

    /** @return The player's skill rating */
    public int getRating() {
        return rating;
    }

    // Setter methods

    /**
     * Updates the player's name.
     * @param name The new name for the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the player's skill rating.
     * @param rating The new rating for the player
     */
    public void setRating(int rating) { this.rating = rating; }
}
