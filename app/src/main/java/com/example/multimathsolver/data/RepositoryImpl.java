package com.example.multimathsolver.data;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;
import com.example.multimathsolver.data.booleanalgebra.PostClass;
import com.example.multimathsolver.data.booleanalgebra.ExpressionHandlerInterface;
import com.example.multimathsolver.data.booleanalgebra.IncorrectFunctionInput;
import com.example.multimathsolver.domain.Repository;

import java.util.Map;

public class RepositoryImpl implements Repository {

    @Override
    public BooleanFunction getBooleanFunction(ExpressionHandlerInterface expressionHandler) throws IncorrectFunctionInput {
        return new BooleanFunction(expressionHandler);
    }

    @Override
    public String getPerfectDNF(BooleanFunction function) {
        return function.getPerfectDNF();
    }

    @Override
    public String getPerfectCNF(BooleanFunction function) {
        return function.getPerfectCNF();
    }

    @Override
    public String getAbbreviatedDNF(BooleanFunction function) {
        return function.getAbbreviatedDNF();
    }

    @Override
    public String getDeadLockedDNF(BooleanFunction function) {
        return function.getDeadLockedDNF();
    }

    @Override
    public String getMinimalDNF(BooleanFunction function) {
        return function.getMinimalDNF();
    }

    @Override
    public String getPolynomial(BooleanFunction function) {
        return function.getPolynomial();
    }

    @Override
    public Map<PostClass, Boolean> getBelongingToPostClasses(BooleanFunction function) {
        return function.getBelongingToPostClasses();
    }


}
