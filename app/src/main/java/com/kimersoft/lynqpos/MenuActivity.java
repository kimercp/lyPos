package com.kimersoft.lynqpos;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MenuActivity extends AppCompatActivity {

    LinearLayout card ,analytics, events;
    ImageView first, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        makeFullscreen();

        first= findViewById(R.id.first);
        second= findViewById(R.id.second);
        card=findViewById(R.id.card);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CaptureActivity.class);
                startActivity(i);
            }
        });

        analytics=findViewById(R.id.analytics);
        analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CardReader.class);
                i.putExtra("Source","menu");
                startActivity(i);
            }
        });

        events =  findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), TestActivity.class);
                startActivity(i);
            }
        });

    }

    // hide UI action bar and make the app fullscreen
    public void makeFullscreen() {
        getSupportActionBar().hide();
        // API 19 (Kit Kat)
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        } else {
            if (Build.VERSION.SDK_INT > 10) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }
}
