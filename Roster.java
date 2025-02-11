// programma per creare 2 squadre

public class Roster {
    
    // array e costruttore
    private Player[] v;
    private int size;

    public Roster(int n) {
        v = new Player[n];
        size = 0;
    }

    // metodi pubblici
    public void insert(Player p) throws Exception {
        if(size == v.length)
            throw new Exception();
        if(size == 0)
            v[size] = p;
        else
            insertionAlg(p);
        size++;
    }

    public String getTeams() {
        Player[] T1 = team1();
        Player[] T2 = team2();
        String str = "Squadra 1:\n";
        for(int i = 0; i < T1.length; i++)
            str = str + T1[i].getName() + "\n";
        str += "\nSquadra 2:\n";
        for(int i = 0; i < T2.length; i++)
            str= str + T2[i].getName() + "\n";
        return str;
    }

    // inserimento ordinato
    private void insertionAlg(Player p) {
        v[size] = p;
        for(int i = size - 1; i >= 0; i--) {
            if(p.getRating() < v[i].getRating()) {
                v[i + 1] = v[i];
                v[i] = p;
            }
            else break;
        }
    }

    // creazione 2 squadre
    private Player[] team1() {
        Player[] T1= new Player[size/2];
        for(int i = 0, k = 0; i < T1.length; i++, k++)
            T1[i] = v[i+k];
        return T1;
    }

    private Player[] team2() {
        Player[] T2= new Player[size/2];
        for(int i = 0, k = 1; i < T2.length; i++, k++)
            T2[i] = v[i+k];
        return T2;
    }

    // calcolo bilanciamento squadre
    private double media(Player[] arr) {
        return 0;
    }

    private double media_quadratica(Player[] arr) {
        return 0;
    }

    private double varianza(Player[] arr) {
        return 0;
    }

}