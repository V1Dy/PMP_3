package com.example.pmp_3;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmp_3.Model.TableModel;
import com.example.pmp_3.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTable extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTable";

    //widgets
    private EditText mEditText;

    private EditText mBeerCount;
    private Button mSaveButton;

    private Button mDeleteTableButton;

    private DataBaseHelper myDb;

    public static AddNewTable newInstance(){
        return new AddNewTable();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_newtable, container , false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.edittext);
        mBeerCount = view.findViewById(R.id.editBeerCount);
        mDeleteTableButton = view.findViewById(R.id.buttonDeleteTable);
        mSaveButton = view.findViewById(R.id.button_save);

        myDb = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String table = bundle.getString("table");
            int beerCount = bundle.getInt("beerCount");
            String beerCountText = String.valueOf(beerCount);
            mEditText.setText(table);

            if (table != null){
                mBeerCount.setVisibility(View.VISIBLE);
                mBeerCount.setText(beerCountText);

                mSaveButton.setText("Upravit");
                mSaveButton.setEnabled(true);
            }
        } else {
            mBeerCount.setVisibility(View.GONE);
            mDeleteTableButton.setVisibility(Button.GONE);
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.toString().equals("")){
                   mSaveButton.setEnabled(false);
                   mSaveButton.setBackgroundColor(Color.GRAY);
               }else{
                   mSaveButton.setEnabled(true);
                   mSaveButton.setBackgroundColor(getResources().getColor(R.color.colorButtonBackground));
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String text = mEditText.getText().toString();

                if (finalIsUpdate){
                    String beerCountText = mBeerCount.getText().toString();
                    int beerCount = Integer.parseInt(beerCountText);
                   myDb.updateTable(bundle.getInt("id") , text);
                   myDb.updateBeerCount(bundle.getInt("id") , beerCount);
               }else{
                   TableModel item = new TableModel();
                   item.setTable(text);
                   item.setStatus(0);
                   item.setBeerCount(0);
                   myDb.insertTable(item);
               }
               dismiss();
            }
        });

        mDeleteTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteTable(bundle.getInt("id"));
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
