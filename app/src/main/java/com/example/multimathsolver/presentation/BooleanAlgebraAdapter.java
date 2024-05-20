package com.example.multimathsolver.presentation;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multimathsolver.R;

import java.util.List;

public class BooleanAlgebraAdapter extends RecyclerView.Adapter<BooleanAlgebraAdapter.ViewHolder> {

    private final List<String> data;

    public BooleanAlgebraAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = data.get(position);

        holder.textView.setText(text);
        if (text.equals("Неправильный ввод!")) {
            holder.textView.setTextColor(Color.RED);
            holder.textView.setTextSize(30);
        }
        String [] dataBooleanAlgebra = new String[] {"СКНФ: ", "СДНФ: ", "Тупиковые ДНФ: ", "Минимальная ДНФ: ", "Сокращённая ДНФ: ", "Полином Жегалкина: ", "Классификация Поста: "};
        for (String t : dataBooleanAlgebra) {
            if (text.equals(t)) {
                holder.textView.setTextColor(Color.WHITE);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewLargeAnswers);
        }
    }
}