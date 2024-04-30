package com.example.multimathsolver.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;

import java.util.ArrayList;
import java.util.List;

public class SlayActivity extends AppCompatActivity {
    private Button addButton;
    private EditText slayString;
    private int count = 0;
    private RecyclerView recyclerView;
    private List<String> rows = new ArrayList<>();
    private RecyclerViewAdapter adapter = new RecyclerViewAdapter();

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
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rows.add(String.valueOf(slayString.getText()));
                slayString.setText("");
                adapter.submitList(new ArrayList<>(rows));
                recyclerView.smoothScrollToPosition(0);
            }
        });

    }
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SlayActivity.class);
        return intent;
    }

    private void initViews() {
        addButton = findViewById(R.id.add_string_button);
        slayString = findViewById(R.id.string_edit_text);
        recyclerView = findViewById(R.id.recyclerview);
    }
}