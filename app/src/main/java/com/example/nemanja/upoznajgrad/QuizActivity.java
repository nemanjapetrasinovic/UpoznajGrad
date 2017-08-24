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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.nemanja.upoznajgrad.R.*;

public class QuizActivity extends AppCompatActivity {

    DatabaseReference dref;
    String ID;
    TextView question1;
    final List<Question> list=new ArrayList<Question>();

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
        question1=(TextView)findViewById(R.id.textView4);
        dref= FirebaseDatabase.getInstance().getReference("question/Medijana");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Question q = postSnapshot.getValue(Question.class);
                    list.add(q);
                    q=null;
                }

                onListLoaded(list);

                Log.e("The read failed: " ,"");


            }


                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        Log.e("The read failed: " ,firebaseError.getMessage());
                    }
                });


    }

    public void onListLoaded(List<Question> list)
    {
        question1.setText(list.get(0).getTekst());

    }
}