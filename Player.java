// Classe Oggetto Player

public class Player {
    
    // variabili d'istanza e costruttore
    private int rating;
    private String name;
    
    private Player(Builder builder) {
        this.rating = builder.rating;
        this.name = builder.name;
    }
    
    // Builder pattern implementation
    public static class Builder {
        private int rating;
        private String name;
        
        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Player build() {
            return new Player(this);
        }
    }

    // metodi
    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }
}