package com.example.peeyush.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peeyush.myapplication.circular_arc.BallView;

public class circleImage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RelativeLayout relativeLayout = (RelativeLayout)(findViewById(R.id.circle_main));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupCirlce(relativeLayout);

    }

    void setupCirlce(RelativeLayout container){

        BallView ballView = (BallView)container.findViewById(R.id.bv_pi_arc);
        TextView textView = (TextView)container.findViewById(R.id.tv_pi_complete_text);

        ballView.setColor(Color.BLUE);
        ballView.setComplete(85);
        textView.setText("85%");
    }

}
