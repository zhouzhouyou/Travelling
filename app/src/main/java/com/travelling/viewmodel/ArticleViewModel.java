package com.travelling.viewmodel;

import android.app.Application;

import com.travelling.datasource.entity.Article;
import com.travelling.util.base.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ArticleViewModel extends BaseViewModel {
    private LiveData<List<Article>> allArticles;
    private MutableLiveData<Article> arg = new MutableLiveData<>();
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ArticleViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        allArticles = mRepository.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    public LiveData<List<Article>> getFavoriteArticles() {
        return mRepository.getFavoriteArticles();
    }

    public LiveData<List<Article>> getBookmarkArticles() {
        return mRepository.getBookmarkArticles();
    }

    public LiveData<List<Article>> queryArticles(String query) {
        return mRepository.queryArticles(query);
    }

    public Article queryArticle(String aid) {
        if (allArticles != null && allArticles.getValue() != null)
            for (Article article : allArticles.getValue())
                if (aid.equals(article.aid)) return article;
        return mRepository.queryArticle(aid);
    }

    public void updateArticle(Article... articles) {
        mRepository.updateArticle(articles);
    }

    public Article getArg() {
        return arg.getValue();
    }

    public void setArg(Article arg) {
        this.arg.setValue(arg);
    }
}
