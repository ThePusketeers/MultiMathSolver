package com.example.multimathsolver.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class LimitActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText parameter;
    EditText limit;
    Button button;
    TextView output;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_limits);
        initViews();
        LimitActivityViewModel viewModel = new ViewModelProvider(this).get(LimitActivityViewModel.class);
        final String[] mathFunctions = new String[] {
                "ln(x) — натуральный логарифм", "sin(x) — синус", "cos(x) — косинус",
                "tg(x) — тангенс", "ctg(x) — котангенс", "arcsin(x) — арксинус",
                "arccos(x) — арккосинус", "arctg(x) — арктангенс",
                "arcctg(x) — арккотангенс", "sqrt(x), root(x) — корень",
                "exp(x) — экспонента в степени x", "a^b, pow(a,b) — a в степени b",
                "abs(x) — модуль",
                "sqrt(n,x) — корень n-ой степени из x",
                "log(a,x) — log(x) по основанию a",
                "pi — число ПИ"
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter adapter = new CustomAdapter(mathFunctions);
        recyclerView.setAdapter(adapter);
        navigationView.setSelectedItemId(R.id.limit_menu);
        button.setOnClickListener(button -> {
            viewModel.solve(parameter.getText().toString(), limit.getText().toString().replace("+", "plus"));
            String text = "Вычисленное решение: \n" + viewModel.getOutput().getValue();
            output.setText(text);
        } );

        navigationView.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.limit_menu) {
                return true;
            } else if (id == R.id.slay_menu) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class)); // заменить MainActivity2 на класс для СЛАУ
                return true;
            } else if (id == R.id.discra_menu) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class)); // заменить MainActivity2 на класс для Дискры
                return true;
            } else if (id == R.id.matrix_menu) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class)); // заменить MainActivity2 на класс для Матриц
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
}