package com.example.multimathsolver.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multimathsolver.R;

import java.util.ArrayList;
import java.util.List;


public class MatrixActivity extends AppCompatActivity {

    private MatrixAdapter matrixAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        initViews();

    }

    private void initViews() {
        matrixAdapter = new MatrixAdapter();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, MatrixActivity.class);
    }
}
