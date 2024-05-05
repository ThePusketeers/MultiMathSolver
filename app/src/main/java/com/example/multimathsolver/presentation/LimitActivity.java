package com.example.multimathsolver.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;


public class LimitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_limits);
        final String[] catNames = new String[] {
                "ln(x) — натуральный логарифм", "sin(x) — синус", "cos(x) — косинус",
                "tg(x) — тангенс", "ctg(x) — котангенс", "arcsin(x) — арксинус",
                "arccos(x) — арккосинус", "arctg(x) — арктангенс",
                "arcctg(x) — арккотангенс", "sqrt(x),root(x) — корень",
                "exp(x) — экспонента в степени x", "a^b,pow(a,b) — a^b", "log(a,x) - log(a)(x)"
        };
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter adapter = new CustomAdapter(catNames);
        recyclerView.setAdapter(adapter);
    }
}