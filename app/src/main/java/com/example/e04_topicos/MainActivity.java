package com.example.e04_topicos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button downloadBtn = findViewById(R.id.btn_download);
        EditText txtLink = findViewById(R.id.txt_img_link);
        ImageView imgView = findViewById(R.id.img_picture);

        downloadBtn.setOnClickListener((view) -> {
                Thread threadSec = new Thread(){
                    ProgressBar barraProgresso = findViewById(R.id.progressBar);
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                barraProgresso.setVisibility(View.VISIBLE);
                            }
                        });
                        Bitmap img = MainActivity.this.downloadImage(txtLink.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgView.setImageBitmap(img);
                                barraProgresso.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                };
                threadSec.start();
        });
    }

    private Bitmap downloadImage(String imgLink) {
        try {
            return ImageDownloader.download(imgLink);
        } catch (IOException e) {
            Log.e("MainActivity", e.toString());
            return null;
        }
    }
}