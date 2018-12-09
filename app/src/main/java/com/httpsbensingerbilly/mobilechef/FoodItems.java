package com.httpsbensingerbilly.mobilechef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FoodItems extends AppCompatActivity {

    private static Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);

        closeBtn = (Button) findViewById(R.id.closeBtn);


        //Set list to food item list
        initRecyclerView();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseClicked();
            }
        });

    }

    private void onCloseClicked(){
        Log.d(AppSettings.tag, "onCloseClicked");

        Intent intent = getIntent();

        //TODO: Save Data to new list and return
        setResult(RESULT_OK, intent);

        if(intent.hasExtra("FoodItems")){
            Log.d(AppSettings.tag, "Data from FoodItem Intent: " + intent.getStringArrayListExtra("FoodItems"));
        }
        else{
            Log.d(AppSettings.tag, "There is no Data");
        }

        finish();
    }

    private void initRecyclerView(){
        Log.d(AppSettings.tag, "initRecyclerView: init recyclerview.");

        Intent intent = getIntent();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, intent.getStringArrayListExtra("FoodItems"));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
