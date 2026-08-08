package com.nibm.findit;

import android.app.Application;
import com.cloudinary.android.MediaManager;
import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", ""); // TODO: Replace with your cloud name
            config.put("api_key", ""); // TODO: Replace with your api key
            config.put("api_secret", "-"); // TODO: Replace with your api secret
            MediaManager.init(this, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
