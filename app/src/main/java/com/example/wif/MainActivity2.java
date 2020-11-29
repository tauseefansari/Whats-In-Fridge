package com.example.wif;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity2 extends AppCompatActivity implements ExampleDialog.ExampleDialogListner {
    ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Button seeRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btnOpen = findViewById(R.id.button_dialog);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        seeRecipes = findViewById(R.id.seeRecipes);
        seeRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mExampleList.isEmpty()){
                    Toast.makeText(MainActivity2.this, "No items Found!", LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity2.this, recipe_list.class);
                    startActivity(intent);
                }
            }
        });
        loadData();
        buildRecyclerView();
        //setInsertButton();

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(MainActivity2.this,"Items Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //@Override
    /*public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("item list", "");
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        mExampleList = gson.fromJson(json, type);
        Log.d("items", json);
    }*/
    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }



    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mExampleList);
        editor.putString("item list",json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("item list", null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        mExampleList = gson.fromJson(json, type);
        if(mExampleList == null){
            mExampleList = new ArrayList<>();
        }
    }

    private void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                saveData();
            }
        });

    }
    public void removeItem(int position) {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    /*private void setInsertButton(){
        Button buttonInsert = findViewById(R.id.button_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText line1 = findViewById(R.id.edittext_line_1);
                //EditText line2 = findViewById(R.id.edittext_line_2);
                //insertItem(line1.getText().toString(), line2.getText().toString());
            }
        });
    }*/
    private void insertItem(String line1, String line2) {
        mExampleList.add(new ExampleItem(R.drawable.cart, line1, line2));
        mAdapter.notifyItemInserted(mExampleList.size());
    }

    @Override
    public void applyText(String itemName, String itemQuan) {
        if(!itemName.matches("") && !itemQuan.matches("")) {
            mExampleList.add(new ExampleItem(R.drawable.cart, itemName, itemQuan));
            mAdapter.notifyItemInserted(mExampleList.size());
            saveData();
        }
        else{
            Toast.makeText(MainActivity2.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
        }
    }

}