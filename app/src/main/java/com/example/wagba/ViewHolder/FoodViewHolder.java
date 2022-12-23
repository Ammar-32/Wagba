package com.example.wagba.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;

public class FoodViewHolder extends RecyclerView.ViewHolder{
    public ImageView foodImage;
    public TextView foodName;
    public TextView foodDescription;
    public TextView foodPrice;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        foodName = (TextView) itemView.findViewById(R.id.foodname);
        foodDescription = (TextView) itemView.findViewById(R.id.fooddescription);
        foodPrice = (TextView) itemView.findViewById(R.id.foodprice);
        foodImage = (ImageView) itemView.findViewById(R.id.foodimg);
    }
}
