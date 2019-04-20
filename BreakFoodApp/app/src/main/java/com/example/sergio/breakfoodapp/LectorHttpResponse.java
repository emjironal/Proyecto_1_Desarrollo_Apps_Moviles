package com.example.sergio.breakfoodapp;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LectorHttpResponse
{
    public static String leer(HttpResponse httpResponse)
    {
        String result = "";
        try
        {
            Log.i("statusline", httpResponse.getStatusLine().toString());
            HttpEntity entity = httpResponse.getEntity();

            if (entity != null)
            {
                InputStream instream = entity.getContent();
                result = convertStreamToString(instream);
                instream.close();
            }
            return result;
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
            return "Error";
        }
    }

    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }
        catch (IOException e)
        {
            Log.e("ERROR",e.getMessage());
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                Log.e("ERROR",e.getMessage());
            }
        }
        return sb.toString();
    }
}
