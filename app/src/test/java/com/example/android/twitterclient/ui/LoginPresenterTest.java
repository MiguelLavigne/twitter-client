package com.example.android.twitterclient.ui;

import android.support.annotation.NonNull;
import com.example.android.twitterclient.domain.LoginResponse;
import com.example.android.twitterclient.domain.LoginUser;
import com.example.android.twitterclient.util.ConnectivityProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest {
    private static final String VALID_USERNAME = "xxxxx";
    private static final String VALID_PASSWORD = "xxxxxx";

    @Mock private LoginView view;
    @Mock private ConnectivityProvider connectivityProvider;
    @Mock private LoginUser loginUser;

    @InjectMocks private LoginPresenter sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(connectivityProvider.observe()).thenReturn(Observable.<Boolean>empty());
    }

    @Test
    public void loginButton_disableOnInvalidUsernameLength() throws Exception {
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn(VALID_PASSWORD);

        sut.takeView(view);

        verify(view).setLoginButtonDisabled();
    }

    @Test
    public void loginButton_disableOnInvalidPasswordLength() throws Exception {
        when(view.getUsername()).thenReturn(VALID_USERNAME);
        when(view.getPassword()).thenReturn("");

        sut.takeView(view);

        verify(view).setLoginButtonDisabled();
    }

    @Test
    public void loginButton_enableOnValidCredentialsLength() throws Exception {
        when(view.getUsername()).thenReturn(VALID_USERNAME);
        when(view.getPassword()).thenReturn(VALID_PASSWORD);

        sut.takeView(view);

        verify(view).setLoginButtonEnabled();
    }

    @Test
    public void loginButton_Click() throws Exception {
        when(loginUser.execute(anyString(), anyString())).thenReturn(Observable.empty());
        when(view.getUsername()).thenReturn(VALID_USERNAME);
        when(view.getPassword()).thenReturn(VALID_PASSWORD);

        sut.takeView(view);
        sut.onLoginClick();

        verify(view).setProgressVisible();
        verify(loginUser).execute(VALID_USERNAME, VALID_PASSWORD);
    }

    @Test
    public void login_invalidCredentials() throws Exception {
        when(loginUser.execute(anyString(), anyString())).thenReturn(failedLoginResponse());
        when(view.getUsername()).thenReturn(VALID_USERNAME);
        when(view.getPassword()).thenReturn(VALID_PASSWORD);

        sut.takeView(view);
        sut.onLoginClick();

        verify(view).setProgressGone();
        verify(view).showInvalidCredentialsError();
    }

    @Test
    public void connectivityLost() throws Exception {
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("");

        when(connectivityProvider.observe()).thenReturn(Observable.just(Boolean.FALSE));

        sut.takeView(view);

        verify(view).showNoConnectivityView();
    }

    @Test
    public void connectivityRestored() throws Exception {
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("");

        when(connectivityProvider.observe()).thenReturn(Observable.just(Boolean.TRUE));

        sut.takeView(view);

        verify(view).hideNoConnectivityView();
    }

    @NonNull
    private Observable<LoginResponse> failedLoginResponse() {
        return Observable.just(new LoginResponse(false, null));
    }
}