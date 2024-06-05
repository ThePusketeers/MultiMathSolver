package com.example.multimathsolver.presentation;

import static com.example.multimathsolver.presentation.BooleanAlgebraActivity.newIntentBooleanAlgera;
import static com.example.multimathsolver.presentation.LimitActivity.newIntentLimit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SlayActivity extends AppCompatActivity {
    private Button addButton;
    private Button solveButton;
    private EditText slayString;
    private TextView answerTextView;
    private RecyclerView recyclerView;
    private List<String> rows = new ArrayList<>();
    private SlayAdapter adapter = new SlayAdapter();
    private SlayActivityViewModel viewModel;
    private BottomNavigationView navigationView;

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
        navigationView.setSelectedItemId(R.id.slay_menu);
        setUpOnClickListeners();
        setUpOnItemListeners();
    }

    private void setUpOnClickListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slayString.getText() != null){
                    rows.add(String.valueOf(slayString.getText()));
                    slayString.setText("");
                    adapter.submitList(new ArrayList<>(rows));
                    recyclerView.smoothScrollToPosition(rows.size() - 1);
                }
            }
        });
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.solve(rows);
            }
        });
    }

    private void setUpOnItemListeners() {
        navigationView.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.discra_menu) {
                startActivity(newIntentBooleanAlgera(this));
                finish();
                return true;
            } else if (id == R.id.slay_menu) {
                return true;
            } else if (id == R.id.limit_menu) {
                startActivity(newIntentLimit(this));
                finish();
                return true;
            } else if (id == R.id.matrix_menu) {
                startActivity(MatrixActivity.newIntentMatrix(this));
                finish();
                return true;
            }
            return false;
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
        navigationView = findViewById(R.id.bottomNavigationView);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
             viewModel.delete(rows, viewHolder.getAdapterPosition());
        }
    };

    public static Intent newIntentSlay(Context context) {
        return new Intent(context, SlayActivity.class);
    }
}