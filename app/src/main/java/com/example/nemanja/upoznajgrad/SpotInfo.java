package com.example.nemanja.upoznajgrad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SpotInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_info);

        Button StartQuiz=(Button) findViewById(R.id.button3);
        StartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quiz=new Intent(SpotInfo.this.getApplicationContext(),QuizActivity.class);
                quiz.putExtra("spot","niska_tvrdjava");
                startActivity(quiz);
            }
        });
    }
}
