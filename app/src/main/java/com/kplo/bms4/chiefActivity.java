package com.kplo.bms4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class chiefActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView chieftxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chief);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);
        chieftxt=findViewById(R.id.chief);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName=acct.getDisplayName();
            String personEmail=acct.getEmail();
            chieftxt.setText(personName);
        }


        Intent intent = getIntent();

        Button logout_button = (Button) findViewById(R.id.logoutbutton);

        logout_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signout();
            }

        });



        Button button = (Button) findViewById(R.id.board);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, chiefboard_activity.class);
                startActivity(intent);
            }

        });


        Button buttonstatement = (Button) findViewById(R.id.statement);

        buttonstatement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, statementActivity.class);
                startActivity(intent);
            }

        });

        Button buttonmodify = (Button) findViewById(R.id.chiefmodify);

        buttonmodify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chiefActivity.this, chiefjoinActivity.class);
                startActivity(intent);
            }

        });


    }

    void signout(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(chiefActivity.this,chiefloginActivity.class));
            }
        });

    }
}