package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.multimathsolver.R;

public class BooleanAlgebraActivity extends AppCompatActivity {
    private EditText booleanFunction;
    private Button buttonSolve;
    private Button buttonDeleteAll;
    private Button buttonOr;
    private Button buttonAnd;
    private ImageButton buttonDelete;
    private Button buttonLeftBracket;
    private Button buttonPierceArrow;
    private Button buttonShefferStroke;
    private Button buttonRightBracket;
    private ImageButton buttonImplication;
    private ImageButton buttonXOR;
    private Button buttonNot;
    private Button buttonEquals;
    private Button buttonX1;
    private Button buttonX2;
    private Button buttonX3;
    private Button buttonX4;
    private Button buttonX5;
    private Button buttonX6;
    private Button buttonX7;
    private Button buttonX8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_booleanalgebra);
        initViews();

        setUpOnClickListeners();
    }

    private void setUpOnClickListeners() {
        buttonDeleteAll.setOnClickListener(button -> booleanFunction.getText().clear());
        buttonOr.setOnClickListener(button -> booleanFunction.append("∨"));
        buttonAnd.setOnClickListener(button -> booleanFunction.append("∧"));
        buttonDelete.setOnClickListener(button -> {
            int length = booleanFunction.getText().length();
            if (length > 0) {
                booleanFunction.getText().delete(length - 1, length);
            }
        });
        buttonLeftBracket.setOnClickListener(button -> booleanFunction.append("("));
        buttonPierceArrow.setOnClickListener(button -> booleanFunction.append("↓"));
        buttonShefferStroke.setOnClickListener(button -> booleanFunction.append("↑"));
        buttonRightBracket.setOnClickListener(button -> booleanFunction.append(")"));
        buttonImplication.setOnClickListener(button -> booleanFunction.append("→"));
        buttonXOR.setOnClickListener(button -> booleanFunction.append("⊕"));
        buttonNot.setOnClickListener(button -> booleanFunction.append("¬"));
        buttonEquals.setOnClickListener(button -> booleanFunction.append("⇔"));
        buttonX1.setOnClickListener(button -> booleanFunction.append("X1"));
        buttonX2.setOnClickListener(button -> booleanFunction.append("X2"));
        buttonX3.setOnClickListener(button -> booleanFunction.append("X3"));
        buttonX4.setOnClickListener(button -> booleanFunction.append("X4"));
        buttonX5.setOnClickListener(button -> booleanFunction.append("X5"));
        buttonX6.setOnClickListener(button -> booleanFunction.append("X6"));
        buttonX7.setOnClickListener(button -> booleanFunction.append("X7"));
        buttonX8.setOnClickListener(button -> booleanFunction.append("X8"));
        buttonSolve.setOnClickListener(view -> {
            String expression = String.valueOf(booleanFunction.getText());
            if (!expression.isEmpty()) {
                Intent intent = new Intent(view.getContext(), BooleanAlgebraSolvedActivity.class);
                intent.putExtra("expression", booleanFunction.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        booleanFunction = findViewById(R.id.limitInput);
        buttonSolve = findViewById(R.id.buttonSolve);
        buttonDeleteAll = findViewById(R.id.buttonDeleteAll);
        buttonOr = findViewById(R.id.buttonOr);
        buttonAnd = findViewById(R.id.buttonAnd);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonLeftBracket = findViewById(R.id.buttonLeftBracket);
        buttonPierceArrow = findViewById(R.id.buttonPierceArrow);
        buttonShefferStroke = findViewById(R.id.buttonShefferStroke);
        buttonRightBracket = findViewById(R.id.buttonRightBracket);
        buttonImplication = findViewById(R.id.buttonImplication);
        buttonXOR = findViewById(R.id.buttonXOR);
        buttonNot = findViewById(R.id.buttonNot);
        buttonEquals = findViewById(R.id.buttonEquals);
        buttonX1 = findViewById(R.id.buttonX1);
        buttonX2 = findViewById(R.id.buttonX2);
        buttonX3 = findViewById(R.id.buttonX3);
        buttonX4 = findViewById(R.id.buttonX4);
        buttonX5 = findViewById(R.id.buttonX5);
        buttonX6 = findViewById(R.id.buttonX6);
        buttonX7 = findViewById(R.id.buttonX7);
        buttonX8 = findViewById(R.id.buttonX8);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BooleanAlgebraActivity.class);
        return intent;
    }
}