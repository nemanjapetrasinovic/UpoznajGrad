package com.example.nemanja.upoznajgrad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuizResultsActivity extends AppCompatActivity {

    TextView tacni,netacni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        String correct = getIntent().getStringExtra("brojTacnih");
        String wrong= String.valueOf(4-Integer.parseInt(correct));

        tacni=(TextView) findViewById(R.id.tacni);
        tacni.setText(correct);

        netacni=(TextView) findViewById(R.id.netacni);
        netacni.setText(wrong);
    }
}
