package com.example.wif;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    Button addItem;
    //Button seeRecipes;
    ArrayList<ExampleItem> mExampleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItem = findViewById(R.id.addButton);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

            /*seeRecipes = findViewById(R.id.seeRecipes);
            seeRecipes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mExampleList.isEmpty()){
                        Toast.makeText(MainActivity.this, "No items Found!", LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, recipe_list.class);
                        startActivity(intent);
                    }
                }
            });*/


    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("item list", "");
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        mExampleList = gson.fromJson(json, type);
        Log.d("items", json);
    }
}