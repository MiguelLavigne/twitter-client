package com.example.android.twitterclient.ui;

import com.example.android.twitterclient.domain.LoginUser;
import com.example.android.twitterclient.util.Booleans;
import com.example.android.twitterclient.util.ConnectivityProvider;
import com.example.android.twitterclient.util.Funcs;
import javax.inject.Inject;
import rx.Observable;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenter extends BasePresenter<LoginView> {
    private final ConnectivityProvider connectivityProvider;
    private final LoginUser loginUser;

    private CompositeSubscription subscriptions;

    @Inject
    public LoginPresenter(ConnectivityProvider connectivityProvider, LoginUser loginUser) {
        this.connectivityProvider = connectivityProvider;
        this.loginUser = loginUser;
    }

    @Override
    protected void onReady() {
        super.onReady();
        subscriptions = new CompositeSubscription();
        Observable<Boolean> observable = connectivityProvider.observe();
        ConnectableObservable<Boolean> connectivityObservable = observable.publish();
        subscriptions.add(
                connectivityObservable.filter(Booleans.TRUE)
                        .subscribe(connected -> handleConnectivityRestored())
        );
        subscriptions.add(
                connectivityObservable.filter(Funcs.not(Booleans.TRUE))
                        .subscribe(notConnected -> handleLostOfConnectivity())
        );
        subscriptions.add(connectivityObservable.connect());
        setLoginButtonEnabledState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }

    public void onUsernameChange() {
        setLoginButtonEnabledState();
    }

    public void onPasswordChange() {
        setLoginButtonEnabledState();
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
        setLoginButtonEnabledState();
    }

    private void unexpectedError() {
        getView().setProgressGone();
        getView().showUnexpectedError();
        enableAllFields();
    }

    private void loginSuccessful() {
        getView().goBack();
    }

    private void loginFailed() {
        getView().setProgressGone();
        getView().showInvalidCredentialsError();
        enableAllFields();
    }

    private void setLoginButtonEnabledState() {
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
