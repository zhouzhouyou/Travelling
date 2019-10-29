package com.travelling.ui.category;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.travelling.R;
import com.travelling.databinding.CategoryMainCardBinding;
import com.travelling.datasource.entity.Category;
import com.travelling.util.base.ViewModelFragment;
import com.travelling.util.recycler.DataBindingRecyclerAdapter;
import com.travelling.util.recycler.DataBindingRecyclerViewHolder;
import com.travelling.viewmodel.CategoryViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryMainFragment extends ViewModelFragment<CategoryViewModel> {
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;

    @Override
    protected void initOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //ignore
    }

    @Override
    protected void afterCreateVM(View root) {
        mRecyclerView = root.findViewById(R.id.recyclerView);

        mCategoryAdapter = new CategoryAdapter(Objects.requireNonNull(getActivity()).getLayoutInflater(),
                null,
                R.layout.category_main_card,
                com.travelling.BR.category);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVM.getAllCategories().observe(getViewLifecycleOwner(), categories -> mCategoryAdapter.setDataList(categories));
    }

    @Override
    protected void initVM() {
        mVM = ViewModelProviders.of(requireActivity()).get(CategoryViewModel.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.recycler_view;
    }

    private class CategoryAdapter extends DataBindingRecyclerAdapter<CategoryMainCardBinding, Category> {
        public CategoryAdapter(LayoutInflater layoutInflater, List<Category> dataList, int layoutId, int brId) {
            super(layoutInflater, dataList, layoutId, brId);
        }

        @Override
        protected void initViewHolder(CategoryMainCardBinding categoryMainCardBinding) {
            categoryMainCardBinding.setHandler(new CategoryMainHandler());
        }

        @Override
        protected void afterBindVH(DataBindingRecyclerViewHolder holder, int position) {
            Glide.with(holder.itemView)
                    .load(dataList.get(position).image)
                    .centerCrop()
                    .into(db.categoryImage);
        }
    }

    public class CategoryMainHandler {
        public void navToCategory(View view, Category category) {
            NavController navController = Navigation.findNavController(view);
            mVM.setArg(category);
            navController.navigate(R.id.nav_category);
        }
    }
}
