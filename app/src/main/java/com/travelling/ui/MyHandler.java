package com.travelling.ui;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.travelling.R;
import com.travelling.datasource.entity.Article;
import com.travelling.viewmodel.ArticleViewModel;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MyHandler {
    private static final String TAG = "MyHandler";

    @BindingAdapter("srcCompat")
    public static void setImageViewSrcCompat(ImageView view, Drawable drawable) {
        Log.d(TAG, "setImageViewSrcCompat() called with: button = [" + view + "], drawable = [" + drawable + "]");
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("srcCompat")
    public static void setImageViewSrcCompat(ImageView view, @DrawableRes int resId) {
        Log.d(TAG, "setImageViewSrcCompat() called with: view = [" + view + "], resId = [" + resId + "]");
        view.setImageResource(resId);
    }
}
