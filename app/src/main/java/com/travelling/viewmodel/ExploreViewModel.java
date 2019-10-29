package com.travelling.viewmodel;

import android.app.Application;

import com.travelling.datasource.entity.Explore;
import com.travelling.util.base.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ExploreViewModel extends BaseViewModel {
    private LiveData<List<Explore>> allExplores;
    private MutableLiveData<Explore> arg = new MutableLiveData<>();

    public ExploreViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        allExplores = mRepository.getAllExplores();
    }

    public LiveData<List<Explore>> getAllExplores() {
        return allExplores;
    }

    public Explore getArg() {
        return arg.getValue();
    }

    public void setArg(Explore arg) {
        this.arg.setValue(arg);
    }
}
