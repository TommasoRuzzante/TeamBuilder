package com.example.teambuilder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilder.data.Player;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PlayerViewModel playerViewModel;
    private PlayerAdapter playerAdapter;
    private MaterialCardView teamsCard;
    private TextView team1Text, team2Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.players_recycler_view);
        playerAdapter = new PlayerAdapter();
        recyclerView.setAdapter(playerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ViewModel
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        playerViewModel.getAllPlayers().observe(this, players -> {
            playerAdapter.submitList(players);
        });

        // Initialize views
        teamsCard = findViewById(R.id.teams_card);
        team1Text = findViewById(R.id.team1_text);
        team2Text = findViewById(R.id.team2_text);

        // Set up FAB click listener
        FloatingActionButton fab = findViewById(R.id.add_player_fab);
        fab.setOnClickListener(v -> showAddPlayerDialog());

        // Set up Build Teams button
        findViewById(R.id.build_teams_button).setOnClickListener(v -> buildTeams());
    }

    private void showAddPlayerDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_player, null);
        TextInputEditText nameInput = dialogView.findViewById(R.id.name_input);
        TextInputEditText ratingInput = dialogView.findViewById(R.id.rating_input);

        new MaterialAlertDialogBuilder(this)
            .setTitle("Add Player")
            .setView(dialogView)
            .setPositiveButton("Add", (dialog, which) -> {
                String name = nameInput.getText().toString();
                String ratingStr = ratingInput.getText().toString();
                if (!name.isEmpty() && !ratingStr.isEmpty()) {
                    int rating = Integer.parseInt(ratingStr);
                    Player player = new Player(name, rating);
                    playerViewModel.insert(player);
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void buildTeams() {
        List<Player> players = playerAdapter.getCurrentList();
        if (players.size() < 2) {
            new MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("Need at least 2 players to build teams")
                .setPositiveButton("OK", null)
                .show();
            return;
        }

        TeamBuilder teamBuilder = new TeamBuilder(new ArrayList<>(players));
        TeamBuilder.Teams teams = teamBuilder.buildTeams();

        // Display teams
        teamsCard.setVisibility(View.VISIBLE);
        team1Text.setText(formatTeamText("Team 1", teams.team1));
        team2Text.setText(formatTeamText("Team 2", teams.team2));
    }

    private String formatTeamText(String teamName, List<Player> players) {
        StringBuilder sb = new StringBuilder(teamName + "\n");
        double avgRating = players.stream()
            .mapToInt(Player::getRating)
            .average()
            .orElse(0.0);
        sb.append(String.format("Average Rating: %.1f\n\n", avgRating));
        
        for (Player player : players) {
            sb.append(player.getName())
              .append(" (")
              .append(player.getRating())
              .append(")\n");
        }
        return sb.toString();
    }
}