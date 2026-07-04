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
import com.example.lostfoundmypart.admin.adapters.AdminUserAdapter;
import com.example.lostfoundmypart.admin.models.User;

import java.util.ArrayList;


public class AdminUsersFragment extends Fragment {


    RecyclerView recyclerViewUsers;

    ArrayList<User> userList;
    ArrayList<String> userIds;

    AdminUserAdapter adapter;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_admin_users,
                container,
                false);


        recyclerViewUsers =
                view.findViewById(
                        R.id.recyclerViewUsers);


        recyclerViewUsers.setLayoutManager(
                new LinearLayoutManager(
                        getContext()));


        userList = new ArrayList<>();
        userIds = new ArrayList<>();


        adapter =
                new AdminUserAdapter(
                        userList,
                        userIds);


        recyclerViewUsers.setAdapter(adapter);



        FirebaseDatabase.getInstance()
                .getReference("Users")
                .addValueEventListener(
                        new ValueEventListener() {


                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {


                                userList.clear();
                                userIds.clear();


                                for(DataSnapshot data :
                                        snapshot.getChildren()){


                                    User user =
                                            data.getValue(
                                                    User.class);


                                    if(user != null){

                                        userList.add(user);

                                        userIds.add(
                                                data.getKey());

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