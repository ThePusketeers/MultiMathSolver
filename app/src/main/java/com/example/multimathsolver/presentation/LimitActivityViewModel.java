package com.example.multimathsolver.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.GetFunctionLimitUseCase;
import com.example.multimathsolver.domain.Repository;

public class LimitActivityViewModel extends ViewModel {

    private final static String DEFAULT_PARAMETER = "+inf";
    private final static String DEFAULT_EXPRESSION = "sqrt(36*x^2plus7*xplus49)-6*x";
    private final Repository repository = new RepositoryImpl();
    private final GetFunctionLimitUseCase useCase = new GetFunctionLimitUseCase(repository);

    private final MutableLiveData<String> output = new MutableLiveData<>();

    public boolean solve(String parameter, String expression) {
        if (parameter.isEmpty()) parameter = DEFAULT_PARAMETER;
        if (expression.isEmpty()) expression = DEFAULT_EXPRESSION;
        try {
            if (parameter.equals("+inf") || parameter.equals("inf")) {
                output.setValue(useCase.getFunctionLimitUseCase(expression, Double.POSITIVE_INFINITY));
            } else if (parameter.equals("-inf")) {
                output.setValue(useCase.getFunctionLimitUseCase(expression, Double.NEGATIVE_INFINITY));
            } else {
                try {
                    Double.parseDouble(parameter);
                } catch (NumberFormatException ex) {
                    parameter = "0";
                }
                output.setValue(useCase.getFunctionLimitUseCase(expression, Double.parseDouble(parameter)));
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public LiveData<String> getOutput() {
        return output;
    }
}
