package com.example.multimathsolver.domain;

import java.io.IOException;

public class IncorrectFunctionInput extends IOException {
    public IncorrectFunctionInput(String message) {
        super(message);
    }
}
