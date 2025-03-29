package com.example.teambuilder;

import com.example.teambuilder.data.Player;
import java.util.ArrayList;
import java.util.List;

public class TeamBuilder {
    private List<Player> players;
    private int totalSum;

    public TeamBuilder(List<Player> players) {
        this.players = players;
        this.totalSum = players.stream().mapToInt(Player::getRating).sum();
    }

    public Teams buildTeams() {
        int size = players.size();
        int playersPerTeam = size / 2;
        int target = totalSum / 2;

        // DP table for checking obtainable sums
        boolean[][][] dp = new boolean[size + 1][target + 1][playersPerTeam + 1];
        dp[0][0][0] = true;

        // Fill DP table
        for (int i = 1; i <= size; i++) {
            Player p = players.get(i - 1);
            int rating = p.getRating();

            for (int j = 0; j <= target; j++) {
                for (int k = 0; k <= playersPerTeam; k++) {
                    // Don't take this player
                    dp[i][j][k] = dp[i-1][j][k];

                    // Take this player if possible
                    if (k > 0 && j >= rating) {
                        dp[i][j][k] = dp[i][j][k] || dp[i-1][j-rating][k-1];
                    }
                }
            }
        }

        // Find best possible sum with playersPerTeam players
        int bestSum = 0;
        for (int j = target; j >= 0; j--) {
            if (dp[size][j][playersPerTeam]) {
                bestSum = j;
                break;
            }
        }

        // Reconstruct teams
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>(players);

        // Backtrack to find team1 members
        int currentSum = bestSum;
        int currentCount = playersPerTeam;
        for (int i = size; i > 0 && currentCount > 0; i--) {
            Player p = players.get(i - 1);
            int rating = p.getRating();

            if (currentSum >= rating && currentCount > 0 && 
                dp[i-1][currentSum-rating][currentCount-1]) {
                team1.add(p);
                team2.remove(p);
                currentSum -= rating;
                currentCount--;
            }
        }

        return new Teams(team1, team2);
    }

    public static class Teams {
        public final List<Player> team1;
        public final List<Player> team2;

        public Teams(List<Player> team1, List<Player> team2) {
            this.team1 = team1;
            this.team2 = team2;
        }
    }
}