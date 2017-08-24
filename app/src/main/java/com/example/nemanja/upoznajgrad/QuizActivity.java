package com.example.nemanja.upoznajgrad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
    final List<Question> list=new ArrayList<Question>();
    TextView question1,question2,question3,question4;
    RadioButton odg11,odg12,odg13,odg14;
    CheckBox odg31,odg32,odg33,odg34;
    String odgovor1,odgovor2,odgovor3,odgovor4;
    String [] niz;
    String [] niz1;
    String tacanOdg1,tacanOdg2,tacanOdg3,tacanOdg4;
    Integer brTacnih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_quiz);

        setQuestion();


        Button submitAnswers = (Button) findViewById(id.button4);
        submitAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                takeAnswers();

                brTacnih=0;

                if(odgovor1==tacanOdg1)
                    brTacnih++;
                if(odgovor2==tacanOdg2)
                    brTacnih++;
                if(odgovor4==odgovor4)
                    brTacnih++;

                String [] datiOdgovori3=odgovor3.split(",");
                String [] tacniOdgovori3=odgovor3.split(",");

                int pom=0;
                if(datiOdgovori3.length==tacniOdgovori3.length) {
                    for (int i = 0; i < datiOdgovori3.length; i++)
                        for(int j=0;j<tacniOdgovori3.length; j++)
                        if (datiOdgovori3[i]==tacniOdgovori3[j])
                            pom++;
                }
                if (pom==datiOdgovori3.length)
                    brTacnih++;


                Intent QuizResults =new Intent(QuizActivity.this.getApplicationContext(),QuizResultsActivity.class);
                QuizResults.putExtra("brojTacnih",brTacnih.toString());
                startActivity(QuizResults);


            }
        });

    }

    void takeAnswers()
    {
        TextView pom2=(TextView)findViewById(id.odgovor2);
        TextView pom4=(TextView)findViewById(id.odgovor4);

        if(odg11.isChecked())
            odgovor1=odg11.getText().toString();
        else if(odg12.isChecked())
            odgovor1=odg12.getText().toString();
        else if(odg13.isChecked())
            odgovor1=odg13.getText().toString();
        else if(odg14.isChecked())
            odgovor1=odg14.getText().toString();
        else
            odgovor1="";

        odgovor2 = pom2.getText().toString();
        odgovor4 = pom4.getText().toString();

        odgovor3="";

        if(odg31.isChecked())
            odgovor3=odgovor3+","+odg31.getText().toString();
        else if(odg32.isChecked())
            odgovor3=odgovor3+","+odg32.getText().toString();
        else if(odg33.isChecked())
            odgovor3=odgovor3+","+odg33.getText().toString();
        else if(odg34.isChecked())
            odgovor3=odgovor3+","+odg34.getText().toString();

        odgovor3 = odgovor3.substring(1, odgovor3.length()-1);

        String s="";

    }
    void setQuestion()
    {
        ID=getIntent().getStringExtra("spot");
        dref= FirebaseDatabase.getInstance().getReference("question/" + ID);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Question q = postSnapshot.getValue(Question.class);
                    list.add(q);


                }

                tacanOdg1=list.get(0).getTacniOdg();
                tacanOdg2=list.get(1).getTacniOdg();
                tacanOdg3=list.get(2).getTacniOdg();
                tacanOdg4=list.get(3).getTacniOdg();

                question1=(TextView) findViewById(id.pitanje1);
                question2=(TextView) findViewById(id.pitanje2);
                question3=(TextView) findViewById(id.pitanje3);
                question4=(TextView) findViewById(id.pitanje4);

                 odg11=(RadioButton) findViewById(id.odg11);
                 odg12=(RadioButton) findViewById(id.odg12);
                 odg13=(RadioButton) findViewById(id.odg13);
                 odg14=(RadioButton) findViewById(id.odg14);

                niz=list.get(0).getPonudjeniOdg().split(",");
                odg11.setText(niz[0]);
                odg12.setText(niz[1]);
                odg13.setText(niz[2]);
                odg14.setText(niz[3]);


                 odg31=(CheckBox) findViewById(id.odg31);
                 odg32=(CheckBox) findViewById(id.odg32);
                 odg33=(CheckBox) findViewById(id.odg33);
                 odg34=(CheckBox) findViewById(id.odg34);

                niz1=list.get(3).getPonudjeniOdg().split(",");
                odg31.setText(niz1[0]);
                odg32.setText(niz1[1]);
                odg33.setText(niz1[2]);
                odg34.setText(niz1[3]);


                question1.setText(list.get(0).getTekst());
                question2.setText(list.get(1).getTekst());
                question3.setText(list.get(2).getTekst());
                question4.setText(list.get(3).getTekst());


            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: " ,firebaseError.getMessage());
            }

        });


    };


}