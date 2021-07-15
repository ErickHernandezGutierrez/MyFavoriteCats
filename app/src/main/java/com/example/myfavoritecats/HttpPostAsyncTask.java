package com.example.myfavoritecats;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.myfavoritecats.DownloadImageAsyncTask;
import com.example.myfavoritecats.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpPostAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;

    // This is a constructor that allows you to pass in the JSON body
        /*public HttpPostAsyncTask(Context context) {
            this.context = context;
        }*/
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
            return convertResultToString(urlConnection, result);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
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
            JSONArray jsonArray  = new JSONArray(jsonString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String imageURL = (String) jsonObject.get("url");

            try {
                ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.image_view);
                DownloadImageAsyncTask imageDownloader = new DownloadImageAsyncTask(imageView);
                imageDownloader.execute(imageURL);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}