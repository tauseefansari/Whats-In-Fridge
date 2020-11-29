package com.example.wif;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class recipe_list extends AppCompatActivity implements RecipeAdapter.OnItemClickListener{
    public static final String EXTRA_URL = "imageUrl";
    ArrayList<ExampleRecipe> mRecipeList;
    ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout mCLayout;
    private Button mButtonDo;
    private TextView mTextView;
    private String ingredients="";
    private Boolean itemsModified=Boolean.TRUE;
    // MODIFY INGREDIENTS TO ACTUAL ARRAY ITEMS LATER ... PENDING

    private RecipeAdapter mExampleAdapter;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recyclerviewRecipes);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setIngredients();
    }

    private void setIngredients() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("item list", "");
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        mExampleList = gson.fromJson(json, type);
            for(int i=0;i<mExampleList.size();i++){
                ingredients+=mExampleList.get(i).getLine1();
                if(i!=mExampleList.size()-1){ ingredients+=",";}
            }
            mRecipeList = new ArrayList<>();
            mRequestQueue = Volley.newRequestQueue(this);
            Log.d("recipe", ingredients);
        ProgressThread progress = new ProgressThread();
        progress.execute();
    }

    private void getRecipeJSON(){
        String mJSONURLString = "https://api.edamam.com/search?q="+ingredients+"&app_id=433353fa&app_key=e239314be6b3122bc5b41494daf55fd7&cusineType=Indian";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //saveRecipeData(response.toString());
                        try{
                            JSONArray recipesJSON = response.getJSONArray("hits");
                            for(int i=0;i<recipesJSON.length();i++){
                                JSONObject recipeArrayJSON = recipesJSON.getJSONObject(i);
                                JSONObject recipeJSON = recipeArrayJSON.getJSONObject("recipe");
                                String name = recipeJSON.getString("label");
                                String health = recipeJSON.getString("healthLabels");
                                String imageUrl = recipeJSON.getString("image");
                                String sourceUrl = recipeJSON.getString("url");
                                String ingredients = recipeJSON.getJSONArray("ingredientLines").toString();
                                mRecipeList.add(new ExampleRecipe(imageUrl, name, ingredients,health,sourceUrl));
                            }
                            mExampleAdapter = new RecipeAdapter(recipe_list.this, mRecipeList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(recipe_list.this);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                });
        // Add JsonObjectRequest to the RequestQueue if and only if items are modified ....PENDING
        mRequestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(recipe_list.this, web_view.class);
        ExampleRecipe clickedItem = mRecipeList.get(position);
        intent.putExtra(EXTRA_URL, clickedItem.getrecipeURL());
        startActivity(intent);

    }

    private class ProgressThread extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog mDialog;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mDialog.isShowing())
            {
                mDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(recipe_list.this);
            mDialog.setTitle("Searching Recipes");
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage("Searching Please Wait...");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getRecipeJSON();
            return null;
        }
    }
}

