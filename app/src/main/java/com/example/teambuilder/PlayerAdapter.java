package com.example.teambuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilder.data.Player;
import com.google.android.material.card.MaterialCardView;

public class PlayerAdapter extends ListAdapter<Player, PlayerAdapter.PlayerViewHolder> {

    protected PlayerAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Player> DIFF_CALLBACK = new DiffUtil.ItemCallback<Player>() {
        @Override
        public boolean areItemsTheSame(@NonNull Player oldItem, @NonNull Player newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Player oldItem, @NonNull Player newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                   oldItem.getRating() == newItem.getRating();
        }
    };

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = getItem(position);
        holder.nameText.setText(player.getName());
        holder.ratingText.setText(String.valueOf(player.getRating()));
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView ratingText;
        private final MaterialCardView cardView;

        PlayerViewHolder(View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            nameText = itemView.findViewById(R.id.player_name);
            ratingText = itemView.findViewById(R.id.player_rating);
        }
    }
}