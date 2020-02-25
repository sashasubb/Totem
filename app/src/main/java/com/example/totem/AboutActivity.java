package com.example.totem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spanned;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    private static final String POLICY_URL = "https://google.com";
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.app_date)
    TextView appDate;
    @BindView(R.id.policy)
    TextView policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        prepareViews();
        initLinkTxt();
    }

    private void prepareViews() {
        appName.setText(R.string.app_name);
        appDate.setText(R.string.app_date);
    }

    @OnClick(R.id.policy)
    public void onPolicyClick() {
        Uri uri = Uri.parse(POLICY_URL);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void initLinkTxt() {
        String htmlTaggedString = "<u>" + getResources().getString(R.string.policy) + "</u>";
        Spanned textSpan = android.text.Html.fromHtml(htmlTaggedString);
        policy.setText(textSpan);
    }
}
