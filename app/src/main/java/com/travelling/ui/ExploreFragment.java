package com.travelling.ui;

import android.graphics.Color;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.travelling.R;
import com.travelling.databinding.FragmentExploreBinding;
import com.travelling.datasource.entity.Explore;
import com.travelling.util.base.DataBindingFragment;
import com.travelling.viewmodel.ExploreViewModel;

import java.util.Objects;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class ExploreFragment extends DataBindingFragment<FragmentExploreBinding> {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mImageView;
    private WebView mWebView;
    private MutableLiveData<Explore> mExplore = new MutableLiveData<>();
    private ExploreViewModel mExploreViewModel;

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }

    @Override
    protected void init(FragmentExploreBinding fragmentExploreBinding) {
        mExploreViewModel = ViewModelProviders.of(requireActivity()).get(ExploreViewModel.class);
        mExplore.setValue(mExploreViewModel.getArg());
        mExplore.observe(getViewLifecycleOwner(), explore -> db.setExplore(explore));


        mWebView = fragmentExploreBinding.webView;
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl("file:///android_asset/Explore/" + Objects.requireNonNull(mExplore.getValue()).content + ".html");
        handleBackPressed();

        mCollapsingToolbarLayout = fragmentExploreBinding.collapsingToolbar;
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);

        mImageView = fragmentExploreBinding.exploreHeaderImage;
        Glide.with(this)
                .load(mExplore.getValue().image)
                .centerCrop()
                .into(mImageView);
    }

    private void handleBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (mWebView.canGoBack()) mWebView.goBack();
                        else {
                            Navigation.findNavController(Objects.requireNonNull(getView())).popBackStack(R.id.nav_explore_main, false);
                        }
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_explore;
    }
}
