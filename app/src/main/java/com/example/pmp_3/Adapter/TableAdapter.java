package com.example.pmp_3.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmp_3.AddNewTable;
import com.example.pmp_3.MainActivity;
import com.example.pmp_3.Model.TableModel;
import com.example.pmp_3.R;
import com.example.pmp_3.Utils.DataBaseHelper;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {

    private List<TableModel> mList;
    private MainActivity activity;
    private DataBaseHelper myDB;

    public TableAdapter(DataBaseHelper myDB , MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_layout, parent , false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TableModel item = mList.get(position);

        holder.mCheckBox.setText(item.getTable());
        holder.textViewBeerCount.setText(String.valueOf(item.getBeerCount()));
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myDB.updateStatus(item.getId() , 1);
                }else
                    myDB.updateStatus(item.getId() , 0);
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTables(List<TableModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addBeer(int position) {
        TableModel item = mList.get(position);
        myDB.updateBeerCount(item.getId(), item.getBeerCount() + 1);
        mList.get(position).setBeerCount(item.getBeerCount() + 1);
        notifyItemChanged(position);
        Log.d("beerCount", "beer count: " + item.getBeerCount());
    }

    public void editTable(int position){
        TableModel item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("table" , item.getTable());
        bundle.putInt("beerCount", item.getBeerCount());

        AddNewTable table = new AddNewTable();
        table.setArguments(bundle);
        table.show(activity.getSupportFragmentManager() , table.getTag());
    }

    public void removeBeer(int position) {
        TableModel item = mList.get(position);
        if (item.getBeerCount() <= 0){
            notifyItemChanged(position);
            return;
        }
        myDB.updateBeerCount(item.getId(), item.getBeerCount() - 1);
        mList.get(position).setBeerCount(item.getBeerCount() - 1);
        notifyItemChanged(position);
        Log.d("beerCount", "Updated beer count: " + item.getBeerCount());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        TextView textViewBeerCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
            textViewBeerCount = itemView.findViewById(R.id.textViewBeerCount);
        }
    }
}
