package com.example.totem.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.totem.models.Totem;

import java.util.List;

@Dao
public interface TotemDao {
    @Query("SELECT * FROM totem")
    List<Totem> getAll();

    @Query("SELECT COUNT(*) FROM totem")
    Integer size();

    @Query("SELECT * FROM totem WHERE id = :id")
    Totem getById(long id);

    @Insert
    void insert(Totem totem);

    @Update
    void update(Totem totem);

    @Delete
    void delete(Totem totem);
}
