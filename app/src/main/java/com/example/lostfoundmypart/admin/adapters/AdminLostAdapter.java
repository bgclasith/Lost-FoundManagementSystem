package com.example.lostfoundmypart.admin.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lostfoundmypart.admin.activities.AdminLostDetailsActivity;
import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.admin.models.LostItem;

import java.util.ArrayList;

public class AdminLostAdapter
        extends RecyclerView.Adapter<AdminLostAdapter.ViewHolder> {

    ArrayList<LostItem> list;

    public AdminLostAdapter(ArrayList<LostItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.admin_lost_item,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        LostItem item = list.get(position);

        holder.itemName.setText(item.getItemName());

        holder.location.setText(item.getLocation());

        holder.status.setText(
                "Status : " + item.getStatus());

        // Load Image
        if (item.getItemImage() != null &&
                !item.getItemImage().isEmpty()) {

            Glide.with(holder.itemView.getContext())
                    .load(item.getItemImage())
                    .placeholder(R.drawable.ic_found)
                    .into(holder.imgItem);

        } else {

            holder.imgItem.setImageResource(
                    R.drawable.ic_found);

        }

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    AdminLostDetailsActivity.class);

            intent.putExtra(
                    "itemId",
                    item.getItemId());

            intent.putExtra(
                    "itemName",
                    item.getItemName());

            intent.putExtra(
                    "description",
                    item.getDescription());

            intent.putExtra(
                    "location",
                    item.getLocation());

            intent.putExtra(
                    "dateLost",
                    item.getDateLost());

            intent.putExtra(
                    "status",
                    item.getStatus());

            intent.putExtra(
                    "itemImage",
                    item.getItemImage());

            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView itemName,
                location,
                status;

        ImageView imgItem;

        public ViewHolder(
                @NonNull View itemView) {

            super(itemView);

            imgItem = itemView.findViewById(
                    R.id.imgItem);

            itemName = itemView.findViewById(
                    R.id.txtItemName);

            location = itemView.findViewById(
                    R.id.txtLocation);

            status = itemView.findViewById(
                    R.id.txtStatus);
        }
    }
}