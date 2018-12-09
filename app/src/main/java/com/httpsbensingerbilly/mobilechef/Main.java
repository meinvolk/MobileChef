package com.httpsbensingerbilly.mobilechef;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONException;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    private static TextView jsonData;
    private static Button button;

    private static EditText foodInputTxt;
    private static Button viewListBtn;
    private static Button addFoodBtn;

    //Possibly change to SQLiteDB
    private static ArrayList<String> foodItems;

    private static final int foodItem = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FOR DEV PURPOSES
        button = (Button)findViewById(R.id.getData);
        jsonData = (TextView)findViewById(R.id.json);

        //Find View Objects
        foodInputTxt = (EditText) findViewById(R.id.foodInputTxt);
        addFoodBtn = (Button) findViewById(R.id.addFoodBtn);
        viewListBtn = (Button) findViewById(R.id.viewListBtn);

        //Initialize food list
        foodItems = new ArrayList<String>();

        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item = foodInputTxt.getText().toString();

                if(item != null && item != ""){
                    AddItemToFoodList(item);
                }
                else{
                    Toast.makeText(Main.this, "Please enter text before adding.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFoodListActivity();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetRequestData();
            }
        });
    }

    private void AddItemToFoodList(String item){

        foodItems.add(item);

        Log.d(AppSettings.tag, item + " added to array");
    }

    private void openFoodListActivity()
    {
        Log.d(AppSettings.tag, "openFoodListActivity: Clicked");

        Intent intent = new Intent("android.intent.action.FoodItems");

        //Pass data to food activity
        intent.putExtra("FoodItems", foodItems);

        startActivityForResult(intent, foodItem);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(AppSettings.tag, "onActivityResult");

        Log.d(AppSettings.tag, "requestCode: " + requestCode);
        Log.d(AppSettings.tag, "resultCode: " + resultCode);

        if(data == null){
            Log.e(AppSettings.tag, "data: NULL");
        }
        else{
            Log.d(AppSettings.tag, "data: " + data);
        }

        if(requestCode == foodItem && resultCode == RESULT_OK){
            Log.d(AppSettings.tag, "Data was passed to intent");
        }
        else{
            Log.d(AppSettings.tag, "Data did not get passed");
        }

    }

    private void GetRequestData(){

        //Format URI
        String listString = "";

        for (String s : foodItems)
        {
            listString += s + ",";
        }

        String url = "http://www.recipepuppy.com/api/?i=" + listString;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            //response.getJSONArray("results");
                            jsonData.setText("Response: " + response.getJSONArray("results"));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Main.this, error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        MySingleton.getInstance(Main.this).addToRequestQueue(jsonObjectRequest);
    }
}
