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
    private Button saveToMatrix_A_Button;
    private Button saveToMatrix_B_Button;
    private TableView tableView;
    private MatrixOperations matrixOperations;
    private TextView determinantDisplay;
    private TextView rangDisplay;
    private BottomNavigationView navigationView;
    private MatrixActivityViewModel viewModel = new MatrixActivityViewModel();
    private MatrixAdapter adapter = new MatrixAdapter(viewModel.matrixAsArray);
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
        double[][] matrixAsArray = new double[][] { {1, 2, 3, 4,8}, {4, 8, 3, 9,11},{5, 1, 8, 2,8}, {9, 22, 13, 7,8},{3,21,5,4,3} };
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
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                Toast.makeText(MatrixActivity.this, string,Toast.LENGTH_LONG).show();
            }
        });

        rangCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rangDisplay.setText(String.valueOf("Значение ранга"));
                viewModel.solveRang(new MatrixOperations(matrixParserFromTableToArray(adapter)));
            }
        });
        viewModel.getRangLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                rangDisplay.setText(String.valueOf(viewModel.getRangLiveData().getValue()));
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
        viewModel.getDeterminantLiveData().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double ints) {
                determinantDisplay.setText(String.valueOf(ints));
            }
        });
        multiplyByConstantMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( String.valueOf(constantInputField.getText()).equals("") ){
                    viewModel.solveMultiplyByConstant(viewModel.matrixOperations,1);
                }
                else{
                    viewModel.solveMultiplyByConstant(viewModel.matrixOperations,Double.parseDouble(String.valueOf(constantInputField.getText())));
                }

            }
        });
        viewModel.getOutputMatrixLiveData().observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter,viewModel.getOutputMatrixLiveData().getValue());
            }
        });
        raiseToDegreeMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ( String.valueOf(degreeInputField.getText()).equals("") ){
                        viewModel.solveRaiseToDegree(viewModel.matrixOperations,1);
                    }
                    else{
                        viewModel.solveRaiseToDegree(viewModel.matrixOperations,Integer.parseInt(String.valueOf(degreeInputField.getText())));
                    }
                } catch (IncorrectMatrixSize e) {
                    throw new RuntimeException(e);
                }
            }
        });
        viewModel.getOutputMatrixLiveData().observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter,viewModel.getOutputMatrixLiveData().getValue());
            }
        });

        multiplyMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewModel.solveMultiplyMatrix(new MatrixOperations(viewModel.matrixA), new MatrixOperations(viewModel.matrixB));
                } catch (Exception e){
                    viewModel.setError("Данные не сохранены в матрицу А или B");
                }
            }
        });
        viewModel.getOutputMatrixLiveData().observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter, viewModel.getOutputMatrixLiveData().getValue());
            }
        });
        sumMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewModel.solveSumMatrix(new MatrixOperations(viewModel.matrixA), new MatrixOperations(viewModel.matrixB));
                } catch (Exception e){
                    viewModel.setError("Данные не сохранены в матрицу А или B");
                }
            }
        });
        viewModel.getOutputMatrixLiveData().observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter, viewModel.getOutputMatrixLiveData().getValue());
            }
        });
        subtractMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewModel.solveSubtractMatrix(new MatrixOperations(viewModel.matrixA), new MatrixOperations(viewModel.matrixB));
                } catch (Exception e) {
                    viewModel.setError("Данные не сохранены в матрицу А или B");
                }
            }
        });
        viewModel.getOutputMatrixLiveData().observe(this, new Observer<MatrixOperations>() {
            @Override
            public void onChanged(MatrixOperations matrixOperations) {
                matrixParserFromArrayToTable(adapter, viewModel.getOutputMatrixLiveData().getValue());
            }
        });
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
                startActivity(SlayActivity.newIntent(MatrixActivity.this));
                finish();
                return true;
            } else if (id == R.id.limit_menu) {
                startActivity(LimitActivity.newIntentLimit(MatrixActivity.this));
                finish();
                return true;
            } else if (id == R.id.discra_menu) {
                startActivity(BooleanAlgebraActivity.newIntentBooleanAlgera(MatrixActivity.this));
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
    }

    public double[][] matrixParserFromTableToArray(MatrixAdapter matrixAdapter){
        int rows = Objects.requireNonNull(matrixAdapter.getCellRowItems(0)).size();
        int columns = matrixAdapter.getCellColumnItems(0).size();
        double[][] resultMatrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = Double.parseDouble(Objects.requireNonNull(matrixAdapter.getCellItem(i, j)));
            }
        }
        return resultMatrix;
    }

    public void matrixParserFromArrayToTable(MatrixAdapter matrixAdapter, MatrixOperations matrixOperations){

        List<List<String>> localOutput = new ArrayList<>();

        for (double[] row : matrixOperations.getMatrix()) {
            List<String> rowList = new ArrayList<>();
            for (double value : row) {
                rowList.add(String.valueOf(value));
            }
            localOutput.add(rowList);
        }

        matrixAdapter.setCellItems(localOutput);
    }
    public static Intent newIntentMatrix(Context context) {
        return new Intent(context, MatrixActivity.class);
    }
}
