package com.example.wagba.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wagba.Model.FoodModel;
import com.example.wagba.R;
import com.example.wagba.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class FoodAdapter extends FirebaseRecyclerAdapter<FoodModel, FoodViewHolder> {

    public FoodAdapter(@NonNull FirebaseRecyclerOptions<FoodModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull FoodModel model) {
        holder.foodName.setText(model.getFoodName());
        holder.foodDescription.setText(model.getFoodDescription());
        holder.foodPrice.setText(model.getFoodPrice());
        Picasso.get().load(model.getFoodImage()).into(holder.foodImage);
        holder.itemView.findViewById(R.id.addicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
                Integer itemCount = Integer.parseInt(itemCountTextView.getText().toString());
                itemCount++;
                itemCountTextView.setText(itemCount.toString());
            }
        });

        holder.itemView.findViewById(R.id.removeicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
                Integer itemCount = Integer.parseInt(itemCountTextView.getText().toString());
                if(itemCount == 0)
                {
                    return;
                }
                itemCount--;
                itemCountTextView.setText(itemCount.toString());
            }
        });
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card, parent, false);
        return new FoodViewHolder(view);
    }
}
