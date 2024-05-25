package com.example.multimathsolver.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;

import java.util.List;

public class BooleanAlgebraSolvedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageButton buttonBack;
    private String expression;
    private String [] mathAlgebraBooleanFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_for_solved_booleanalgebra);
        initViews();
        setUpOnClickListeners();
        mathAlgebraBooleanFunctions = getResources().getStringArray(R.array.list_of_names_functions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BooleanAlgebraViewModel viewModel = new ViewModelProvider(this).get(BooleanAlgebraViewModel.class);
        observeViewModel(viewModel);
        checkSolver();
        super.onCreate(savedInstanceState);
    }

    private void setUpOnClickListeners() {
        buttonBack.setOnClickListener(buttonBack -> finish());
    }
    private void checkSolver() {
        BooleanAlgebraViewModel viewModel = new ViewModelProvider(this).get(BooleanAlgebraViewModel.class);
        viewModel.solve(expression);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void observeViewModel(BooleanAlgebraViewModel viewModel) {
        viewModel.getOutput().observe(this, (string -> {
            List<String> a = viewModel.getOutput().getValue();
            BooleanAlgebraAdapter adapter = new BooleanAlgebraAdapter(viewModel.getOutput().getValue(), mathAlgebraBooleanFunctions);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }) );
        viewModel.getError().observe(this, (string -> {
            Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
            toast.show();
        }) );
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        buttonBack = findViewById(R.id.buttonBack);
        expression = getIntent().getExtras().getString("expression");
    }

    public static Intent newIntentSolved(Context context, String expression) {
        Intent intent = new Intent(context, BooleanAlgebraSolvedActivity.class);
        intent.putExtra("expression", expression);
        return intent;
    }
}
