// Classe Oggetto Player

public class Player {
    
    // variabili d'istanza e costruttore
    private int rating;
    private String name;
    public Player(int v, String n) {
        rating= v;
        name= n;
    }

    // metodi
    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

}