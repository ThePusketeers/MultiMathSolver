package com.example.multimathsolver.presentation;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multimathsolver.R;


public class MatrixActivity extends AppCompatActivity {

    private RecyclerView numbersList;
    private MatrixAdapter matrixAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        numbersList.findViewById(R.id.rv_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        numbersList.setLayoutManager(layoutManager);

        numbersList.hasFixedSize();

        matrixAdapter = new MatrixAdapter(100); // вот здесь аргумент - сколько я хочу элементов в списке

        numbersList.setAdapter(matrixAdapter);
    }
}
