package com.example.multimathsolver.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.GetFunctionLimitUseCase;
import com.example.multimathsolver.domain.Repository;

public class LimitActivityViewModel extends ViewModel {

    private final Repository repository = new RepositoryImpl();
    private final GetFunctionLimitUseCase useCase = new GetFunctionLimitUseCase(repository);

    private MutableLiveData<String> output = new MutableLiveData<>();

    public void solve(String parameter, String expression) {
        if (parameter.isEmpty()) parameter = "+inf";
        if (expression.isEmpty()) expression = "sqrt(36*x^2plus7*xplus49)-6*x";
        if (parameter.equals("+inf") || parameter.equals("inf")) {
            output = new MutableLiveData<>(useCase.getFunctionLimitUseCase(expression, Double.POSITIVE_INFINITY));
        } else if (parameter.equals("-inf")) {
            output = new MutableLiveData<>(useCase.getFunctionLimitUseCase(expression, Double.NEGATIVE_INFINITY));
        } else {
            try {
                Double.parseDouble(parameter);
            } catch (NumberFormatException ex) {
                parameter = "0";
            }
            output = new MutableLiveData<>(useCase.getFunctionLimitUseCase(expression, Double.parseDouble(parameter)));
        }
    }

    public LiveData<String> getOutput() {
        return output;
    }
}
