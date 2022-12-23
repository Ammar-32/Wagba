package com.example.wagba.Fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.Adapter.RestaurantAdapter;
import com.example.wagba.Model.RestaurantModel;
import com.example.wagba.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.Locale;


public class RestaurantsListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference restaurantRef;
    RestaurantAdapter restaurantAdapter;
    EditText searchBar;
    FirebaseRecyclerOptions<RestaurantModel> options;

    public RestaurantsListFragment() {
    }

    public static RestaurantsListFragment newInstance(String param1, String param2) {
        RestaurantsListFragment fragment = new RestaurantsListFragment();
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
        restaurantAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        restaurantAdapter.stopListening();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_restaurant);
        searchBar = (EditText) view.findViewById(R.id.searchBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        database = FirebaseDatabase.getInstance("https://wagba-ed603-default-rtdb.europe-west1.firebasedatabase.app");
        restaurantRef = database.getReference("RestaurantModel");



        loadDataInRecyclerView("");

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null)
                {
                    loadDataInRecyclerView(s.toString());
                }
                else
                {
                    loadDataInRecyclerView("");
                }
            }
        });

        return view;
    }

    public void loadDataInRecyclerView(String data)
    {
        Query query = restaurantRef.orderByChild("restaurantName").startAt(data).endAt(data + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<RestaurantModel>().setQuery(query, RestaurantModel.class).build();
        restaurantAdapter = new RestaurantAdapter(options);
        recyclerView.setAdapter(restaurantAdapter);
        restaurantAdapter.startListening();
    }


}