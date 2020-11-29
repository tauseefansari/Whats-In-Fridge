package com.example.wif;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ExampleDialog extends AppCompatDialogFragment {
    private AutoCompleteTextView editTextItemName;
    private AutoCompleteTextView editTextItemWeight;
    private TextInputLayout editTextItemQuan;
    private ExampleDialogListner listner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view)
                .setTitle("Add Item")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String itemName = editTextItemName.getText().toString();
                        String itemQuan = editTextItemQuan.getEditText().getText().toString()+" "+editTextItemWeight.getText().toString();
                        if(editTextItemWeight.getText().toString().matches(""))
                        {
                            itemQuan = editTextItemQuan.getEditText().getText().toString()+" kg";
                        }
                        listner.applyText(itemName,itemQuan);
                    }
                });
        editTextItemName = view.findViewById(R.id.edit_itemname);
        String[] items = {"Chicken","Mutton","Beef","Fish","Prawns","Crabs","Carrot","Spinach","Cucumber","Brinjal","Ladyfinger","Cauliflower","BitterGourd","French Beans","Cabbage","Fenugreek","Pumpkin","Beetroot","Rice","Water","Milk","Curd","Raisins","Almond","Cashews","Tea Pack","Sugar","Salt","Red Mirch Powder","Haldi Powder","Jeera","Elaichi","Long","Lassan","Phudhina","Potato","Onion","Capsicum","Red Chilli","Dhaniya Powder","Tomatoes","Wheat","Corn","Soya Sauce","Chilli Flake Sauce","Schezwan Sauce","Egg","Oil","Kadipatta","Banana","Apple","Chickoo","Grapes","Anaar","Mango","Jelly","Bread","Butter"};
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, items);
        editTextItemName.setAdapter(mArrayAdapter);

        editTextItemWeight = view.findViewById(R.id.edit_itemweight);
        String[] weights = {"Kg","Ltr","gm","Pcs"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, weights);
        editTextItemWeight.setAdapter(adapter);

        editTextItemQuan = view.findViewById(R.id.edit_itemquan);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (ExampleDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement ExampleDialogListner");
        }
    }

    public interface ExampleDialogListner{
        void applyText(String itemName ,String itemQuan);
    }
}
