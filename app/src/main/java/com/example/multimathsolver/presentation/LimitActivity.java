package com.example.multimathsolver.presentation;

import static com.example.multimathsolver.presentation.BooleanAlgebraActivity.newIntentBooleanAlgera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class LimitActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText parameter;
    private EditText limit;
    private Button button;
    private TextView output;
    private BottomNavigationView navigationView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_limits);
        initViews();
        LimitActivityViewModel viewModel = new ViewModelProvider(this).get(LimitActivityViewModel.class);
        final String[] mathFunctions = getResources().getStringArray(R.array.list_of_functions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LimitAdapter(mathFunctions));
        navigationView.setSelectedItemId(R.id.limit_menu);
        observeViewModel(viewModel);
        setUpOnClickListeners(viewModel);
        setUpOnItemListeners();
    }

    private void setUpOnItemListeners() {
        navigationView.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.limit_menu) {
                return true;
            } else if (id == R.id.slay_menu) {
                startActivity(SlayActivity.newIntent(this));
                finish();
                return true;
            } else if (id == R.id.discra_menu) {
                startActivity(newIntentBooleanAlgera(this));
                finish();
                return true;
            } else if (id == R.id.matrix_menu) {
                startActivity(MatrixActivity.newIntentMatrix(this)); // заменить MainActivity2 на класс для Матриц
                finish();
                return true;
            }
            return false;
        });
    }

    private void setUpOnClickListeners(LimitActivityViewModel viewModel) {
        button.setOnClickListener(button ->
                viewModel.solve(parameter.getText().toString(),
                        limit.getText().toString().replace("+", "plus")));
    }

    private void observeViewModel(LimitActivityViewModel viewModel) {
        viewModel.getOutput().observe(this, (string -> {
            String text = "Вычисленное решение: \n" + viewModel.getOutput().getValue();
            output.setText(text);
        }) );
        viewModel.getError().observe(this, (string -> {
            Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        }) );
        viewModel.getIsProgress().observe(this, (isProgress -> {
            if (isProgress)
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.INVISIBLE);
        }));
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        parameter = findViewById(R.id.parameter);
        limit = findViewById(R.id.limit_input);
        button = findViewById(R.id.solve_button);
        output = findViewById(R.id.output_text);
        navigationView = findViewById(R.id.bottomNavigationView);
        progressBar = findViewById(R.id.progressBar);
    }

    public static Intent newIntentLimit(Context context) {
        return new Intent(context, LimitActivity.class);
    }
}