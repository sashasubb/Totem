package com.example.totem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    private final int LOADING_DELAY = 4000;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Handler handler = new Handler();
    private Runnable loadingWorker = () -> {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(loadingWorker, LOADING_DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(loadingWorker);
    }
}
