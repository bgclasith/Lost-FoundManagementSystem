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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.admin.adapters.AdminClaimAdapter;
import com.example.lostfoundmypart.admin.models.Claim;

import java.util.ArrayList;

public class AdminClaimsFragment extends Fragment {

    RecyclerView recyclerView;

    ArrayList<Claim> list;

    AdminClaimAdapter adapter;

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_admin_claims,
                container,
                false);

        recyclerView = view.findViewById(
                R.id.recyclerViewClaims);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        getContext()));

        list = new ArrayList<>();

        adapter = new AdminClaimAdapter(list);

        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Claims");

        databaseReference
                .addValueEventListener(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {

                                list.clear();

                                for (DataSnapshot ds :
                                        snapshot.getChildren()) {

                                    Claim claim =
                                            ds.getValue(
                                                    Claim.class);

                                    if (claim != null) {

                                        claim.setClaimId(
                                                ds.getKey());

                                        list.add(claim);
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