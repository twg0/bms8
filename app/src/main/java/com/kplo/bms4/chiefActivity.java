package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class chiefActivity extends AppCompatActivity {

    private TextView chief;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chief);

        chief=findViewById(R.id.chief);

        Intent intent = getIntent();

       /* String userID = intent.getStringExtra("userID");

        chief.setText(userID);*/

        Button button = (Button)findViewById(R.id.board);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, chiefboard_activity.class);
                startActivity(intent);
            }

        });


        Button broadcastbutton = (Button)findViewById(R.id. broadcast);

        broadcastbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, choose_txtsound_Activity.class);
                startActivity(intent);
            }

        });



        Button logout_button = (Button)findViewById(R.id.logoutbutton);

        logout_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
}