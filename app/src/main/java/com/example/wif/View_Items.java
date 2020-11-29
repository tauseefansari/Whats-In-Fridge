package com.example.wif;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class View_Items extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ListView lv;
        ListView lvQ;
        ArrayList<String> itemNamesList = new ArrayList();
        ArrayList<String> itemQuantityList = new ArrayList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__items);
        Context context = getApplicationContext();
        String key = "Key";
        SharedPreferences shref;
        SharedPreferences.Editor editor;
        shref = context.getSharedPreferences("itemsInFridge", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String response=shref.getString( key , "");
        if(response!="") {
            ArrayList<String> importedItemsInFridge = gson.fromJson(response,
                    new TypeToken<List<String>>() {
                    }.getType());
        /*for(int i=0;i<importedItemsInFridge.size();i++)
        {
            String[] separated = importedItemsInFridge.get(i).split(":");
            itemNamesList.add(separated[0]);
            itemQuantityList.add(separated[1]);
        }
        String[] separated = importedItemsInFridge.get(7).split(":");
        itemNamesList.add(separated[0]);
        itemQuantityList.add(separated[1]);
        // Adding items to list view
        lv = (ListView) findViewById(R.id.itemsListView);
        lvQ = (ListView) findViewById(R.id.itemListView2);
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                itemNamesList );
        lv.setAdapter(arrayAdapter);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, itemNamesList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);

                return view;
            }
        };
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                itemQuantityList );
        lv.setAdapter(arrayAdapter);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, itemQuantityList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);

                return view;
            }
        };
        lvQ.setAdapter(adapter2);
        lv.setAdapter(adapter);
        */
            lv = (ListView) findViewById(R.id.itemsListView);
            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    importedItemsInFridge);
            lv.setAdapter(arrayAdapter);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, importedItemsInFridge) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);

                    textView.setTextColor(Color.WHITE);

                    return view;
                }
            };
            lv.setAdapter(adapter);
        }
        else {
            CharSequence text = "List is Empty!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}