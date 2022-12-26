package com.example.wagba.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.Database.OrderItemModel;
import com.example.wagba.R;

public class OrderItemViewHolder extends RecyclerView.ViewHolder {
    TextView foodNameTextView;
    TextView countTextView;
    TextView priceTextView;

    public OrderItemViewHolder(View itemView) {
        super(itemView);
        foodNameTextView = itemView.findViewById(R.id.foodname);
        countTextView = itemView.findViewById(R.id.item_count);
        priceTextView = itemView.findViewById(R.id.price);
    }

    public void bind(OrderItemModel orderItem) {
        foodNameTextView.setText(orderItem.getOrderItemName());
        countTextView.setText(String.valueOf(orderItem.getOrderItemCount()));
        priceTextView.setText(String.valueOf(orderItem.getOrderItemTotalPrice()));
    }
}

