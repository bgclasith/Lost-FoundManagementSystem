package com.example.lostfoundmypart.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.admin.models.User;

import java.util.ArrayList;

public class AdminUserAdapter
        extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    ArrayList<User> list;
    ArrayList<String> userIds;

    public AdminUserAdapter(
            ArrayList<User> list,
            ArrayList<String> userIds) {

        this.list = list;
        this.userIds = userIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.admin_user_item,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        User user = list.get(position);
        String userId = userIds.get(position);

        holder.txtName.setText(
                user.getFullName());

        holder.txtEmail.setText(
                user.getEmail());

        holder.btnDisable
                .setOnClickListener(v -> {

                    FirebaseDatabase
                            .getInstance()
                            .getReference("Users")
                            .child(userId)
                            .child("disabled")
                            .setValue(true);

                });

        holder.btnDelete
                .setOnClickListener(v -> {

                    FirebaseDatabase
                            .getInstance()
                            .getReference("Users")
                            .child(userId)
                            .removeValue();

                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtName,
                txtEmail;

        Button btnDisable,
                btnDelete;

        public ViewHolder(
                @NonNull View itemView) {

            super(itemView);

            txtName =
                    itemView.findViewById(
                            R.id.txtName);

            txtEmail =
                    itemView.findViewById(
                            R.id.txtEmail);

            btnDisable =
                    itemView.findViewById(
                            R.id.btnDisable);

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDelete);
        }
    }
}