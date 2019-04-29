package com.example.sergio.breakfoodapp.gestorfirebase;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.sergio.breakfoodapp.PrintMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class GestorImagenesFirebase
{
    private GestorImagenesFirebase(){}

    public static void deleteImage(final Activity activity, String contenedor, String nombreConExtension)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageReference = storageReference.child(contenedor + "/" + nombreConExtension);
        imageReference.delete()
        .addOnSuccessListener(activity, new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                PrintMessage.printMessage(activity, "deleteImage", "Se eliminó");
            }
        }).addOnFailureListener(activity, new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                PrintMessage.printMessage(activity, "deleteImage", "No se eliminó");
            }
        });
    }

    public static void uploadLocalImage(final Activity activity, String contenedor, Uri file)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageReference = storageReference.child(contenedor + "/" + file.getLastPathSegment());
        imageReference.putFile(file)
        .addOnSuccessListener(activity, new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                PrintMessage.printMessage(activity, "uploadLocalImage", "Se subió");
            }
        }).addOnFailureListener(activity, new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                PrintMessage.printMessage(activity, "uploadLocalImage", "No se subió");
            }
        });
    }

    public static Task<Uri> getUrlFoto(String contenedor, String nombreConExtension)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        return storageReference.child(contenedor + "/" + nombreConExtension).getDownloadUrl();
    }
}
