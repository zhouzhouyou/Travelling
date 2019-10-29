package com.travelling.ui.authentication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.travelling.R;
import com.travelling.util.base.ViewModelFragment;
import com.travelling.viewmodel.LoginViewModel;


import java.util.Objects;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class LoginFragment extends ViewModelFragment<LoginViewModel> {
    private NavController mNavController;
//    private ImageView mLeft;
//    private ImageView mRight;
    private Button mLoginButton;
//    private Button mRegisterButton;
    private TextView mRigister;
    private EditText mAccount;
    private EditText mPassword;

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }


    /**
     * override the back action item
     *
     * @param item item clicked
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavController.popBackStack(R.id.nav_home, false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVM() {
        mVM = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
    }

    @Override
    protected void afterCreateVM(View root) {
        mNavController = Navigation.findNavController(root);
//        mLeft = root.findViewById(R.id.left_image);
//        mRight = root.findViewById(R.id.right_image);
        mLoginButton = root.findViewById(R.id.login_button);
//        mRegisterButton = root.findViewById(R.id.register_button);
        mRigister = root.findViewById(R.id.register);
        mAccount = root.findViewById(R.id.et_account);
        mPassword = root.findViewById(R.id.et_password);
        handleLogin();
        handleAuthenticationStateChanged(root);
        handleRegister();
        handleBackPressed();
        initPasswordEntering();
    }

    private void initPasswordEntering() {
//        mPassword.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                mLeft.setImageResource(R.drawable.left_password);
//                mRight.setImageResource(R.drawable.right_password);
//            } else {
//                mLeft.setImageResource(R.drawable.left_normal);
//                mRight.setImageResource(R.drawable.right_normal);
//            }
//        });
    }

    private void handleLogin() {
        mLoginButton.setOnClickListener(v -> {
            String account = mAccount.getText().toString();
            String password = mPassword.getText().toString();
            if (account.length() == 0 && password.length() == 0) {
                Snackbar.make(v, getString(R.string.please_check_input), Snackbar.LENGTH_SHORT).show();
                return;
            }

            mVM.authenticate(account, password);
        });
    }

    private void handleAuthenticationStateChanged(View root) {
        mVM.authenticationState.observe(getViewLifecycleOwner(), authenticationState -> {
            switch (authenticationState) {
                case AUTHENTICATED:
                    mNavController.popBackStack();
                    break;
                case INVALID_AUTHENTICATION:
                    Snackbar.make(root, getString(R.string.wrong_password_or_account), Snackbar.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void handleRegister() {
        mRigister.setOnClickListener(v -> mNavController.navigate(R.id.action_loginFragment_to_register_navigation));
    }

    private void handleBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mVM.refuseAuthentication();
                mNavController.popBackStack(R.id.nav_home, false);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.login_fragment;
    }
}
