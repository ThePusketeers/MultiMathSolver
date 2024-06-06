package com.example.multimathsolver.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.example.multimathsolver.R;

public class MatrixAdapter extends AbstractTableAdapter<String, String, String> {
    private final double[][] data;

    public MatrixAdapter(double[][] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_view_cell_layout, parent, false);
        return new MyCellViewHolder(layout);
    }
    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, String cellItemModel, int columnPosition, int rowPosition) {
        String cell = (String) cellItemModel;
        MyCellViewHolder viewHolder = (MyCellViewHolder) holder;
        viewHolder.cell_textview.setText(cell);
        viewHolder.cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.cell_textview.requestLayout();
    }
    public static class MyCellViewHolder extends AbstractViewHolder {
        final LinearLayout cell_container;
        final TextView cell_textview;
        public MyCellViewHolder(View itemView) {
            super(itemView);
            cell_container = itemView.findViewById(R.id.cell_container);
            cell_textview = itemView.findViewById(R.id.cell_data);

        }
    }
    static class MyColumnHeaderViewHolder extends AbstractViewHolder {
        final LinearLayout column_header_container;

        public MyColumnHeaderViewHolder(View itemView) {
            super(itemView);
            column_header_container = itemView.findViewById(R.id.column_header_container);

        }
    }
    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout, parent, false);
        return new MyColumnHeaderViewHolder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, String columnHeaderItemModel, int
            position) {
        String columnHeader = (String) columnHeaderItemModel;
        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.column_header_container.getLayoutParams().width = LinearLayout
                .LayoutParams.WRAP_CONTENT;

    }

    static class MyRowHeaderViewHolder extends AbstractViewHolder {
        final TextView row_header_textView;
        public MyRowHeaderViewHolder(View itemView) {
            super(itemView);
            row_header_textView = itemView.findViewById(R.id.cell_data);
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_row_header_layout, parent, false);
        return new MyRowHeaderViewHolder(layout);
    }
    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, String rowHeaderItemModel, int
            position) {
        String rowHeader = (String) rowHeaderItemModel;
        MyRowHeaderViewHolder rowHeaderViewHolder = (MyRowHeaderViewHolder) holder;
    }

    @NonNull
    @Override
    public View onCreateCornerView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_corner_layout, parent, false);
    }

}