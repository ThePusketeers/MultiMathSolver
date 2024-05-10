package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.evrencoskun.tableview.TableView;
import com.example.multimathsolver.R;

import java.util.ArrayList;
import java.util.List;


public class MatrixActivity extends AppCompatActivity {

    private List<String> mRowHeaderList = new ArrayList<>();
    private List<String> mColumnHeaderList = new ArrayList<>();
    ;
    private List<List<String>> mCellList = new ArrayList<>();
    private Button btn;
    ;
    private TableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        btn = findViewById(R.id.add_item_button);

        tableView = findViewById(R.id.table_view);

        // Create our custom TableView Adapter
        MyTableViewAdapter adapter = new MyTableViewAdapter();

        // Set this adapter to the our TableView
        tableView.setAdapter(adapter);


        Object[][] testMatrix = new Object[][]{
                new double[][] {{1, 2, 3, 4, 5}, {4, 8, 3, 9, 1}},
                new double[][] {{5, 1, 8, 2, 1}, {9, 22, 13, 7, 4}},
                new double[][] {{6, 3, 11, 6, 6}, {13, 30, 16, 16, 5}}};

        for (int i = 0; i < testMatrix.length; i++) {

            List<String> temp = new ArrayList<>();

            for (int j = 0; j < testMatrix.length; j++) {
                temp.add(String.valueOf(  ));
            }

            mCellList.add(temp);
        }


        // Let's set datas of the TableView on the Adapter
        adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MatrixActivity.class);
    }
}
