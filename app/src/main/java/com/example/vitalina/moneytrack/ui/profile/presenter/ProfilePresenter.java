package com.example.vitalina.moneytrack.ui.profile.presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.vitalina.moneytrack.data.AvatarStorage;
import com.example.vitalina.moneytrack.data.UserFirestore;
import com.example.vitalina.moneytrack.model.entities.User;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter {
    private ProfileView mView;
    private UserFirestore userFirestore;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AvatarStorage avatarStorage;

    public ProfilePresenter(ProfileView view) {
        userFirestore = new UserFirestore();
        this.mView = view;
        avatarStorage = new AvatarStorage();
    }

    public void getUser() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            disposable.add(userFirestore.getUser(FirebaseAuth.getInstance().getUid())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(next -> {
                                mView.displayProfile(next);
                            },
                            error -> {
                                mView.userLoadError(error.getMessage());
                            }));
        }
    }

    public void updateUser(User user) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            disposable.add(userFirestore.updateUser(user)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {

                            },
                            error -> {

                            }));
        }
    }

    public void updateAvatar(Bitmap bitmap, User user) {

        Disposable d = avatarStorage.uploadAvatar(bitmap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((next) -> {
                  user.setAvatar(next);
                  updateUser(user);
                }, (error) -> {

                });

    }

    public void updateAvatar(Uri bitmap, User user) {
        Disposable d = avatarStorage.uploadAvatar(bitmap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((next) -> {
                    user.setAvatar(next);
                    updateUser(user);
                }, (error) -> {

                });
    }
}
