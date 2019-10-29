package com.travelling.viewmodel;

import android.app.Application;

import com.travelling.datasource.entity.Article;
import com.travelling.datasource.entity.Category;
import com.travelling.util.base.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CategoryViewModel extends BaseViewModel {
    private LiveData<List<Category>> allCategories;
    private MutableLiveData<Category> arg = new MutableLiveData<>();

    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Article>> queryArticleInCategory(String cid) {
        return mRepository.queryArticleInCategory(cid);
    }

    @Override
    protected void init() {
        allCategories = mRepository.getAllCategories();
    }

    public Category getArg() {
        return arg.getValue();
    }

    public void setArg(Category arg) {
        this.arg.setValue(arg);
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
}
