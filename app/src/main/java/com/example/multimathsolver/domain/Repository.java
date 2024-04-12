package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;
import com.example.multimathsolver.data.booleanalgebra.PostClass;
import com.example.multimathsolver.data.booleanalgebra.ExpressionHandlerInterface;
import com.example.multimathsolver.data.booleanalgebra.IncorrectFunctionInput;

import java.util.Map;

public interface Repository {
    BooleanFunction getBooleanFunction(ExpressionHandlerInterface expressionHandler) throws IncorrectFunctionInput;

    String getPerfectDNF(BooleanFunction function);

    String getPerfectCNF(BooleanFunction function);

    String getAbbreviatedDNF(BooleanFunction function);

    String getDeadLockedDNF(BooleanFunction function);

    String getMinimalDNF(BooleanFunction function);

    String getPolynomial(BooleanFunction function);

    Map<PostClass, Boolean> getBelongingToPostClasses(BooleanFunction function);
}
