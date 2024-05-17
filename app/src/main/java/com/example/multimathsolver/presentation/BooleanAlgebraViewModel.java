package com.example.multimathsolver.presentation;

import androidx.lifecycle.ViewModel;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.BooleanFunction;
import com.example.multimathsolver.domain.GetAbbreviatedDNFUseCase;
import com.example.multimathsolver.domain.GetDeadLockedDNFUseCase;
import com.example.multimathsolver.domain.GetMinimalDNFUseCase;
import com.example.multimathsolver.domain.GetPerfectCNFUseCase;
import com.example.multimathsolver.domain.GetPerfectDNFUseCase;
import com.example.multimathsolver.domain.GetPolynomialUseCase;
import com.example.multimathsolver.domain.IncorrectFunctionInput;
import com.example.multimathsolver.domain.Repository;

import java.util.ArrayList;
import java.util.List;

public class BooleanAlgebraViewModel extends ViewModel {
    private final Repository repository = new RepositoryImpl();
    private final GetPerfectDNFUseCase useCasePerfectDNF = new GetPerfectDNFUseCase(repository);
    private final GetPerfectCNFUseCase useCasePerfectCNF = new GetPerfectCNFUseCase(repository);
    private final GetDeadLockedDNFUseCase useCaseDeadLocked = new GetDeadLockedDNFUseCase(repository);
    private final GetMinimalDNFUseCase useCaseMinimalDNF = new GetMinimalDNFUseCase(repository);
    private final GetAbbreviatedDNFUseCase useCaseAbbreviatedDNF = new GetAbbreviatedDNFUseCase(repository);
    private final GetPolynomialUseCase useCasePolynomial = new GetPolynomialUseCase(repository);


    public List<String> solve(String expression) throws IncorrectFunctionInput {
        List<String> answer = new ArrayList<>();
        answer.add("СКНФ: ");
        answer.add(useCasePerfectCNF.getPerfectCNFUseCase(repository.getBooleanFunction(expression)) + "\n");

        answer.add("СДНФ: ");
        answer.add(useCasePerfectDNF.getPerfectDNFUseCase(repository.getBooleanFunction(expression)) + "\n");

        answer.add("Тупиковые ДНФ: ");
        answer.add(useCaseDeadLocked.getDeadLockedDNFUseCase(repository.getBooleanFunction(expression)) + "\n");

        answer.add("Минимальная ДНФ: ");
        answer.add(useCaseMinimalDNF.getMinimalDNFUseCase(repository.getBooleanFunction(expression)) + "\n");

        answer.add("Сокращённая ДНФ: ");
        answer.add(useCaseAbbreviatedDNF.getAbbreviatedDNFUseCase(repository.getBooleanFunction(expression)) + "\n");

        answer.add("Полином Жегалкина: ");
        answer.add(useCasePolynomial.getPolynomialUseCase(repository.getBooleanFunction(expression)) + "\n");

        return answer;
    }
}
