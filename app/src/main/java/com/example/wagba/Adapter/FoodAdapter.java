package com.example.wagba.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wagba.Database.OrderItemDao;
import com.example.wagba.Database.OrderItemDatabase;
import com.example.wagba.Database.OrderItemModel;
import com.example.wagba.Model.FoodModel;
import com.example.wagba.R;
import com.example.wagba.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class FoodAdapter extends FirebaseRecyclerAdapter<FoodModel, FoodViewHolder> {
    OrderItemDao orderItemDao;
    public FoodAdapter(@NonNull FirebaseRecyclerOptions<FoodModel> options, OrderItemDatabase cartDatabase) {
        super(options);
        orderItemDao = cartDatabase.orderItemDao();

    }

    @Override
    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull FoodModel model) {
        int pos = position;
        TextView itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
        itemCountTextView.setText(getItemCountForFood(getRef(pos).getKey()).toString());
        holder.foodName.setText(model.getFoodName());
        holder.foodDescription.setText(model.getFoodDescription());
        holder.foodPrice.setText(model.getFoodPrice());
        Picasso.get().load(model.getFoodImage()).into(holder.foodImage);

        if(model.getFoodAvailability().equals("Available")) {
            holder.itemView.findViewById(R.id.addicon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
                    Integer itemCount = Integer.parseInt(itemCountTextView.getText().toString());
                    itemCount++;
                    Float totalPrice = Float.parseFloat(model.getFoodPrice()) * itemCount;
                    itemCountTextView.setText(itemCount.toString());
                    OrderItemModel orderItem = new OrderItemModel(getRef(pos).getKey(), model.getFoodName(), itemCount.toString(), totalPrice.toString());
                    orderItemDao.insert(orderItem);
                }
            });

            holder.itemView.findViewById(R.id.removeicon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
                    Integer itemCount = Integer.parseInt(itemCountTextView.getText().toString());
                    if (itemCount == 0) {
                        return;
                    }
                    itemCount--;
                    Float totalPrice = Float.parseFloat(model.getFoodPrice()) * itemCount;
                    itemCountTextView.setText(itemCount.toString());
                    OrderItemModel orderItem = new OrderItemModel(getRef(pos).getKey(), model.getFoodName(), itemCount.toString(), totalPrice.toString());
                    orderItemDao.update(orderItem);

                    if (itemCount == 0) {
                        orderItemDao.deleteOrderItemById(getRef(pos).getKey());
                    }

                }
            });
        }else{
            holder.itemView.findViewById(R.id.removeicon).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.addicon).setVisibility(View.GONE);
            itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
            itemCountTextView.setText("N/A");

        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card, parent, false);
        return new FoodViewHolder(view);
    }

    private Integer getItemCountForFood(String foodId) {
        OrderItemModel orderItem = orderItemDao.getOrderItemById(foodId);
        if (orderItem == null) {
            return 0;
        }
        return Integer.parseInt(orderItem.getOrderItemCount());
    }
}
