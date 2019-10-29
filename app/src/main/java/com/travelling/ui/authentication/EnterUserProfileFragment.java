package com.travelling.ui.authentication;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.travelling.R;
import com.travelling.util.base.ViewModelFragment;
import com.travelling.viewmodel.RegistrationViewModel;

import java.util.Objects;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class EnterUserProfileFragment extends ViewModelFragment<RegistrationViewModel> {
    private EditText mFirstNameET;
    private EditText mLastNameET;
    private Button mNavNext;
    private NavController mNavController;

    @Override
    protected void initVM() {
        mVM = ViewModelProviders.of(requireActivity()).get(RegistrationViewModel.class);
    }

    @Override
    protected void afterCreateVM(View root) {
        mNavController = Navigation.findNavController(root);
        mNavNext = root.findViewById(R.id.nav_chooseUserPassword);
        mFirstNameET = root.findViewById(R.id.et_first_name);
        mLastNameET = root.findViewById(R.id.et_last_name);
        handleNextButton();
        handleRegistrationStateChanged();
        handleBackPressed();
    }

    private void handleNextButton() {
        mNavNext.setOnClickListener(v -> {
            String firstName = mFirstNameET.getText().toString();
            String lastName = mLastNameET.getText().toString();
            if (firstName.equals("") || lastName.equals("")) {
                Snackbar.make(v, getString(R.string.please_check_input), Snackbar.LENGTH_SHORT).show();
                return;
            }
            mVM.collectProfileData(firstName, lastName);
        });
    }

    private void handleRegistrationStateChanged() {
        mVM.getRegistrationState().observe(getViewLifecycleOwner(), state -> {
            if (state == RegistrationViewModel.RegistrationState.COLLECT_USER_PASSWORD) {
                mNavController.navigate(R.id.nav_chooseUserPassword);
            }
        });
    }

    private void handleBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        mVM.userCancelledRegistration();
                        mNavController.popBackStack(R.id.nav_login, false);
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.enter_user_profile_fragment;
    }
}
