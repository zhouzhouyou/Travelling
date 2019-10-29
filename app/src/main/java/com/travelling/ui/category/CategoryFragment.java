package com.travelling.ui.category;

import com.travelling.datasource.entity.Article;
import com.travelling.datasource.entity.Category;
import com.travelling.ui.articles.AbstractArticleRecyclerViewFragment;
import com.travelling.viewmodel.CategoryViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class CategoryFragment extends AbstractArticleRecyclerViewFragment {

    @Override
    protected LiveData<List<Article>> getData() {
        CategoryViewModel categoryViewModel = ViewModelProviders.of(requireActivity()).get(CategoryViewModel.class);
        Category category = categoryViewModel.getArg();
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setTitle(category.name);
        return categoryViewModel.queryArticleInCategory(category.cid);
    }
}
