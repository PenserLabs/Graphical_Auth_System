package com.app.graphicalauthsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //var
    private int chunkNumbers, rows, cols, chunkHeight, chunkWidth;
    private ArrayList<Bitmap> chunkedImages;
    private Boolean split = true;

    //widgets
    private ImageView imageView;
    private GridView gridView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageVie_IV);
        gridView = findViewById(R.id.gridView_GV);
        button = findViewById(R.id.button_BT);

        chunkedImages = new ArrayList<>();

    }

    public void splitImage(View view) {

        if (split){
            chunkedImages.clear();
            chunkNumbers = 16;
            rows = cols = (int) Math.sqrt(chunkNumbers);

            //Getting the scaled bitmap of the source image
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

            chunkHeight = bitmap.getHeight()/rows;
            chunkWidth = bitmap.getWidth()/cols;

            //xCoOrd and yCoOrd are the pixel positions of the image chunks
            int yCoord = 0;
            for(int x=0; x<rows; x++){
                int xCoord = 0;
                for(int y=0; y<cols; y++){
                    chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                    xCoord += chunkWidth;
                }
                yCoord += chunkHeight;
            }

            initGridView(chunkedImages);

            split = false;
            button.setText("Combine");
        }else {

            imageView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            button.setText("Split");
            split = true;

        }

    }

    private void initGridView(ArrayList<Bitmap> chunkedImages) {

        gridView.setAdapter(new ImageAdapter(this, chunkedImages));
        gridView.setNumColumns(cols);
        imageView.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);

    }
}
