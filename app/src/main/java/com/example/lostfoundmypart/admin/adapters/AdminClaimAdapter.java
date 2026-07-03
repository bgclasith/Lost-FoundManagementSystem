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
import com.example.lostfoundmypart.admin.activities.AdminClaimDetailsActivity;
import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.admin.models.Claim;

import java.util.ArrayList;

public class AdminClaimAdapter
        extends RecyclerView.Adapter<AdminClaimAdapter.ViewHolder> {

    ArrayList<Claim> list;

    public AdminClaimAdapter(ArrayList<Claim> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.admin_claim_item,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Claim claim = list.get(position);

        holder.txtItemName.setText(claim.getItemName());

        holder.txtEmail.setText(claim.getClaimantEmail());

        holder.txtStatus.setText(
                "Status : " + claim.getStatus());

        // Load Image
        if (claim.getItemImage() != null &&
                !claim.getItemImage().isEmpty()) {

            Glide.with(holder.itemView.getContext())
                    .load(claim.getItemImage())
                    .placeholder(R.drawable.ic_found)
                    .into(holder.imgItem);

        } else {

            holder.imgItem.setImageResource(
                    R.drawable.ic_found);

        }

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    AdminClaimDetailsActivity.class);

            intent.putExtra("claimId", claim.getClaimId());
            intent.putExtra("itemId", claim.getItemId());
            intent.putExtra("claimantId", claim.getClaimantId());
            intent.putExtra("itemName", claim.getItemName());
            intent.putExtra("claimantName", claim.getClaimantName());
            intent.putExtra("claimantEmail", claim.getClaimantEmail());
            intent.putExtra("proof", claim.getProof());
            intent.putExtra("status", claim.getStatus());
            intent.putExtra("itemImage", claim.getItemImage());

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
                txtEmail,
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

            txtEmail =
                    itemView.findViewById(
                            R.id.txtEmail);

            txtStatus =
                    itemView.findViewById(
                            R.id.txtStatus);
        }
    }
}