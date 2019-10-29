package com.travelling.ui.articles;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.travelling.R;
import com.travelling.databinding.ArticleCardBinding;
import com.travelling.datasource.entity.Article;
import com.travelling.util.base.ViewModelFragment;
import com.travelling.util.recycler.DataBindingRecyclerAdapter;
import com.travelling.util.recycler.DataBindingRecyclerViewHolder;
import com.travelling.viewmodel.ArticleViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractArticleRecyclerViewFragment extends ViewModelFragment<ArticleViewModel> {
    private RecyclerView mRecyclerView;
    private ArticleAdapter mArticleAdapter;

    @Override
    protected void initOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //ignore
    }

    @Override
    protected void afterCreateVM(View root) {
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mArticleAdapter = new ArticleAdapter(Objects.requireNonNull(getActivity()).getLayoutInflater(),
                null,
                R.layout.article_card,
                com.travelling.BR.article);
        mRecyclerView.setAdapter(mArticleAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getData().observe(getViewLifecycleOwner(), articles -> {
            if (articles.size() == 0) showToast(getString(R.string.no_data), Toast.LENGTH_SHORT);
            mArticleAdapter.setDataList(articles);
        });
    }

    protected abstract LiveData<List<Article>> getData();

    @Override
    protected void initVM() {
        mVM = ViewModelProviders.of(requireActivity()).get(ArticleViewModel.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.recycler_view;
    }

    private class ArticleAdapter extends DataBindingRecyclerAdapter<ArticleCardBinding, Article> {
        public ArticleAdapter(LayoutInflater layoutInflater, List<Article> dataList, int layoutId, int brId) {
            super(layoutInflater, dataList, layoutId, brId);
        }

        @Override
        protected void initViewHolder(ArticleCardBinding ArticleCardBinding) {
            ArticleCardBinding.setHandler(new ArticleHandler());
        }

        @Override
        protected void afterBindVH(DataBindingRecyclerViewHolder holder, int position) {
            Glide.with(holder.itemView)
                    .load(dataList.get(position).image)
                    .centerCrop()
                    .into(db.articleHeaderImage);
        }
    }

    public class ArticleHandler {
        public void navToArticle(View view, Article article) {
            mVM.setArg(article);
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.nav_article);
        }

        public void favoriteButtonClicked(Article article) {
            article.favorite = !article.favorite;
            mVM.updateArticle(article);
            if (article.favorite) return;
            Snackbar.make(Objects.requireNonNull(getView()), R.string.notice_remove, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_undo, v -> {
                        article.favorite = true;
                        mVM.updateArticle(article);
                    })
                    .show();
        }

        public void bookmarkButtonClicked(Article article) {
            article.bookmark = !article.bookmark;
            mVM.updateArticle(article);
            if (article.bookmark) return;
            Snackbar.make(Objects.requireNonNull(getView()), R.string.notice_remove, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_undo, v -> {
                        article.bookmark = true;
                        mVM.updateArticle(article);
                    })
                    .show();
        }

        public void shareButtonClicked(Article article) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, article.title);
            startActivity(Intent.createChooser(intent, getString(R.string.share)));
        }
    }
}
