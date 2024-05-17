package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multimathsolver.R;
import com.example.multimathsolver.domain.IncorrectFunctionInput;

public class BooleanAlgebraSolvedActivity extends AppCompatActivity {
    private TextView textViewDNF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_solved_booleanalgebra);
        BooleanAlgebraViewModel viewModel = new ViewModelProvider(this).get(BooleanAlgebraViewModel.class);
        initViews();
        Bundle bundle = getIntent().getExtras();
        try {
            String expression = bundle.getString("expression");
            textViewDNF.setText(viewModel.solve(expression));
        } catch (IncorrectFunctionInput e) {
            throw new RuntimeException(e);
        }

    }

    private void initViews() {
        textViewDNF = findViewById(R.id.textViewDNF);
        textViewDNF.setMovementMethod(new ScrollingMovementMethod());
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BooleanAlgebraSolvedActivity.class);
        return intent;
    }
}
