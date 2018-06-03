package com.example.vitalina.moneytrack.data;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AvatarStorage {
    private StorageReference storageRef;
    private FirebaseStorage storage;

    public AvatarStorage() {
        storage = FirebaseStorage.getInstance();


    }
    public Single<String> uploadAvatar(Bitmap bitmap){
        return Single.create(emitter->{
            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            storageRef = storage.getReference().child(id+".jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = storageRef.putBytes(data);
            uploadTask.addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(taskSnapshot -> {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> emitter.onSuccess(uri.toString()))
                        .addOnFailureListener(emitter::onError);

            });
        });
    }
    public Single<String> uploadAvatar(Uri uri){
        return Single.create(emitter->{
            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            storageRef = storage.getReference().child(id+".jpg");
            UploadTask uploadTask = storageRef.putFile(uri);
            uploadTask.addOnFailureListener(emitter::onError)
                    .addOnSuccessListener(taskSnapshot -> {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        storageRef.getDownloadUrl().addOnSuccessListener(img -> emitter.onSuccess(img.toString()))
                                .addOnFailureListener(emitter::onError);
                    });
        });
    }
    public Single getAvatar(String token) {
        return Single.create(emitter -> {
            StorageReference pathReference = storageRef.child("images/" + token + ".png");
            pathReference.getDownloadUrl().addOnCompleteListener(
                    task -> {
                        emitter.onSuccess(task.getResult());
                    }
            );
        });
    }
}
