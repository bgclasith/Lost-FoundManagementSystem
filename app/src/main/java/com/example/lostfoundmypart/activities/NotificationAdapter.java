package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        
        holder.tvTitle.setText(notification.getTitle());
        holder.tvMessage.setText(notification.getMessage());
        holder.tvTime.setText(notification.getTime());
        holder.tvId.setText(notification.getClaimId());

        // Background based on read/unread status
        if (notification.isRead()) {
            holder.layoutContainer.setBackgroundResource(R.drawable.bg_notif_card);
            holder.viewUnreadDot.setVisibility(View.GONE);
        } else {
            holder.layoutContainer.setBackgroundResource(R.drawable.bg_notif_card_unread);
            holder.viewUnreadDot.setVisibility(View.VISIBLE);
        }

        // Icon and background color based on type
        int iconRes = android.R.drawable.ic_dialog_info;
        int bgColor = R.color.notif_tag_bg;
        int iconTint = R.color.primary_dark;

        switch (notification.getType()) {
            case APPROVED:
                iconRes = android.R.drawable.checkbox_on_background; // Approximation for check
                bgColor = R.color.notif_icon_bg_approved;
                iconTint = android.R.color.holo_green_dark;
                break;
            case MATCHING:
                iconRes = android.R.drawable.ic_dialog_info;
                bgColor = R.color.notif_icon_bg_found;
                iconTint = android.R.color.holo_orange_dark;
                break;
            case RECOVERED:
                iconRes = android.R.drawable.ic_menu_agenda; // Approximation for package/item
                bgColor = R.color.notif_icon_bg_recovered;
                iconTint = R.color.primary_dark;
                break;
            case REJECTED:
                iconRes = android.R.drawable.ic_delete; // Approximation for X
                bgColor = R.color.notif_icon_bg_rejected;
                iconTint = android.R.color.holo_red_dark;
                break;
        }

        holder.ivIcon.setImageResource(iconRes);
        holder.layoutIconBg.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), bgColor));
        holder.ivIcon.setImageTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), iconTint));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        View layoutContainer, layoutIconBg, viewUnreadDot;
        ImageView ivIcon;
        TextView tvTitle, tvMessage, tvTime, tvId;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutContainer = itemView.findViewById(R.id.layout_container);
            layoutIconBg = itemView.findViewById(R.id.layout_icon_bg);
            viewUnreadDot = itemView.findViewById(R.id.view_unread_dot);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvTitle = itemView.findViewById(R.id.tv_notification_title);
            tvMessage = itemView.findViewById(R.id.tv_notification_message);
            tvTime = itemView.findViewById(R.id.tv_notification_time);
            tvId = itemView.findViewById(R.id.tv_notification_id);
        }
    }
}