package com.example.nemanja.upoznajgrad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.nemanja.upoznajgrad.R.*;

public class QuizActivity extends AppCompatActivity {

    DatabaseReference dref;
    String ID;
    TextView question1,question2,question3,question4;
    ArrayList<Question> list=new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_quiz);

        Button submitAnswers = (Button) findViewById(id.button4);
        submitAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QuizResults =new Intent(QuizActivity.this.getApplicationContext(),QuizResultsActivity.class);
                startActivity(QuizResults);
            }
        });


       // dref= FirebaseDatabase.getInstance().getReference("question/" + ID);
        dref= FirebaseDatabase.getInstance().getReference("question").child("Medijana");
        final Gson gson=new Gson();
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Object o=postSnapshot.getValue();
                    String json=gson.toJson(o);
                    Question q = gson.fromJson(json,Question.class);
                    list.add(q);
                }
                question1=(TextView)findViewById(id.textView4);
                question1.setText(list.get(0).getTekst());
                question2=(TextView)findViewById(id.textView8);
             //   question2.setText(list.get(1).getTekst());
              //  question3=(TextView)findViewById(id.textView10);
              //  question3.setText(list.get(2).getTekst());
               // question4=(TextView)findViewById(id.textView12);
                //question4.setText(list.get(3).getTekst());
            }


                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        Log.e("The read failed: " ,firebaseError.getMessage());
                    }
                });


    }
}