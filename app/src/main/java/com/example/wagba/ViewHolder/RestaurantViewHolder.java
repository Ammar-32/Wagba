package com.example.wagba.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    public ImageView restaurantImage;
    public TextView restaurantName;
    public TextView restaurantCategory;
    public RatingBar restaurantRatingBar;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        restaurantImage=itemView.findViewById(R.id.restaurantimg);
        restaurantName=itemView.findViewById(R.id.restaurantname);
        restaurantCategory=itemView.findViewById(R.id.restaurantcategory);
        restaurantRatingBar=itemView.findViewById(R.id.ratingbar);
    }
}
