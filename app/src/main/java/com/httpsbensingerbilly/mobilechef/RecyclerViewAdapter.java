package com.httpsbensingerbilly.mobilechef;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{



    private ArrayList<String> mFoodItems;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> foodItems){

        mFoodItems = foodItems;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(AppSettings.tagRecycle, "onCreateViewHolder: called.");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(AppSettings.tagRecycle, "onBindViewHolder: called.");

//        Glide.with(mContext)
//                .asBitmap()
//                .load(mImages.get(position))
//                .into(holder.image);

        holder.foodName.setText(mFoodItems.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(AppSettings.tagRecycle, "onClick: clicked on: " + mFoodItems.get(position));

                Toast.makeText(mContext, mFoodItems.get(position), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {

        return mFoodItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        //CircleImageView image;
        TextView foodName;
        LinearLayout parentLayout;

        public ViewHolder(View itemView){
            super(itemView);

            //image = itemView.findViewById(R.id.image);
            foodName = itemView.findViewById(R.id.foodName);
            parentLayout = itemView.findViewById(R.id.parentLayout);

        }
    }
}
