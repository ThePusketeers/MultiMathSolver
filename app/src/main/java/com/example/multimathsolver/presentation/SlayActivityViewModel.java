package com.example.multimathsolver.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.GetSLAYSolutionUseCase;
import com.example.multimathsolver.domain.Repository;
import com.example.multimathsolver.domain.SLAY;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class SlayActivityViewModel extends ViewModel {
    private final Repository repository = new RepositoryImpl();
    private final GetSLAYSolutionUseCase useCase = new GetSLAYSolutionUseCase(repository);
    private final MutableLiveData<String> output = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<String> getOutput() {
        return output;
    }

    public LiveData<String> getError() {
        return error;
    }

    public boolean add(String row, List<String> list) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[0-9])[a-z0-9.+\\-]*=[0-9.+\\-]*$", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(row).matches()) {
            list.add(row);
            return true;
        }
        else {
            error.setValue("Неверный ввод строки");
            return false;
        }
    }

    public void solve(List<String> rows) {
        try {
            rows = replaceSpace(rows);
            ListOfStringToSlay parser = new ListOfStringToSlay();
            parser.doSLAY(rows, 0);
            SLAY coeffSLAY = parser.getCoeffSLAY();
            SLAY additionalSLAY = parser.getAdditionalSLAY();
            output.setValue(useCase.getSLAYSolutionUseCase(coeffSLAY, additionalSLAY));
        } catch (RuntimeException e) {
            error.setValue("Неверный ввод СЛАУ");
            output.setValue("");
        }
    }

    public void delete(List<String> rows, int position) {
        rows.remove(position);
    }

    public LiveData<String> getSolution() {
        if (output == null)
            return output = new MutableLiveData<>("Нет решения");
        return output;
    }

    private List<String> replaceSpace(List<String> rows) {
        List<String> newRows = new ArrayList<>();
        for (String s : rows)
            newRows.add(s.replace(" ", ""));
        return newRows;
    }

}
