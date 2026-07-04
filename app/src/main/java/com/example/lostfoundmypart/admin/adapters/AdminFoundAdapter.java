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
import com.example.lostfoundmypart.admin.activities.AdminFoundDetailsActivity;
import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.admin.models.FoundItem;

import java.util.ArrayList;

public class AdminFoundAdapter
        extends RecyclerView.Adapter<AdminFoundAdapter.ViewHolder> {

    ArrayList<FoundItem> list;

    public AdminFoundAdapter(ArrayList<FoundItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.admin_found_item,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        FoundItem item = list.get(position);

        holder.txtItemName.setText(item.getItemName());

        holder.txtLocation.setText(item.getLocation());

        holder.txtStatus.setText(
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

            Intent intent =
                    new Intent(
                            v.getContext(),
                            AdminFoundDetailsActivity.class);

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
                    "dateFound",
                    item.getDateFound());

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

        TextView txtItemName,
                txtLocation,
                txtStatus;

        ImageView imgItem;

        public ViewHolder(
                @NonNull View itemView) {

            super(itemView);

            imgItem =
                    itemView.findViewById(
                            R.id.imgItem);

            txtItemName =
                    itemView.findViewById(
                            R.id.txtItemName);

            txtLocation =
                    itemView.findViewById(
                            R.id.txtLocation);

            txtStatus =
                    itemView.findViewById(
                            R.id.txtStatus);
        }
    }
}