package com.example.vitalina.moneytrack.ui.auth.presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.vitalina.moneytrack.data.AvatarStorage;
import com.example.vitalina.moneytrack.data.UserFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SignUpPresenter {
    private FirebaseAuth mAuth;
    private SignUpView mView;
    private AvatarStorage avatarStorage;
    private UserFirestore userFirestore;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private boolean needAvatarUpload = false;

    private Single<String> uploadAvatarTask;

    public SignUpPresenter(SignUpView mView) {
        this.mView = mView;
        mAuth = FirebaseAuth.getInstance();
        avatarStorage = new AvatarStorage();
        userFirestore = new UserFirestore();
    }

    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (needAvatarUpload) {
                            mDisposable.add(uploadAvatarTask
                                    .flatMap(it -> userFirestore.createNewUser(mAuth.getUid(), it))
                                    .subscribe((next) -> {
                                        mView.onSignUp(next);
                                    }, (error) -> {
                                        mView.onSignUpFailed(error.getMessage());
                                        error.printStackTrace();
                                    }));
                        } else {
                            mDisposable.add(userFirestore.createNewUser(mAuth.getUid(), null)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(next -> {
                                        mView.onSignUp(next);
                                    }, error -> mView.onSignUpFailed(error.getMessage())));
                        }
                    } else {
                        mView.onSignUpFailed(task.getException().getMessage());
                    }
                });
    }

    public void uploadAvatar(Bitmap bitmap) {
        needAvatarUpload = true;
        uploadAvatarTask = avatarStorage.uploadAvatar(bitmap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void uploadAvatar(Uri bitmap) {
        needAvatarUpload = true;
        uploadAvatarTask = avatarStorage.uploadAvatar(bitmap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }
}



