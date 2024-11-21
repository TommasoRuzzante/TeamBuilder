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

    public int compareTo(Player aa) {
        if(rating == aa.getRating())
            return 0;
        else if(rating > aa.getRating())
            return 1;
        else return -1;
    }

}