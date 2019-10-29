package com.travelling.ui;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.travelling.R;
import com.travelling.util.base.ViewModelFragment;
import com.travelling.viewmodel.LoginViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ProfileFragment extends ViewModelFragment<LoginViewModel> {
    private NavController mNavController;
    private ImageView background;
    private ImageView picture;
    private TextView username;

    @Override
    protected void initOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //ignore
    }

    @Override
    protected void afterCreateVM(View root) {
        mNavController = Navigation.findNavController(root);
        mVM.authenticationState.observe(getViewLifecycleOwner(), authenticationState -> {
            switch (authenticationState) {
                case AUTHENTICATED:
                    initProfile(root);
                    break;
                case UNAUTHENTICATED:
                    mNavController.navigate(R.id.nav_login);
                    break;
                default:
                    //TODO
            }
        });
    }

    private void initProfile(View root) {
        background = root.findViewById(R.id.profileBackground);
        picture = root.findViewById(R.id.profileImage);
        username = root.findViewById(R.id.username);

        Glide.with(root)
                .load("https://uploadbeta.com/api/pictures/random/?key=BingEverydayWallpaperPicture")
                .placeholder(R.mipmap.beijing_zoo)
                .skipMemoryCache(true)
                .centerCrop()
                .into(background);
        Glide.with(root)
                .load("https://uploadbeta.com/api/pictures/random/")
                .placeholder(R.drawable.ic_account_circle_white_24dp)
                .skipMemoryCache(true)
                .circleCrop()
                .into(picture);
        username.setText(mVM.account);
    }

    @Override
    protected void initVM() {
        mVM = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }
}
