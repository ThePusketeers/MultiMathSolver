package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_limits);
        initViews();
        LimitActivityViewModel viewModel = new ViewModelProvider(this).get(LimitActivityViewModel.class);
        final String[] mathFunctions = getResources().getStringArray(R.array.list_of_functions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LimitAdapter adapter = new LimitAdapter(mathFunctions);
        recyclerView.setAdapter(adapter);
        navigationView.setSelectedItemId(R.id.limit_menu);
        viewModel.getOutput().observe(this, (string -> {
            String text = "Вычисленное решение: \n" + viewModel.getOutput().getValue();
            output.setText(text);
        }) );
        viewModel.getError().observe(this, (string -> {
            Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
            toast.show();
        }) );
        button.setOnClickListener(button -> viewModel.solve(parameter.getText().toString(), limit.getText().toString().replace("+", "plus")));

        navigationView.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.limit_menu) {
                return true;
            } else if (id == R.id.slay_menu) {
                startActivity(new Intent(LimitActivity.this, MainActivity2.class)); // заменить MainActivity2 на класс для СЛАУ
                return true;
            } else if (id == R.id.discra_menu) {
                startActivity(new Intent(LimitActivity.this, MainActivity2.class)); // заменить MainActivity2 на класс для Дискры
                return true;
            } else if (id == R.id.matrix_menu) {
                startActivity(new Intent(LimitActivity.this, MainActivity2.class)); // заменить MainActivity2 на класс для Матриц
                return true;
            }
            return false;
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        parameter = findViewById(R.id.parameter);
        limit = findViewById(R.id.limit_input);
        button = findViewById(R.id.solve_button);
        output = findViewById(R.id.output_text);
        navigationView = findViewById(R.id.bottomNavigationView);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LimitActivity.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LimitActivityViewModel viewModel = new ViewModelProvider(this).get(LimitActivityViewModel.class);
        viewModel.getCompositeDisposable().clear();
    }
}