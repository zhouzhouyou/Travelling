package com.travelling.ui;

import com.travelling.datasource.entity.Article;
import com.travelling.ui.articles.AbstractArticleRecyclerViewFragment;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SearchResultFragment extends AbstractArticleRecyclerViewFragment {
    @Override
    protected LiveData<List<Article>> getData() {
        return mVM.queryArticles(mVM.getQuery());
    }
}
