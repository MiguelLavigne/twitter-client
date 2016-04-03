package com.example.android.twitterclient.ui;

import android.app.Activity;
import com.example.android.twitterclient.data.TwitterApi;
import com.example.android.twitterclient.domain.LoginUser;
import com.example.android.twitterclient.util.Booleans;
import com.example.android.twitterclient.util.ConnectivityProvider;
import com.example.android.twitterclient.util.Funcs;
import javax.inject.Inject;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenter extends BasePresenter<LoginView> {
    private final TwitterApi twitterApi;
    private final ConnectivityProvider connectivityProvider;
    private final LoginUser loginUser;

    private CompositeSubscription subscriptions;

    @Inject
    public LoginPresenter(TwitterApi twitterApi, ConnectivityProvider connectivityProvider, LoginUser loginUser) {
        this.twitterApi = twitterApi;
        this.connectivityProvider = connectivityProvider;
        this.loginUser = loginUser;
    }

    @Override
    protected void onReady() {
        super.onReady();
        subscriptions = new CompositeSubscription();
        ConnectableObservable<Boolean> connectivityObservable = connectivityProvider.observe().publish();
        subscriptions.add(
                connectivityObservable.filter(Booleans.TRUE)
                        .subscribe(connected -> handleConnectivityRestored())
        );
        subscriptions.add(
                connectivityObservable.filter(Funcs.not(Booleans.TRUE))
                        .subscribe(notConnected -> handleLostOfConnectivity())
        );
        subscriptions.add(connectivityObservable.connect());
        enableLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }

    public void onUsernameChange() {
        enableLogin();
    }

    public void onPasswordChange() {
        enableLogin();
    }

    public void onLoginClick() {
        String username = getView().getUsername();
        String password = getView().getPassword();

        getView().setProgressVisible();
        disableAllFields();

        loginUser.execute(username, password)
                .subscribe(result -> {
                    if (result.successful) {
                        loginSuccessful();
                    } else {
                        loginFailed();
                    }
                }, t -> unexpectedError());
    }

    private void handleLostOfConnectivity() {
        getView().showNoConnectivityView();
        disableAllFields();
    }

    private void handleConnectivityRestored() {
        getView().hideNoConnectivityView();
        enableAllFields();
    }

    private void disableAllFields() {
        getView().setUsernameDisabled();
        getView().setPasswordDisabled();
        getView().setLoginButtonDisabled();
    }

    private void enableAllFields() {
        getView().setUsernameEnabled();
        getView().setPasswordEnabled();
        enableLogin();
    }

    private void unexpectedError() {
        getView().setProgressGone();
        getView().showUnexpectedError();
        enableAllFields();
    }

    private void loginSuccessful() {
        ((Activity) getView().getContext()).setResult(Activity.RESULT_OK);
        ((Activity) getView().getContext()).finish();
    }

    private void loginFailed() {
        getView().setProgressGone();
        getView().showInvalidCredentialsError();
        enableAllFields();
    }

    private void enableLogin() {
        if (validateField()) {
            getView().setLoginButtonEnabled();
        } else {
            getView().setLoginButtonDisabled();
        }
    }

    private boolean validateField() {
        return getView().getUsername().length() > 4 && getView().getPassword().length() > 5;
    }
}
