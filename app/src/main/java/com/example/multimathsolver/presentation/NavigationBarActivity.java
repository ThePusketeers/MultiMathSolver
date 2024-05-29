package com.example.multimathsolver.presentation;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multimathsolver.R;
import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.models.LimitResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class NavigationBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        RepositoryImpl repository = new RepositoryImpl();
        Disposable disposable = repository.getFunctionLimit("sqrt(36*x^2plus7*xplus49)-6*x", Double.POSITIVE_INFINITY).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LimitResponse>() {
                    @Override
                    public void accept(LimitResponse limitResponse) throws Throwable {
                        Log.d("aboba", limitResponse.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("TAG", throwable.toString());
                    }
                });
    }
}