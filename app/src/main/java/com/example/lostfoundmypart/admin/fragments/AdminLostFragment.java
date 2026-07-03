package com.example.lostfoundmypart.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.admin.adapters.AdminLostAdapter;
import com.example.lostfoundmypart.admin.models.LostItem;

import java.util.ArrayList;


public class AdminLostFragment extends Fragment {


    RecyclerView recyclerViewLost;

    ArrayList<LostItem> list;

    AdminLostAdapter adapter;



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_admin_lost,
                container,
                false);



        recyclerViewLost =
                view.findViewById(
                        R.id.recyclerViewLost);



        recyclerViewLost.setLayoutManager(
                new LinearLayoutManager(
                        getContext()
                ));



        list = new ArrayList<>();


        adapter =
                new AdminLostAdapter(list);


        recyclerViewLost.setAdapter(adapter);



        FirebaseDatabase.getInstance()
                .getReference("LostItems")
                .addValueEventListener(
                        new ValueEventListener() {


                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {


                                list.clear();


                                for(DataSnapshot data :
                                        snapshot.getChildren()) {


                                    LostItem item =
                                            data.getValue(
                                                    LostItem.class);



                                    if(item != null){


                                        list.add(item);

                                    }

                                }


                                adapter.notifyDataSetChanged();

                            }



                            @Override
                            public void onCancelled(
                                    @NonNull DatabaseError error) {


                            }

                        });



        return view;

    }
}