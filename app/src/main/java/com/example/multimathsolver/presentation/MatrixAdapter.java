package com.example.multimathsolver.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;

public class MatrixAdapter extends RecyclerView.Adapter<MatrixAdapter.NumberViewHolder> {
    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.matrix_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder{
        TextView matrixItemNumberView;
        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            matrixItemNumberView = itemView.findViewById(R.id.tv_number_item);
        }

        void bind(int listIndex){
            matrixItemNumberView.setText(String.valueOf(listIndex));
        }
    }
}
