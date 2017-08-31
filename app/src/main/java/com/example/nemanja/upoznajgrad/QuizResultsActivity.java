package com.example.nemanja.upoznajgrad;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class QuizResultsActivity extends AppCompatActivity {

    TextView tacni,netacni;
    DatabaseReference dref;
    FirebaseUser user;
    String correct,wrong;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        ID= getIntent().getStringExtra("id");
        correct= getIntent().getStringExtra("brojTacnih");
        wrong= String.valueOf(3-Integer.parseInt(correct));

        tacni=(TextView) findViewById(R.id.tacni);
        tacni.setText(correct);

        netacni=(TextView) findViewById(R.id.netacni);
        netacni.setText(wrong);

        Button gotovo=(Button) findViewById(R.id.button2);
        gotovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();

        final Gson gson=new Gson();
        dref=FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Korisnik k=dataSnapshot.getValue(Korisnik.class);
                k.setScore(k.getScore()+Integer.parseInt(correct));

                if(k.getPlaces().equals(""))
                    k.setPlaces(ID);
                else if(k.getPlaces().indexOf(ID)==-1)
                    k.setPlaces(k.getPlaces()+","+ID);

                dref.setValue(k);

                addAdvards(k);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
    void addAdvards(Korisnik k)
    {
        String s=k.getPlaces();
        Integer poeni=k.getScore();

        if(poeni/47.0>=1.5 && poeni/47.0<2.0)
            Toast.makeText(this, "Dobili ste bronzanu znacku", Toast.LENGTH_SHORT).show();
        else if(poeni/47.0>=2 && poeni/47.0<2.5)
            Toast.makeText(this, "Dobili ste serbrnu znacku", Toast.LENGTH_SHORT).show();
        else if(poeni/47>=2.5)
            Toast.makeText(this, "Dobili ste zlatnu znacku", Toast.LENGTH_SHORT).show();

        String brojMesta, preostalaMesta;

        String [] posecenaMesta=k.getPlaces().split(",");

        brojMesta=Integer.toString(posecenaMesta.length);
        preostalaMesta=Integer.toString(47-posecenaMesta.length);

        Toast.makeText(this, "Obisli ste "+brojMesta+" mesta, ostalo vam je jos "+preostalaMesta, Toast.LENGTH_SHORT).show();

    }
}
