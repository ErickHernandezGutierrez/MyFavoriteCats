package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import com.example.myfavoritecats.DownloadImageAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showRandomCat();

        //showCat();
        //showCat( "https://api.thecatapi.com/v1/images/search?format=json" );

        //TextView calis = (TextView) findViewById(R.id.calis);
        //calis.setText( getJSON("https://api.thecatapi.com/v1/images/search?format=json") );
        //calis.setText( getJSON("https://api.thecatapi.com/v1/images/search?x-api-key=8d3afb4a-8a77-4fab-b6cb-ab3d16f5edc1&format=json") );


        Button nextButton = (Button) findViewById(R.id.next_image_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRandomCat();
            }
        });

        Button saveButton = (Button) findViewById(R.id.save_buttom);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserDatabaseHelper db = new UserDatabaseHelper(MainActivity.this );
                db.addCat("c9JL47_hX", "https://cdn2.thecatapi.com/images/c9JL47_hX.png", 2232, 1920);
            }
        });

        Button myImagesButton = (Button) findViewById(R.id.my_images_button);
        myImagesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, MyImagesActivity.class );
                startActivity(intent);
            }
        });
    }

    private void showRandomCat(){
        //HttpPostAsyncTask task = new HttpPostAsyncTask(this );
        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute( "https://api.thecatapi.com/v1/images/search" );
    }

    public class HttpPostAsyncTask extends AsyncTask<String, Void, String>{
        //private Context context;

        // This is a constructor that allows you to pass in the JSON body
        /*public HttpPostAsyncTask(Context context) {
            this.context = context;
        }*/
        public HttpPostAsyncTask() {}

        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected String doInBackground(String... params) {
            try{
                String requestURL = params[0];
                URL url = new URL( requestURL );
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("format", "json");
                //urlConnection.setRequestProperty("x-api-key", "8d3afb4a-8a77-4fab-b6cb-ab3d16f5edc1");

                InputStream result = urlConnection.getInputStream();
                System.out.println("MAAAAAAAAAAAAAAANTA222");
                return convertResultToString(urlConnection, result);
            }
            catch (MalformedURLException e){
                System.out.println("HOOOOOOOOOOOOOOOOOLA");
                e.printStackTrace();
            }
            catch(IOException e){
                System.out.println("MUUUUUUUUUUUUUUUUUUNDO");
                e.printStackTrace();
            }

            return null;
        }

        private String convertResultToString(HttpURLConnection urlConnection, InputStream result){
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                return stringBuilder.toString();
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String jsonString){
            try {
                // there is always only one JSON object
                JSONArray  jsonArray  = new JSONArray(jsonString);
                JSONObject jsonObject = jsonArray.getJSONObject(0 );
                String imageURL = (String) jsonObject.get("url");

                try {
                    ImageView imageView = (ImageView) findViewById(R.id.image_view);
                    DownloadImageAsyncTask imageLoader = new DownloadImageAsyncTask(imageView);
                    imageLoader.execute(imageURL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}