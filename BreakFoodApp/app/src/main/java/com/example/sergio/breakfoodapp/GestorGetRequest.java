package com.example.sergio.breakfoodapp;

import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class GestorGetRequest
{
    //Obtenido de https://stackoverflow.com/questions/2938502/sending-post-data-in-android
    /**
     * Envia un request de post a un servidor
     * @param url url del servidor con la direccion del post
     * @return HttpResponse objeto json
     */
    @Nullable
    public static HttpResponse getData(String url)
    {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        try
        {
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httpget);
            return response;
        }
        catch (ClientProtocolException e)
        {
            Log.e("Error", e.getMessage());
        }
        catch (IOException e)
        {
            Log.e("Error", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
        }
        return null;
    }
}
