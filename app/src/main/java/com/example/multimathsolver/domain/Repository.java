package com.example.multimathsolver.domain;

import java.util.Map;

public interface Repository {
    BooleanFunction getBooleanFunction(String expression) throws IncorrectFunctionInput;

    String getPerfectDNF(BooleanFunction function);

    String getPerfectCNF(BooleanFunction function);

    String getAbbreviatedDNF(BooleanFunction function);

    String getDeadLockedDNF(BooleanFunction function);

    String getMinimalDNF(BooleanFunction function);

    String getPolynomial(BooleanFunction function);

    Map<PostClass, Boolean> getBelongingToPostClasses(BooleanFunction function);
    String getSolutionOfSLAY(SLAY matrix);
}
