package com.myapp.teambuilder.builder;
import java.util.ArrayList;
import java.util.List;

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
        int playersPerTeam = size / 2;
        int target = totalSum / 2;

        // Tabella DP 3d per verificare le somme ottenibili
        boolean[][][] dp = new boolean[size + 1][target + 1][playersPerTeam + 1];
        
        // Caso base
        dp[0][0][0] = true;
        
        // Riempimento della tabella DP
        for (int i = 1; i <= size; i++) {
            Player p = values[i - 1];
            int rating = p.getRating();
            
            for (int j = 0; j <= target; j++) {
                for (int k = 0; k <= playersPerTeam; k++) {
                    // Non prendere questo giocatore
                    dp[i][j][k] = dp[i-1][j][k];
                    
                    // Se possibile prendi questo giocatore
                    if (k > 0 && j >= rating) {
                        dp[i][j][k] = dp[i][j][k] || dp[i-1][j-rating][k-1];
                    }
                }
            }
        }
        
        // Trova la migliore somma possibile con playersPerTeam giocatori
        int bestSum = 0;
        for (int j = target; j >= 0; j--) {
            if (dp[size][j][playersPerTeam]) {
                bestSum = j;
                break;
            }
        }
        
        // Costruzione delle due squadre con la somma migliore trovata
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();
        
        int remainingSum = bestSum;
        int remainingPlayers = playersPerTeam;
        
        for (int i = size; i > 0; i--) {
            Player p = values[i-1];
            int rating = p.getRating();
            
            // Se questo giocatore ci da la migliore somma
            if (remainingPlayers > 0 && remainingSum >= rating && 
                dp[i-1][remainingSum-rating][remainingPlayers-1]) {
                
                team1.add(p);
                remainingSum -= rating;
                remainingPlayers--;
            } else {
                team2.add(p);
            }
        }
        
        return new List[]{team1, team2};
    }

}