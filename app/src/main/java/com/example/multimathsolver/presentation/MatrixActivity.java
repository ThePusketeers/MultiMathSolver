package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.evrencoskun.tableview.TableView;
import com.example.multimathsolver.R;
import com.example.multimathsolver.domain.IncorrectMatrixSize;
import com.example.multimathsolver.domain.MatrixOperations;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MatrixActivity extends AppCompatActivity {
    private List<String> mRowHeaderList = new ArrayList<>();
    private List<String> mColumnHeaderList = new ArrayList<>();
    private List<List<String>> mCellList = new ArrayList<>();
    private Button determinantCountButton;
    private Button rangCountButton;
    private Button multiplyMatrixButton;
    private Button sumMatrixButton;
    private Button multiplyByConstantMatrixButton;
    private Button raiseToDegreeMatrixButton;
    private Button subtractMatrixButton;
    private EditText degreeInputField;
    private EditText constantInputField;

    private EditText rowCountToInput;
    private EditText columnCountToInput;

    private Button saveToMatrix_A_Button;
    private Button saveToMatrix_B_Button;
    private TableView tableView;
    private MatrixOperations matrixOperations;
    private TextView determinantDisplay;
    private TextView rangDisplay;
    private BottomNavigationView navigationView;
    private final MatrixActivityViewModel viewModel = new MatrixActivityViewModel();
    private final MyTableViewAdapter adapter = new MyTableViewAdapter(viewModel.matrixAsArray);




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        initViews();

        navigationView.setSelectedItemId(R.id.matrix_menu);
        setUpOnClickListeners();
        setUpOnItemListeners();
        tableView.setAdapter(adapter);
        initTable();

    }
    private void initTable() {
        //double[][] matrixAsArray = new double[viewModel.rows.getValue()][viewModel.columns.getValue()];

        //double[][] matrixAsArray = new double[5][5];

        double[][] matrixAsArray = new double[][] { {1, 2, 3, 4}, {4, 8, 3, 9},{5, 1, 8, 2}, {9, 22, 13, 7} };
        for (int i = 0; i < matrixAsArray.length; i++) {

            List<String> temp = new ArrayList<>();

            for (int j = 0; j < matrixAsArray[0].length; j++) {
                temp.add(String.valueOf(matrixAsArray[i][j]));
            }

            mCellList.add(temp);
        }


        adapter.setAllItems(mColumnHeaderList,mRowHeaderList,mCellList);
    }

    private void setUpOnClickListeners() {
        rangCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rangDisplay.setText(String.valueOf("Значение ранга"));
                viewModel.solveRang(new MatrixOperations(matrixParserFromTableToArray(adapter)));
            }
        });
        viewModel.rang.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                rangDisplay.setText(String.valueOf(viewModel.rang.getValue()));
            }
        });
        determinantCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determinantDisplay.setText(String.valueOf("Значение определителя"));
                try {
                    viewModel.solveDeterminant(new MatrixOperations(matrixParserFromTableToArray(adapter)));
                } catch (IncorrectMatrixSize e) {
                    throw new RuntimeException(e);
                }
            }
        });
        viewModel.determinant.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double ints) {
                determinantDisplay.setText(String.valueOf(viewModel.determinant.getValue()));
            }
        });
        multiplyByConstantMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.solveMultiplyByConstant(viewModel.matrixOperations,Double.parseDouble(String.valueOf(constantInputField.getText())));
            }
        });
        viewModel.outputMatrix.observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter,viewModel.outputMatrix.getValue());
            }
        });
        raiseToDegreeMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewModel.solveRaiseToDegree(viewModel.matrixOperations,Integer.parseInt(String.valueOf(degreeInputField.getText())));
                } catch (IncorrectMatrixSize e) {
                    throw new RuntimeException(e);
                }
            }
        });
        viewModel.outputMatrix.observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter,viewModel.outputMatrix.getValue());
            }
        });

        multiplyMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewModel.solveMultiplyMatrix(new MatrixOperations(viewModel.matrixA), new MatrixOperations(viewModel.matrixB));
                } catch (IncorrectMatrixSize e) {
                    throw new RuntimeException(e);
                }
            }
        });
        viewModel.outputMatrix.observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter, viewModel.outputMatrix.getValue());
            }
        });

        sumMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewModel.solveSumMatrix(new MatrixOperations(viewModel.matrixA), new MatrixOperations(viewModel.matrixB));
                } catch (IncorrectMatrixSize e) {
                    throw new RuntimeException(e);
                }
            }
        });
        viewModel.outputMatrix.observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter, viewModel.outputMatrix.getValue());
            }
        });
        subtractMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewModel.solveSubtractMatrix(new MatrixOperations(viewModel.matrixA), new MatrixOperations(viewModel.matrixB));
                } catch (IncorrectMatrixSize e) {
                    throw new RuntimeException(e);
                }
            }
        });
        viewModel.outputMatrix.observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter, viewModel.outputMatrix.getValue());
            }
        });

//        viewModel.rows.observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                viewModel.rows.setValue(Integer.parseInt(String.valueOf(rowCountToInput.getText())));
//            }
//        });
//
//        viewModel.columns.observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                viewModel.columns.setValue(Integer.parseInt(String.valueOf(columnCountToInput.getText())));
//            }
//        });

        saveToMatrix_A_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.matrixA = matrixParserFromTableToArray(adapter);
            }
        });
        saveToMatrix_B_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.matrixB = matrixParserFromTableToArray(adapter);
            }
        });
    }

    private void setUpOnItemListeners() {
        navigationView.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.matrix_menu) {
                return true;
            } else if (id == R.id.slay_menu) {
                startActivity(new Intent(MatrixActivity.this, MainActivity2.class));
                finish();
                return true;
            } else if (id == R.id.limit_menu) {
                startActivity(new Intent(MatrixActivity.this, LimitActivity.class));
                finish();
                return true;
            } else if (id == R.id.discra_menu) {
                startActivity(new Intent(MatrixActivity.this, BooleanAlgebraActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void initViews(){
        determinantCountButton = findViewById(R.id.get_determinant_button);
        rangCountButton = findViewById(R.id.get_rang_button);
        determinantDisplay = findViewById(R.id.determinant_display);
        rangDisplay = findViewById(R.id.rang_display);
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
        navigationView = findViewById(R.id.bottomNavigationViewMatrix);
        rowCountToInput = findViewById(R.id.row_count_for_input);
        columnCountToInput = findViewById(R.id.column_count_for_input);
    }

    public static double[][] matrixParserFromTableToArray(MyTableViewAdapter myTableViewAdapter){

        int rows = Objects.requireNonNull(myTableViewAdapter.getRowHeaderItem(0)).length();
        int columns = Objects.requireNonNull(myTableViewAdapter.getColumnHeaderItem(0)).length();

        double[][] resultMatrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = Double.parseDouble(Objects.requireNonNull(myTableViewAdapter.getCellItem(i, j)));
            }
        }

        return resultMatrix;
    }

    public static void matrixParserFromArrayToTable(MyTableViewAdapter myTableViewAdapter, MatrixOperations matrixOperations){

        List<List<String>> localOutput = new ArrayList<>();

        for (double[] row : matrixOperations.getMatrix()) {
            List<String> rowList = new ArrayList<>();
            for (double value : row) {
                rowList.add(String.valueOf(value));
            }
            localOutput.add(rowList);
        }

        myTableViewAdapter.setCellItems(localOutput);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MatrixActivity.class);
    }
}
