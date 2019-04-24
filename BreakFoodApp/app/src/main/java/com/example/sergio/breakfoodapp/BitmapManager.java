package com.example.sergio.breakfoodapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BitmapManager
{
    public static byte[] bitmapToByteArray(Bitmap bitmap)
    {
        //Obtenido de https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null/13758637
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] byteArray)
    {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static ArrayList<String> byteToStrings(byte[] byteArray)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        for(Byte b : byteArray)
        {
            arrayList.add(b.toString());
        }
        return arrayList;
    }

    public static byte[] stringsToByte(ArrayList<String> arrayList)
    {
        byte[] bytes = new byte[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++)
        {
            bytes[i] = Byte.decode(arrayList.get(i));
        }
        return bytes;
    }
}
