package com.mythsman.onlinelibrary.component;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by myths on 5/18/17.
 */
@Component
public class ApiComponent {

    public static String doGet(String url){
        try {

            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();

            InputStream inputStream = urlConnection.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            inputStream.close();
            urlConnection.disconnect();
            return sb.toString();

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
