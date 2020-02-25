package com.example.totem.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.totem.models.Totem;

@Database(entities = {Totem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TotemDao totemDao();
}
