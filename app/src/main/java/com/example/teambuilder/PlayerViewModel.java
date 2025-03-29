package com.example.teambuilder;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.teambuilder.data.Player;
import com.example.teambuilder.data.AppDatabase;
import com.example.teambuilder.data.PlayerDao;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {
    private PlayerDao playerDao;
    private LiveData<List<Player>> allPlayers;

    public PlayerViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        playerDao = db.playerDao();
        allPlayers = playerDao.getAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers() {
        return allPlayers;
    }

    public void insert(Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.insert(player);
        });
    }

    public void update(Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.update(player);
        });
    }

    public void delete(Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.delete(player);
        });
    }
}