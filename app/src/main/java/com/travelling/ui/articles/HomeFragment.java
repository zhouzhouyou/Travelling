package com.travelling.ui.articles;

import android.view.Menu;
import android.view.MenuInflater;

import com.travelling.datasource.entity.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


public class HomeFragment extends AbstractArticleRecyclerViewFragment {
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

    }

    @Override
    protected LiveData<List<Article>> getData() {
        return mVM.getAllArticles();
    }
}
