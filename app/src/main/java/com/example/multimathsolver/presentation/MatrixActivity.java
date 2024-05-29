package com.example.multimathsolver.presentation;

import static com.example.multimathsolver.presentation.LimitActivity.newIntentLimit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.evrencoskun.tableview.TableView;
import com.example.multimathsolver.R;
import com.example.multimathsolver.domain.MatrixOperations;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MatrixActivity extends AppCompatActivity {
    private List<String> mRowHeaderList = new ArrayList<>();
    private List<String> mColumnHeaderList = new ArrayList<>();
    private List<List<String>> mCellList = new ArrayList<>();
    private Button matrixDisplayButton;
    private Button determinantCountButton;
    private Button rangCountButton;
    private Button multiplyMatrixButton;
    private Button sumMatrixButton;
    private Button multiplyByConstantMatrixButton;
    private Button raiseToDegreeMatrixButton;
    private Button subtractMatrixButton;
    private EditText degreeInputField;
    private EditText constantInputField;
    private Button saveToMatrix_A_Button;
    private Button saveToMatrix_B_Button;
    private TableView tableView;
    private MatrixOperations matrix;
    private TextView determinantAnswer;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        initViews();

        navigationView.setSelectedItemId(R.id.matrix_menu);
        setUpOnClickListeners();
        setUpOnItemListeners();


        // Create our custom TableView Adapter
        MyTableViewAdapter adapter = new MyTableViewAdapter();

        // Set this adapter to the our TableView
        tableView.setAdapter(adapter);

        matrixDisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[][] matrixAsArray = new double[][] { {1, 2, 3, 4, 5}, {4, 8, 3, 9, 1},{5, 1, 8, 2, 1}, {9, 22, 13, 7, 4} };
                for (int i = 0; i < matrixAsArray.length; i++) {

                    List<String> temp = new ArrayList<>();

                    for (int j = 0; j < matrixAsArray[0].length; j++) {
                        temp.add(String.valueOf(matrixAsArray[i][j]));
                    }

                    mCellList.add(temp);
                }

                // Let's set datas of the TableView on the Adapter
                adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

            }
        });

        determinantCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void setUpOnClickListeners() {

    }

    private void setUpOnItemListeners() {
        navigationView.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.matrix_menu) {
                return true;
            } else if (id == R.id.slay_menu) {
                startActivity(new Intent(MatrixActivity.this, MainActivity2.class)); // заменить MainActivity2 на класс для СЛАУ
                finish();
                return true;
            } else if (id == R.id.limit_menu) {
                startActivity(new Intent(MatrixActivity.this, LimitActivity.class)); // заменить MainActivity2 на класс для Пределов
                finish();
                return true;
            } else if (id == R.id.discra_menu) {
                startActivity(new Intent(MatrixActivity.this, BooleanAlgebraActivity.class)); // заменить MainActivity2 на класс для Дискры
                finish();
                return true;
            }
            return false;
        });
    }

    private void initViews(){
        matrixDisplayButton = findViewById(R.id.add_item_button);
        determinantCountButton = findViewById(R.id.get_determinant_button);
        determinantAnswer = findViewById(R.id.determinant_display);
        rangCountButton = findViewById(R.id.get_rang_button);
        tableView = findViewById(R.id.table_view);
        multiplyMatrixButton = findViewById(R.id.multiply_matrix);
        sumMatrixButton = findViewById(R.id.sum_matrix);
        multiplyByConstantMatrixButton = findViewById(R.id.multiply_by_constant_matrix);
        raiseToDegreeMatrixButton = findViewById(R.id.raise_to_degree_matrix);
        subtractMatrixButton = findViewById(R.id.subtract_matrix);
        degreeInputField = findViewById(R.id.degree_input_field);
        constantInputField = findViewById(R.id.constant_input_field);
        saveToMatrix_A_Button = findViewById(R.id.save_to_matrix_A_button);
        saveToMatrix_B_Button = findViewById(R.id.save_to_matrix_B_button);

    }

    public static double[][] matrixMapper(MyTableViewAdapter myTableViewAdapter){

    double[][] resultMatrix = new double[4][5];
        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[0].length; j++) {
                resultMatrix[i][j] = Double.parseDouble(Objects.requireNonNull(myTableViewAdapter.getCellItem(i, j)));
            }
        }
        return resultMatrix;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MatrixActivity.class);
    }
}
