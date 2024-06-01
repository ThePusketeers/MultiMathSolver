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

import java.util.List;

public class MyTableViewAdapter extends AbstractTableAdapter<String, String, String> {
    private final double[][] data;

    public MyTableViewAdapter(double[][] data) {
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


    class MyColumnHeaderViewHolder extends AbstractViewHolder {
        final LinearLayout column_header_container;
        final TextView column_header_textView;

        public MyColumnHeaderViewHolder(View itemView) {
            super(itemView);
            column_header_container = itemView.findViewById(R.id.column_header_container);
            column_header_textView = itemView.findViewById(R.id.column_header_textView);
        }
    }


    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Column Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new MyColumnHeaderViewHolder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, String columnHeaderItemModel, int
            position) {
        String columnHeader = (String) columnHeaderItemModel;

        // Get the holder to update cell item text
        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.column_header_textView.setText(columnHeader);

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        columnHeaderViewHolder.column_header_container.getLayoutParams().width = LinearLayout
                .LayoutParams.WRAP_CONTENT;
        columnHeaderViewHolder.column_header_textView.requestLayout();
    }

    class MyRowHeaderViewHolder extends AbstractViewHolder {

        final TextView row_header_textView;

        public MyRowHeaderViewHolder(View itemView) {
            super(itemView);
            row_header_textView = itemView.findViewById(R.id.cell_data);
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new MyRowHeaderViewHolder(layout);
    }
    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, String rowHeaderItemModel, int
            position) {
        String rowHeader = (String) rowHeaderItemModel;

        // Get the holder to update row header item text
        MyRowHeaderViewHolder rowHeaderViewHolder = (MyRowHeaderViewHolder) holder;
        //rowHeaderViewHolder.row_header_textView.setText(rowHeader);
    }

    @Override
    public View onCreateCornerView(ViewGroup parent) {
        // Get Corner xml layout
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_corner_layout, parent, false);
    }

    @Override
    public int getColumnHeaderItemViewType(int columnPosition) {
        // The unique ID for this type of column header item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of ColumnViewHolder on "onCreateColumnViewHolder"
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int rowPosition) {
        // The unique ID for this type of row header item
        // If you have different items for Row Header View by Y (Row) position,
        // then you should fill this method to be able create different
        // type of RowHeaderViewHolder on "onCreateRowHeaderViewHolder"
        return 0;
    }

    @Override
    public int getCellItemViewType(int columnPosition) {
        // The unique ID for this type of cell item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        return 0;
    }
}