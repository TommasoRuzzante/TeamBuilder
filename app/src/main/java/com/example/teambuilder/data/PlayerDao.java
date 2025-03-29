package com.example.teambuilder.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM players ORDER BY rating DESC")
    LiveData<List<Player>> getAllPlayers();
    
    @Insert
    void insert(Player player);
    
    @Update
    void update(Player player);
    
    @Delete
    void delete(Player player);
    
    @Query("DELETE FROM players")
    void deleteAll();
}