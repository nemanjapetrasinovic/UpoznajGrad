package com.example.nemanja.upoznajgrad;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

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
        dref=FirebaseDatabase.getInstance().getReference("user/"+ user.getUid()+"/score");

        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Object oldPoints= dataSnapshot.getValue();
                String json=gson.toJson(oldPoints);
                Integer p=gson.fromJson(json,Integer.class);

                Integer points=p+Integer.parseInt(correct);

                dref.setValue(points);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
}
