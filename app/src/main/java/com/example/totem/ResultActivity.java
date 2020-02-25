package com.example.totem;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.totem.repo.TotemRepo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends AppCompatActivity {

    private static final int PERMISION_REQUEST_CODE = 1203;

    @BindView(R.id.result_image)
    ImageView resultImage;
    @BindView(R.id.result)
    TextView resultText;
    @BindView(R.id.result_description)
    TextView resultDescription;
    @BindView(R.id.share)
    TextView share;

    private TotemRepo totemRepo = TotemRepo.instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        initAboutTxt();
    }

    @Override
    protected void onStart() {
        super.onStart();
        prepareResult();
    }

    private void initAboutTxt() {
        String htmlTaggedString = "<u>" + getResources().getString(R.string.share) + "</u>";
        Spanned textSpan = android.text.Html.fromHtml(htmlTaggedString);
        share.setText(textSpan);
    }

    private void prepareResult() {
        String title = totemRepo.title();
        String description = totemRepo.description();
        int image = totemRepo.image();

        resultImage.setImageResource(image);
        resultText.setText(title);
        resultDescription.setText(description);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takeScreenshot();
        }
    }

    @OnClick(R.id.share)
    public void onAboutClick() {
        //Intent intent = Utils.getShareIntent();
        //startActivity(intent);
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (ActivityCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED) {
            takeScreenshot();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    PERMISION_REQUEST_CODE);
        }
    }

    private void takeScreenshot() {
        Date now = new Date();
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String mPath = Environment.getExternalStorageDirectory().toString()
                + File.separator + now + ".jpg";
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        File imageFile = new File(mPath);
        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
            outputStream.flush();
            startActivity(Utils.getShareIntent(bitmap, this));
        } catch (Throwable e) {
            Log.d(getClass().getName(), Objects.requireNonNull(e.getMessage()));
        }
    }
}
