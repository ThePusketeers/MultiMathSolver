package com.example.multimathsolver.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multimathsolver.data.RepositoryImpl;

public class MainViewModel extends ViewModel {

    private final RepositoryImpl repository = new RepositoryImpl();

    private MutableLiveData<Integer> a = new MutableLiveData<>(0);

    public MutableLiveData<Integer> getA() {
        if (a == null) {
            a = new MutableLiveData<>(0);
        }
        return a;
    }

    public void incrementA() {
        int b = a.getValue();
        a.postValue(repository.incrementByOne(b));
    }


}
