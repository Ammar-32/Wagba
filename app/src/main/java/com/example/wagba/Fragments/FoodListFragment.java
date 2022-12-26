package com.example.wagba.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.wagba.Adapter.FoodAdapter;
import com.example.wagba.Database.OrderItemDao;
import com.example.wagba.Database.OrderItemDatabase;
import com.example.wagba.Model.FoodModel;
import com.example.wagba.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoodListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference foodRef;
    FoodAdapter foodAdapter;
    Button viewCartButton;

    public FoodListFragment() {
    }

    public static FoodListFragment newInstance(String param1, String param2) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        foodAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        foodAdapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        FoodListFragmentArgs args = FoodListFragmentArgs.fromBundle(getArguments());
        String restaurantID = args.getRestaurantID();
        String restaurantName = args.getRestaurantName();

        viewCartButton = view.findViewById(R.id.goToCart);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = FoodListFragmentDirections.actionFoodListFragmentToMycart();
                Navigation.findNavController(view).navigate(action);
            }
        });


        OrderItemDatabase cartDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), OrderItemDatabase.class, "order_items_database").allowMainThreadQueries().build();


        TextView toolbarTitle = (TextView) getActivity().findViewById(R.id.titletoolbar);
        toolbarTitle.setText(restaurantName + " Menu");
        database = FirebaseDatabase.getInstance("https://wagba-ed603-default-rtdb.europe-west1.firebasedatabase.app");
        foodRef = database.getReference("FoodModel");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(!(restaurantID.isEmpty()))
        {
            // Load corresponding Menu
            FirebaseRecyclerOptions<FoodModel> options =
                    new FirebaseRecyclerOptions.Builder<FoodModel>()
                            .setQuery(foodRef.orderByChild("restaurantID").equalTo(restaurantID), FoodModel.class)
                            .build();
            foodAdapter = new FoodAdapter(options, cartDatabase);
            recyclerView.setAdapter(foodAdapter);
        }

        return view;
    }
}
