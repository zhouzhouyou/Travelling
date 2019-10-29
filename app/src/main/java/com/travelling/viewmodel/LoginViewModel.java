package com.travelling.viewmodel;

import android.app.Application;

import com.travelling.util.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<AuthenticationState> authenticationState;
    public String account;

    public enum AuthenticationState {
        UNAUTHENTICATED,
        AUTHENTICATED,
        INVALID_AUTHENTICATION
    }


    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void authenticate(String authToken) {
        //TODO judge if true
        authenticationState.setValue(AuthenticationState.AUTHENTICATED);
    }

    public void authenticate(String account, String password) {
        if (passwordIsValidateForUsername(account, password)) {
            this.account = account;
            authenticationState.setValue(AuthenticationState.AUTHENTICATED);
        } else {
            authenticationState.setValue(AuthenticationState.INVALID_AUTHENTICATION);
        }
    }

    private boolean passwordIsValidateForUsername(String account, String password) {
        return true;
    }

    public void refuseAuthentication() {
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    }

    @Override
    protected void init() {
        authenticationState = new MutableLiveData<>();
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
        account = "";
    }
}
