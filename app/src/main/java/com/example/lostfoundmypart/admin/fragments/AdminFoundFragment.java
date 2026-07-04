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
import com.example.lostfoundmypart.admin.adapters.AdminFoundAdapter;
import com.example.lostfoundmypart.admin.models.FoundItem;

import java.util.ArrayList;


public class AdminFoundFragment extends Fragment {


    RecyclerView recyclerViewFound;

    ArrayList<FoundItem> list;

    AdminFoundAdapter adapter;



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_admin_found,
                container,
                false);



        recyclerViewFound =
                view.findViewById(
                        R.id.recyclerViewFound);



        recyclerViewFound.setLayoutManager(
                new LinearLayoutManager(
                        getContext()
                ));



        list = new ArrayList<>();


        adapter =
                new AdminFoundAdapter(list);


        recyclerViewFound.setAdapter(adapter);



        FirebaseDatabase.getInstance()
                .getReference("FoundItems")
                .addValueEventListener(
                        new ValueEventListener() {


                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {


                                list.clear();


                                for(DataSnapshot data :
                                        snapshot.getChildren()) {


                                    FoundItem item =
                                            data.getValue(
                                                    FoundItem.class);



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