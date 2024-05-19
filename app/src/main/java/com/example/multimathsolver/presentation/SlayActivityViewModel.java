package com.example.multimathsolver.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.GetSLAYSolutionUseCase;
import com.example.multimathsolver.domain.Repository;
import com.example.multimathsolver.domain.SLAY;

import java.util.List;


public class SlayActivityViewModel extends ViewModel {
    private final Repository repository = new RepositoryImpl();
    private final GetSLAYSolutionUseCase useCase = new GetSLAYSolutionUseCase(repository);
    private MutableLiveData<String> output = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private SLAY coeffSLAY;
    private SLAY additionalSLAY;

    public LiveData<String> getOutput() {
        return output;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void solve(List<String> rows) {
        try {
            ListOfStringToSlay parser = new ListOfStringToSlay();
            parser.doSLAY(rows, 0);
            coeffSLAY = parser.getCoeffSLAY();
            additionalSLAY = parser.getAdditionalSLAY();
            output.setValue(useCase.getSLAYSolutionUseCase(coeffSLAY, additionalSLAY));
        } catch (RuntimeException e) {
            error.setValue("Неверный ввод СЛАУ");
            output.setValue("");
        }
    }

    public LiveData<String> getSolution() {
        if (output == null)
            return output = new MutableLiveData<>("Нет решения");
        return output;
    }
}
