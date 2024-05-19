package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;

import java.util.ArrayList;
import java.util.List;

public class SlayActivity extends AppCompatActivity {
    private Button addButton;
    private Button solveButton;
    private EditText slayString;
    private TextView answerTextView;
    private int count = 0;
    private RecyclerView recyclerView;
    private List<String> rows = new ArrayList<>();
    private RecyclerViewAdapter adapter = new RecyclerViewAdapter();
    private SlayActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slay);
        initViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(SlayActivityViewModel.class);
        observeViewModel(viewModel);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rows.add(String.valueOf(slayString.getText()));
                slayString.setText("");
                adapter.submitList(new ArrayList<>(rows));
                recyclerView.smoothScrollToPosition(rows.size()-1);
            }
        });

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.solve(rows);
            }
        });
    }

    private void observeViewModel(SlayActivityViewModel viewModel) {
        viewModel.getOutput().observe(this, (string -> {
            String text = "Ответ: " + viewModel.getOutput().getValue();
            answerTextView.setText(text);
        }));
        viewModel.getError().observe(this, (string -> {
            Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
            toast.show();
        }));
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SlayActivity.class);
        return intent;
    }

    private void initViews() {
        solveButton = findViewById(R.id.solve_button);
        addButton = findViewById(R.id.add_string_button);
        slayString = findViewById(R.id.string_edit_text);
        recyclerView = findViewById(R.id.recyclerview);
        answerTextView = findViewById(R.id.answer);
    }
}