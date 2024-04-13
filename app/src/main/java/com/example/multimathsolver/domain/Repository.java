package com.example.multimathsolver.domain;

import java.util.Map;


import com.example.multimathsolver.data.slay.SLAY;

public interface Repository {
    BooleanFunction getBooleanFunction(String expression) throws IncorrectFunctionInput;

    String getPerfectDNF(BooleanFunction function);

    String getPerfectCNF(BooleanFunction function);

    String getAbbreviatedDNF(BooleanFunction function);

    String getDeadLockedDNF(BooleanFunction function);

    String getMinimalDNF(BooleanFunction function);

    String getPolynomial(BooleanFunction function);

    Map<PostClass, Boolean> getBelongingToPostClasses(BooleanFunction function);
    public String solutionOfSLAY(SLAY matrix);
}
