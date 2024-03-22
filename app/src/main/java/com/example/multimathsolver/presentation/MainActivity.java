package com.example.multimathsolver.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.multimathsolver.R;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button buttonActivity;
    private TextView textView;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.incrementA();
            }
        });

        viewModel.getA().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textView.setText(String.valueOf(integer));
            }
        });

        buttonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity2.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        btn = findViewById(R.id.button);
        textView = findViewById(R.id.tv);
        buttonActivity = findViewById(R.id.buttonActivity);
    }
}