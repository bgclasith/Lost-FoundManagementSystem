package com.example.lostfoundmypart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.activities.ReportDetailsActivity;
import com.example.lostfoundmypart.models.Report;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final Context context;
    private final List<Report> reportList;

    public ReportAdapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_report, parent, false);

        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {

        Report report = reportList.get(position);

        holder.txtItemName.setText(report.getItemName());
        holder.txtType.setText(report.getReportType());
        holder.txtDate.setText(report.getDate());
        holder.txtStatus.setText(report.getStatus());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReportDetailsActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView txtItemName, txtType, txtDate, txtStatus;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtType = itemView.findViewById(R.id.txtType);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}