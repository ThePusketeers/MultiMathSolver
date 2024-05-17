package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_solved_booleanalgebra);
        BooleanAlgebraViewModel viewModel = new ViewModelProvider(this).get(BooleanAlgebraViewModel.class);
        initViews();
        Bundle bundle = getIntent().getExtras();
        try {

            String expression = bundle.getString("expression");
            List<String> data = viewModel.solve(expression);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new BooleanAlgebraAdapter(data));
        } catch (IncorrectFunctionInput e) {
            throw new RuntimeException(e);
        }

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
//        textViewDNF.setMovementMethod(new ScrollingMovementMethod());
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BooleanAlgebraSolvedActivity.class);
        return intent;
    }
}
