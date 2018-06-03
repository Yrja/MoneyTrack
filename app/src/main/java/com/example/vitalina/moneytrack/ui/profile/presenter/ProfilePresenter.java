package com.example.vitalina.moneytrack.ui.profile.presenter;

import com.example.vitalina.moneytrack.data.UserFirestore;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter {
    private ProfileView mView;
    private UserFirestore userFirestore;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ProfilePresenter(ProfileView view) {
        userFirestore = new UserFirestore();
        this.mView = view;
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
}
