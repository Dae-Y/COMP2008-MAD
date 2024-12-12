package com.example.basicremotecall;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    SearchResponseViewModel sViewModel;
    ImageViewModel imageViewModel;
    ErrorViewModel errorViewModel;
    Button loadImage;
    ImageView picture;
    ProgressBar progressBar;
    EditText searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(SearchResponseViewModel.class);
        imageViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ImageViewModel.class);
        errorViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ErrorViewModel.class);

        loadImage = findViewById(R.id.loadImage);
        picture = findViewById(R.id.picureId);
        progressBar = findViewById(R.id.progressBarId);
        searchKey = findViewById(R.id.inputSearch);

        picture.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture.setVisibility(View.INVISIBLE);
                String searchValues = searchKey.getText().toString();
                APISearchThread searchThread = new APISearchThread(searchValues,MainActivity.this,sViewModel);
                progressBar.setVisibility(View.VISIBLE);
                searchThread.start();
            }
        });

        sViewModel.response.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Search Complete",Toast.LENGTH_LONG).show();
                ImageRetrievalThread imageRetrievalThread = new ImageRetrievalThread(MainActivity.this, sViewModel, imageViewModel, errorViewModel);
                progressBar.setVisibility(View.VISIBLE);
                imageRetrievalThread.start();

            }
        });

        imageViewModel.image.observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                progressBar.setVisibility(View.INVISIBLE);
                picture.setVisibility(View.VISIBLE);
                picture.setImageBitmap(imageViewModel.getImage());
            }
        });
        errorViewModel.errorCode.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


}