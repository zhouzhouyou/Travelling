package com.travelling;

import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.navigation.NavigationView;
import com.travelling.util.base.BaseActivity;
import com.travelling.viewmodel.ArticleViewModel;
import com.travelling.viewmodel.LoginViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private LoginViewModel mLoginViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;



    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        initialHeadView();


    }

    private void initialHeadView() {
        View headerView = navigationView.getHeaderView(0);
        TextView name = headerView.findViewById(R.id.username);
        ImageView imageView = headerView.findViewById(R.id.profileImage);
        imageView.setOnClickListener(v -> {
            navController.navigate(R.id.nav_profile);
            drawer.closeDrawer(GravityCompat.START);
        });

        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.authenticationState.observe(this, state -> {
            switch (state) {
                case AUTHENTICATED:
                    Glide.with(headerView)
                            .load("https://uploadbeta.com/api/pictures/random/?key=BingEverydayWallpaperPicture")
                            .placeholder(R.drawable.side_nav_bar)
                            .skipMemoryCache(true)
                            .centerCrop()
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    headerView.setBackground(resource);
                                }
                            });

                    Glide.with(headerView)
                            .load("https://uploadbeta.com/api/pictures/random/")
                            .placeholder(R.drawable.ic_account_circle_white_24dp)
                            .skipMemoryCache(true)
                            .circleCrop()
                            .into(imageView);
                    name.setText(mLoginViewModel.account);
                    break;
                default:
                    Glide.with(headerView)
                            .load(R.drawable.side_nav_bar)
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    headerView.setBackground(resource);
                                }
                            });
                    imageView.setImageResource(R.drawable.ic_account_circle_white_24dp);
                    name.setText(getString(R.string.click_profile_image_to_login));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ViewModelProviders.of(MainActivity.this).get(ArticleViewModel.class).setQuery(query);
                navController.navigate(R.id.nav_search);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
