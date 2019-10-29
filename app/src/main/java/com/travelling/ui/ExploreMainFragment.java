package com.travelling.ui;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.travelling.R;
import com.travelling.databinding.ExploreCardBinding;
import com.travelling.datasource.entity.Explore;
import com.travelling.util.base.ViewModelFragment;
import com.travelling.util.recycler.DataBindingRecyclerAdapter;
import com.travelling.util.recycler.DataBindingRecyclerViewHolder;
import com.travelling.viewmodel.ExploreViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreMainFragment extends ViewModelFragment<ExploreViewModel> {
    private RecyclerView mRecyclerView;
    private ExploreAdapter mExploreAdapter;

    @Override
    protected void initOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //ignore
    }

    @Override
    protected void afterCreateVM(View root) {
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mExploreAdapter = new ExploreAdapter(Objects.requireNonNull(getActivity()).getLayoutInflater(),
                null,
                R.layout.explore_card,
                com.travelling.BR.explore
        );
        mRecyclerView.setAdapter(mExploreAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVM.getAllExplores().observe(getViewLifecycleOwner(), explores -> mExploreAdapter.setDataList(explores));
    }


    @Override
    protected void initVM() {
        mVM = ViewModelProviders.of(requireActivity()).get(ExploreViewModel.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.recycler_view;
    }

    private class ExploreAdapter extends DataBindingRecyclerAdapter<ExploreCardBinding, Explore> {
        public ExploreAdapter(LayoutInflater layoutInflater, List<Explore> dataList, int layoutId, int brId) {
            super(layoutInflater, dataList, layoutId, brId);
        }

        @Override
        protected void initViewHolder(ExploreCardBinding exploreLayoutBinding) {
            exploreLayoutBinding.setHandler(new ExploreHandler());
        }

        @Override
        protected void afterBindVH(DataBindingRecyclerViewHolder holder, int position) {
            Glide.with(holder.itemView)
                    .load(dataList.get(position).image)
                    .centerCrop()
                    .into(db.exploreImage);
        }
    }

    public class ExploreHandler {
        public void navToExplore(View view, Explore explore) {
            NavController navController = Navigation.findNavController(view);
            mVM.setArg(explore);
            navController.navigate(R.id.nav_explore);
        }
    }
}
