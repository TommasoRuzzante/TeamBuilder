// programma per creare 2 squadre
import java.util.*;

public class Roster {
    
    // Variabili e Costruttore
    private Player[] v;
    private int size;
    private int totalSum;

    public Roster(int n) {
        v = new Player[n];
        size = 0;
        totalSum = 0;
    }

    // Metodi pubblici
    public void insert(Player p) throws Exception {
        if(size == v.length)
            throw new Exception();
        if(size == 0)
            v[size] = p;
        else
            insertionAlg(p);
        size++;
        totalSum += p.getRating();
    }

    public String getTeams() {
        List<Player>[] teams = partition(v);
        List<Player> T1 = teams[0];
        List<Player> T2 = teams[1];
        String str = media(T1) + "  Squadra 1:\n";
        for(int i = 0; i < T1.size(); i++)
            str = str + T1.get(i).getName() + "\n";
        str += "\n" + media(T2) + "  Squadra 2:\n";
        for(int i = 0; i < T2.size(); i++)
            str= str + T2.get(i).getName() + "\n";
        return str;
    }

    // Inserimento ordinato
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

    // Calcolo media della squadra
    private double media(List<Player> arr) {
        double e = 0;
        for(int i = 0; i < arr.size(); i++)
            e += (arr.get(i).getRating())/arr.size();
        
        return e;
    }

    // Algoritmo Programmazione Dinamica
    @SuppressWarnings("unchecked")
    private List<Player>[] partition(Player[] values) {
        int size = values.length;
        int target = totalSum / 2;

        // Tabella DP per verificare le somme ottenibili
        boolean[][] dp = new boolean[size + 1][target + 1];
        dp[0][0] = true;  // Possiamo sempre ottenere somma 0 senza elementi

        // Riempimento della tabella DP
        for (int i = 1; i <= size; i++) {
            Player p = values[i - 1];
            for (int s = target; s >= p.getRating(); s--) {
                dp[i][s] = dp[i - 1][s] || dp[i - 1][s - (p.getRating())];
            }
        }

        // Trova la somma più vicina a target ottenibile
        int bestSum = 0;
        for (int s = target; s >= 0; s--) {
            if (dp[size][s]) {
                bestSum = s;
                break;
            }
        }

        // Ricostruzione del sottoinsieme con la somma migliore trovata
        List<Player> set1 = new ArrayList<>();
        List<Player> set2 = new ArrayList<>();
        int w = bestSum;
        for (int i = size; i > 0; i--) {
            if (w >= values[i - 1].getRating() && dp[i - 1][w - (values[i - 1].getRating())]) {
                set1.add(values[i - 1]);
                w -= values[i - 1].getRating();
            } else {
                set2.add(values[i - 1]);
            }
        }

        return new List[]{set1, set2};
    }

}