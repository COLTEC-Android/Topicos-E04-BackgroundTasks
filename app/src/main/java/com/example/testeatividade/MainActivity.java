package com.example.testeatividade;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button downloadBtn ;
    EditText txtLink ;
    ImageView imgView ;
    ProgressBar progress;

    //Linka os componentes XML com variáveis Java.
    private void startComponents(){
        downloadBtn = findViewById(R.id.btn_download);
        txtLink = findViewById(R.id.txt_img_link);
        imgView = findViewById(R.id.img_picture);
        progress = findViewById(R.id.progress);
    }

    //Retorna uma Thread que realiza o Download da imagem.
    private Thread threadDownload(){
        return new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap img = MainActivity.this.downloadImage(txtLink.getText().toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgView.setImageBitmap(img);
                        progress.setVisibility(View.INVISIBLE);//Após fazer o download da imagem e setar na tela, remove o ProgressBar

                    }
                });
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startComponents();

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);//Inicia o ProgressBar quando vai iniciar o download.
                threadDownload().start(); //Inicializa o download em uma Thread secundaria
            }
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