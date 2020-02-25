package com.example.totem.repo;

import androidx.annotation.DrawableRes;

import com.example.totem.App;
import com.example.totem.R;
import com.example.totem.db.TotemDao;

public class TotemRepo {
    private static final TotemRepo instance = new TotemRepo();
    private TotemDao totemDao;

    public static TotemRepo instance() {
        return instance;
    }

    private TotemRepo() {
        totemDao = App.getInstance().getDatabase().totemDao();
    }

    public String title() {
        return App.getInstance().getApplicationContext().getResources().getString(R.string.result);
    }

    public String description() {
        return App.getInstance().getApplicationContext().getResources().getString(R.string.result_text);
    }

    @DrawableRes
    public int image() {
        return R.drawable.wolf_icon;
    }
}
