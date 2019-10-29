package com.travelling.ui.articles;

import com.travelling.datasource.entity.Article;


import java.util.List;


import androidx.lifecycle.LiveData;

public class FavoriteFragment extends AbstractArticleRecyclerViewFragment {
    @Override
    protected LiveData<List<Article>> getData() {
        return mVM.getFavoriteArticles();
    }
}
