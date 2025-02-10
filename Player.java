// Classe Oggetto Player

public class Player {
    
    // variabili d'istanza e costruttore
    private double rating;
    private String name;
    public Player(double v, String n) {
        rating= v;
        name= n;
    }

    // metodi
    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

}