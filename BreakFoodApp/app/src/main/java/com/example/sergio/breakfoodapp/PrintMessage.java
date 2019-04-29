package com.example.sergio.breakfoodapp;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class PrintMessage
{
    public static void printMessage(Activity activity, String label, String message)
    {
        Log.i(label, message);
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
