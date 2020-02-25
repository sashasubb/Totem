package com.example.totem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final long FINGERPRINT_TIME = 7000;

    @BindView(R.id.question_image)
    ImageView circleCenter;
    @BindView(R.id.finger_print)
    ImageView fingerPrint;
    @BindView(R.id.hold)
    TextView hold;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.ripple)
    ImageView ripple;

    Animation animationUp;
    Animation animationDown;
    Handler handler = new Handler();
    Runnable fingerWorker = () -> {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    };

    private boolean isRunning = false;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        prepareFingerBtn();
        initAboutTxt();
        animationUp = AnimationUtils.loadAnimation(this, R.anim.ripple_anim);
        animationDown = AnimationUtils.loadAnimation(this, R.anim.ripple_anim_down);
        prepareRipple();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = false;
        wasRunning = false;
        prepareByState(MainState.START);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRunning = false;
        wasRunning = false;
        prepareByState(MainState.START);
    }

    private void prepareRipple() {
        animationUp.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ripple.clearAnimation();
                        if (isRunning)
                            ripple.startAnimation(animationDown);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }
        );
        animationDown.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ripple.clearAnimation();
                        if (isRunning)
                            ripple.startAnimation(animationUp);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }
        );
    }

    private void initAboutTxt() {
        String htmlTaggedString = "<u>" + getResources().getString(R.string.about) + "</u>";
        Spanned textSpan = android.text.Html.fromHtml(htmlTaggedString);
        about.setText(textSpan);
    }

    private void prepareByState(MainState state) {
        switch (state) {
            case START:
                circleCenter.setImageResource(R.drawable.placeholder_questionmark);
                if (wasRunning) {
                    hold.setText(R.string.try_again);
                } else {
                    hold.setText(R.string.hold_to_start);
                }
                hold.setTextColor(getResources().getColor(R.color.hold_to_start));
                fingerPrint.setImageResource(R.drawable.fingerprint_start);
                ripple.setVisibility(View.INVISIBLE);
                ripple.clearAnimation();
                return;
            case EXPLORING:
                circleCenter.setImageResource(R.drawable.placeholder_progress);
                hold.setText(R.string.exploring);
                hold.setTextColor(getResources().getColor(R.color.exploring));
                fingerPrint.setImageResource(R.drawable.fingerprint_exploring);
                ripple.setVisibility(View.VISIBLE);
                ripple.startAnimation(animationUp);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void prepareFingerBtn() {
        fingerPrint.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    prepareByState(MainState.EXPLORING);
                    isRunning = true;
                    wasRunning = true;
                    handler.postDelayed(fingerWorker, FINGERPRINT_TIME);
                    break;
                case MotionEvent.ACTION_UP:
                    isRunning = false;
                    if (handler.hasCallbacks(fingerWorker)) {
                        prepareByState(MainState.START);
                        handler.removeCallbacks(fingerWorker);
                    }
                    break;
            }
            return true;
        });
    }

    @OnClick(R.id.about)
    public void onAboutClick() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    enum MainState {
        EXPLORING,
        START
    }
}
