package com.example.nemanja.upoznajgrad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button map=(Button) findViewById(R.id.button2);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMain=new Intent(LoginActivity.this.getApplicationContext(),MainActivity.class);
                startActivity(openMain);
            }
        });
    }
}
