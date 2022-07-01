package br.ufmg.coltec.topicos_e04_backgroundtasks;
import static android.view.View.VISIBLE;

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
        ProgressBar bar = findViewById(R.id.barra);

//        TODO[1]: Processo de download e carregamento da imagem acontecendo na Main Thread, ALTERAR!!
        downloadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO[2]: Exibir barra de progresso quando estiver fazendo download da imagem
                bar.setVisibility(VISIBLE);
                Thread t = new Thread(){
                    public void run(){
                        //thread "secundaria"
                        Bitmap img = MainActivity.this.downloadImage(txtLink.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //thread "principal"
                                bar.setVisibility(View.INVISIBLE);
                                imgView.setImageBitmap(img);


                            }
                        });
                    }
                };
                t.start();
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