package com.example.multimathsolver.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.GetFunctionLimitUseCase;
import com.example.multimathsolver.domain.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LimitActivityViewModel extends ViewModel {

    private final static Double DEFAULT_PARAMETER = Double.POSITIVE_INFINITY;
    private final static String DEFAULT_EXPRESSION = "sqrt(36*x^2plus7*xplus49)-6*x";
    private final Repository repository = new RepositoryImpl();
    private final GetFunctionLimitUseCase useCase = new GetFunctionLimitUseCase(repository);
    private final MutableLiveData<String> output = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isProgress = new MutableLiveData<>();

    public void solve(String parameter, String expression) {
        isProgress.setValue(true);
        double parameterDoubleValue;
        if (parameter.isEmpty() || parameter.equals("+inf") || parameter.equals("inf"))
            parameterDoubleValue = DEFAULT_PARAMETER;
        else if (parameter.equals("-inf")) {
            parameterDoubleValue = Double.NEGATIVE_INFINITY;
        } else {
            try {
                parameterDoubleValue = Double.parseDouble(parameter);
            } catch (NumberFormatException ex) {
                error.setValue("Неверный ввод параметра");
                output.setValue("");
                isProgress.setValue(false);
                return;
            }
        }
        if (expression.isEmpty()) expression = DEFAULT_EXPRESSION;

        Disposable disposable = useCase.getFunctionLimitUseCase(expression, parameterDoubleValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((limitResponse) -> {
                    if (!limitResponse.getLimitResult().getSuccess()) {
                        error.setValue("Неверный ввод предела");
                        output.setValue("");
                        isProgress.setValue(false);
                        return;
                    }
                    try {
                        String response = limitResponse.getLimitResult().getPods().get(0).getSubpods().get(0).getPlaintext();
                        response = response.substring(response.lastIndexOf('=') + 1);
                        output.setValue(response);
                        isProgress.setValue(false);

                    } catch (Exception e) {
                        error.setValue(e.getMessage());
                        output.setValue("");
                        isProgress.setValue(false);
                    }
                }, (throwable -> {
                    error.setValue(throwable.getMessage());
                    output.setValue("");
                    isProgress.setValue(false);
                    })
                );
        compositeDisposable.add(disposable);
    }

    public LiveData<String> getOutput() {
        return output;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsProgress() {
        return isProgress;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
