package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;
import com.example.multimathsolver.domain.IncorrectFunctionInput;

import java.util.ArrayList;
import java.util.List;

public class BooleanAlgebraSolvedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<String> data = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        BooleanAlgebraViewModel viewModel = new ViewModelProvider(this).get(BooleanAlgebraViewModel.class);
        try {
            String expression = bundle.getString("expression");
            data = viewModel.solve(expression);
        }  catch (IncorrectFunctionInput e) {
            data.add("Неправильный ввод!");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_solved_booleanalgebra);
        initViews();
        setUpOnClickListeners();
//        try {
//            String expression = bundle.getString("expression");
//            List<String> data = viewModel.solve(expression);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new BooleanAlgebraAdapter(data));
//        }  catch (IncorrectFunctionInput e) {
//            throw new RuntimeException(e);
//        }

    }

    private void setUpOnClickListeners() {
        buttonBack.setOnClickListener(buttonBack -> finish());
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        buttonBack = findViewById(R.id.buttonBack);
//        textViewDNF.setMovementMethod(new ScrollingMovementMethod());
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BooleanAlgebraSolvedActivity.class);
        return intent;
    }
}