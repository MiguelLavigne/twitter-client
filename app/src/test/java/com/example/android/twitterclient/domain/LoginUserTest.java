package com.example.android.twitterclient.domain;

import com.example.android.twitterclient.TestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(TestRunner.class)
public class LoginUserTest {

    @Mock private TwitterApi twitterApi;
    @Mock private UserPersistence userPersistence;

    @InjectMocks private LoginUser sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveCredentialsOnSuccessfulLogin() {
        when(twitterApi.login(anyString(), anyString())).thenReturn(successfulLoginResponse());
        sut.execute("username", "password").subscribe();
        verify(userPersistence).save(any(User.class));
    }

    private Observable<LoginResponse> successfulLoginResponse() {
        return Observable.just(new LoginResponse(true, "username"));
    }
}