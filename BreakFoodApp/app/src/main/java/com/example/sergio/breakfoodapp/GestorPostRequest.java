package com.example.sergio.breakfoodapp;

import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;

public class GestorPostRequest
{
    //Obtenido de https://stackoverflow.com/questions/2938502/sending-post-data-in-android
    /**
     * Envia un request de post a un servidor
     * @param url url del servidor con la direccion del post
     * @param nameValuePairs BasicNameValuePair(<nombre de propiedad>, <value>)
     * @return HttpResponse objeto json
     */
    @Nullable
    public static HttpResponse postData(String url, List<NameValuePair> nameValuePairs)
    {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try
        {
            // Add your data
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
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
