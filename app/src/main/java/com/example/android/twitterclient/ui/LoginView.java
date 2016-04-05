package com.example.android.twitterclient.ui;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.android.twitterclient.App;
import com.example.android.twitterclient.R;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

public class LoginView extends FrameLayout implements BaseView {
    @Bind(R.id.username) EditText username;
    @Bind(R.id.password) EditText password;
    @Bind(R.id.login) Button login;
    @Bind(R.id.coordinator) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.progress_container) View progress;

    @Inject LoginPresenter presenter;

    private CompositeSubscription subscriptions;
    private Snackbar snackbar;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((App) context.getApplicationContext()).component().inject(this);
        subscriptions = new CompositeSubscription();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
        subscriptions.add(
                RxTextView.afterTextChangeEvents(username)
                        .subscribe(event -> presenter.onUsernameChange())
        );
        subscriptions.add(
                RxTextView.afterTextChangeEvents(password)
                        .subscribe(event -> presenter.onPasswordChange())
        );
        subscriptions.add(
                RxView.clicks(login).subscribe(aVoid -> {
                    presenter.onLoginClick();
                })
        );
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        subscriptions.unsubscribe();
        presenter.dropView(this);
    }

    public String getUsername() {
        return username.getText().toString();
    }

    public void setUsernameEnabled() {
        username.setEnabled(true);
    }

    public void setUsernameDisabled() {
        username.setEnabled(false);
    }

    public String getPassword() {
        return password.getText().toString();
    }

    public void setPasswordEnabled() {
        password.setEnabled(true);
    }

    public void setPasswordDisabled() {
        password.setEnabled(false);
    }

    public void setLoginButtonEnabled() {
        login.setEnabled(true);
    }

    public void setLoginButtonDisabled() {
        login.setEnabled(false);
    }

    public void showInvalidCredentialsError() {
        Snackbar.make(coordinatorLayout, "Invalid credentials", Snackbar.LENGTH_LONG).show();
    }

    public void showUnexpectedError() {
        Snackbar.make(coordinatorLayout, "We're sorry we had an unexpected error", Snackbar.LENGTH_LONG).show();
    }

    public void setProgressVisible() {
        progress.setVisibility(View.VISIBLE);
    }

    public void setProgressGone() {
        progress.setVisibility(View.GONE);
    }

    public void hideNoConnectivityView() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    public void showNoConnectivityView() {
        snackbar = Snackbar.make(coordinatorLayout, "No connectivity", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void goBack() {
        ((Activity) getContext()).setResult(Activity.RESULT_OK);
        ((Activity) getContext()).finish();
    }
}
