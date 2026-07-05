package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClaimAdapter extends RecyclerView.Adapter<ClaimAdapter.ClaimViewHolder> {

    private List<Claim> claimList;

    public ClaimAdapter(List<Claim> claimList) {
        this.claimList = claimList;
    }

    @NonNull
    @Override
    public ClaimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_claim_status, parent, false);
        return new ClaimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimViewHolder holder, int position) {
        Claim claim = claimList.get(position);
        holder.tvItemName.setText(claim.getItemName());
        holder.tvClaimDate.setText("Submitted on: " + claim.getDate());
        holder.tvStatus.setText("Status: " + claim.getStatus());
        
        // Simple coloring based on status
        if (claim.getStatus().equalsIgnoreCase("Approved")) {
            holder.tvStatus.setTextColor(0xFF4CAF50); // Green
        } else if (claim.getStatus().equalsIgnoreCase("Rejected")) {
            holder.tvStatus.setTextColor(0xFFF44336); // Red
        } else {
            holder.tvStatus.setTextColor(0xFFFF9800); // Orange
        }
    }

    @Override
    public int getItemCount() {
        return claimList.size();
    }

    public static class ClaimViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvClaimDate, tvStatus;

        public ClaimViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvClaimDate = itemView.findViewById(R.id.tv_claim_date);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}