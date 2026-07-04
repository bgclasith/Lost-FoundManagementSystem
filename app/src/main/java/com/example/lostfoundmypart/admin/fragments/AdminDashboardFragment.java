package com.example.lostfoundmypart.admin.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.lostfoundmypart.R;


public class AdminDashboardFragment extends Fragment {


    TextView txtUsers,
            txtLost,
            txtFound,
            txtRecovered,
            txtPending;



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_admin_dashboard,
                container,
                false);



        txtUsers =
                view.findViewById(R.id.txtUsers);

        txtLost =
                view.findViewById(R.id.txtLost);

        txtFound =
                view.findViewById(R.id.txtFound);

        txtRecovered =
                view.findViewById(R.id.txtRecovered);

        txtPending =
                view.findViewById(R.id.txtPending);



        loadReports();


        return view;
    }



    private void loadReports(){


        FirebaseDatabase db =
                FirebaseDatabase.getInstance();



        // Users Count

        db.getReference("Users")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {

                                txtUsers.setText(
                                        "Total Users : "
                                                + snapshot.getChildrenCount());

                            }

                            @Override
                            public void onCancelled(
                                    @NonNull DatabaseError error) {

                            }
                        });



        // Lost Count

        db.getReference("LostItems")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {

                                txtLost.setText(
                                        "Total Lost Items : "
                                                + snapshot.getChildrenCount());


                                int recovered = 0;


                                for(DataSnapshot data :
                                        snapshot.getChildren()){


                                    String status =
                                            data.child("status")
                                                    .getValue(String.class);


                                    if("Recovered".equals(status)){

                                        recovered++;

                                    }

                                }


                                txtRecovered.setText(
                                        "Recovered Items : "
                                                + recovered);

                            }


                            @Override
                            public void onCancelled(
                                    @NonNull DatabaseError error) {

                            }

                        });



        // Found Count

        db.getReference("FoundItems")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {

                                txtFound.setText(
                                        "Total Found Items : "
                                                + snapshot.getChildrenCount());

                            }


                            @Override
                            public void onCancelled(
                                    @NonNull DatabaseError error) {

                            }

                        });



        // Pending Claims

        db.getReference("Claims")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {


                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {


                                int pending = 0;


                                for(DataSnapshot data :
                                        snapshot.getChildren()){


                                    String status =
                                            data.child("status")
                                                    .getValue(String.class);



                                    if("Pending".equals(status)){

                                        pending++;

                                    }

                                }


                                txtPending.setText(
                                        "Pending Claims : "
                                                + pending);

                            }


                            @Override
                            public void onCancelled(
                                    @NonNull DatabaseError error) {

                            }

                        });

    }

}