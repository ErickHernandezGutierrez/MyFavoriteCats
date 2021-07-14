package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;*/

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


        Button button = (Button) findViewById(R.id.next_image);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRandomCat();
            }
        });
    }

    private void showRandomCat(){
        HttpPostAsyncTask task = new HttpPostAsyncTask(this );
        task.execute( "https://api.thecatapi.com/v1/images/search" );
    }

    public class HttpPostAsyncTask extends AsyncTask<String, Void, String>{
        private Context context;

        // This is a constructor that allows you to pass in the JSON body
        public HttpPostAsyncTask(Context context) {
            this.context = context;
        }

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
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
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

    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageAsyncTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... params) {
            String imageURL = params[0];
            Bitmap bmp = null;
            try {
                InputStream inputStream = new URL(imageURL).openStream();
                bmp = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage() );
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    void showCat(String requestURL) {
        //TextView calis = (TextView) findViewById(R.id.calis);

        try{
            URL url = new URL( requestURL );
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream( urlConnection.getInputStream() );
            System.out.println("MAAAAAAAAAAAAAAANTA222");
        }
        catch (MalformedURLException e){
            System.out.println("HOOOOOOOOOOOOOOOOOLA");
            e.printStackTrace();
        }
        catch(IOException e){
            System.out.println("MUUUUUUUUUUUUUUUUUUNDO");
            e.printStackTrace();
        }
    }

    void showCat(){
        //TextView calis = (TextView) findViewById(R.id.calis);
        //calis.setText("calis");

        try{
            URL url = new URL( "https://api.thecatapi.com/v1/images/search?x-api-key=8d3afb4a-8a77-4fab-b6cb-ab3d16f5edc1&format=json" );

            // create the urlConnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // set connection headers
            //urlConnection.setRequestProperty("x-api-key", "8d3afb4a-8a77-4fab-b6cb-ab3d16f5edc1");
            //urlConnection.setRequestProperty("Format", "JSON");

            /*urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");*/

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Request Failed. HTTP Error Code: " + urlConnection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer jsonString = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            urlConnection.disconnect();

            //calis.setText(jsonString);
        }
        catch(Exception e) {
            System.out.println("HOOOOOOOOOOOOOOOOOLA");
            e.printStackTrace();
        }
    }
}