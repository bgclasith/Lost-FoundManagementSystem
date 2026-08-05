package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView rvNotifications;
    private TextView tvUnreadCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        tvUnreadCount = findViewById(R.id.tv_unread_count);
        rvNotifications = findViewById(R.id.rv_notifications);
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));

        // Sample Data from Screenshot
        List<Notification> notificationList = new ArrayList<>();
        
        notificationList.add(new Notification(
                "Claim Approved", 
                "Your ownership claim for Samsung Galaxy S23 has been approved. Visit the main office to collect your item.", 
                "2 hours ago", 
                "CLM-2026-0041", 
                Notification.Type.APPROVED, 
                false));

        notificationList.add(new Notification(
                "Matching Item Found", 
                "A brown leather wallet matching your lost item report was found near Pettah Bus Terminal.", 
                "5 hours ago", 
                "", 
                Notification.Type.MATCHING, 
                false));

        notificationList.add(new Notification(
                "Item Recovered", 
                "Your item Samsung Galaxy S23 has been successfully recovered and handed over.", 
                "Yesterday", 
                "CLM-2026-0041", 
                Notification.Type.RECOVERED, 
                true));

        notificationList.add(new Notification(
                "Claim Rejected", 
                "Your claim for National Identity Card (CLM-2026-0029) was rejected. Insufficient supporting evidence.", 
                "2 days ago", 
                "CLM-2026-0029", 
                Notification.Type.REJECTED, 
                true));

        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        rvNotifications.setAdapter(adapter);
        
        // Update unread count dynamically
        long unreadCount = 0;
        for (Notification n : notificationList) {
            if (!n.isRead()) unreadCount++;
        }
        tvUnreadCount.setText(unreadCount + " unread");
    }
}