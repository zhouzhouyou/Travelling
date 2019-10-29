package com.travelling.ui;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.travelling.R;
import com.travelling.databinding.FragmentArticleBinding;
import com.travelling.datasource.entity.Article;
import com.travelling.util.base.DataBindingFragment;
import com.travelling.viewmodel.ArticleViewModel;


import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

public class ArticleFragment extends DataBindingFragment<FragmentArticleBinding> {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private WebView mWebView;
    private ImageView mImageView;
    private MutableLiveData<Article> mArticle = new MutableLiveData<>();
    private ArticleViewModel mArticleViewModel;
    private LinearLayout mFavoriteLayout, mBookmarkLayout, mShareLayout, mMapLayout;
    private View mBGLayout;
    private FloatingActionButton mFab;
    public boolean showFabMenu = false;

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
    protected void init(FragmentArticleBinding fragmentArticleBinding) {
        mArticleViewModel = ViewModelProviders.of(requireActivity()).get(ArticleViewModel.class);
        mArticle.setValue( mArticleViewModel.getArg());
        mArticle.observe(getViewLifecycleOwner(), article -> db.setArticle(article));

        fragmentArticleBinding.setHandler(new ArticleHandler());

        mCollapsingToolbarLayout = fragmentArticleBinding.collapsingToolbar;
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mWebView = fragmentArticleBinding.webView;
        mWebView.loadUrl("file:///android_asset/WebSites/" + Objects.requireNonNull(mArticle.getValue()).content + ".html");

        mFab = fragmentArticleBinding.fab;

        mBGLayout = fragmentArticleBinding.fabBGLayout;
        mFavoriteLayout = fragmentArticleBinding.fabFavoriteLayout;
        mBookmarkLayout = fragmentArticleBinding.fabBookmarkLayout;
        mShareLayout = fragmentArticleBinding.fabShareLayout;
        mMapLayout = fragmentArticleBinding.fabMapLayout;

        mImageView = fragmentArticleBinding.articleHeaderImage;
        Glide.with(this)
                .load(mArticle.getValue().image)
                .centerCrop()
                .into(mImageView);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mArticleViewModel.updateArticle(mArticle.getValue());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_article;
    }

    public class ArticleHandler {
        public void favoriteButtonClicked() {
            Article article = mArticle.getValue();
            article.favorite = !article.favorite;
            mArticle.setValue(article);
        }

        public void bookmarkButtonClicked() {
            Article article = mArticle.getValue();
            article.bookmark = !article.bookmark;
            mArticle.setValue(article);
        }

        public void floatMenu() {
            if (showFabMenu) closeMenu();
            else openMenu();
        }

        public void openMenu() {
            showFabMenu = true;
            mFab.animate().rotationBy(180);
            mBGLayout.setVisibility(View.VISIBLE);
            mFavoriteLayout.setVisibility(View.VISIBLE);
            mBookmarkLayout.setVisibility(View.VISIBLE);
            mShareLayout.setVisibility(View.VISIBLE);
            mMapLayout.setVisibility(View.VISIBLE);
            mFavoriteLayout.animate().translationY(-getResources().getDimension(R.dimen.anim_favorite));
            mBookmarkLayout.animate().translationY(-getResources().getDimension(R.dimen.anim_bookmark));
            mShareLayout.animate().translationY(-getResources().getDimension(R.dimen.anim_share));
            mMapLayout.animate().translationY(-getResources().getDimension(R.dimen.anim_map));
        }

        public void closeMenu() {
            showFabMenu = false;
            mFab.animate().rotationBy(0);
            mBGLayout.setVisibility(View.GONE);
            mFavoriteLayout.animate().translationY(0);
            mBookmarkLayout.animate().translationY(0);
            mShareLayout.animate().translationY(0);
            mMapLayout.animate().translationY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!showFabMenu) {
                        mFavoriteLayout.setVisibility(View.GONE);
                        mBookmarkLayout.setVisibility(View.GONE);
                        mShareLayout.setVisibility(View.GONE);
                        mMapLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        public void mapButtonClicked() {
            startActivity(new Intent(Intent.ACTION_VIEW, getUri()));
        }

        private Uri getUri() {
            String title = mArticle.getValue().title;
            String temp = "https://m.amap.com/search/view/keywords=" + title.replace(" ", "%20");
            return Uri.parse(temp);
        }

        public void shareButtonClicked() {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mArticle.getValue().title);
            startActivity(Intent.createChooser(intent, getString(R.string.share)));
        }
    }
}
