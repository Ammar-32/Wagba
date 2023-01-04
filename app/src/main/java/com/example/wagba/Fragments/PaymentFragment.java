package com.example.wagba.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.Database.OrderItemDao;
import com.example.wagba.Database.OrderItemDatabase;
import com.example.wagba.Database.OrderItemModel;
import com.example.wagba.Database.User;
import com.example.wagba.Database.UserDao;
import com.example.wagba.Model.OrderModel;
import com.example.wagba.MyApplication;
import com.example.wagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PaymentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserDao userDao = MyApplication.getUserDao();

    public PaymentFragment() {

    }

    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        OrderItemDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), OrderItemDatabase.class, "order_items_database").allowMainThreadQueries().build();
        OrderItemDao orderItemDao = db.orderItemDao();
        List<OrderItemModel> cartItems = orderItemDao.getAll();
        List<String> orderSummary = new ArrayList<>();
        TextView orderSummaryPrice = view.findViewById(R.id.order_summary_price);
        if(cartItems.size() != 0) {
            for (OrderItemModel orderItem : cartItems) {
                orderSummary.add("x" + orderItem.getOrderItemCount() + " " + orderItem.getOrderItemName() + "\n" + orderItem.getOrderItemTotalPrice() + "EGP");
            }
            ListView orderSummaryListView = view.findViewById(R.id.listView);
            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, orderSummary);
            orderSummaryListView.setAdapter(adapter);
            orderSummaryPrice.setText(String.valueOf(Float.parseFloat(orderItemDao.getTotalPriceSum().toString()) + 20.0F));
        }





        // Get a reference to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagba-ed603-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference ordersRef = database.getReference("Orders");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        User user = userDao.getUser(firebaseUser.getEmail());

        Spinner deliveryGateSpinner = view.findViewById(R.id.delivery_gate_spinner);
        Spinner deliveryTimeSpinner = view.findViewById(R.id.delivery_time_spinner);
        Spinner paymentMethodSpinner = view.findViewById(R.id.payment_method_spinner);

        Button confirmOrderButton = view.findViewById(R.id.confirm_order);
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current time
                Calendar calendar = Calendar.getInstance();
                long currentTime = calendar.getTimeInMillis();

                // Get the delivery time string in the "HH:mm" format
                String deliveryTime = deliveryTimeSpinner.getSelectedItem().toString();

                // Create a SimpleDateFormat object for parsing the delivery time string
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

                // Set the calendar's time to the current time
                calendar.setTimeInMillis(currentTime);

                // Parse the delivery time string as a relative time from the current time
                Date date = null;
                try {
                    date = sdf.parse(deliveryTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Set the calendar's hour and minute to the parsed delivery time
                calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
                calendar.set(Calendar.MINUTE, date.getMinutes());

                // Get the delivery time in milliseconds
                long deliveryTimeMillis = calendar.getTimeInMillis();

                // Calculate the difference between the current time and delivery time
                long timeDifference = deliveryTimeMillis - currentTime;

                // Calculate the time difference in hours as the absolute value of the difference
                long timeDifferenceHours = Math.abs(TimeUnit.MILLISECONDS.toHours(timeDifference));

                if (timeDifferenceHours >= 2) {
                    // Place the order if the difference is greater than or equal to 2 hours
                    OrderModel order = new OrderModel();
                    order.setOrderID(generateUniqueId()); // generate a unique ID for the order
                    order.setOrderItems(orderItemDao.getAll()); // get the order items from the Room database
                    order.setUserFullName(user.getFullName()); // get the user's full name
                    order.setUserPhoneNumber(user.getPhoneNumber()); // get the user's phone number
                    order.setDeliveryTime(deliveryTimeSpinner.getSelectedItem().toString()); // get the selected delivery time
                    order.setDeliveryGate(deliveryGateSpinner.getSelectedItem().toString()); // get the selected delivery gate
                    order.setPaymentMethod(paymentMethodSpinner.getSelectedItem().toString()); // get the selected payment method
                    order.setOrderStatus("Order Requested");
                    order.setPrice(String.valueOf(Float.parseFloat(orderItemDao.getTotalPriceSum().toString()) + 20.0F));

                    ordersRef.child(order.getOrderID()).setValue(order);
                    orderItemDao.clearCart();
                    // Display an alert dialog with a message
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Order Placed Successfully");
                    builder.setMessage("Thank you for placing your order!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog

                            NavDirections action = PaymentFragmentDirections.actionPaymentFragmentToOrderhistory();
                            Navigation.findNavController(view).navigate(action);

                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    // Display an error message if the difference is less than 2 hours
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Sorry");
                    builder.setMessage("Please select a delivery time at least 2 hours from now.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });





        return view;
    }

    public String generateUniqueId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 6).toUpperCase(Locale.ROOT);
    }
}