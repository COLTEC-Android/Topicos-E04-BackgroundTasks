package br.coltec.gabriel.topicos_04;

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
        ProgressBar progressBar = findViewById(R.id.progessBar);


//        TODO[1]: Processo de download e carregamento da imagem acontecendo na Main Thread, ALTERAR!!
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO[2]: Exibir barra de progresso quando estiver fazendo download da imagem
                progressBar.setVisibility(View.VISIBLE);
                imageDownload.start();

            }
        });
    }


    Thread imageDownload = new Thread(){
        public void run(){

            EditText txtLink = findViewById(R.id.txt_img_link);
            ImageView imgView = findViewById(R.id.img_picture);
            ProgressBar progressBar = findViewById(R.id.progessBar);

                Bitmap img = MainActivity.this.downloadImage(txtLink.getText().toString());

                runOnUiThread(new Runnable() {
                    public void run() {
                        imgView.setImageBitmap(img);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        }
    };

    private Bitmap downloadImage(String imgLink) {
        try {
            return ImageDownloader.download(imgLink);
        } catch (IOException e) {
            Log.e("MainActivity", e.toString());
            return null;
        }
    }
}