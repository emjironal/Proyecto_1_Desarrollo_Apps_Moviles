package com.example.sergio.breakfoodapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileInputStream;

public class GestorImagenes
{
    private static final GestorImagenes instance = new GestorImagenes();
    private StorageReference storageReference;

    private GestorImagenes()
    {
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public Task<Void> borrarFoto(String contenedor, String nombre)
    {
        StorageReference imageReference = storageReference.child(contenedor + "/" + nombre + ".jpg");
        return imageReference.delete();
    }

    public UploadTask subirFoto(FileInputStream file, String contenedor, String nombre)
    {
        StorageReference imageReference = storageReference.child(contenedor + "/" + nombre + ".jpg");
        return imageReference.putStream(file);
    }

    public static GestorImagenes getInstance()
    {
        return instance;
    }

    public Task<Uri> getUriFoto(String contenedor, String nombre)
    {
        return storageReference.child(contenedor + "/" + nombre + ".jpg").getDownloadUrl();
    }
}
