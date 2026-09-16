package com.myapp.teambuilder.builder;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of players and partitions them
 * into two balanced teams using dynamic programming.
 * 
 * The goal of the partitioning algorithm is to split the
 * players into two teams of equal size such that the sum
 * of their ratings is as close as possible (the classic
 * "balanced partition" problem).
 */
public class Roster {
    
    // Instance variables and constructor

    // Array storing all players, kept sorted by rating (ascending)
    private Player[] v;
    // Current number of players in the roster
    private int size;
    // Sum of all players' ratings (used for the partition target)
    private int totalSum;

    /**
     * Creates an empty roster that can hold up to n players.
     * @param n The maximum (and expected) number of players
     */
    public Roster(int n) {
        v = new Player[n];
        size = 0;
        totalSum = 0;
    }

    // Public methods

    /**
     * Adds a player to the roster, maintaining the internal
     * array sorted by rating in ascending order via insertion sort.
     * Also accumulates the total rating sum.
     * @param p The player to insert
     */
    public void insert(Player p) {
        if(size == 0)
            v[size] = p;
        else
            insertionAlg(p);
        size++;
        totalSum += p.getRating();
    }

    /**
     * Partitions the roster into two balanced teams and
     * returns a human-readable string listing each team's members.
     * @return A formatted string with "Squadra 1" and "Squadra 2" rosters
     */
    public String getTeams() {
        List<Player>[] teams = partition(v);
        List<Player> T1 = teams[0];
        List<Player> T2 = teams[1];
        String str = "Squadra 1:\n";
        for(int i = 0; i < T1.size(); i++)
            str = str + T1.get(i).getName() + "\n";
        str += "\n" + "Squadra 2:\n";
        for(int i = 0; i < T2.size(); i++)
            str= str + T2.get(i).getName() + "\n";
        return str;
    }

    // Sorted insertion helper

    /**
     * Inserts a player into the correct position within the
     * already-sorted portion of the array using insertion sort.
     * Precondition: v[0..size-1] is sorted by rating ascending.
     * @param p The player to insert into v[0..size]
     */
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

    // Dynamic programming partitioning algorithm

    /**
     * Splits the given player array into two teams of equal size
     * whose total ratings are as balanced as possible.
     * 
     * Uses a 3D DP table:
     *   dp[i][j][k] = true if using the first i players,
     *                 we can select exactly k of them whose
     *                 ratings sum to exactly j.
     * 
     * After filling the DP table, the best achievable sum
     * closest to (totalSum / 2) with exactly (size/2) players
     * is found, then the selected players are traced back.
     * 
     * @param values The sorted array of players
     * @return An array of two Lists: [team1, team2]
     */
    @SuppressWarnings("unchecked")
    private List<Player>[] partition(Player[] values) {
        int size = values.length;
        // Each team must have exactly half of the players
        int playersPerTeam = size / 2;
        // Target sum: we want team1's total to be as close as possible to half the total
        int target = totalSum / 2;

        // 3D DP table to track achievable sums
        boolean[][][] dp = new boolean[size + 1][target + 1][playersPerTeam + 1];
        
        // Base case: zero players, zero sum, zero selected = achievable
        dp[0][0][0] = true;
        
        // Fill the DP table iteratively
        for (int i = 1; i <= size; i++) {
            Player p = values[i - 1];
            int rating = p.getRating();
            
            for (int j = 0; j <= target; j++) {
                for (int k = 0; k <= playersPerTeam; k++) {
                    // Option 1: do not include the i-th player
                    dp[i][j][k] = dp[i-1][j][k];
                    
                    // Option 2: include the i-th player (if possible)
                    if (k > 0 && j >= rating) {
                        dp[i][j][k] = dp[i][j][k] || dp[i-1][j-rating][k-1];
                    }
                }
            }
        }
        
        // Find the largest achievable sum <= target with exactly playersPerTeam players
        int bestSum = 0;
        for (int j = target; j >= 0; j--) {
            if (dp[size][j][playersPerTeam]) {
                bestSum = j;
                break;
            }
        }
        
        // Backtrack through the DP table to reconstruct team1 and team2
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();
        
        int remainingSum = bestSum;
        int remainingPlayers = playersPerTeam;
        
        for (int i = size; i > 0; i--) {
            Player p = values[i-1];
            int rating = p.getRating();
            
            // Check if this player was part of the optimal selection for team1
            if (remainingPlayers > 0 && remainingSum >= rating && 
                dp[i-1][remainingSum-rating][remainingPlayers-1]) {
                
                team1.add(p);
                remainingSum -= rating;
                remainingPlayers--;
            } else {
                // If not in team1, the player goes to team2
                team2.add(p);
            }
        }
        
        return new List[]{team1, team2};
    }

}
