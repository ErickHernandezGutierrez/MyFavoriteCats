package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showRandomCat();

        Button nextButton = findViewById(R.id.next_image_button);
        nextButton.setOnClickListener(v -> showRandomCat());

        Button saveButton = findViewById(R.id.save_buttom);
        saveButton.setOnClickListener(v -> {
            UserDatabaseHelper dataBase = new UserDatabaseHelper(MainActivity.this );

            TextView idView     = findViewById(R.id.id_view);
            TextView urlView    = findViewById(R.id.url_view);
            TextView widthView  = findViewById(R.id.width_view);
            TextView heightView = findViewById(R.id.height_view);

            String imageID  = (String) idView.getText();
            String imageURL = (String) urlView.getText();
            int imageWidth  = Integer.valueOf( (String)widthView.getText()  );
            int imageHeight = Integer.valueOf( (String)heightView.getText() );

            dataBase.addCat( imageID, imageURL, imageWidth, imageHeight );
        });

        Button myImagesButton = findViewById(R.id.my_images_button);
        myImagesButton.setOnClickListener(v -> {
            UserDatabaseHelper dataBase = new UserDatabaseHelper(MainActivity.this );
            Cursor cursor = dataBase.readAllData();
            ArrayList<CatItem> savedCats = getSavedCatsFromCursor(cursor);

            if(savedCats != null){
                Intent intent = new Intent(MainActivity.this, MyImagesActivity.class);
                intent.putExtra("SAVED_CATS", savedCats);
                startActivity(intent);
            }
        });
    }

    private void showRandomCat() {
        HttpPostAsyncTask task = new HttpPostAsyncTask( MainActivity.this );
        task.execute( "https://api.thecatapi.com/v1/images/search" );
    }

    private ArrayList<CatItem> getSavedCatsFromCursor(Cursor cursor) {
        ArrayList<CatItem> savedCats = new ArrayList<>();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String catapi_id = cursor.getString(1);
                String url       = cursor.getString(2);
                int width        = cursor.getInt(3);
                int height       = cursor.getInt(4);

                CatItem newCat = new CatItem(catapi_id, url, width, height);

                savedCats.add(newCat);
            }
        } else {
            Toast.makeText(getBaseContext(), "No saved cats to show.", Toast.LENGTH_SHORT ).show();
            return null;
        }

        return savedCats;
    }
}