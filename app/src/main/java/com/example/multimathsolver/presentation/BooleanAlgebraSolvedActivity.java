package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multimathsolver.R;

public class BooleanAlgebraSolvedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_solved_booleanalgebra);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BooleanAlgebraSolvedActivity.class);
        return intent;
    }
}
