package com.example.wagba.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.wagba.Fragments.RestaurantsListFragmentDirections;
import com.example.wagba.Model.RestaurantModel;
import com.example.wagba.R;
import com.example.wagba.ViewHolder.RestaurantViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class RestaurantAdapter extends FirebaseRecyclerAdapter<RestaurantModel, RestaurantViewHolder> {

    public RestaurantAdapter(@NonNull FirebaseRecyclerOptions<RestaurantModel> options) {
        super(options);
    }
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position, @NonNull RestaurantModel model) {
        int pos = position;
        holder.restaurantName.setText(model.getRestaurantName());
        holder.restaurantCategory.setText(model.getRestaurantCategory());
        holder.restaurantRatingBar.setRating(Float.parseFloat(model.getRestaurantRating()));
        Picasso.get().load(model.getRestaurantImage()).into(holder.restaurantImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = RestaurantsListFragmentDirections.actionRestaurantsToFoodListFragment(getRef(pos).getKey(), model.getRestaurantName());
                Navigation.findNavController(holder.itemView).navigate(action);
            }
        });
    }



}
