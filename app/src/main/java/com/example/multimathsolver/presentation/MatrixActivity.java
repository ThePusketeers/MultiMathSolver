package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.evrencoskun.tableview.TableView;
import com.example.multimathsolver.R;
import com.example.multimathsolver.domain.MatrixOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MatrixActivity extends AppCompatActivity {

    private List<String> mRowHeaderList = new ArrayList<>();
    private List<String> mColumnHeaderList = new ArrayList<>();
    private List<List<String>> mCellList = new ArrayList<>();
    private Button matrixDisplayButton;
    private Button determinantCountButton;
    private TableView tableView;
    private MatrixOperations matrix;
    private TextView determinantAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        matrixDisplayButton = findViewById(R.id.add_item_button);
        determinantCountButton = findViewById(R.id.get_determinant_button);
        determinantAnswer = findViewById(R.id.determinant_display);

        tableView = findViewById(R.id.table_view);

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
